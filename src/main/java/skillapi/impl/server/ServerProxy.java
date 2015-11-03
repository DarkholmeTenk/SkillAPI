package skillapi.impl.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import skillapi.Proxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ServerProxy extends Proxy
{

	@Override
	public void init(FMLInitializationEvent event)
	{
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

}
