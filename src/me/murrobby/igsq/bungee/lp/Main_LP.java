package me.murrobby.igsq.bungee.lp;

import me.murrobby.igsq.bungee.Main_Bungee;

public class Main_LP 
{
	public static int taskID = 0;
	private static Main_Bungee plugin;
	public Main_LP(Main_Bungee plugin)
	{
		Main_LP.plugin = plugin;
		//Events run forever and cannot be turned off
		Start_LuckPerms();
	}
	public static void Start_LuckPerms() //Tasks will close if the permission system is turned off therefor they will need to be rerun for enabling the permission system
	{
		taskID++;
		new Rank_LP(plugin,taskID);
	}
}
