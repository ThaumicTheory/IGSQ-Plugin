package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlEntityWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;


public class EntityDeathEvent_Main implements Listener
{
	public EntityDeathEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDeath_Main(org.bukkit.event.entity.EntityDeathEvent event) 
	{
		if(!(event.getEntity() instanceof Player)) new YamlEntityWrapper(event.getEntity()).delete();
		if(event.getEntity() instanceof EnderDragon) 
		{
			EnderDragon enderDragon = (EnderDragon) event.getEntity();
			if(YamlWrapper.isEggRespawn() && enderDragon.getDragonBattle().hasBeenPreviouslyKilled())
			{
				Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
		    	{

					@Override
					public void run() 
					{
						Location location = enderDragon.getLocation();
						location.setX(0);
						location.setZ(0);
						Common.getHighestBlock(location,255).getLocation().add(0, 1, 0).getBlock().setType(Material.DRAGON_EGG);
					}
		    	},200);
			}
		}
	}
	
}
