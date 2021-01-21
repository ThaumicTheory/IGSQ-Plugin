package me.murrobby.igsq.bungee.security;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import me.murrobby.igsq.bungee.Common;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerConnectEvent.Reason;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectEvent_Security implements Listener
{
	public ServerConnectEvent_Security()
	{
		
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void ServerConnect_Security(net.md_5.bungee.api.event.ServerConnectEvent event) 
	{
		if(event.getReason().equals(Reason.JOIN_PROXY)) 
		{
			Common_Security.setPlayerModList(new ArrayList<String>(), event.getPlayer());
			if(event.getPlayer().getPendingConnection().getVersion() <= 340) //Legacy
			{
				event.setTarget(ProxyServer.getInstance().getServerInfo("modded"));
				Common.bungee.getProxy().registerChannel("FML|HS");
				Common.bungee.getProxy().getScheduler().schedule(Common.bungee, new Runnable() 
		    	{

					@Override
					public void run() 
					{
						event.getPlayer().sendData("FML|HS", new byte[] {-2, 0});
						event.getPlayer().sendData("FML|HS", new byte[] {0, 2, 0, 0, 0, 0});
					} 		
		    	}, 450, TimeUnit.MILLISECONDS);
			}
			else //1.13 +
			{
				/*
				plugin.getProxy().registerChannel("fml:handshake");
				plugin.getProxy().getScheduler().schedule(plugin, new Runnable() 
		    	{

					@Override
					public void run() 
					{
						event.getPlayer().sendData("fml:handshake", new byte[] {-2, 0});
						event.getPlayer().sendData("fml:handshake", new byte[] {0, 2, 0, 0, 0, 0});
					} 		
		    	}, 150, TimeUnit.MILLISECONDS);
		    	*/
			}
		}
	}
}
