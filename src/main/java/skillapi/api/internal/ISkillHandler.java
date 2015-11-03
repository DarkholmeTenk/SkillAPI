package skillapi.api.internal;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import skillapi.api.implement.ISkill;

/**
 * Get an instance of this by calling {@link skillapi.api.internal.ISkillAPI#getSkillHandler(EntityLivingBase ent) getSkillHandler}
 * on the instance of {@link skillapi.api.internal.ISkillAPI ISkillAPI} you obtained via IMC
 * @author dark
 *
 */
public interface ISkillHandler
{
	/**
	 * @return the entity which this skill handler represents
	 */
	public EntityLivingBase getEntity();

	/**
	 * Use this to get the level of a particular skill.
	 * Also performs checks to ensure skill level is between the min and max level of that skill
	 * @param skill the skill to get the level of
	 * @return the level of this skill which the entity has
	 */
	public int getLevel(ISkill skill);

	/**
	 * Sets the level of a skill that the entity has.
	 * Will be constrained between the skills minimum and maximum.
	 * @param skill the skill to change the level of.
	 * @param level the new level of the skill.
	 * @return the old level of the skill.
	 */
	public int setLevel(ISkill skill, int level);

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
	public double getXP(ISkill skill);

	/**
	 * @param skill the skill to get the amount of xp to level up
	 * @return the amount of xp the entity must have for a level up
	 */
	public double getXPForNextLevel(ISkill skill);

	/**
	 * Set the amount of xp that the entity has for the skill.
	 * If the amount of xp exceeds the xp for the next level, a level up will occur.
	 * @param skill the skill to set the amount of xp for
	 * @param xp the amount of xp that the entity should have.
	 * @return the amount of xp that the entity previously had.
	 */
	public double setXP(ISkill skill, double xp);

	/**
	 * @return a list containing only the skills which are visible to this player.
	 */
	public List<ISkill> getVisibleSkills();
}
