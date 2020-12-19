package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class BlockDamageEvent_BlockHunt implements Listener
{
	public BlockDamageEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void BlockDamage_BlockHunt(org.bukkit.event.block.BlockDamageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(YamlWrapper.isBlockHunt()) 
			{
				if(Game_BlockHunt.getPlayersGame(event.getPlayer()) != null) 
				{
					event.setCancelled(true);
				}
			}
		}
	}
	
}
