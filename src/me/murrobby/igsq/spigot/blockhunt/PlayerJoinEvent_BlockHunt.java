package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerJoinEvent_BlockHunt implements Listener
{
	public PlayerJoinEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin_BlockHunt(org.bukkit.event.player.PlayerJoinEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck()) 
		{
			Player_BlockHunt player = Player_BlockHunt.getPlayer(event.getPlayer());
			if(player != null) 
			{
				player.cleanup();
				player.getGame().removePlayer(event.getPlayer());
			}
			event.getPlayer().teleport(Map_BlockHunt.getHubLocation());
		}
	}
	
}
