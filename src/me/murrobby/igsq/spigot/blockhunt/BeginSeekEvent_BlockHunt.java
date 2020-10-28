package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

public class BeginSeekEvent_BlockHunt implements Listener
{
	
	public BeginSeekEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void BeginSeek_BlockHunt(me.murrobby.igsq.spigot.event.BeginSeekEvent event) 
	{
		if(!event.isCancelled()) 
		{
			Common_BlockHunt.timer = Yaml.getFieldInt("gametime", "blockhunt");
			Common_BlockHunt.stage = Stage.IN_GAME;
			for (Player seeker : event.getSeekers()) 
			{
				seeker.setGameMode(GameMode.SURVIVAL);
				seeker.teleport(Common_BlockHunt.seekerSpawnLocation);
			}
		}
		else 
		{
			Common_BlockHunt.timer = Yaml.getFieldInt("hidetime", "blockhunt");
		}
	}
}
