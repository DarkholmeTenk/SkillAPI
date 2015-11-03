package skillapi.api.implement;

import net.minecraft.util.ResourceLocation;
import skillapi.api.internal.ISkillHandler;
import skillapi.api.internal.SkillVisibility;

/**
 * Implement this on a skill class and then call your ISkillAPI instance's registerSkill function with an instance of this class
 *
 * @author dark
 */
public interface ISkill
{
	/**
	 * @return a string to uniquely identify this class
	 */
	public String getID();

	/**
	 * @return the name for the client/server to display to the user
	 */
	public String getName();

	/**
	 * @return the description for the client/server to display to the user
	 */
	public String getDescription();

	/**
	 * @return a ResourceLocation identifying the texture to use for the skill
	 */
	public ResourceLocation getIcon();

	/**
	 * @return how visible the skill should be to the user
	 */
	public SkillVisibility getVisibility();

	/**
	 * @param handler contains a handler which lets you check other skills owned and respond accordingly
	 * @return the default/minimum level the skill should have
	 */
	public int getMinimumSkillLevel(ISkillHandler handler);

	/**
	 * @param handler contains a handler which lets you check other skills owned and respond accordingly
	 * @return the maximum level the skill can reach
	 */
	public int getMaximumSkillLevel(ISkillHandler handler);

	/**
	 * @param currentLevel the level which the entity currently has in this skill
	 * @param handler the handler representing the entity
	 * @return the amount of xp needed for the next level
	 */
	public double getXPForNextLevel(int currentLevel, ISkillHandler handler);
}
