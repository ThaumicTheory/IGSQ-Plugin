package thaumictheory.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class EntityDamageEvent_BlockHunt implements Listener
{
	public EntityDamageEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamage_BlockHunt(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(YamlWrapper.isBlockHunt() && (!event.isCancelled()))
		{
			if(event.getEntityType() == EntityType.PLAYER) 
			{
				Player_BlockHunt player = Player_BlockHunt.getPlayer((Player)event.getEntity());
				if(player != null) 
				{
					if(player.getGame().isStage(Stage.IN_LOBBY)) event.setCancelled(true);
					else if(player.getGame().isStage(Stage.PRE_SEEKER) && player.isSeeker()) event.setCancelled(true);
					else if(player.isDead()) event.setCancelled(true);
					else
					{
						if (player.getPlayer().getHealth() - event.getDamage() <= 0) //Player Would Die
						{
							player.kill();
							event.setCancelled(true);
						}
					}
				}
			}
			else event.setCancelled(true);
		}
	}
}
