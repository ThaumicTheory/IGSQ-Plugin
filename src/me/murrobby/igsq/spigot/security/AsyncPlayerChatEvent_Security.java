package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class AsyncPlayerChatEvent_Security implements Listener
{
	public AsyncPlayerChatEvent_Security(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void AsyncPlayerChat_Security(org.bukkit.event.player.AsyncPlayerChatEvent event) 
	{
		if(!event.isCancelled()) 
		{
			//if (Common_Security.SecurityProtection(event.getPlayer(),"Chat")) event.setCancelled(true);
		}
	}
	
}
