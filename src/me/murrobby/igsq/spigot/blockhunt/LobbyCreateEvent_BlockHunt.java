package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

public class LobbyCreateEvent_BlockHunt implements Listener
{
	public LobbyCreateEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void LobbyCreate_BlockHunt(me.murrobby.igsq.spigot.event.LobbyCreateEvent event) 
	{
		if(!event.isCancelled()) 
		{
			Common_BlockHunt.cleanup();
			Common_BlockHunt.stage = Stage.IN_LOBBY;
			Common_BlockHunt.timer = Yaml.getFieldInt("lobbytime", "blockhunt");
			if(!Common_BlockHunt.isMapSelected()) Common_BlockHunt.loadMap(event.getMap());
			Main_BlockHunt.startBlockHunt();
		}
	}
	
}
