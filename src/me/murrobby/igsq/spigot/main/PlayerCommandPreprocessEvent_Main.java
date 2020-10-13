package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;


public class PlayerCommandPreprocessEvent_Main implements Listener
{
	public PlayerCommandPreprocessEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerCommandPreprocess_Main(org.bukkit.event.player.PlayerCommandPreprocessEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(!Common.filterChat(event.getMessage(), event.getPlayer())) 
			{
				event.setCancelled(true);
			}
		}
	}
}
