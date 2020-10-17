package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;
import me.murrobby.igsq.spigot.Configuration;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.expert.Common_Expert;
import me.murrobby.igsq.spigot.expert.Main_Expert;

public class Expert_Command {

	private CommandSender sender;
	private String[] args = new String[0];
	public Boolean result;
	public Expert_Command(CommandSender sender,String[] args) 
	{
		this.sender = sender;
		this.args = args;
		result = expertQuery();
	}
	private Boolean expert() 
	{
		Boolean currentSetting = Common_Expert.expertCheck();
		if((args.length == 0 || args[0].equalsIgnoreCase("true")) && !currentSetting) 
		{
			Configuration.updateField("GAMEPLAY.expert", "config", true);
     		sender.sendMessage(Messaging.chatFormatter("&#84FF00Expert Mode &#00FF00Enabled&#84FF00!"));
			Main_Expert.Start_Expert();
			return true;
		}
		else if((args.length == 0 || args[0].equalsIgnoreCase("false")) && currentSetting) 
		{
			Configuration.updateField("GAMEPLAY.expert", "config", false);
     		sender.sendMessage(Messaging.chatFormatter("&#84FF00Expert Mode &#C8C8C8Disabled&#84FF00!"));
			return true;
		}
		else if(args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("true")) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#FFb900Expert is already " + (currentSetting ? "on" : "off") + "."));
			return true;
		}
		else return false;
		
	}
	private Boolean expertQuery() 
	{
			if(Common_Command.requirePermission("igsq.difficulty",sender)) 
			{
				if(expert()) 
				{
					return true;
				}
				else 
				{
	                sender.sendMessage(Messaging.chatFormatter("&#FFFF00expert [true/false]"));
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
