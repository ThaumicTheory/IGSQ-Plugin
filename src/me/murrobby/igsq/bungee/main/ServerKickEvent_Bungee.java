package me.murrobby.igsq.bungee.main;

import me.murrobby.igsq.bungee.Common;
import me.murrobby.igsq.bungee.Yaml;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerKickEvent_Bungee implements Listener
{
	public ServerKickEvent_Bungee()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void ServerKick_Bungee(net.md_5.bungee.api.event.ServerKickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getPlayer().isConnected()) 
			{
				if (!event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase(Yaml.getFieldString("SERVER.backupredirect", "config"))) 
				{
					event.setCancelled(true);
					event.getPlayer().connect(ProxyServer.getInstance().getServerInfo(Yaml.getFieldString("SERVER.backupredirect", "config")));
				}
			}
		}
	}
}
