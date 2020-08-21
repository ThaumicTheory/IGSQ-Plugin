package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class ThunderChangeEvent_Main implements Listener
{
	public ThunderChangeEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	
	@EventHandler
	public void ThunderChange_Main(org.bukkit.event.weather.ThunderChangeEvent event)
	{
		if(!event.isCancelled());
		{
			if(event.toThunderState() && event.getWorld().getEnvironment() == Environment.NORMAL)
			{
				try {
					Common_Spigot.internalData.set(event.getWorld().getUID() + ".thunder",true);
					Common_Spigot.internalData.save(Common_Spigot.internalDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else 
			{
				try {
					Common_Spigot.internalData.set(event.getWorld().getUID() + ".thunder",false);
					Common_Spigot.internalData.save(Common_Spigot.internalDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}	
}

	


