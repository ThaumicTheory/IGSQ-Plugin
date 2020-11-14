package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerDropItemEvent_BlockHunt implements Listener
{
	public PlayerDropItemEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerDropItem_BlockHunt(org.bukkit.event.player.PlayerDropItemEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				if(Game_BlockHunt.getPlayersGame(event.getPlayer()) != null) 
				{
					event.setCancelled(true);
				}
			}
		}
	}
	
}
