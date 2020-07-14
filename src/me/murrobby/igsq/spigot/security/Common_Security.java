package me.murrobby.igsq.spigot.security;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common_Spigot;

public class Common_Security 
{
	public static String[] illegalCommands = {"MINECRAFT:OP","OP","MINECRAFT:DEOP","DEOP"};
	
    public static boolean FilterCommand(String command,CommandSender sender) 
    {
		for(String illegalCommand: illegalCommands)
		{
			if(sender instanceof Player) 
			{
				if(command.toUpperCase().startsWith("/" +illegalCommand)) 
				{
					sender.sendMessage(Common_Spigot.GetMessage("illegalcommand", "<blocked>", illegalCommand));
					return false;
				}
			}
			else 
			{
				if(command.toUpperCase().startsWith(illegalCommand)) 
				{
					sender.sendMessage(Common_Spigot.GetMessage("illegalcommand", "<blocked>", illegalCommand));
					return false;
				}
			}
		}
    	return true;
    }
}
