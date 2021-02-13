package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerJoinEvent_Expert implements Listener
{
	public PlayerJoinEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin_Expert(PlayerJoinEvent event) 
	{
		if(YamlWrapper.isExpert())
		{
			new UI_Expert(event.getPlayer());
			if(Bukkit.getOnlinePlayers().size() == 1)
			{
				Main_Expert.refreshExpert();
			}
		}
	}
}
