package me.murrobby.igsq.spigot.security;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common_Spigot;

public class Common_Security 
{
	public static String[] illegalCommands = {"op","deop"};
	
    public static boolean FilterCommand(String command,CommandSender sender) 
    {
    	command = command.split(" ")[0];
    	command = Common_Shared.removeBeforeCharacter(command,'/');
    	command = Common_Shared.removeBeforeCharacter(command,'/');//If Player Uses Worldedit a // will be present in command
    	command = Common_Shared.removeBeforeCharacter(command,':');
		if (command != null && sender != null) for(String illegalCommand: illegalCommands)
		{
			if(command.equalsIgnoreCase(illegalCommand)) 
			{
				if(sender instanceof Player) sender.sendMessage(Common_Spigot.getFormattedMessage("illegalcommand", "<blocked>", illegalCommand));
				else System.out.println(Common_Spigot.getFormattedMessageConsole("illegalcommand", "<blocked>", illegalCommand));
				return false;
			}
		}
    	return true;
    }
    @Deprecated
    public static boolean SecurityProtection(Player player,String cancelName) //returning true means that twofa protection should be enabled false otherwise
    {
		String player2FA = Common_Spigot.getFieldString(player.getUniqueId().toString() + ".discord.2fa.status", "player");
		Boolean enableProtection = true;
		String code2FA = Common_Spigot.getFieldString(player.getUniqueId().toString() + ".discord.2fa.code", "player");
		if(player2FA == null) enableProtection = false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) enableProtection = false;
    	else if(player2FA.equalsIgnoreCase("")) enableProtection = false;
    	else if(player2FA.equalsIgnoreCase("accepted")) enableProtection = false;
    	if(enableProtection) 
    	{
    		player.sendMessage(Common_Spigot.chatFormatter("&#FF00002FA Security Enabled! &#ffb900Sorry, but until you login you cannot &#C8C8C8"+ cancelName + "!"));
    		return true;
    	}
    	else return false;
    }
    public static boolean SecurityProtectionQuery(Player player) //returning true means that 2fa protection should be enabled false otherwise
    {
		String player2FA = Common_Spigot.getFieldString(player.getUniqueId().toString() + ".discord.2fa.status", "player");
		String code2FA = Common_Spigot.getFieldString(player.getUniqueId().toString() + ".discord.2fa.code", "player");
		if(player2FA == null) return false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) return false;
    	else if(player2FA.equalsIgnoreCase("")) return false;
    	else if(player2FA.equalsIgnoreCase("accepted")) return false;
    	else return true;
    }
}
