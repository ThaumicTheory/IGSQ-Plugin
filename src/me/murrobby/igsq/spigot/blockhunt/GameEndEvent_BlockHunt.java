package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

public class GameEndEvent_BlockHunt implements Listener
{
	
	public GameEndEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void GameEnd_BlockHunt(me.murrobby.igsq.spigot.event.GameEndEvent event) 
	{
		if(!event.isCancelled()) 
		{
			event.getGame().setStage(Stage.NO_GAME);
			for(Player_BlockHunt player : event.getGame().getPlayers()) player.outOfGame();
			
			
			
			Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
	    	{

				@Override
				public void run() 
				{
					event.getGame().delete();
				}
	    	},200);
		}
		else 
		{
			if(event.getReason().equals(EndReason.TIME_UP)) event.getGame().setTimer(Yaml.getFieldInt("gametime", "blockhunt"));
		}
	}
}
