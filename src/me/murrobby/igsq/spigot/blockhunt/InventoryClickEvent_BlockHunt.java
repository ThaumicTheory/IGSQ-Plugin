package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class InventoryClickEvent_BlockHunt implements Listener
{
	public InventoryClickEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void InventoryClick_BlockHunt(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getWhoClicked() instanceof Player) 
			{
				Player player = (Player) event.getWhoClicked();
				if(Common_BlockHunt.isPlayer(player)) 
				{
					event.setCancelled(true);
				}
			}
		}
	}
	
}
