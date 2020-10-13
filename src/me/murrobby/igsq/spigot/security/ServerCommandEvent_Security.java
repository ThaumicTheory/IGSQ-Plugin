package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Spigot;


public class ServerCommandEvent_Security implements Listener
{
	public ServerCommandEvent_Security(Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void ServerCommand_Security(org.bukkit.event.server.ServerCommandEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(!Common_Security.FilterCommand(event.getCommand(),event.getSender())) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
