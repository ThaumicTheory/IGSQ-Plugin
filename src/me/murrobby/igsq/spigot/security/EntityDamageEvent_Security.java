package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class EntityDamageEvent_Security implements Listener
{
	public EntityDamageEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamage_Security(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getEntity() instanceof Player) 
			{
				Player player = (Player) event.getEntity();
				if (Common_Security.SecurityProtectionQuery(player)) event.setCancelled(true);
			}
		}
	}
	
}
