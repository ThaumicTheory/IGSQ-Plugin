package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
import me.murrobby.igsq.spigot.Messaging;


public class PlayerBedEnterEvent_Expert implements Listener
{
	public PlayerBedEnterEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerBedEnter_Expert(org.bukkit.event.player.PlayerBedEnterEvent event) 
	{
		if(Common_Expert.expertCheck() && (!event.isCancelled()))
		{
			if(Yaml.getFieldBool(event.getPlayer().getWorld().getUID() + ".event.bloodmoon", "internal") && event.getPlayer().getWorld().getEnvironment() != Environment.NETHER && event.getPlayer().getWorld().getEnvironment() != Environment.THE_END)	
			{
				event.setCancelled(true);
				event.getPlayer().sendTitle(Messaging.chatFormatter("&#84FF00Expert Mode &#A600FFEvent Present!"),Messaging.chatFormatter("&#A600FFYou Cant Sleep Through a &#32FF82Blood Moon!"),10,70,20);
			}
		}
	}
}
