package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.Main_Spigot;

import java.util.Random;


public class EntityTargetEvent_Main implements Listener
{
	Random random = new Random();
	public EntityTargetEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityTarget_Main(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		if(!event.isCancelled())
		{
		}
	}
	
}
