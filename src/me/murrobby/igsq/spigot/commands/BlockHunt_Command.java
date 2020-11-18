package me.murrobby.igsq.spigot.commands;

import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.blockhunt.EndReason;
import me.murrobby.igsq.spigot.blockhunt.Game_BlockHunt;
import me.murrobby.igsq.spigot.blockhunt.Hider_BlockHunt;
import me.murrobby.igsq.spigot.blockhunt.Seeker_BlockHunt;

public class BlockHunt_Command {

	private CommandSender sender;
	private String[] args;
	public Boolean result;
	private static Random random = new Random();
	public BlockHunt_Command(CommandSender sender,String[] args) 
	{
		this.args = args;
		
		this.sender = sender;
		result = BlockHuntQuery();
	}
	private Boolean BlockHunt() 
	{
		if(args.length <= 2 && args.length >= 1 && sender instanceof Player) 
		{
			Player player = (Player) sender;
			if(args[0].equalsIgnoreCase("start")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null) 
				{
					gameInstance.start();
					return true;
				}
				return false;
			}
			else if(args[0].equalsIgnoreCase("forceseeker")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null) 
				{
					gameInstance.addSeeker(player);
					Seeker_BlockHunt.getSeeker(player).setup(true);
					return true;
				}
				return false;
			}
			else if(args[0].equalsIgnoreCase("forcehider")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null) 
				{
					gameInstance.addHider(player);
					Hider_BlockHunt.getHider(player).setup(true);
					return true;
				}
				return false;
			}
			else if(args[0].equalsIgnoreCase("end")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null) 
				{
					gameInstance.end(EndReason.FORCED);
					return true;
				}
				return false;
			}
			else if(args[0].equalsIgnoreCase("joinlobby")) 
			{
				Game_BlockHunt.removePlayerFromGames(player);
				if(args.length == 1) 
				{
					if(Game_BlockHunt.getGameInstances().length >= 1) 
					{
						Game_BlockHunt.getGameInstances()[random.nextInt(Game_BlockHunt.getGameInstances().length)].joinLobby(player);
					}
					else 
					{
						new Game_BlockHunt().joinLobby(player);
					}
					return true;
				}
				else 
				{
					Game_BlockHunt gameInstance = Game_BlockHunt.getInstanceByName(args[1]);
					if(gameInstance == null) 
					{
						gameInstance = new Game_BlockHunt(args[1]);
					}
					gameInstance.joinLobby(player);
					return true;
				}
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
