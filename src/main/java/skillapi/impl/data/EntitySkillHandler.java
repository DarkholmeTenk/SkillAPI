package skillapi.impl.data;

import java.util.Collection;
import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.IEntitySkillHandler;

public class EntitySkillHandler implements IEntitySkillHandler, IExtendedEntityProperties
{
	private EntityLivingBase entity;
	private HashMap<ISkill,Integer> skillLevels = new HashMap();
	private HashMap<ISkill,Double> skillXPs = new HashMap();

	public EntitySkillHandler(EntityLivingBase ent)
	{
		entity = ent;
	}

	@Override
	public EntityLivingBase getEntity()
	{
		return entity;
	}

	@Override
	public int getSkillLevel(ISkill skill)
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

	private void setLevel(ISkill skill, int level, int min, int max)
	{
		if(level < min)
			level = min;
		if(level  > max)
			level = max;
		skillLevels.put(skill, level);
	}

	@Override
	public boolean addXP(ISkill skill, double xp)
	{
		if(xp == 0)
			return false;
		int level = getSkillLevel(skill);
		int min = skill.getMinimumSkillLevel(this);
		int max = skill.getMaximumSkillLevel(this);
		if(level == max)
		{
			skillXPs.remove(skill);
			return false;
		}

		double cXP = getCurrentXP(skill);
		cXP += (xp * SkillAPIMod.xpMult);

		double xpToLevel = skill.getXPForNextLevel(level, this);
		boolean leveled = false;
		while(cXP >= xpToLevel)
		{
			leveled = true;
			min = skill.getMinimumSkillLevel(this);
			max = skill.getMaximumSkillLevel(this);
			setLevel(skill, ++level, min, max);
			cXP -= xpToLevel;
			xpToLevel = skill.getXPForNextLevel(level, this);
		}
		if(cXP == 0)
			skillXPs.remove(skill);
		else
			skillXPs.put(skill, cXP);
		return leveled;
	}

	@Override
	public double getCurrentXP(ISkill skill)
	{
		if(skillXPs.containsKey(skill))
			return skillXPs.get(skill);
		return 0;
	}

	@Override
	public double getXPForNextLevel(ISkill skill)
	{
		return skill.getXPForNextLevel(getSkillLevel(skill), this);
	}

	private static final String nbtIdent = "SkillAPITag";

	@Override
	public void saveNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound subTag = new NBTTagCompound();
		for(ISkill skill : skillLevels.keySet())
			subTag.setInteger("lvl" + skill.getID(), skillLevels.get(skill));
		for(ISkill skill : skillXPs.keySet())
			subTag.setDouble("xp" + skill.getID(), skillXPs.get(skill));
		nbt.setTag(nbtIdent, subTag);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt)
	{
		if(nbt.hasKey(nbtIdent))
		{
			NBTTagCompound subTag = nbt.getCompoundTag(nbtIdent);
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
	public void init(Entity entity, World world)
	{
		if(entity instanceof EntityLivingBase)
			this.entity = (EntityLivingBase) entity;
	}

}
