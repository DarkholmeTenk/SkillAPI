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
	 * Call this to register a skill in the mod. Must be done before post-init.
	 * I recommend doing this in your callback function when you get given access to an ISkillAPI
	 * @param skill the skill to register
	 * @return a skill which is either the skill you are registering or the skill registered with the same id in case of a clash
	 */
	public ISkill registerSkill(ISkill skill);

	/**
	 * Use this method to get a skill handler for a living entity which you can use to check skill levels and give experience, etc.
	 * @param ent the entity to get the skill handler for
	 * @return a skill handler. Returns null iff ent is null.
	 */
	public IEntitySkillHandler getSkillHandler(EntityLivingBase ent);

	/**
	 * @param id the id of the skill to get
	 * @return the skill associated with id or null if none exists
	 */
	public ISkill getSkill(String id);

	/**
	 * @return a collection containing all of the registered skills.
	 */
	public Collection<ISkill> getSkills();
}
