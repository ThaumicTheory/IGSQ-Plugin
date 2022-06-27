package thaumictheory.igsq.bungee.security;



import java.sql.ResultSet;
import java.sql.SQLException;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import thaumictheory.igsq.bungee.Database;
import thaumictheory.igsq.bungee.Messaging;
import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlPlayerWrapper;

public class Common_Security 
{
	public static String[] illegalCommands = {};
	private static String[] whitelistedCommands2FA = {"2FA"};
	public static String[] protectedChannels = {"igsq:yml","igsq:sound"};
	
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
    	command = IGSQ.removeBeforeCharacter(command,':');
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
    	YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
		String player2FA = yaml.getStatus();
		String code2FA = yaml.getCode();
		if(!yaml.is2fa()) return false;
		else if((code2FA == null || code2FA.equalsIgnoreCase("")) && player2FA.equalsIgnoreCase("pending")) return false;
    	else if(player2FA.equalsIgnoreCase("")) return false;
    	else if(player2FA.equalsIgnoreCase("accepted")) return false;
    	else return true;
    }
	public static void codeConfirm(ProxiedPlayer player,String message) 
	{
		ResultSet discord_2fa = Database.queryCommand("SELECT current_status,code FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
		try 
		{
			if(discord_2fa.next()) 
			{
				String current_status = discord_2fa.getString(1);
				String code = discord_2fa.getString(2);
				String[] socket = player.getPendingConnection().getSocketAddress().toString().split(":");
				socket[0] = IGSQ.removeBeforeCharacter(socket[0], '/');
				if(current_status != null && socket.length == 2 && current_status.equalsIgnoreCase("pending"))
				{
					if(code != null && message.equals(code)) 
					{
						Database.updateCommand("UPDATE discord_2fa SET current_status = 'accepted', ip = '"+ socket[0] +"',code = null WHERE uuid = '" +  player.getUniqueId().toString() +"';");
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
