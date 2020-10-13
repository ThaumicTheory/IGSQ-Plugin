package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.entity.Player;

public class OnGameEnd_BlockHunt
{
	public OnGameEnd_BlockHunt()
	{
		end();
	}
	public static void end()
	{
		for(Player player : Common_BlockHunt.players)
		{
			Common_BlockHunt.cleanup(player);
			
		}
		Common_BlockHunt.cleanup();
	}
}
