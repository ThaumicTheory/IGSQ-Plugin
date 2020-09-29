package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class PlayerDropItemEvent_BlockHunt implements Listener
{
	public PlayerDropItemEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerDropItem_BlockHunt(org.bukkit.event.player.PlayerDropItemEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				if(Common_BlockHunt.isPlayer(event.getPlayer())) 
				{
					event.setCancelled(true);
				}
			}
		}
	}
	
}
