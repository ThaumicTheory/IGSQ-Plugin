package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main_Spigot;

public class PlayerJoinEvent_Main implements Listener
{
	public PlayerJoinEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerJoin_Main(PlayerJoinEvent event) 
	{
		Player player = event.getPlayer();
		
		if(Common.getFieldInt(player.getUniqueId().toString() + ".damage.last", "internal") == 0) 
		{
			Common.Default(player);
		}
	}
	
}
