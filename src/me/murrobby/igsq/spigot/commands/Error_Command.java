package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Configuration;
import me.murrobby.igsq.spigot.Messaging;

public class Error_Command {

	private CommandSender sender;
	private String[] args = new String[0];
	public Boolean result;
	public Error_Command(CommandSender sender,String[] args) 
	{
		
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
		    		Messaging.sendException(e,"Manually initiated error","DIRT",sender);
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
						player.sendMessage(Messaging.chatFormatter("&#FF0000Error Logging &#00FF00Enabled&#FF0000!"));
						Configuration.updateField(player.getUniqueId().toString() + ".errorlog", "player", "enabled");
					}
					else
					{
						sender.sendMessage(Messaging.chatFormatterConsole("&cError Logging &aEnabled&c!"));
						Configuration.updateField("console.errorlog", "internal", "enabled");
					}
				}
				else if(args[1].equalsIgnoreCase("verbose"))
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						player.sendMessage(Messaging.chatFormatter("&#FF0000Error Logging &#00FFFFEnabled & Verbose&#FF0000!"));
						Configuration.updateField(player.getUniqueId().toString() + ".errorlog", "player", "verbose");
					}
					else
					{
						sender.sendMessage(Messaging.chatFormatterConsole("&cError Logging &bEnabled & Verbose&c!"));
						Configuration.updateField("console.errorlog", "internal", "verbose");
					}
				}
				else
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						player.sendMessage(Messaging.chatFormatter("&#FF0000Error Logging &#C8C8C8Disabled&#FF0000!"));
						Configuration.updateField(player.getUniqueId().toString() + ".errorlog", "player", "");
					}
					else
					{
						sender.sendMessage(Messaging.chatFormatterConsole("&cError Logging &7Disabled&c!"));
						Configuration.updateField("console.errorlog", "internal", "");
					}
				}
				return true;
			}
		}
		return false;
	}
	private Boolean ErrorQuery() 
	{
			if(Common_Command.requirePermission("igsq.error",sender)) 
			{
				if(Error()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FFFF00error [test/log]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
}
