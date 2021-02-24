package me.murrobby.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class BlockPistonRetractEvent_Protection implements Listener
{
	public BlockPistonRetractEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void BlockPistonExtend_Protection(org.bukkit.event.block.BlockPistonRetractEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			Location correction = event.getDirection().getDirection().toLocation(event.getBlock().getWorld());
			for(Block block : event.getBlocks()) if(Common_Protection.isProtected(event.getBlock().getLocation(), block.getLocation().add(correction)))
			{
				event.setCancelled(true);
				break;
			}
		}
	}
	
}
