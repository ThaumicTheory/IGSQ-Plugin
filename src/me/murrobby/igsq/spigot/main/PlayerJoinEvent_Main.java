package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

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
		
		if(Common_Spigot.getFieldInt(player.getUniqueId().toString() + ".damage.last", "internal") == 0) 
		{
			Common_Spigot.applyDefault(player);
		}
	}
	
}
