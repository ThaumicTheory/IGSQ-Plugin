package me.murrobby.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerInteractEntityEvent_Protection implements Listener
{
	public PlayerInteractEntityEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerInteractEntity_Protection(org.bukkit.event.player.PlayerInteractEntityEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			if(event.getRightClicked() != null  && Common_Protection.isProtected(event.getPlayer(),event.getRightClicked().getLocation())) event.setCancelled(true);
		}
	}
	
}
