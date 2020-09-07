package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class Error_Command {

	private Main_Command commands;
	private CommandSender sender;
	private String[] args = new String[0];
	public Boolean result;
	public Error_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		this.commands = commands;
		
		this.sender = sender;
		this.args = args;
		result = ErrorQuery();
	}
	private Boolean Error() 
	{
		if(args.length == 1) 
		{
			if(args[0].equalsIgnoreCase("test")) 
			{
		    	try 
		    	{
		    		throw null;
		    	}
		    	catch(Exception e) 
		    	{
		    		Common_Spigot.sendException(e,"Manually initiated error","DIRT",sender);
		    		return true;
		    	}
			}
		}
		else if (args.length == 2) 
		{
			if(args[0].equalsIgnoreCase("log")) 
			{
				if(args[1].equalsIgnoreCase("enabled")) 
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						player.sendMessage(Common_Spigot.chatFormatter("&#FF0000Error Logging &#00FF00Enabled&#FF0000!"));
						Common_Spigot.updateField(player.getUniqueId().toString() + ".errorlog", "player", "enabled");
					}
					else
					{
						sender.sendMessage(Common_Spigot.chatFormatterConsole("&cError Logging &aEnabled&c!"));
						Common_Spigot.updateField("console.errorlog", "internal", "enabled");
					}
				}
				else if(args[1].equalsIgnoreCase("verbose"))
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						player.sendMessage(Common_Spigot.chatFormatter("&#FF0000Error Logging &#00FFFFEnabled & Verbose&#FF0000!"));
						Common_Spigot.updateField(player.getUniqueId().toString() + ".errorlog", "player", "verbose");
					}
					else
					{
						sender.sendMessage(Common_Spigot.chatFormatterConsole("&cError Logging &bEnabled & Verbose&c!"));
						Common_Spigot.updateField("console.errorlog", "internal", "verbose");
					}
				}
				else
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						player.sendMessage(Common_Spigot.chatFormatter("&#FF0000Error Logging &#C8C8C8Disabled&#FF0000!"));
						Common_Spigot.updateField(player.getUniqueId().toString() + ".errorlog", "player", "");
					}
					else
					{
						sender.sendMessage(Common_Spigot.chatFormatterConsole("&cError Logging &7Disabled&c!"));
						Common_Spigot.updateField("console.errorlog", "internal", "");
					}
				}
				return true;
			}
		}
		return false;
	}
	private Boolean ErrorQuery() 
	{
			if(commands.RequirePermission("igsq.error")) 
			{
				if(Error()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common_Spigot.chatFormatter("&#FFFF00error [test/log]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Common_Spigot.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
}
