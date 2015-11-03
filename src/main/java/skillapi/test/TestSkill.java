package skillapi.test;

import net.minecraft.util.ResourceLocation;
import skillapi.api.implement.ISkill;
import skillapi.api.implement.ISkillIcon;
import skillapi.api.internal.ISkillHandler;
import skillapi.api.internal.SkillVisibility;

public class TestSkill implements ISkill
{
	private final int id;
	private ISkillIcon icon;

	public TestSkill(int iid)
	{
		id = iid;
		icon = new ISkillIcon(){
				public ResourceLocation rl = new ResourceLocation("skillapi","textures/skills/test"+id+".png");

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
			};
	}

	@Override
	public String getID()
	{
		return "test" + id;
	}

	@Override
	public String getName()
	{
		return "TestSkillName - " + id;
	}

	@Override
	public String getDescription()
	{
		return "Test skill description word word word word word word word word word word word word word word word word word word word word";
	}

	@Override
	public ISkillIcon getIcon(ISkillHandler handler)
	{
		return icon;
	}

	@Override
	public SkillVisibility getVisibility()
	{
		switch(id % 3)
		{
			case 0: return SkillVisibility.OWNED;
			case 1: return SkillVisibility.ALWAYS;
			case 2: return SkillVisibility.NEVER;
		}
		return SkillVisibility.ALWAYS;
	}

	@Override
	public int getMinimumSkillLevel(ISkillHandler handler)
	{
		return 0;
	}

	@Override
	public int getMaximumSkillLevel(ISkillHandler handler)
	{
		return 999;
	}

	@Override
	public double getXPForNextLevel(int currentLevel, ISkillHandler handler)
	{
		return (currentLevel + 1) * 20;
	}

}
