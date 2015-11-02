package skillapi.impl.commands;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommandRegister
{
	public static void registerCommands(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new BaseCommand());
	}
}
