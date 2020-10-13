package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Spigot;


public class PlayerCommandPreprocessEvent_Security implements Listener
{
	public PlayerCommandPreprocessEvent_Security(Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerCommandPreprocess_Security(org.bukkit.event.player.PlayerCommandPreprocessEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(!Common_Security.FilterCommand(event.getMessage(),event.getPlayer())) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
