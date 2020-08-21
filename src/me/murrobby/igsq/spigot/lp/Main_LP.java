package me.murrobby.igsq.spigot.lp;

import me.murrobby.igsq.spigot.Main_Spigot;

public class Main_LP 
{
	public static int taskID = 0;
	private static Boolean nameTagEdit;
	private static Main_Spigot plugin;
	public Main_LP(Main_Spigot plugin,Boolean nameTagEdit)
	{
		Main_LP.nameTagEdit = nameTagEdit;
		Main_LP.plugin = plugin;
		//Events run forever and cannot be turned off
		new AsyncPlayerChatEvent_LP(plugin);
		Start_LuckPerms();
	}
	public static void Start_LuckPerms() //Tasks will close if LP is turned off therefor they will need to be rerun for enabling lp
	{
		taskID++;
		if(nameTagEdit) new NametagEdit_LP(plugin,taskID);
	}
}
