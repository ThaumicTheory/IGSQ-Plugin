package me.murrobby.igsq.spigot.smp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerJoinEvent_SMP implements Listener
{
	public PlayerJoinEvent_SMP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin_SMP(PlayerJoinEvent event) 
	{
		if(YamlWrapper.isSMP())
		{
			if(Bukkit.getOnlinePlayers().size() == 1)
			{
				Main_SMP.refreshSMP();
			}
		}
	}
}
