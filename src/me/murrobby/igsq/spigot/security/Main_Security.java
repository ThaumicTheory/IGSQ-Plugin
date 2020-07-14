package me.murrobby.igsq.spigot.security;

import me.murrobby.igsq.spigot.Main_Spigot;

public class Main_Security 
{
	private static Main_Spigot plugin;
	public static int taskID = 0;
	public Main_Security(Main_Spigot plugin)
	{
		Main_Security.plugin = plugin;
		//Events run forever and cannot be turned off
		new BlockBreakEvent_Security(plugin);
		new PlayerCommandPreprocessEvent_Security(plugin);
		new ServerCommandEvent_Security(plugin);
		Start_Security();
	}
	public static void Start_Security() //Tasks will close if expert is turned off therefor they will need to be rerun for enabling expert
	{
		taskID++;
		new TwoFactorAuthentication_Security(plugin,taskID);
	}
}
