package me.murrobby.igsq.spigot.expert.protection;

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
	public void EntityPickupItem_Security(org.bukkit.event.entity.EntityPickupItemEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isExpert()) 
		{
			if(event.getEntity() instanceof Player) 
			{
				if(Common_Protection.isProtected((Player) event.getEntity())) event.setCancelled(true);
			}
		}
	}
	
}
