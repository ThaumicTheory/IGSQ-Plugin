package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class InventoryClickEvent_BlockHunt implements Listener
{
	public InventoryClickEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void InventoryClick_BlockHunt(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				if(event.getWhoClicked() instanceof Player) 
				{
					Player player = (Player) event.getWhoClicked();
					Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
					if(gameInstance != null) 
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
}
