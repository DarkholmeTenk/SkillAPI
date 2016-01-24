package skillapi.api.implement;

import io.darkcraft.darkcore.mod.datastore.UVStore;
import net.minecraft.util.ResourceLocation;

/**
 * Allows you to provide information for an icon.
 * @author dark
 *
 */
public interface ISkillIcon
{
	public ResourceLocation getResourceLocation();

	public UVStore getUV();
}
