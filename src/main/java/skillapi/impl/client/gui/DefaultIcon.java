package skillapi.impl.client.gui;

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
	public int u()
	{
		return 0;
	}

	@Override
	public int U()
	{
		return 1;
	}

	@Override
	public int v()
	{
		return 0;
	}

	@Override
	public int V()
	{
		return 1;
	}

}
