package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

public class GameEndEvent_BlockHunt implements Listener
{
	
	public GameEndEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void GameEnd_BlockHunt(me.murrobby.igsq.spigot.event.GameEndEvent event) 
	{
		if(!event.isCancelled()) 
		{
			Common_BlockHunt.stage = Stage.NO_GAME;
		}
		else 
		{
			if(event.getReason().equals(EndReason.TIME_UP)) Common_BlockHunt.timer = Yaml.getFieldInt("gametime", "blockhunt");
		}
	}
}
