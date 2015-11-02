package skillapi.impl.commands;

import io.darkcraft.darkcore.mod.abstracts.AbstractCommandNew;
import io.darkcraft.darkcore.mod.helpers.ServerHelper;

import java.util.Collection;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.IEntitySkillHandler;

public class GetPlayerInfoCommand extends AbstractCommandNew
{

	@Override
	public String getCommandName()
	{
		return "getinfo";
	}

	@Override
	public void getAliases(List<String> list)
	{
		list.add("getplayerinfo");
		list.add("getplinfo");
	}

	@Override
	public boolean process(ICommandSender sen, List<String> strList)
	{
		EntityPlayer pl = null;
		if(sen instanceof EntityPlayer)
			pl = (EntityPlayer) sen;
		else if(strList.size() > 0)
			pl = ServerHelper.getPlayer(strList.get(0));
		if(pl == null)
		{
			sendString(sen, "Unknown player");
			return false;
		}
		IEntitySkillHandler handler = SkillAPIMod.api.getSkillHandler(pl);
		Collection<ISkill> skills = SkillAPIMod.api.getSkills();
		sendString(sen, "Player: " + ServerHelper.getUsername(pl));
		for(ISkill skill : skills)
		{
			int level = handler.getSkillLevel(skill);
			double xp = handler.getCurrentXP(skill);
			double nxp = handler.getXPForNextLevel(skill);
			sendString(sen, "Skill: " + skill.getName() + " lvl: " + level + " xp: " + String.format("%.0f/%.0f",xp,nxp));
		}
		return true;
	}

}
