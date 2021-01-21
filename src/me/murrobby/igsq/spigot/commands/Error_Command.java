package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.ErrorLogging;
import me.murrobby.igsq.spigot.Messaging;

public class Error_Command {

	private CommandSender sender;
	private ArrayList<String> args = new ArrayList<>();
	public Boolean result;
	public Error_Command(CommandSender sender,ArrayList<String> args) 
	{
		
		this.sender = sender;
		this.args = args;
		result = ErrorQuery();
	}
	private Boolean Error() 
	{
		if(args.size() == 1) 
		{
			if(args.get(0).equalsIgnoreCase("test")) 
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
		else if (args.size() == 2) 
		{
			if(args.get(0).equalsIgnoreCase("log")) 
			{
				if(args.get(1).equalsIgnoreCase("basic")) 
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
						player.sendMessage(Messaging.chatFormatter("&#FF0000Error Logging &#00FF00Basic&#FF0000!"));
						yaml.setErrorLogSetting(ErrorLogging.BASIC);
					}
					else
					{
						sender.sendMessage(Messaging.chatFormatterConsole("&cError Logging &aBasic&c!"));
						YamlWrapper.setErrorLogSetting(ErrorLogging.BASIC);
					}
				}
				else if(args.get(1).equalsIgnoreCase("detailed"))
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
						player.sendMessage(Messaging.chatFormatter("&#FF0000Error Logging &#00FFFFDetailed&#FF0000!"));
						yaml.setErrorLogSetting(ErrorLogging.DETAILED);
					}
					else
					{
						sender.sendMessage(Messaging.chatFormatterConsole("&cError Logging &bDetailed&c!"));
						YamlWrapper.setErrorLogSetting(ErrorLogging.DETAILED);
					}
				}
				else
				{
					if(sender instanceof Player) 
					{
						Player player = (Player) sender;
						YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
						player.sendMessage(Messaging.chatFormatter("&#FF0000Error Logging &#C8C8C8Disabled&#FF0000!"));
						yaml.setErrorLogSetting(ErrorLogging.DISABLED);
					}
					else
					{
						sender.sendMessage(Messaging.chatFormatterConsole("&cError Logging &7Disabled&c!"));
						YamlWrapper.setErrorLogSetting(ErrorLogging.DISABLED);
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
