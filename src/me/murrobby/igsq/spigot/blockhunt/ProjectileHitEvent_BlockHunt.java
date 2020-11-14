package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class ProjectileHitEvent_BlockHunt implements Listener
{
	public ProjectileHitEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void ProjectileHit_BlockHunt(org.bukkit.event.entity.ProjectileHitEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck()) 
		{
			if(event.getEntity() instanceof EnderPearl && event.getEntity().getShooter() instanceof Player) 
			{
				Player player = (Player) event.getEntity().getShooter();
				Game_BlockHunt playersGame = Game_BlockHunt.getPlayersGame(player);
				if(playersGame != null && playersGame.isHider(player))
				{
					if(event.getHitBlock() != null) 
					{
						if(playersGame.isBlockPlayable(event.getHitBlock().getType())) 
						{
							Common_BlockHunt.hiderChangeDisguise(player, event.getHitBlock().getType());				
						}
					}
				}
			}
		}
	}
	
}
