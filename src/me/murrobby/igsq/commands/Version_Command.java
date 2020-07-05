package me.murrobby.igsq.commands;

import org.bukkit.command.CommandSender;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main_Spigot;

public class Version_Command {

	private Main_Spigot plugin;
	private Executor_Command commands;
	private CommandSender sender;
	private String[] args = new String[0];
	public Boolean result;
	public Version_Command(Main_Spigot plugin,Executor_Command commands,CommandSender sender,String[] args) 
	{
		this.plugin = plugin;
		this.commands = commands;
		
		this.sender = sender;
		this.args = args;
		result = VersionQuery();
	}
	private Boolean Version() 
	{
		String version = plugin.getDescription().getVersion();
		String forBuild = plugin.getDescription().getAPIVersion();
		if(args.length == 0) 
		{
			sender.sendMessage(Common.ChatColour("&bIGSQ Version " + version + " for " + forBuild + "!"));
			return true;
		}
		if(args[0].equalsIgnoreCase("version")) 
		{
			sender.sendMessage(Common.ChatColour("&bIGSQ Version " + version + "!"));
			return true;
		}
		else if(args[0].equalsIgnoreCase("build"))
		{
			sender.sendMessage(Common.ChatColour("&bIGSQ for " + forBuild + "!"));
			return true;
		}
		return false;
		
	}
	private Boolean VersionQuery() 
	{
			if(commands.RequirePermission("igsq.version")) 
			{
				if(Version()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common.ChatColour("&1version [build/version]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
}
