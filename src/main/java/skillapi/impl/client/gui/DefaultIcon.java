package skillapi.impl.client.gui;

import io.darkcraft.darkcore.mod.datastore.UVStore;
import net.minecraft.util.ResourceLocation;
import skillapi.api.implement.ISkillIcon;

public class DefaultIcon implements ISkillIcon
{
	private static final ResourceLocation rl = new ResourceLocation("skillapi","textures/skills/test.png");

	@Override
	public ResourceLocation getResourceLocation()
	{
		return rl;
	}

	@Override
	public UVStore getUV()
	{
		return UVStore.defaultUV;
	}

}
