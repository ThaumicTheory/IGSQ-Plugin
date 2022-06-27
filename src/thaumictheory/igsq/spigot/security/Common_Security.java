package thaumictheory.igsq.spigot.security;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.Messaging;

public class Common_Security 
{
	public static String[] illegalCommands = {"op","deop"};
	
    public static boolean filterCommand(String command,CommandSender sender) 
    {
    	command = command.split(" ")[0];
    	command = IGSQ.removeBeforeCharacter(command,'/');
    	command = IGSQ.removeBeforeCharacter(command,'/');//If Player Uses Worldedit a // will be present in command
    	command = IGSQ.removeBeforeCharacter(command,':');
		if (command != null && sender != null) for(String illegalCommand: illegalCommands)
		{
			if(command.equalsIgnoreCase(illegalCommand)) 
			{
				if(sender instanceof Player) sender.sendMessage(Messaging.getFormattedMessage("illegalcommand", "<blocked>", illegalCommand));
				else System.out.println(Messaging.getFormattedMessageConsole("illegalcommand", "<blocked>", illegalCommand));
				return false;
			}
		}
    	return true;
    }
    public static boolean isLocked(Player player) //returning true means that 2fa protection should be enabled false otherwise
    {
    	YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
		String player2FA = yaml.getStatus();
		String code2FA = yaml.getCode();
		if(!yaml.is2fa()) return false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) return false;
    	else if(player2FA.equalsIgnoreCase("")) return false;
    	else if(player2FA.equalsIgnoreCase("accepted")) return false;
    	else return true;
    }
}
