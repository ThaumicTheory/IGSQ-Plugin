package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;

public class PlayerQuitEvent_BlockHunt implements Listener
{
	public PlayerQuitEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerQuit_BlockHunt(org.bukkit.event.player.PlayerQuitEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck()) 
		{
			Game_BlockHunt playersGame = Game_BlockHunt.getPlayersGame(event.getPlayer());
			if(playersGame != null) 
			{
				playersGame.cleanup(event.getPlayer());
			}
		}
	}
	
}
