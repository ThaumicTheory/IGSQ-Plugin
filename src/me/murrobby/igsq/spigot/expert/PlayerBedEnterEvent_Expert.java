package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;


public class PlayerBedEnterEvent_Expert implements Listener
{
	public PlayerBedEnterEvent_Expert(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerBedEnter_Expert(org.bukkit.event.player.PlayerBedEnterEvent event) 
	{
		if(Common_Expert.ExpertCheck() && (!event.isCancelled()))
		{
			if(Common_Spigot.getFieldBool(event.getPlayer().getWorld().getUID() + ".event.bloodmoon", "internal") && event.getPlayer().getWorld().getEnvironment() != Environment.NETHER && event.getPlayer().getWorld().getEnvironment() != Environment.THE_END)	
			{
				event.setCancelled(true);
				event.getPlayer().sendTitle(Common_Spigot.ChatFormatter("&#84FF00Expert Mode &#A600FFEvent Present!"),Common_Spigot.ChatFormatter("&#A600FFYou Cant Sleep Through a &#32FF82Blood Moon!"),10,70,20);
			}
		}
	}
	
}
