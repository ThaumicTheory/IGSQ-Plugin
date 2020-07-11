package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.Main_Spigot;


public class EntityAirChangeEvent_Main implements Listener
{
	public EntityAirChangeEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityAirChange_Main(org.bukkit.event.entity.EntityAirChangeEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
	
}
