package skillapi.impl.commands;

import io.darkcraft.darkcore.mod.abstracts.AbstractCommandNew;

import java.util.List;

import net.minecraft.command.ICommandSender;

public class BaseCommand extends AbstractCommandNew
{
	public BaseCommand()
	{
		super(new AddXPCommand(), new GetPlayerInfoCommand());
	}

	@Override
	public String getCommandName()
	{
		return "skillapi";
	}

	@Override
	public void getAliases(List<String> list)
	{
		list.add("sapi");
	}

	@Override
	public boolean process(ICommandSender sen, List<String> strList)
	{
		return false;
	}

}
