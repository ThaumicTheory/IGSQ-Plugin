package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Main_Spigot;

public class EntityChangeBlockEvent_BlockHunt implements Listener
{
	public EntityChangeBlockEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityChangeBlock_BlockHunt(org.bukkit.event.entity.EntityChangeBlockEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				if(event.getEntity() instanceof FallingBlock) 
				{
					if(Common_BlockHunt.isBlockPlayable(event.getTo())) 
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
}
