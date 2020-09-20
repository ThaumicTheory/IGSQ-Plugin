package me.murrobby.igsq.spigot.game;

import me.murrobby.igsq.spigot.Main_Spigot;

public class Main_Game 
{
	public static Main_Spigot plugin;
	public static int taskID = 0;
	public Main_Game(Main_Spigot plugin)
	{
		Main_Game.plugin = plugin;
		//Events run forever and cannot be turned off
		Start_Game();
	}
	public static void Start_Game() //Tasks will close if the game is turned off therefor they will need to be rerun for enabling the game
	{
		taskID++;
	}
}
