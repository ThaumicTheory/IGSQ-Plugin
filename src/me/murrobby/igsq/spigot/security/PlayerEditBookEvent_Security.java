package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerEditBookEvent_Security implements Listener
{
	public PlayerEditBookEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerEditBook_Security(org.bukkit.event.player.PlayerEditBookEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.SecurityProtectionQuery(event.getPlayer())) event.setCancelled(true);
		}
	}
	
}
