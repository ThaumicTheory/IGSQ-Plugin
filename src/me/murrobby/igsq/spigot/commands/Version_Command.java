package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class Version_Command {

	private Main_Spigot plugin;
	private Main_Command commands;
	private CommandSender sender;
	private String[] args = new String[0];
	public Boolean result;
	public Version_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
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
			sender.sendMessage(Common_Spigot.ChatColour("&bIGSQ Version " + version + " for " + forBuild + "!"));
			return true;
		}
		if(args[0].equalsIgnoreCase("version")) 
		{
			sender.sendMessage(Common_Spigot.ChatColour("&bIGSQ Version " + version + "!"));
			return true;
		}
		else if(args[0].equalsIgnoreCase("build"))
		{
			sender.sendMessage(Common_Spigot.ChatColour("&bIGSQ for " + forBuild + "!"));
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
					sender.sendMessage(Common_Spigot.ChatColour("&1version [build/version]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Common_Spigot.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
}
