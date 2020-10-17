package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class EntityTargetEvent_Security implements Listener
{
	public EntityTargetEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityTarget_Security(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getTarget() instanceof Player) 
			{
				Player player = (Player) event.getTarget();
				if(Common_Security.SecurityProtectionQuery(player)) event.setTarget(null);
			}
		}
	}
	
}
