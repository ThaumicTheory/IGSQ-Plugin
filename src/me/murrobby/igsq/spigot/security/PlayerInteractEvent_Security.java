package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerInteractEvent_Security implements Listener
{
	public PlayerInteractEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerInteract_Security(org.bukkit.event.player.PlayerInteractEvent event) 
	{
		if (Common_Security.isLocked(event.getPlayer())) event.setCancelled(true);
	}
	
}
