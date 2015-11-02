package skillapi.impl.commands;

import io.darkcraft.darkcore.mod.abstracts.AbstractCommandNew;
import io.darkcraft.darkcore.mod.helpers.MathHelper;
import io.darkcraft.darkcore.mod.helpers.ServerHelper;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.IEntitySkillHandler;

public class AddXPCommand extends AbstractCommandNew
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
	public boolean process(ICommandSender sen, List<String> strList)
	{
		EntityPlayer pl = null;
		if((strList.size() > 3) || (strList.size() < 2))
			return false;
		if(sen instanceof EntityPlayer)
			pl = (EntityPlayer) sen;
		else if(strList.size() == 3)
				pl = ServerHelper.getPlayer(strList.get(0));
		if(pl == null)
		{
			sendString(sen,"Invalid player");
			return false;
		}
		String skillID = strList.get(strList.size()-2);
		int xp = Math.abs(MathHelper.toInt(strList.get(strList.size()-1), 0));
		ISkill skill = SkillAPIMod.api.getSkill(skillID);
		if(skill == null)
		{
			sendString(sen, "Invalid skill");
			return false;
		}
		IEntitySkillHandler handler = SkillAPIMod.api.getSkillHandler(pl);
		handler.addXP(skill, xp);
		sendString(sen, xp + " xp added to " + skill.getName() + " for " + ServerHelper.getUsername(pl));
		return true;
	}

}
