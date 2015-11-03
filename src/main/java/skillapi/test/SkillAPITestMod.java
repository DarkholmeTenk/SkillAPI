package skillapi.test;

import skillapi.SkillAPIMod;
import skillapi.api.internal.ISkillAPI;
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
			for(int i = 0; i < 6; i++)
				api.registerSkill(new TestSkill(i));
		}
	}
}
