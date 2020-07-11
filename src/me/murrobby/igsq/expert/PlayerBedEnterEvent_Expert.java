package me.murrobby.igsq.expert;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main_Spigot;

import java.util.Random;


public class PlayerBedEnterEvent_Expert implements Listener
{
	Random random = new Random();
	public PlayerBedEnterEvent_Expert(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerBedEnter_Expert(org.bukkit.event.player.PlayerBedEnterEvent event) 
	{
		if(Common.ExpertCheck() && !event.isCancelled())
		{
			if(Common.getFieldBool(event.getPlayer().getWorld().getUID() + ".event.bloodmoon", "internal") && event.getPlayer().getWorld().getEnvironment() != Environment.NETHER && event.getPlayer().getWorld().getEnvironment() != Environment.THE_END)	
			{
				event.setCancelled(true);
				event.getPlayer().sendTitle(Common.ChatColour("&cExpert Mode Event Present!"),Common.ChatColour("&4You Cant Sleep Through a Blood Moon!"),10,70,20);
			}
		}
	}
	
}
