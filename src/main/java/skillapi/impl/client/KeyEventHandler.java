package skillapi.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import skillapi.SkillAPIMod;
import skillapi.impl.client.gui.SkillListGui;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyEventHandler
{
	public static KeyEventHandler i;

	KeyBinding keyBind;

	{
		i = this;
		keyBind = new KeyBinding("skillapi.key.open.desc", Keyboard.KEY_H, "skillapi.key.category");
		ClientRegistry.registerKeyBinding(keyBind);
	}

	private boolean handled = false;

	@SubscribeEvent
	public void keyEvent(KeyInputEvent event)
	{
		if(!FMLClientHandler.instance().getClient().inGameHasFocus || FMLClientHandler.instance().isGUIOpen(GuiChat.class))
			return;
		if(keyBind.isPressed() && !handled)
		{
			handled = true;
			//System.out.println("X");
			EntityPlayer pl = Minecraft.getMinecraft().thePlayer;
			Minecraft.getMinecraft().thePlayer.openGui(SkillAPIMod.i, SkillListGui.ID, pl.worldObj, (int) pl.posX, (int) pl.posY, (int) pl.posZ);
		}
		if(!keyBind.isPressed() && handled)
			handled = false;
	}
}
