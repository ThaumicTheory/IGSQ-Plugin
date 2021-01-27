package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerMoveEvent_Security implements Listener
{
	public PlayerMoveEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerMove_Security(org.bukkit.event.player.PlayerMoveEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (Common_Security.isLocked(event.getPlayer())) event.setTo(Common.getHighestBlock(event.getPlayer().getLocation(),(int) event.getPlayer().getLocation().getY()).getLocation().add(.5f, 1, .5f));
		}
	}
	
}
