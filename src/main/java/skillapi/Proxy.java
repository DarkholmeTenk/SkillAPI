package skillapi;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;

public abstract class Proxy implements IGuiHandler
{
	public abstract void init(FMLInitializationEvent event);
}
