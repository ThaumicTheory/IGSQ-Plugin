package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;


public class EntityDeathEvent_Main implements Listener
{
	private Main_Spigot plugin;
	public EntityDeathEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void EntityDeath_Main(org.bukkit.event.entity.EntityDeathEvent event) 
	{
		if(event.getEntity() instanceof EnderDragon) 
		{
			EnderDragon enderDragon = (EnderDragon) event.getEntity();
			if(Common_Spigot.getFieldBool("GAMEPLAY.dragoneggrespawn", "config") && enderDragon.getDragonBattle().hasBeenPreviouslyKilled())
			{
				plugin.scheduler.scheduleSyncDelayedTask(plugin, new Runnable()
		    	{

					@Override
					public void run() 
					{
						Location location = enderDragon.getLocation();
						location.setX(0);
						location.setZ(0);
						Common_Spigot.getHighestBlock(location,255).getLocation().add(0, 1, 0).getBlock().setType(Material.DRAGON_EGG);
					}
		    	},200);
			}
		}
	}
	
}
