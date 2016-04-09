package skillapi.impl.data;

import io.darkcraft.darkcore.mod.abstracts.AbstractEntityDataStore;
import io.darkcraft.darkcore.mod.datastore.UVStore;
import io.darkcraft.darkcore.mod.helpers.MessageHelper;
import io.darkcraft.darkcore.mod.helpers.ServerHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.implement.ISkillIcon;
import skillapi.api.internal.ISkillHandler;
import skillapi.api.internal.events.EntitySkillChangeEvent;
import skillapi.impl.SkillAPI;

public class SkillHandler extends AbstractEntityDataStore implements ISkillHandler
{
	public static final String disc = "sapi.skills";
	private HashMap<ISkill,Integer> skillLevels = new HashMap();
	private HashMap<ISkill,Double> skillXPs = new HashMap();

	public SkillHandler(EntityLivingBase ent)
	{
		super(ent, disc);
	}

	@Override
	public int getLevel(ISkill skill)
	{
		int level;
		int max = skill.getMaximumSkillLevel(this);
		int min = skill.getMinimumSkillLevel(this);
		if(skillLevels.containsKey(skill))
			level = skillLevels.get(skill);
		else
			level = min;

		if(level > max)
			skillLevels.put(skill, level = max);
		else if(level < min)
			skillLevels.put(skill, level = min);
		return level;
	}

	@Override
	public double getLevelPercent(ISkill skill)
	{
		if(skill.getMaximumSkillLevel(this) == skill.getMinimumSkillLevel(this)) return 1;
		return (getLevel(skill) - skill.getMinimumSkillLevel(this))/(double)(skill.getMaximumSkillLevel(this)-skill.getMinimumSkillLevel(this));
	}

	@Override
	public int setLevel(ISkill skill, int level)
	{
		int oldLevel = getLevel(skill);
		if(oldLevel == level) return oldLevel;
		int nl = setLevel(skill, level, skill.getMinimumSkillLevel(this), skill.getMaximumSkillLevel(this));
		MinecraftForge.EVENT_BUS.post(new EntitySkillChangeEvent(getEntity(), skill, oldLevel, nl));
		sendUpdate();
		return oldLevel;
	}

	private int setLevel(ISkill skill, int level, int min, int max)
	{
		if(level < min)
			level = min;
		if(level  > max)
			level = max;
		skillLevels.put(skill, level);
		return level;
	}

	@Override
	public boolean addXP(ISkill skill, double xp)
	{
		if(xp == 0)
			return false;
		int level = getLevel(skill);
		int max = skill.getMaximumSkillLevel(this);
		if(level == max)
		{
			skillXPs.remove(skill);
			return false;
		}

		double cXP = getXP(skill);
		cXP += (xp * SkillAPIMod.xpMult);

		return setXP(skill, level, cXP);
	}

	@Override
	public double getXP(ISkill skill)
	{
		if(skillXPs.containsKey(skill))
			return skillXPs.get(skill);
		return 0;
	}

	@Override
	public double setXP(ISkill skill, double xp)
	{
		int level = getLevel(skill);
		double old = skillXPs.containsKey(skill) ? skillXPs.get(skill) : 0;
		setXP(skill, level, xp);
		return old;
	}

	private boolean setXP(ISkill skill, int level, double xp)
	{
		EntityLivingBase ent = getEntity();
		int min;
		int max = skill.getMaximumSkillLevel(this);
		if(level >= max)
		{
			skillXPs.remove(skill);
			return false;
		}
		double xpToLevel = skill.getXPForNextLevel(level, this);
		boolean leveled = false;
		int ol = leve;;
		while(xp >= xpToLevel)
		{
			leveled = true;
			min = skill.getMinimumSkillLevel(this);
			max = skill.getMaximumSkillLevel(this);
			if(level >= max)
			{
				skillXPs.remove(skill);
				return true;
			}
			setLevel(skill, ++level, min, max);
			xp -= xpToLevel;
			xpToLevel = skill.getXPForNextLevel(level, this);
		}
		if(leveled && ServerHelper.isServer() && (ent instanceof EntityPlayerMP))
		{
			ISkillIcon icon = skill.getIcon(this);
			UVStore uv = icon.getUV();
			MinecraftForge.EVENT_BUS.post(new EntitySkillChangeEvent(getEntity(), skill, ol, level));
			MessageHelper.sendMessage((EntityPlayerMP)ent, icon.getResourceLocation(), uv, skill.getName() + " levelled up to " + getLevel(skill), MessageHelper.defaultSeconds);
		}
		if(xp == 0)
			skillXPs.remove(skill);
		else
			skillXPs.put(skill, xp);
		sendUpdate();
		return leveled;
	}

	@Override
	public double getXPForNextLevel(ISkill skill)
	{
		return skill.getXPForNextLevel(getLevel(skill), this);
	}

	@Override
	public List<ISkill> getVisibleSkills()
	{
		List<ISkill> skillList = new ArrayList<ISkill>(SkillAPI.i.allSet);
		for(ISkill skill : SkillAPI.i.ownSet)
			if(getLevel(skill) > skill.getMinimumSkillLevel(this))
				skillList.add(skill);
		return skillList;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){}

	@Override
	public void readFromNBT(NBTTagCompound nbt){}

	@Override
	public void writeTransmittable(NBTTagCompound nbt)
	{
		if((skillLevels.size() == 0) && (skillXPs.size() == 0))
			return;
		NBTTagCompound subTag = new NBTTagCompound();
		for(ISkill skill : skillLevels.keySet())
			subTag.setInteger("lvl" + skill.getID(), skillLevels.get(skill));
		for(ISkill skill : skillXPs.keySet())
			subTag.setDouble("xp" + skill.getID(), skillXPs.get(skill));
		nbt.setTag(disc, subTag);
	}

	@Override
	public void readTransmittable(NBTTagCompound nbt)
	{
		if(nbt.hasKey(disc))
		{
			NBTTagCompound subTag = nbt.getCompoundTag(disc);
			skillLevels.clear();
			skillXPs.clear();
			Collection<ISkill> skills = SkillAPIMod.api.getSkills();
			for(ISkill skill : skills)
			{
				String id = skill.getID();
				if(subTag.hasKey("xp" + id))
					skillXPs.put(skill, subTag.getDouble("xp"+id));
				if(subTag.hasKey("lvl" + id))
					skillLevels.put(skill, subTag.getInteger("lvl" + id));
			}
		}
	}

	@Override
	public boolean notifyArea()
	{
		return false;
	}

	@Override
	public void init(Entity entity, World world){}
}
