package thaumictheory.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlEntityWrapper;

import java.util.Random;


public class CreatureSpawnEvent_Main implements Listener
{
	Random random = new Random();
	public CreatureSpawnEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void CreatureSpawn_Main(org.bukkit.event.entity.CreatureSpawnEvent event) 
	{
		if(!event.isCancelled())
		{
			new YamlEntityWrapper(event.getEntity()).applyDefault();
		}
			
	}
}
