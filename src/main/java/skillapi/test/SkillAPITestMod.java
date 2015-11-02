package skillapi.test;

import net.minecraft.util.ResourceLocation;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.IEntitySkillHandler;
import skillapi.api.internal.ISkillAPI;
import skillapi.api.internal.SkillVisibility;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "SkillAPITest", name = "Skill API TEST", version = "0.01", dependencies = "required-after:FML; required-after:darkcore@[0.3,]; required-after:SkillAPI")
public class SkillAPITestMod
{
	public static ISkillAPI api;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLInterModComms.sendMessage("SkillAPI", "register", "skillapi.test.SkillAPITestMod.requestAPI");
	}

	public static void requestAPI(ISkillAPI apiR)
	{
		api = apiR;
		if(SkillAPIMod.testModInit)
		{
			api.registerSkill(skill);
		}
	}

	private static ISkill skill = new ISkill(){

		@Override
		public String getID()
		{
			return "test";
		}

		@Override
		public String getName()
		{
			return "TestSkill";
		}

		@Override
		public String getDescription()
		{
			return "Test skill description";
		}

		@Override
		public ResourceLocation getIcon()
		{
			return null;
		}

		@Override
		public SkillVisibility getVisibility()
		{
			return SkillVisibility.ALWAYS;
		}

		@Override
		public int getMinimumSkillLevel(IEntitySkillHandler handler)
		{
			return 0;
		}

		@Override
		public int getMaximumSkillLevel(IEntitySkillHandler handler)
		{
			return 999;
		}

		@Override
		public double getXPForNextLevel(int currentLevel, IEntitySkillHandler handler)
		{
			return (currentLevel + 1) * 20;
		}

	};
}
