package me.murrobby.igsq.bungee.security;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectEvent_Security implements Listener
{
	//https://github.com/Mas281/ForgeModBlocker/blob/master/src/main/java/me/itsmas/forgemodblocker/messaging/MessageListener.java
	public ServerConnectEvent_Security(Main_Bungee plugin)
	{
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void ServerConnect_Security(net.md_5.bungee.api.event.ServerConnectEvent event) 
	{
		if(event.getPlayer().getPendingConnection().getVersion() <= 340) //Legacy
		{
			//event.getPlayer().sendData("FML", new byte[] {0, 0, 0, 0, 0, 2});
			event.getPlayer().sendData("FML|HS", new byte[] {-2, 0});
			event.getPlayer().sendData("FML|HS", new byte[] {0, 2, 0, 0, 0, 0});
			event.getPlayer().sendData("FML|HS", new byte[] {2, 0, 0, 0, 0});
		}
		else //1.13 +
		{
			try
			{
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(stream);
				out.writeInt(0);
				event.getPlayer().sendData("fml:handshake", stream.toByteArray());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
