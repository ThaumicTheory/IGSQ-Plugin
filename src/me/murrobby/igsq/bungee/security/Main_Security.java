package me.murrobby.igsq.bungee.security;

public class Main_Security 
{
	public static int taskID = 0;
	public Main_Security()
	{
		//Events run forever and cannot be turned off
		new PostLoginEvent_Security();
		new PreLoginEvent_Security();
		new ChatEvent_Security();
		new TabCompleteEvent_Security();
		
		new PluginMessageEvent_Security();
		new ServerConnectEvent_Security();
		Start_Security();
	}
	public static void Start_Security() //Tasks will need to be closed if security is turned off therefor they will need to be rerun for enabling security
	{
		taskID++;
		new TwoFactorAuthentication_Security(taskID);
		new DiscordLink_Security(taskID);
	}
}
