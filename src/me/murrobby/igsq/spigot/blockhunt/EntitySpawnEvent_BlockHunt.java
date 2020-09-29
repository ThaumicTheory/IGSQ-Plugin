package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class EntitySpawnEvent_BlockHunt implements Listener
{
	private Main_Spigot plugin;
	public EntitySpawnEvent_BlockHunt(Main_Spigot plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
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
					plugin.scheduler.scheduleSyncDelayedTask(plugin, new Runnable()
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
					pearl.setGravity(false);
					pearl.setSilent(true);
					if(pearl.getShooter() instanceof Player) 
					{
						Player player = (Player) pearl.getShooter();
						if(Common_BlockHunt.isHider(player)) 
						{
							plugin.scheduler.scheduleSyncDelayedTask(plugin, new Runnable()
					    	{

								@Override
								public void run() 
								{
									if(player.getInventory().getItem(0).getType() == Material.ENDER_EYE) 
									{
										Common_BlockHunt.giveEye(player,false);
									}
									pearl.remove();
								}
					    	}, 20);
							plugin.scheduler.scheduleSyncDelayedTask(plugin, new Runnable()
					    	{

								@Override
								public void run() 
								{
									Common_BlockHunt.giveEye(player,true);
								}
					    	}, 0);
						}
						
					}
				}
			}
		}
	}
	
}
