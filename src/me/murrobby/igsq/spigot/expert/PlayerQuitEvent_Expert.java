package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerQuitEvent_Expert implements Listener
{
	public PlayerQuitEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerQuit_Expert(org.bukkit.event.player.PlayerQuitEvent event) 
	{
		if(YamlWrapper.isExpert())
		{
			UI_Expert.getUIFromPlayer(event.getPlayer()).delete();
		}
	}
	
}
