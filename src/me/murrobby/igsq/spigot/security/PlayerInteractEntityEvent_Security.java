package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerInteractEntityEvent_Security implements Listener
{
	public PlayerInteractEntityEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerInteractEntity_Security(org.bukkit.event.player.PlayerInteractEntityEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.isLocked(event.getPlayer())) event.setCancelled(true);
		}
	}
	
}
