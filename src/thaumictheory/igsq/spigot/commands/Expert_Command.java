package thaumictheory.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.YamlWrapper;
import thaumictheory.igsq.spigot.expert.Main_Expert;

public class Expert_Command {

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	public Boolean result;
	public Expert_Command(CommandSender sender,List<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = expertQuery();
	}
	private Boolean expert() 
	{
		Boolean currentSetting = YamlWrapper.isExpert();
		if((args.size() == 0 || args.get(0).equalsIgnoreCase("true")) && !currentSetting) 
		{
			YamlWrapper.setExpert(true);
     		sender.sendMessage(Messaging.chatFormatter("&#84FF00Expert Mode &#00FF00Enabled&#84FF00!"));
			Main_Expert.startExpert();
			return true;
		}
		else if((args.size() == 0 || args.get(0).equalsIgnoreCase("false")) && currentSetting) 
		{
			YamlWrapper.setExpert(false);
     		sender.sendMessage(Messaging.chatFormatter("&#84FF00Expert Mode &#C8C8C8Disabled&#84FF00!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("false") || args.get(0).equalsIgnoreCase("true")) 
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
