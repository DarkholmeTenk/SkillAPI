package skillapi;

import io.darkcraft.darkcore.mod.config.ConfigFile;
import io.darkcraft.darkcore.mod.config.ConfigHandler;
import io.darkcraft.darkcore.mod.config.ConfigHandlerFactory;
import io.darkcraft.darkcore.mod.interfaces.IConfigHandlerMod;

import java.lang.reflect.Method;
import java.util.List;

import net.minecraftforge.common.MinecraftForge;
import skillapi.api.internal.ISkillAPI;
import skillapi.impl.SkillAPI;
import skillapi.impl.SkillAPIPacketHandler;
import skillapi.impl.commands.CommandRegister;
import skillapi.impl.data.SkillHandlerFactory;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "SkillAPI", name = "Skill API", version = "0.01", dependencies = "required-after:FML; required-after:darkcore@[0.3,];")
public class SkillAPIMod implements IConfigHandlerMod
{
	public static SkillAPIMod			i;
	public static ConfigHandler			configHandler;
	private static ConfigFile			mainConfig;
	public static ISkillAPI				api;
	public static SkillHandlerFactory	skillHandlerFactory	= new SkillHandlerFactory();
	@SidedProxy(clientSide = "skillapi.impl.client.ClientProxy", serverSide = "skillapi.impl.server.ServerProxy")
	public static Proxy					proxy;
	public static boolean				testModInit			= true;

	public static double				xpMult				= 1.0;

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
		refreshConfigs();
	}

	public static void refreshConfigs()
	{
		xpMult = mainConfig.getDouble("xp multiplier", 1.0, "Allows you to increase or decrease the rate of xp gain for all skills");
		testModInit = mainConfig.getBoolean("test skills", false, "If true, 6 test skills will be added to the game");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		handleAPIStuff();
		proxy.init(event);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		SkillAPIPacketHandler.register();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(skillHandlerFactory);
	}

	@EventHandler
	public void serverStartEvent(FMLServerStartingEvent event)
	{
		CommandRegister.registerCommands(event);
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
