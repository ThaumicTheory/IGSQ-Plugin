package thaumictheory.igsq.bungee.main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.Communication;
import thaumictheory.igsq.shared.IGSQ;

public class PluginMessageEvent_Bungee implements Listener
{
	public PluginMessageEvent_Bungee()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void PluginMessage_Bungee(net.md_5.bungee.api.event.PluginMessageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			String channel = event.getTag();
			if(channel.equals("igsq:yml")) 
			{
				try
				{
			        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(event.getData()));
			        String fileName = in.readUTF();
					String path = in.readUTF();
			        Object data = in.readObject();
		        	IGSQ.getYaml().setField(path, fileName, data);
					
				}
				catch (Exception e) {}
			}
			else if(channel.equals("igsq:ymlreq")) 
			{
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
				try
				{
			        String fileName = in.readUTF();
					String path = in.readUTF();
					Communication.sendConfigUpdate(path, fileName, IGSQ.getYaml().getField(path, fileName));
				}
				catch(Exception e) {}
			}
		}
	}
}
