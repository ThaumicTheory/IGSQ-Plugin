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
			shufflePlayers(100,event.getGame());
			allocatePlayers(event.getGame());
			event.getGame().setStage(Stage.PRE_SEEKER);
			event.getGame().setTimer(Yaml.getFieldInt("hidetime", "blockhunt"));
			for (Player_BlockHunt selectedPlayer : event.getGame().getPlayers()) 
			{
				//event.getGame().setupPlayers(selectedPlayer);
				if(selectedPlayer.isSeeker()) selectedPlayer.getPlayer().teleport(event.getGame().getMap().getSeekerWaitLocation());
				else if(selectedPlayer.isHider()) selectedPlayer.getPlayer().teleport(event.getGame().getMap().getHiderSpawnLocation());
			}
		}
		else 
		{
			event.getGame().setTimer( Yaml.getFieldInt("lobbytime", "blockhunt"));
		}
	}
	
	
	
	
	
	
	
	private void shufflePlayers(int shuffles,Game_BlockHunt gameInstance)
	{
		for(int i = 0; i < shuffles;i++) 
		{
			int randomNumber = random.nextInt(Common_BlockHunt.getSeekerCount(gameInstance.getQueueCount()));
			Player player = gameInstance.getQueuedPlayers()[randomNumber];
			gameInstance.removeQueuedPlayer(player);
			gameInstance.addQueuedPlayer(player);
			
		}
	}
	private void allocatePlayers(Game_BlockHunt gameInstance) 
	{
		Player[] allocation = gameInstance.getQueuedPlayers();
		for(int i = 0; i < Common_BlockHunt.getSeekerCount(allocation) ;i++) //seeker allocation
		{
			int randomNumber = random.nextInt(Common_BlockHunt.getSeekerCount(gameInstance.getPlayerCount()));
			gameInstance.addSeeker(allocation[randomNumber]);
			allocation = Common.depend(allocation, randomNumber);
		}
		for(Player player : allocation) //hider allocation
		{
			gameInstance.addHider(player);
		}
	}
	
}
