package me.murrobby.igsq.bungee.security;



import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Common_Security 
{
	public static String[] illegalCommands = {};
	private static String[] whitelistedCommands2FA = {"2FA"};
	public static String[] protectedChannels = {"igsq:yml","igsq:sound"};
	
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
				if(sender instanceof ProxiedPlayer) sender.sendMessage(Common_Bungee.getFormattedMessage("illegalcommand", "<blocked>", illegalCommand));
				else System.out.println(Common_Bungee.getFormattedMessageConsole("illegalcommand", "<blocked>", illegalCommand));
				return false;
			}
		}
    	return true;
    }
    public static boolean IsWhitelistedCommand2FA(String command,ProxiedPlayer player) 
    {
    	command = command.split(" ")[0];
    	command = Common_Shared.removeBeforeCharacter(command,':');
		if (command != null && player != null) for(String whitelistedCommand: whitelistedCommands2FA)
		{

			if(command.equalsIgnoreCase("/" +whitelistedCommand)) 
			{
				return true;
			}
		}
    	return false;
    }
    @Deprecated
    public static boolean SecurityProtection(ProxiedPlayer player,String cancelName) //returning true means that twofa protection should be enabled false otherwise
    {
		String player2FA = Common_Bungee.getFieldString(player.getUniqueId().toString() + ".discord.2fa.status", "player");
		Boolean enableProtection = true;
		String code2FA = Common_Bungee.getFieldString(player.getUniqueId().toString() + ".discord.2fa.code", "player");
		if(player2FA == null) enableProtection = false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) enableProtection = false;
    	else if(player2FA.equalsIgnoreCase("")) enableProtection = false;
    	else if(player2FA.equalsIgnoreCase("accepted")) enableProtection = false;
    	if(enableProtection) 
    	{
    		player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.chatFormatter("&#FF00002FA Security Enabled! &#ffb900Sorry, but until you login you cannot &#C8C8C8"+ cancelName + "!")));
    		return true;
    	}
    	else return false;
    }
    public static boolean SecurityProtectionQuery(ProxiedPlayer player) //returning true means that twofa protection should be enabled false otherwise
    {
		String player2FA = Common_Bungee.getFieldString(player.getUniqueId().toString() + ".discord.2fa.status", "player");
		String code2FA = Common_Bungee.getFieldString(player.getUniqueId().toString() + ".discord.2fa.code", "player");
		if(player2FA == null) return false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) return false;
    	else if(player2FA.equalsIgnoreCase("")) return false;
    	else if(player2FA.equalsIgnoreCase("accepted")) return false;
    	else return true;
    }
}
