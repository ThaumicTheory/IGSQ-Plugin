package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class InventoryClickEvent_Security implements Listener
{
	public InventoryClickEvent_Security(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void InventoryClick_Security(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getWhoClicked() instanceof Player) 
			{
				Player player = (Player) event.getWhoClicked();
				if (Common_Security.SecurityProtection(player,"Move Inventory")) event.setCancelled(true);
			}
		}
	}
	
}
