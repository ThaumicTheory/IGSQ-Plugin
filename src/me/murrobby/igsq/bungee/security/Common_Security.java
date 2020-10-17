package me.murrobby.igsq.bungee.security;



import java.util.HashMap;
import java.util.Map;

import me.murrobby.igsq.bungee.Messaging;
import me.murrobby.igsq.bungee.Yaml;
import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Common_Security 
{
	public static String[] illegalCommands = {};
	private static Map<ProxiedPlayer,String[]> modList = new HashMap<>();
	private static String[] whitelistedCommands2FA = {"2FA"};
	public static String[] protectedChannels = {"igsq:yml","igsq:sound"};
	private static String[] modWhitelist = {"minecraft","FML","mcp","forge"};
	
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
				if(sender instanceof ProxiedPlayer) sender.sendMessage(Messaging.getFormattedMessage("illegalcommand", "<blocked>", illegalCommand));
				else System.out.println(Messaging.getFormattedMessageConsole("illegalcommand", "<blocked>", illegalCommand));
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
    public static boolean SecurityProtectionQuery(ProxiedPlayer player) //returning true means that twofa protection should be enabled false otherwise
    {
		String player2FA = Yaml.getFieldString(player.getUniqueId().toString() + ".discord.2fa.status", "player");
		String code2FA = Yaml.getFieldString(player.getUniqueId().toString() + ".discord.2fa.code", "player");
		if(player2FA == null) return false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) return false;
    	else if(player2FA.equalsIgnoreCase("")) return false;
    	else if(player2FA.equalsIgnoreCase("accepted")) return false;
    	else return true;
    }
	public static String[] getPlayerModList(ProxiedPlayer player)
	{
		return modList.get(player);
	}
	public static void setPlayerModList(String[] modData,ProxiedPlayer player)
	{
		modList.put(player, modData);
		checkPlayerModList(player); //ModList Denied
	}
	public static void checkPlayerModList(ProxiedPlayer player)
	{
		String[] modData = getPlayerModList(player);
		String[] deniedMods = {};
		for(int i = 0;i < modData.length;i+=2) 
		{
			Boolean denyMod = true;
			for(String whitelistedMod : modWhitelist) 
			{
				if(modData[i].equalsIgnoreCase(whitelistedMod)) 
				{
					denyMod = false;
					break;
				}
			}
			if(denyMod) deniedMods = Common_Shared.append(deniedMods, modData[i]);
		}
		if(deniedMods.length != 0) player.disconnect(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000Your Client is running the following unsuported modifications:\n" + Common_Shared.convertArgs(deniedMods,", "))+ "."));
	}
}
