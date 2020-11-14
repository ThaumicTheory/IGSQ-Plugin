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
				Game_BlockHunt playersGame = Game_BlockHunt.getPlayersGame(player);
				if(playersGame != null) 
				{
					if(playersGame.isStage(Stage.IN_LOBBY)) event.setCancelled(true);
					else if(playersGame.isStage(Stage.PRE_SEEKER) && playersGame.isSeeker(player)) event.setCancelled(true);
					else if(playersGame.isDead(player)) event.setCancelled(true);
					else
					{
						if (player.getHealth() - event.getDamage() <= 0) //Player Would Die
						{
							System.out.println(player.getHealth() + " - " + event.getDamage() + " = " + (player.getHealth() - event.getDamage()));
							playersGame.killPlayer(player);
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
