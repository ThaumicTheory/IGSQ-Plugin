package thaumictheory.igsq.bungee.security;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import thaumictheory.igsq.bungee.Database;
import thaumictheory.igsq.bungee.Messaging;
import thaumictheory.igsq.bungee.YamlPlayerWrapper;
import thaumictheory.igsq.bungee.YamlWrapper;
import thaumictheory.igsq.shared.Common_Shared;

public class Common_Security 
{
	public static String[] illegalCommands = {};
	private static Map<ProxiedPlayer,ArrayList<String>> modList = new HashMap<>();
	private static String[] whitelistedCommands2FA = {"2FA"};
	public static String[] protectedChannels = {"igsq:yml","igsq:sound"};
	private static String[] modWhitelist = {};
	
    public static boolean filterCommand(String command,CommandSender sender) 
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
    public static boolean isWhitelistedCommand2FA(String command,ProxiedPlayer player) 
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
    public static boolean securityProtectionQuery(ProxiedPlayer player) //returning true means that twofa protection should be enabled false otherwise
    {
    	YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
		String player2FA = yaml.getStatus();
		String code2FA = yaml.getCode();
		if(!yaml.is2fa()) return false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) return false;
    	else if(player2FA.equalsIgnoreCase("")) return false;
    	else if(player2FA.equalsIgnoreCase("accepted")) return false;
    	else return true;
    }
	public static ArrayList<String> getPlayerModList(ProxiedPlayer player)
	{
		return modList.get(player);
	}
	public static void setPlayerModList(ArrayList<String> modData,ProxiedPlayer player)
	{
		modList.put(player, modData);
		checkPlayerModList(player); //ModList Denied
	}
	public static void checkPlayerModList(ProxiedPlayer player)
	{
		modWhitelist = YamlWrapper.getModList().split(" ");
		ArrayList<String> modData = getPlayerModList(player);
		String disconnectMessage = "&cYour Client is running the following unsuported modifications:\n&4";
		Boolean denied = false;
		for(int i = 0;i < modData.size();i+=2) //Client mod crosscheck
		{
			if(!isModAllowed(player,modData.get(i))) 
			{
				denied = true;
				disconnectMessage += modData.get(i)+ " ";
				
			}
		}
		disconnectMessage += "\n&4";
		int j = 0;
		for(int i = 0;i < modData.size();i+=2) //version crosscheck
		{
			if(isModAllowed(player,modData.get(i)) && !isVersionMatched(player, modData.get(i))) 
			{
				denied = true;
				
				disconnectMessage += "&e"+ modData.get(i)+ " &4"+ getClientModVersion(player, modData.get(i)) + "&6 -->&a " + getServerModVersion(modData.get(i)) +" ";
				if(++j % 3 == 0) disconnectMessage += "\n";
			}
		}
		if(denied) player.disconnect(TextComponent.fromLegacyText(Messaging.chatFormatterConsole(disconnectMessage)));
	}
	public static String getClientModVersion(ProxiedPlayer player,String modName) 
	{
		ArrayList<String> modData = getPlayerModList(player);
		for(int i = 0;i < modData.size();i+=2) 
		{
			if(modData.get(i).equals(modName)) 
			{
				return modData.get(i+1);
			}
		}
		return "";
	}
	public static String getServerModVersion(String modName) 
	{
		for(int i = 0;i < modWhitelist.length;i++) 
		{
			if(modWhitelist[i].split("~")[0].equals(modName)) 
			{
				if(modWhitelist[i].split("~").length == 2) 
				{
					return modWhitelist[i].split("~")[1];
				}
				else return "";
			}
		}
		return "";
	}
	public static Boolean isVersionMatched(ProxiedPlayer player,String modName) 
	{
		return getServerModVersion(modName).equals(getClientModVersion(player,modName)) || getServerModVersion(modName).equals("");
	}
	public static Boolean isModAllowed(ProxiedPlayer player,String modName) 
	{
		for(int i = 0;i < modWhitelist.length;i++) 
		{
			if(modWhitelist[i].split("~")[0].equals(modName)) 
			{
				return true;
			}
		}
		return false;
	}
	public static void codeConfirm(ProxiedPlayer player,String message) 
	{
		ResultSet discord_2fa = Database.QueryCommand("SELECT current_status,code FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
		try 
		{
			if(discord_2fa.next()) 
			{
				String current_status = discord_2fa.getString(1);
				String code = discord_2fa.getString(2);
				String[] socket = player.getPendingConnection().getSocketAddress().toString().split(":");
				socket[0] = Common_Shared.removeBeforeCharacter(socket[0], '/');
				if(current_status != null && socket.length == 2 && current_status.equalsIgnoreCase("pending"))
				{
					if(code != null && message.equals(code)) 
					{
						Database.UpdateCommand("UPDATE discord_2fa SET current_status = 'accepted', ip = '"+ socket[0] +"',code = null WHERE uuid = '" +  player.getUniqueId().toString() +"';");
						player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF002FA Code matched! 2FA Security standing down!")));
					}
					else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000Code does not match try again!")));
				}
				else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#C8C8C8You dont need a 2FA code right now!")));

			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FF0000Something Went Wrong When Trying To Read Your 2FA code.")));
		}
	}
}
