package skillapi.api.internal;

import net.minecraft.entity.EntityLivingBase;
import skillapi.api.implement.ISkill;

/**
 * Get an instance of this by calling {@link skillapi.api.internal.ISkillAPI#getSkillHandler(EntityLivingBase ent) getSkillHandler}
 * on the instance of {@link skillapi.api.internal.ISkillAPI ISkillAPI} you obtained via IMC
 * @author dark
 *
 */
public interface IEntitySkillHandler
{
	/**
	 * @return the entity which this skill handler represents
	 */
	public EntityLivingBase getEntity();

	/**
	 * Use this to get the player level of a particular skill.
	 * Also performs checks to ensure skill level is between the min and max level of that skill
	 * @param skill the skill to get the level of
	 * @return the level of this skill which the entity has
	 */
	public int getSkillLevel(ISkill skill);

	/**
	 * @param skill the skill to add the xp to
	 * @param xp the amount of xp to add
	 * @return true if xp caused level up, false if not
	 */
	public boolean addXP(ISkill skill, double xp);

	/**
	 * @param skill the skill to get the current xp of
	 * @return the amount of xp that this entity has in this skill
	 */
	public double getCurrentXP(ISkill skill);

	/**
	 * @param skill the skill to get the amount of xp to level up
	 * @return the amount of xp the entity must have for a level up
	 */
	public double getXPForNextLevel(ISkill skill);
}
