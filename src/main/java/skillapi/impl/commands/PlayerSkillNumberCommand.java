package skillapi.impl.commands;

import io.darkcraft.darkcore.mod.abstracts.AbstractCommandNew;
import io.darkcraft.darkcore.mod.helpers.MathHelper;
import io.darkcraft.darkcore.mod.helpers.ServerHelper;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import skillapi.SkillAPIMod;
import skillapi.api.implement.ISkill;
import skillapi.api.internal.ISkillHandler;

public abstract class PlayerSkillNumberCommand extends AbstractCommandNew
{
	@Override
	public boolean process(ICommandSender sen, List<String> strList)
	{
		EntityPlayer pl = null;
		if((strList.size() > 3) || (strList.size() < 2))
		{
			sendString(sen, "Invalid number of arguments");
			return false;
		}
		if(sen instanceof EntityPlayer)
			pl = (EntityPlayer) sen;
		if(strList.size() == 3)
			pl = ServerHelper.getPlayer(strList.get(0));
		if(pl == null)
		{
			sendString(sen,"Invalid player");
			return false;
		}
		String skillID = strList.get(strList.size()-2);
		int num = MathHelper.toInt(strList.get(strList.size()-1), 0);
		ISkill skill = SkillAPIMod.api.getSkill(skillID);
		if(skill == null)
		{
			sendString(sen, "Invalid skill");
			return false;
		}
		ISkillHandler handler = SkillAPIMod.api.getSkillHandler(pl);
		return process(sen, handler, skill, num);
	}

	public abstract boolean process(ICommandSender sen, ISkillHandler pl, ISkill skill, int num);

}
