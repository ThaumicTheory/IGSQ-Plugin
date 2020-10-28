package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;


public class EntityDamageByEntityEvent_BlockHunt implements Listener
{
	public EntityDamageByEntityEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamagedByEntity_BlockHunt(org.bukkit.event.entity.EntityDamageByEntityEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck() && (!event.isCancelled()))
		{
			if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) 
			{
				Player victum = (Player) event.getEntity();
				Player attacker = (Player) event.getDamager();
				if(Common_BlockHunt.isDead(attacker)) event.setCancelled(true); //attacker cannot hurt players.
				else if(Common_BlockHunt.isHider(victum) == Common_BlockHunt.isHider(attacker)) event.setCancelled(true); //Stop friendly fire if teams fail to do so
				else if(Common_BlockHunt.isCloaked(attacker)) event.setCancelled(true);
			}
				
		}
	}
	
}
