package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;

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
			for(Player player : Common_BlockHunt.players)
			{
				Common_BlockHunt.cleanup(player);
				
			}
			Common_BlockHunt.cleanup();
			
			Common_BlockHunt.stage = Stage.NO_GAME;
			
		}
	}
}
