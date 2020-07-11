package me.murrobby.igsq.bungeecord;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectedEvent_Bungee implements Listener
{
	public ServerConnectedEvent_Bungee(Main_Bungee plugin)
	{
		BungeeCord.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void PostLogin_Bungee(net.md_5.bungee.api.event.ServerConnectedEvent event) 
	{
		//runs on connect to server and when switching servers
	}
	
}
