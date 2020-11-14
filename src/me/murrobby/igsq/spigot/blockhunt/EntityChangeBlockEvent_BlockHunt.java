package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class EntityChangeBlockEvent_BlockHunt implements Listener
{
	public EntityChangeBlockEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
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
					event.setCancelled(true);
				}
			}
		}
	}
	
}
