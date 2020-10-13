package me.murrobby.igsq.spigot.lp;

import me.murrobby.igsq.spigot.Spigot;

public class Main_LP 
{
	public static int taskID = 0;
	private static Boolean nameTagEdit;
	public Main_LP(Spigot plugin,Boolean nameTagEdit)
	{
		Main_LP.nameTagEdit = nameTagEdit;
		//Events run forever and cannot be turned off
		new AsyncPlayerChatEvent_LP();
		Start_LuckPerms();
	}
	public static void Start_LuckPerms() //Tasks will close if LP is turned off therefor they will need to be rerun for enabling lp
	{
		taskID++;
		if(nameTagEdit) new NametagEdit_LP(taskID);
	}
}
