package thaumictheory.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;

public class PlayerBedEnterEvent_Security implements Listener
{
	public PlayerBedEnterEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerBedEnter_Security(org.bukkit.event.player.PlayerBedEnterEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.isLocked(event.getPlayer())) event.setCancelled(true);
		}
	}
	
}
