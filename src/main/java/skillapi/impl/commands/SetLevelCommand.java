package skillapi.impl.commands;

import io.darkcraft.darkcore.mod.helpers.ServerHelper;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.ISkillHandler;

public class SetLevelCommand extends PlayerSkillNumberCommand
{

	@Override
	public String getCommandName()
	{
		return "setlevel";
	}

	@Override
	public void getAliases(List<String> list)
	{
		list.add("sl");
	}

	@Override
	public boolean process(ICommandSender sen, ISkillHandler pl, ISkill skill, int num)
	{
		Entity ent = pl.getEntity();
		pl.setLevel(skill, num);
		if(ent instanceof EntityPlayer)
			sendString(sen, "Level of " + skill.getName() + " set to " + num + " for " + ServerHelper.getUsername((EntityPlayer) ent));
		return true;
	}

}
