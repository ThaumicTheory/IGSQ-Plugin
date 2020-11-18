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
				
				Player_BlockHunt attacker = Player_BlockHunt.getPlayer((Player) event.getDamager());
				Player_BlockHunt victim = Player_BlockHunt.getPlayer((Player) event.getEntity());
				if(attacker != null) 
				{
					if(!attacker.getGame().equals(victim.getGame())) event.setCancelled(true); //People from different games cannot hurt people
					else if(attacker.isDead()) event.setCancelled(true); //dead attacker cannot hurt players.
					else if(attacker.isHider() == victim.isHider()) event.setCancelled(true); //Stop friendly fire if teams fail to do so
					else if(attacker.isHider() && attacker.toHider().getGeneric().isCloaked()) event.setCancelled(true);
				}
			}
				
		}
	}
	
}
