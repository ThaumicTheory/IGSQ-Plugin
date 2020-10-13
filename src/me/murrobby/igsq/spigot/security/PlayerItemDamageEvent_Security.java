package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Spigot;

public class PlayerItemDamageEvent_Security implements Listener
{
	public PlayerItemDamageEvent_Security(Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerItemDamage_Security(org.bukkit.event.player.PlayerItemDamageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.SecurityProtectionQuery(event.getPlayer())) event.setCancelled(true);
		}
	}
	
}
