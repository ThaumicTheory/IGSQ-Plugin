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
				Player victim = (Player) event.getEntity();
				Player attacker = (Player) event.getDamager();
				Game_BlockHunt attackerGame = Game_BlockHunt.getPlayersGame(attacker);
				if(attackerGame != null) 
				{
					if(!attackerGame.equals(Game_BlockHunt.getPlayersGame(victim))) event.setCancelled(true); //People from different games cannot hurt people
					else if(attackerGame.isDead(attacker)) event.setCancelled(true); //dead attacker cannot hurt players.
					else if(attackerGame.isHider(victim) == Game_BlockHunt.getPlayersGame(attacker).isHider(attacker)) event.setCancelled(true); //Stop friendly fire if teams fail to do so
					else if(Common_BlockHunt.isCloaked(attacker)) event.setCancelled(true);
				}
			}
				
		}
	}
	
}
