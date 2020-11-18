package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class EntitySpawnEvent_BlockHunt implements Listener
{
	public EntitySpawnEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntitySpawn_BlockHunt(org.bukkit.event.entity.EntitySpawnEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				if(event.getEntity() instanceof FallingBlock) 
				{
					FallingBlock block = (FallingBlock) event.getEntity();
					block.setDropItem(false);
					block.setGravity(false);
					block.setSilent(true);
					block.setHurtEntities(false);
					Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
			    	{

						@Override
						public void run() 
						{
							block.remove();
						} 		
			    	}, 1);
				}
				if(event.getEntity() instanceof EnderPearl)
				{
					EnderPearl pearl = (EnderPearl) event.getEntity();
					if(pearl.getShooter() instanceof Player) 
					{
						Player_BlockHunt player = Player_BlockHunt.getPlayer((Player) pearl.getShooter());
						if(player != null && player.isHider()) 
						{
							pearl.setGravity(false);
							pearl.setSilent(true);
							Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
					    	{

								@Override
								public void run() 
								{
									if(player.getPlayer().getInventory().getItem(0).getType() == Material.ENDER_EYE) 
									{
										//Common_BlockHunt.setBlockPickerCooldown(player, Yaml.getFieldInt("blockpickcooldown", "blockhunt")/Yaml.getFieldInt("failcooldown", "blockhunt"));
									}
									pearl.remove();
								}
					    	}, 20);
							Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
					    	{

								@Override
								public void run() 
								{
									//Common_BlockHunt.updateBlockPickerItem(player);
								}
					    	}, 0);
						}
						
					}
				}
			}
		}
	}
	
}
