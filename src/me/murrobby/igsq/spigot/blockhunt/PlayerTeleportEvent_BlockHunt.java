package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.murrobby.igsq.spigot.Common;

public class PlayerTeleportEvent_BlockHunt implements Listener
{
	public PlayerTeleportEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerTeleport_BlockHunt(org.bukkit.event.player.PlayerTeleportEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				Game_BlockHunt playersGame = Game_BlockHunt.getPlayersGame(event.getPlayer());
				if(playersGame != null) 
				{
					if(event.getCause().equals(TeleportCause.ENDER_PEARL))
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
}
