package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.murrobby.igsq.spigot.Main_Spigot;

public class PlayerTeleportEvent_BlockHunt implements Listener
{
	public PlayerTeleportEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerTeleport_BlockHunt(org.bukkit.event.player.PlayerTeleportEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(Common_BlockHunt.blockhuntCheck()) 
			{
				if(Common_BlockHunt.isHider(event.getPlayer())) 
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
