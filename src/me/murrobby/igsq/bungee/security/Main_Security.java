package me.murrobby.igsq.bungee.security;

import me.murrobby.igsq.bungee.Main_Bungee;

public class Main_Security 
{
	private static Main_Bungee plugin;
	public static int taskID = 0;
	public Main_Security(Main_Bungee plugin)
	{
		Main_Security.plugin = plugin;
		//Events run forever and cannot be turned off
		new PostLoginEvent_Security(plugin);
		new PreLoginEvent_Security(plugin);
		new ChatEvent_Security(plugin);
		new TabCompleteEvent_Security(plugin);
		
		new PluginMessageEvent_Security(plugin);
		new ServerConnectEvent_Security(plugin);
		Start_Security();
	}
	public static void Start_Security() //Tasks will need to be closed if security is turned off therefor they will need to be rerun for enabling security
	{
		taskID++;
		new TwoFactorAuthentication_Security(plugin,taskID);
		new DiscordLink_Security(plugin,taskID);
	}
}
