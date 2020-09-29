package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class ProjectileHitEvent_BlockHunt implements Listener
{
	public ProjectileHitEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void ProjectileHit_BlockHunt(org.bukkit.event.entity.ProjectileHitEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck()) 
		{
			if(event.getEntity() instanceof EnderPearl && event.getEntity().getShooter() instanceof Player) 
			{
				Player player = (Player) event.getEntity().getShooter();
				if(Common_BlockHunt.isHider(player))
				{
					if(event.getHitBlock() != null) 
					{
						if(Common_BlockHunt.isBlockPlayable(event.getHitBlock().getType())) 
						{
							Common_Spigot.updateField(player.getUniqueId().toString() + ".blockhunt.block", "internal", event.getHitBlock().getType().toString());
							
						}
					}
					Common_BlockHunt.giveEye(player,false);
					Common_BlockHunt.hiderBlockVisuals(player);
				}
			}
		}
	}
	
}
