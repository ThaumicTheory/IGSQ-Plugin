package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerItemDamageEvent_Security implements Listener
{
	public PlayerItemDamageEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerItemDamage_Security(org.bukkit.event.player.PlayerItemDamageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.isLocked(event.getPlayer())) event.setCancelled(true);
		}
	}
	
}
