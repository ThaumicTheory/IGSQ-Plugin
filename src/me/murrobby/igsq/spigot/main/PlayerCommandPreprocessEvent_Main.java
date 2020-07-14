package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;


public class PlayerCommandPreprocessEvent_Main implements Listener
{
	String[] illegalCommands = {"MINECRAFT:OP","OP","MINECRAFT:DEOP","DEOP"};
	public PlayerCommandPreprocessEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerCommandPreprocess_Security(org.bukkit.event.player.PlayerCommandPreprocessEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(!Common_Spigot.FilterChat(event.getMessage(), event.getPlayer())) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
