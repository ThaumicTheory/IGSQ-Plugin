package me.murrobby.igsq.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Configuration;

public class TimeSkipEvent_Command implements Listener
{
	public TimeSkipEvent_Command()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void TimeSkip_Command(org.bukkit.event.world.TimeSkipEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Configuration.getFieldBool(event.getWorld().getUID().toString() + ".realtime.enabled", "internal") && event.getSkipReason() != SkipReason.CUSTOM) event.setCancelled(true);
		}
	}
	
}
