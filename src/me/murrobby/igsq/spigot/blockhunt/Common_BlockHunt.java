package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.entity.Player;
import me.murrobby.igsq.spigot.Common_Spigot;

public class Common_BlockHunt 
{
	public static int numberPerTraitor = 6;
	
	public static Player[] seekers = {};
	public static Player[] hiders = {};
	
	public static Player[] players = {};
	public static int[][] colours = {{255,0,0},{0,255,0},{0,0,255},{255,255,0},{255,0,255},{0,255,255},{0,0,0},{255,255,255},{126,0,199},{150,150,150},{255,100,0},{0,150,0},{0,255,200},{255,166,255},{150,50,0}};
	public static int playerCount;
	public static void start()
	{
		OnGameStart_BlockHunt.start();
	}
	public static int getTraitorCount() 
	{
		return (int)(playerCount/numberPerTraitor)+1;
	}
	
	public static int getUniqueId(Player player) 
	{
		for(int i = 0;i < playerCount;i++) 
		{
			if(player.getUniqueId().equals(players[i].getUniqueId())) 
			{
				return i;
			}
		}
		return -1;
	}
    public static Boolean blockhuntCheck() 
    {
    	return Common_Spigot.getFieldBool("GAMEPLAY.blockhunt", "config");
    }
    public static Boolean isSeeker(Player player) 
    {
    	for(Player selectedPlayer :seekers) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public static Boolean isHider(Player player) 
    {
    	for(Player selectedPlayer :hiders) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public static Boolean isPlayer(Player player) 
    {
    	for(Player selectedPlayer :players) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
}
