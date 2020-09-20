package me.murrobby.igsq.spigot.game;

import org.bukkit.entity.Player;

public class Common_Game 
{
	public static int numberPerTraitor = 6;
	
	public static Player[] traitors = {};
	public static Player[] innocents = {};
	
	public static Player[] players = {};
	public static int[][] colours = {{255,0,0},{0,255,0},{0,0,255},{255,255,0},{255,0,255},{0,255,255},{0,0,0},{255,255,255},{126,0,199},{150,150,150},{255,100,0},{0,150,0},{0,255,200},{255,166,255},{150,50,0}};
	public static int playerCount;
	public static void start()
	{
		OnGameStart_Game.start();
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
}
