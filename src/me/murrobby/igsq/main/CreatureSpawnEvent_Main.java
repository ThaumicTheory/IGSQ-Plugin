package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.Main_Spigot;

import java.util.Random;


public class CreatureSpawnEvent_Main implements Listener
{
	Random random = new Random();
	public CreatureSpawnEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void CreatureSpawn_Main(org.bukkit.event.entity.CreatureSpawnEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
}
