package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;
import me.murrobby.igsq.spigot.blockhunt.EndReason;
import me.murrobby.igsq.spigot.blockhunt.Game_BlockHunt;
import me.murrobby.igsq.spigot.blockhunt.Stage;

public class BlockHunt_Command {

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	public Boolean result;
	private static Random random = new Random();
	public BlockHunt_Command(CommandSender sender,List<String> args) 
	{
		this.args = args;
		
		this.sender = sender;
		result = BlockHuntQuery();
	}
	private Boolean BlockHunt() 
	{
		if(args.size() <= 2 && args.size() >= 1 && sender instanceof Player) 
		{
			Player player = (Player) sender;
			if(args.get(0).equalsIgnoreCase("start")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null) 
				{
					gameInstance.start();
					return true;
				}
				return false;
			}
			else if(args.get(0).equalsIgnoreCase("forceseeker")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null && (gameInstance.isStage(Stage.IN_GAME) || gameInstance.isStage(Stage.PRE_SEEKER))) 
				{
					gameInstance.addSeeker(player);
					return true;
				}
				return false;
			}
			else if(args.get(0).equalsIgnoreCase("forcehider")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null && (gameInstance.isStage(Stage.IN_GAME) || gameInstance.isStage(Stage.PRE_SEEKER))) 
				{
					gameInstance.addHider(player);
					return true;
				}
				return false;
			}
			else if(args.get(0).equalsIgnoreCase("end")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null) 
				{
					gameInstance.end(EndReason.FORCED);
					return true;
				}
				return false;
			}
			else if(args.get(0).equalsIgnoreCase("joinlobby")) 
			{
				Game_BlockHunt.removePlayerFromGames(player);
				if(args.size() == 1) 
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
					Game_BlockHunt gameInstance = Game_BlockHunt.getInstanceByName(args.get(1));
					if(gameInstance == null) 
					{
						gameInstance = new Game_BlockHunt(args.get(1));
					}
					gameInstance.joinLobby(player);
					return true;
				}
			}
			else if(args.get(0).equalsIgnoreCase("testmode")) 
			{
				Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
				if(gameInstance != null) 
				{
					gameInstance.setTestMode(!gameInstance.isTestMode());
					player.sendMessage(Messaging.chatFormatter("&#ccccccTest mode " + (gameInstance.isTestMode() ? "enabled" : "disabled") + "."));
					return true;
				}
				return false;
			}
			else if(args.get(0).equalsIgnoreCase("gui")) 
			{
				Common_BlockHunt.updateGui(player);
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
