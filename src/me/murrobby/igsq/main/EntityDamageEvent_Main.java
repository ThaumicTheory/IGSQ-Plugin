package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.murrobby.igsq.Main_Spigot;

public class EntityDamageEvent_Main implements Listener
{
	public EntityDamageEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityDamage_Main(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
	
}
