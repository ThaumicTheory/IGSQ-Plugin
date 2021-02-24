package me.murrobby.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class EntityPickupItemEvent_Protection implements Listener
{
	public EntityPickupItemEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityPickupItem_Protection(org.bukkit.event.entity.EntityPickupItemEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			if(event.getEntity() instanceof Player) 
			{
				if(Common_Protection.isProtected((Player) event.getEntity(),event.getItem().getLocation())) event.setCancelled(true);
			}
		}
	}
	
}
