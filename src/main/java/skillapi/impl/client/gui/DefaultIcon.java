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
	public double u()
	{
		return 0;
	}

	@Override
	public double U()
	{
		return 1;
	}

	@Override
	public double v()
	{
		return 0;
	}

	@Override
	public double V()
	{
		return 1;
	}

}
