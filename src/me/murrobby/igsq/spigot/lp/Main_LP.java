package me.murrobby.igsq.spigot.lp;

import me.murrobby.igsq.spigot.Spigot;

public class Main_LP 
{
	public static int taskID = 0;
	public Main_LP(Spigot plugin)
	{
		//Events run forever and cannot be turned off
		new AsyncPlayerChatEvent_LP();
		new PlayServerPlayerInfo_LP();
		Start_LuckPerms();
	}
	public static void Start_LuckPerms() //Tasks will close if LP is turned off therefor they will need to be rerun for enabling lp
	{
		taskID++;
		new CustomTag_LP(taskID);
	}
}
