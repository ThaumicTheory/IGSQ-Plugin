package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;

public class EntityDamageEvent_BlockHunt implements Listener
{
	public EntityDamageEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamage_BlockHunt(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck() && (!event.isCancelled()))
		{
			if(event.getEntityType() == EntityType.PLAYER) 
			{
				Player player = (Player)event.getEntity();
				if(Common_BlockHunt.isPlayer(player)) 
				{
					if(Common_BlockHunt.stage.equals(Stage.IN_LOBBY) && Common_BlockHunt.isPlayer(player)) event.setCancelled(true);
					else if(Common_BlockHunt.stage.equals(Stage.PRE_SEEKER) && Common_BlockHunt.isSeeker(player)) event.setCancelled(true);
					else if(Common_BlockHunt.isDead(player)) event.setCancelled(true);
					else
					{
						if (player.getHealth() - event.getFinalDamage() <= 0) //Player Would Die
						{
							Common_BlockHunt.killPlayer(player);
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
