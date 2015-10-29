package skillapi.api.internal;

public enum SkillVisibility
{
	/**The skill is always visible in the menu*/
	ALWAYS,
	/**The skill is only visible when the player has at least 1 level*/
	OWNED,
	/**The skill is never visible in the menu*/
	NEVER;
}
