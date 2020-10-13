package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Spigot;

public class PlayerDropItemEvent_Security implements Listener
{
	public PlayerDropItemEvent_Security(Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerDropItem_Security(org.bukkit.event.player.PlayerDropItemEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.SecurityProtectionQuery(event.getPlayer())) event.setCancelled(true);
		}
	}
	
}
