package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;
import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;

public class BlockHunt_Command {

	private Main_Command commands;
	private CommandSender sender;
	public Boolean result;
	public BlockHunt_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		this.commands = commands;
		
		this.sender = sender;
		result = BlockHuntQuery();
	}
	private Boolean BlockHunt() 
	{
		Common_BlockHunt.start();
		return true;
		
	}
	private Boolean BlockHuntQuery() 
	{
			if(commands.RequirePermission("igsq.blockhunt.start")) 
			{
				if(BlockHunt()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common_Spigot.chatFormatter("&#FFFF00blockhunt [start]"));
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
