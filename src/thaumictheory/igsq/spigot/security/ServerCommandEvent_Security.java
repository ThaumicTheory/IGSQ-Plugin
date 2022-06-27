package thaumictheory.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;


public class ServerCommandEvent_Security implements Listener
{
	public ServerCommandEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void ServerCommand_Security(org.bukkit.event.server.ServerCommandEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(!Common_Security.filterCommand(event.getCommand(),event.getSender())) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
