package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
			event.getGame().setTimer(Yaml.getFieldInt("gametime", "blockhunt"));
			event.getGame().setStage(Stage.IN_GAME);
			for (Seeker_BlockHunt seeker : event.getGame().getSeekers()) 
			{
				seeker.getPlayer().setGameMode(GameMode.SURVIVAL);
				seeker.getPlayer().teleport(event.getGame().getMap().getSeekerSpawnLocation());
			}
		}
		else 
		{
			event.getGame().setTimer(Yaml.getFieldInt("hidetime", "blockhunt"));
		}
	}
}
