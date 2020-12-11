package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayerInteractEntityEvent_BlockHunt implements Listener
{
	public PlayerInteractEntityEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerInteractEntity_BlockHunt(org.bukkit.event.player.PlayerInteractEntityEvent event) 
	{
		if(YamlWrapper.isBlockHunt() && (!event.isCancelled()))
		{
			if(Game_BlockHunt.getPlayersGame(event.getPlayer()) != null) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
