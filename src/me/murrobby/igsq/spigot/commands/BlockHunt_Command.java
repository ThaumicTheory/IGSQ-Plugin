package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;
import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;

public class BlockHunt_Command {

	private Main_Command commands;
	private CommandSender sender;
	private String[] args;
	public Boolean result;
	public BlockHunt_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		this.commands = commands;
		this.args = args;
		
		this.sender = sender;
		result = BlockHuntQuery();
	}
	private Boolean BlockHunt() 
	{
		if(args.length == 1 && sender instanceof Player) 
		{
			Player player = (Player) sender;
			if(args[0].equalsIgnoreCase("start")) 
			{
				Common_BlockHunt.start();
				return true;
			}
			else if(args[0].equalsIgnoreCase("forceseeker")) 
			{
				if(Common_BlockHunt.isPlayer(player)) Common_BlockHunt.removePlayer(player);
				Common_BlockHunt.addSeeker(player);
				Common_BlockHunt.setupGear(player);
				return true;
			}
			else if(args[0].equalsIgnoreCase("forcehider")) 
			{
				if(Common_BlockHunt.isPlayer(player)) Common_BlockHunt.removePlayer(player);
				Common_BlockHunt.addHider(player);
				Common_BlockHunt.setupGear(player);
				return true;
			}
		}
		return false;
		
	}
	private Boolean BlockHuntQuery() 
	{
			if(commands.RequirePermission("igsq.blockhunt") && commands.IsPlayer()) 
			{
				if(BlockHunt()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common_Spigot.chatFormatter("&#FFFF00blockhunt [start/forceseeker/forcehider]"));
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
