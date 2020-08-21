package me.murrobby.igsq.bungee.lp;

import me.murrobby.igsq.bungee.Main_Bungee;

public class Main_LP 
{
	public static int taskID = 0;
	public Main_LP(Main_Bungee plugin)
	{
		//Events run forever and cannot be turned off
		Start_Permission();
	}
	public static void Start_Permission() //Tasks will close if the permission system is turned off therefor they will need to be rerun for enabling the permission system
	{
		taskID++;
	}
}
