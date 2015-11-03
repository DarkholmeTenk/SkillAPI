package skillapi.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.entity.EntityLivingBase;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.ISkillHandler;
import skillapi.api.internal.ISkillAPI;

public class SkillAPI implements ISkillAPI
{
	public static SkillAPI i;
	private HashMap<String,ISkill> skillMap = new HashMap<String,ISkill>();
	public ArrayList<ISkill> skillList = new ArrayList<ISkill>();
	public HashSet<ISkill> invSet = new HashSet();
	public HashSet<ISkill> ownSet = new HashSet();
	public HashSet<ISkill> allSet = new HashSet();

	{
		i = this;
	}

	@Override
	public ISkill registerSkill(ISkill skill)
	{
		String id = skill.getID();
		if(skillMap.containsKey(id))
			return skillMap.get(id);
		skillMap.put(id, skill);
		skillList.add(skill);
		switch(skill.getVisibility())
		{
			case ALWAYS: allSet.add(skill); break;
			case OWNED: ownSet.add(skill); break;
			case NEVER: invSet.add(skill); break;
		}
		return skill;
	}

	@Override
	public ISkillHandler getSkillHandler(EntityLivingBase ent)
	{
		return SkillAPIMod.skillHandlerFactory.getSkillHandler(ent);
	}

	@Override
	public ISkill getSkill(String id)
	{
		return skillMap.get(id);
	}

	@Override
	public Collection<ISkill> getSkills()
	{
		return skillMap.values();
	}

}
