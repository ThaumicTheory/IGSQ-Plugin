package me.murrobby.igsq.spigot.expert.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerArmorStandManipulateEvent_Protection implements Listener
{
	public PlayerArmorStandManipulateEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerArmorStandManipulate_Expert(org.bukkit.event.player.PlayerArmorStandManipulateEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isExpert()) 
		{
			if(Common_Protection.isProtected(event.getPlayer(),event.getRightClicked().getLocation())) event.setCancelled(true);
		}
	}
	
}
