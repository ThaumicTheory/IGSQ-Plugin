package me.murrobby.igsq.spigot.smp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerQuitEvent_SMP implements Listener
{
	public PlayerQuitEvent_SMP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerQuit_SMP(org.bukkit.event.player.PlayerQuitEvent event) 
	{
		if(YamlWrapper.isSMP())
		{
			UI_SMP.getUIFromPlayer(event.getPlayer()).delete();
		}
	}
	
}
