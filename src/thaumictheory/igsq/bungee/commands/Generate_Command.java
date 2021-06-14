package thaumictheory.igsq.bungee.commands;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import thaumictheory.igsq.bungee.Messaging;
import thaumictheory.igsq.bungee.security.Common_Security;

public class Generate_Command  extends Command implements TabExecutor
{
	public Generate_Command() 
	{
		super("generate");
	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args)
	{
		List<String> options = new ArrayList<String>();
		return options;
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		
		if(sender instanceof ProxiedPlayer && args.length == 0) 
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if(player.hasPermission("igsq.generate")) 
			{
				ArrayList<String> modList = Common_Security.getPlayerModList(player);
				boolean isMod = true;
				String message = "";
				int modCount = 0;
				for(String modData : modList) 
				{
					if(isMod) 
					{
						if(modCount++ != 0) message += " ";
						message += modData;
					}
					else 
					{
						message += "~" + modData;
					}
					isMod = !isMod;
				}
				System.out.println(message);
				
			}
			else sender.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission")));
		}
		else sender.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FF0000This is an invalid arg length!")));
		
	}
}
