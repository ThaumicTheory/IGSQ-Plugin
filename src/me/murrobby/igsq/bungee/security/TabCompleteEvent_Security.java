package me.murrobby.igsq.bungee.security;

import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabCompleteEvent_Security implements Listener
{
	public TabCompleteEvent_Security(Main_Bungee plugin)
	{
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void TabComplete_Security(net.md_5.bungee.api.event.TabCompleteEvent event) 
	{
		//Runs on bungee whenever a chat message is sent
		if(event.getSender() instanceof ProxiedPlayer) 
		{
			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			if((!Common_Security.IsWhitelistedCommand2FA(event.getCursor(),player)) && Common_Security.SecurityProtectionQuery(player)) event.setCancelled(true);
		}
	}
}
