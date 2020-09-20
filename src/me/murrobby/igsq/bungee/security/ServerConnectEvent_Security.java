package me.murrobby.igsq.bungee.security;

import java.util.concurrent.TimeUnit;

import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerConnectEvent.Reason;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectEvent_Security implements Listener
{
	private Main_Bungee plugin;
	public ServerConnectEvent_Security(Main_Bungee plugin)
	{
		
		this.plugin = plugin;
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void ServerConnect_Security(net.md_5.bungee.api.event.ServerConnectEvent event) 
	{
		if(event.getReason().equals(Reason.JOIN_PROXY)) 
		{
			Common_Security.setPlayerModList(new String[]{}, event.getPlayer());
			if(event.getPlayer().getPendingConnection().getVersion() <= 340) //Legacy
			{
				plugin.getProxy().registerChannel("FML|HS");
				plugin.getProxy().getScheduler().schedule(plugin, new Runnable() 
		    	{

					@Override
					public void run() 
					{
						event.getPlayer().sendData("FML|HS", new byte[] {-2, 0});
						event.getPlayer().sendData("FML|HS", new byte[] {0, 2, 0, 0, 0, 0});
					} 		
		    	}, 150, TimeUnit.MILLISECONDS);
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
