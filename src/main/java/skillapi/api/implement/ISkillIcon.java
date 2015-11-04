package skillapi.api.implement;

import net.minecraft.util.ResourceLocation;

/**
 * Allows you to provide information for an icon.
 * @author dark
 *
 */
public interface ISkillIcon
{
	public ResourceLocation getResourceLocation();

	public double u();
	public double U();
	public double v();
	public double V();
}
