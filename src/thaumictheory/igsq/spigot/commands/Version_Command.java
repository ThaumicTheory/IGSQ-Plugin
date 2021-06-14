package thaumictheory.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;

public class Version_Command {

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	public Boolean result;
	public Version_Command(CommandSender sender,List<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = VersionQuery();
	}
	private Boolean Version() 
	{
		String version = Common.spigot.getDescription().getVersion();
		String forBuild = Common.spigot.getDescription().getAPIVersion();
		String description = Common.spigot.getDescription().getDescription();
		if(args.size() == 0) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#00FFFFIGSQ Version " + version + " for " + forBuild + "!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("version")) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#00FFFFIGSQ Version " + version + "!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("build"))
		{
			sender.sendMessage(Messaging.chatFormatter("&#00FFFFIGSQ for " + forBuild + "!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("description"))
		{
			sender.sendMessage(Messaging.chatFormatter("&#00FFFF" + description + "!"));
			return true;
		}
		
	
		return false;
		
	}
	private Boolean VersionQuery() 
	{
			if(Common_Command.requirePermission("igsq.version",sender)) 
			{
				if(Version()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FFFF00version [build/version/description]"));
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
