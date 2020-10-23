package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;
import me.murrobby.igsq.spigot.blockhunt.EndReason;

public class BlockHunt_Command {

	private CommandSender sender;
	private String[] args;
	public Boolean result;
	public BlockHunt_Command(CommandSender sender,String[] args) 
	{
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
				Common_BlockHunt.setupPlayers(player);
				return true;
			}
			else if(args[0].equalsIgnoreCase("forcehider")) 
			{
				if(Common_BlockHunt.isPlayer(player)) Common_BlockHunt.removePlayer(player);
				Common_BlockHunt.addHider(player);
				Common_BlockHunt.setupPlayers(player);
				return true;
			}
			else if(args[0].equalsIgnoreCase("end")) 
			{
				Common_BlockHunt.end(EndReason.FORCED);
				return true;
			}
		}
		return false;
		
	}
	private Boolean BlockHuntQuery() 
	{
			if(sender instanceof Player && Common_Command.requirePermission("igsq.blockhunt",sender))
			{
				if(BlockHunt()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FFFF00blockhunt [start/forceseeker/forcehider/end]"));
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
