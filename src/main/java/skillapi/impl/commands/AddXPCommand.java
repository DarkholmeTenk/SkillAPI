package skillapi.impl.commands;

import io.darkcraft.darkcore.mod.helpers.ServerHelper;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.ISkillHandler;

public class AddXPCommand extends PlayerSkillNumberCommand
{

	@Override
	public String getCommandName()
	{
		return "addxp";
	}

	@Override
	public void getAliases(List<String> list)
	{
	}

	@Override
	public boolean process(ICommandSender sen, ISkillHandler handler, ISkill skill, int num)
	{
		Entity ent = handler.getEntity();
		if(ent instanceof EntityPlayer)
		{
			handler.addXP(skill, num);
			sendString(sen, num + " xp added to " + skill.getName() + " for " + ServerHelper.getUsername((EntityPlayer) ent));
		}
		return true;
	}

}
