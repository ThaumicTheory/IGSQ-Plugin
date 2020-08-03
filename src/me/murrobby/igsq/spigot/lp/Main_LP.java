package me.murrobby.igsq.spigot.lp;

import me.murrobby.igsq.spigot.Main_Spigot;

public class Main_LP 
{
	public static int taskID = 0;
	public Main_LP(Main_Spigot plugin)
	{
		//Events run forever and cannot be turned off
		new AsyncPlayerChatEvent_LP(plugin);
		Start_Permission();
	}
	public static void Start_Permission() //Tasks will close if expert is turned off therefor they will need to be rerun for enabling security
	{
		taskID++;
	}
}
