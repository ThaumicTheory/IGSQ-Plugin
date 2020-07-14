package me.murrobby.igsq.spigot.security;

import org.bukkit.command.CommandSender;

import me.murrobby.igsq.spigot.Common_Spigot;

public class Common_Security 
{
	public static String[] illegalCommands = {"MINECRAFT:OP","OP","MINECRAFT:DEOP","DEOP"};
	
    public static boolean FilterCommand(String command,CommandSender sender) 
    {
		for(String illegalCommand: illegalCommands)
		{
			if(command.toUpperCase().contains(illegalCommand)) 
			{
				sender.sendMessage(Common_Spigot.GetMessage("MESSAGE.illegalcommand", "<blocked>", illegalCommand));
				return false;
			}
		}
    	return true;
    }
}
