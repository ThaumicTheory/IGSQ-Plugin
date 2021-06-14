package thaumictheory.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;

public class EntityPickupItemEvent_Security implements Listener
{
	public EntityPickupItemEvent_Security()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityPickupItem_Security(org.bukkit.event.entity.EntityPickupItemEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getEntity() instanceof Player) 
			{
				Player player = (Player) event.getEntity();
				if (Common_Security.isLocked(player)) event.setCancelled(true);
			}
		}
	}
	
}
