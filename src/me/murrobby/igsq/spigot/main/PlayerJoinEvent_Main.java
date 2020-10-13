package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Configuration;

public class PlayerJoinEvent_Main implements Listener
{
	public PlayerJoinEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin_Main(PlayerJoinEvent event) 
	{
		Player player = event.getPlayer();
		
		if(Configuration.getFieldInt(player.getUniqueId().toString() + ".damage.last", "internal") == 0) 
		{
			Configuration.applyDefault(player);
		}
	}
}
