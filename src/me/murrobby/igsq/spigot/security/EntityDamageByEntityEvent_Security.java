package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class EntityDamageByEntityEvent_Security implements Listener
{
	public EntityDamageByEntityEvent_Security(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityDamageByEntity_Security(org.bukkit.event.entity.EntityDamageByEntityEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getDamager() instanceof Player) 
			{
				Player player = (Player) event.getDamager();
				if(Common_Security.SecurityProtection(player, "Hurt " + event.getEntityType())) event.setCancelled(true);
			}
		}
	}
	
}
