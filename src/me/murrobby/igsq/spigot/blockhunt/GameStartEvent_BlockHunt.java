package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

public class GameStartEvent_BlockHunt implements Listener
{
	private static Random random = new Random();
	
	public GameStartEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void GameStart_BlockHunt(me.murrobby.igsq.spigot.event.GameStartEvent event) 
	{
		if(!event.isCancelled()) 
		{
			Common_BlockHunt.setupTeams();
			shufflePlayers(100);
			allocatePlayers();
			Common_BlockHunt.stage = Stage.PRE_SEEKER;
			Common_BlockHunt.timer = Yaml.getFieldInt("hidetime", "blockhunt");
			for (Player selectedPlayer : Common_BlockHunt.players) 
			{
				Common_BlockHunt.setupPlayers(selectedPlayer);
				if(Common_BlockHunt.isSeeker(selectedPlayer)) selectedPlayer.teleport(Common_BlockHunt.seekerWaitLocation);
				else if(Common_BlockHunt.isHider(selectedPlayer)) selectedPlayer.teleport(Common_BlockHunt.hiderSpawnLocation);
			}
		}
		else 
		{
			Common_BlockHunt.timer = Yaml.getFieldInt("lobbytime", "blockhunt");
		}
	}
	
	
	
	
	
	
	
	private static void shufflePlayers(int shuffles) 
	{
		for(int i = 0; i < shuffles;i++) 
		{
			int randomNumber = random.nextInt(Common_BlockHunt.getPlayerCount());
			Player player = Common_BlockHunt.players[randomNumber];
			Common_BlockHunt.players = Common.depend(Common_BlockHunt.players, randomNumber);
			Common_BlockHunt.players = Common.append(Common_BlockHunt.players, player);
			
		}
	}
	private static void allocatePlayers() 
	{
		Player[] allocation = Common_BlockHunt.players;
		for(int i = 0; i < Common_BlockHunt.getSeekerCount() ;i++) //seeker allocation
		{
			int randomNumber = random.nextInt(Common_BlockHunt.getPlayerCount());
			Common_BlockHunt.seekers = Common.append(Common_BlockHunt.seekers,allocation[randomNumber]);
			allocation = Common.depend(allocation, randomNumber);
		}
		for(Player player : allocation) //hider allocation
		{
			Common_BlockHunt.hiders = Common.append(Common_BlockHunt.hiders, player);
		}
	}
	
}
