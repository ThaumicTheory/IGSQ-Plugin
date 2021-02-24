package me.murrobby.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class BlockPlaceEvent_Protection implements Listener
{
	public BlockPlaceEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void BlockPlace_Protection(org.bukkit.event.block.BlockPlaceEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			if(Common_Protection.isProtected(event.getPlayer(),event.getBlock().getLocation())) event.setCancelled(true);
		}
	}
	
}
