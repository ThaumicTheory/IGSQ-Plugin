package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.Main_Spigot;

import java.util.Random;


public class PlayerBedEnterEvent_Main implements Listener
{
	Random random = new Random();
	public PlayerBedEnterEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerBedEnter_Main(org.bukkit.event.player.PlayerBedEnterEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
	
}
