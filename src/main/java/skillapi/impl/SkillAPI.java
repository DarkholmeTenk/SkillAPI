package skillapi.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import net.minecraft.entity.EntityLivingBase;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.IEntitySkillHandler;
import skillapi.api.internal.ISkillAPI;

public class SkillAPI implements ISkillAPI
{
	private HashMap<String,ISkill> skillMap = new HashMap<String,ISkill>();
	public ArrayList<ISkill> skillList = new ArrayList<ISkill>();

	@Override
	public ISkill registerSkill(ISkill skill)
	{
		String id = skill.getID();
		if(skillMap.containsKey(id))
			return skillMap.get(id);
		skillMap.put(id, skill);
		skillList.add(skill);
		return skill;
	}

	@Override
	public IEntitySkillHandler getSkillHandler(EntityLivingBase ent)
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
