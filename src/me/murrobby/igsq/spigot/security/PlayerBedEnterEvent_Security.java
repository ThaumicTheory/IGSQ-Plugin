package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class PlayerBedEnterEvent_Security implements Listener
{
	public PlayerBedEnterEvent_Security(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerBedEnter_Security(org.bukkit.event.player.PlayerBedEnterEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.SecurityProtection(event.getPlayer(),"Enter Beds")) event.setCancelled(true);
		}
	}
	
}
