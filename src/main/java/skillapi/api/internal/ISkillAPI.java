package skillapi.api.internal;

import java.util.Collection;

import net.minecraft.entity.EntityLivingBase;
import skillapi.api.implement.ISkill;


/**
 * You can receive an ISkillAPI by sending a IMC to "SkillAPI" with the key "register"
 * and a string message which corresponds to a class (including package) and static method which
 * takes a single parameter of type ISkillAPI.
 * E.g. FMLInterModComms.sendMessage("SkillAPI","register","com.packageName.MyClass.getAPI");
 * for
 * package com.packageName
 * public class MyClass
 * {
 * 	public static getAPI(ISkillAPI param){}
 * }
 * @author dark
 *
 */
public interface ISkillAPI
{
	/**
	 *
	 * @param skill
	 * @return
	 */
	public ISkill registerSkill(ISkill skill);

	public IEntitySkillHandler getSkillHandler(EntityLivingBase ent);

	public ISkill getSkill(String id);

	public Collection<ISkill> getSkills();
}
