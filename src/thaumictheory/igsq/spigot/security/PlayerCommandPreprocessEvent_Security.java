package thaumictheory.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;


public class PlayerCommandPreprocessEvent_Security implements Listener
{
	public PlayerCommandPreprocessEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerCommandPreprocess_Security(org.bukkit.event.player.PlayerCommandPreprocessEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(!Common_Security.filterCommand(event.getMessage(),event.getPlayer())) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
