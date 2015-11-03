package skillapi.impl.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import skillapi.Proxy;
import skillapi.impl.SkillAPI;
import skillapi.impl.client.gui.SkillListGui;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends Proxy
{

	@Override
	public void init(FMLInitializationEvent event)
	{
		KeyEventHandler keh = new KeyEventHandler();
		FMLCommonHandler.instance().bus().register(keh);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if(id  == SkillListGui.ID)
			return new SkillListGui(SkillAPI.i.getSkillHandler(player));
		return null;
	}

}
