package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class FoodLevelChangeEvent_BlockHunt implements Listener
{
	public FoodLevelChangeEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void FoodLevelChange_BlockHunt(org.bukkit.event.entity.FoodLevelChangeEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				if(event.getEntity() instanceof Player) 
				{
					Player player = (Player) event.getEntity();
					if(Common_BlockHunt.isPlayer(player)) 
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
}
