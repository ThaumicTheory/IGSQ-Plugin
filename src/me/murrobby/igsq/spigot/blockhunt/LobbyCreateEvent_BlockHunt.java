package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

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
			event.getGame().setStage(Stage.IN_LOBBY);
			event.getGame().setTimer(YamlWrapper.getBlockHuntLobbyTime());
			Main_BlockHunt.startBlockHunt();
		}
		else 
		{
			event.getGame().delete();
		}
	}
	
}
