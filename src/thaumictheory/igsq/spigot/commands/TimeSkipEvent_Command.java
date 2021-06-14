package thaumictheory.igsq.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlWorldWrapper;

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
			YamlWorldWrapper yaml = new YamlWorldWrapper(event.getWorld());
			if(yaml.isRealtime() && event.getSkipReason() != SkipReason.CUSTOM) event.setCancelled(true);
		}
	}
	
}
