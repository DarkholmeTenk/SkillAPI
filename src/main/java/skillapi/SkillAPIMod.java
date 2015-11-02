package skillapi;

import io.darkcraft.darkcore.mod.config.ConfigFile;
import io.darkcraft.darkcore.mod.config.ConfigHandler;
import io.darkcraft.darkcore.mod.config.ConfigHandlerFactory;
import io.darkcraft.darkcore.mod.interfaces.IConfigHandlerMod;

import java.lang.reflect.Method;
import java.util.List;

import skillapi.api.internal.ISkillAPI;
import skillapi.impl.SkillAPI;
import skillapi.impl.SkillEventHandler;
import skillapi.impl.data.SkillHandlerFactory;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "SkillAPI", name = "Skill API", version = "0.01", dependencies = "required-after:FML; required-after:darkcore@[0.3,];")
public class SkillAPIMod implements IConfigHandlerMod
{
	public static SkillAPIMod			i;
	public static ConfigHandler			configHandler;
	private static ConfigFile			mainConfig;
	public static ISkillAPI				api;
	private static SkillEventHandler	eventHandler;
	public static SkillHandlerFactory	skillHandlerFactory;
	public static SkillHandlerFactory	skillHandlerFactory = new SkillHandlerFactory();

	public static double				xpMult = 1.0;

	{
		i = this;
		api = new SkillAPI();
	}

	@Override
	public String getModID()
	{
		return "SkillAPI";
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		configHandler = ConfigHandlerFactory.getConfigHandler(this);
		mainConfig = configHandler.registerConfigNeeder("Main");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		handleAPIStuff();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		xpMult = mainConfig.getDouble("xp mult", 1.0, "Multiplier applied to all xp gained");
		MinecraftForge.EVENT_BUS.register(skillHandlerFactory);
	}

	private void sendAPI(Class c, String methodName)
	{
		try
		{
			Method m = c.getMethod(methodName, ISkillAPI.class);
			m.invoke(null, api);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void handleAPIStuff()
	{
		List<IMCMessage> messages = FMLInterModComms.fetchRuntimeMessages(this);
		for (IMCMessage message : messages)
		{
			Object key = message.key;
			if ("register".equals(key))
			{
				String value = message.getStringValue();
				String[] split = value.split("\\.");
				String methodName = split[split.length - 1];
				String className = value.replace("." + methodName, "");
				try
				{
					Class c = Class.forName(className);
					sendAPI(c, methodName);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
