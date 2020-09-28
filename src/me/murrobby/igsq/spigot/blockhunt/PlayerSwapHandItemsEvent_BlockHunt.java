package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class PlayerSwapHandItemsEvent_BlockHunt implements Listener
{
	public PlayerSwapHandItemsEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerSwapHandItems_Security(org.bukkit.event.player.PlayerSwapHandItemsEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.isPlayer(event.getPlayer())) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
