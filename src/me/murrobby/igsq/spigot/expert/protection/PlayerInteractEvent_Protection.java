package me.murrobby.igsq.spigot.expert.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import me.murrobby.igsq.spigot.Common;

public class PlayerInteractEvent_Protection implements Listener
{
	public PlayerInteractEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerInteract_Expert(org.bukkit.event.player.PlayerInteractEvent event) 
	{
		if(Common_Protection.isProtected(event.getPlayer())) 
		{
			if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) event.setCancelled(true);
		}
	}
	
}
