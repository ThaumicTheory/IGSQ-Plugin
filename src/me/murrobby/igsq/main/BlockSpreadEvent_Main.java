package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.Main_Spigot;
import java.util.Random;

public class BlockSpreadEvent_Main implements Listener
{
	Random random = new Random();
	public BlockSpreadEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void BlockSpread_Main(org.bukkit.event.block.BlockSpreadEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
	
}
