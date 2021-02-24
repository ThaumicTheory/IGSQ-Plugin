package me.murrobby.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerInteractEvent_Protection implements Listener
{
	public PlayerInteractEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerInteract_Protection(org.bukkit.event.player.PlayerInteractEvent event) 
	{
		if(YamlWrapper.isSMP()) 
		{
			if(event.getClickedBlock() != null && Common_Protection.isProtected(event.getPlayer(),event.getClickedBlock().getLocation())) event.setCancelled(true);
		}
	}
	
}
