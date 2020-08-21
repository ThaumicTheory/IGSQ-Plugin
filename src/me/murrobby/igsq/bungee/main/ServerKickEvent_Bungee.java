package me.murrobby.igsq.bungee.main;

import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerKickEvent_Bungee implements Listener
{
	public ServerKickEvent_Bungee(Main_Bungee plugin)
	{
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void PostLogin_Bungee(net.md_5.bungee.api.event.ServerKickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if (!event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase("hub")) 
			{
				event.setCancelled(true);
				event.getPlayer().connect(ProxyServer.getInstance().getServerInfo("hub"));
			}
		}
	}
}
