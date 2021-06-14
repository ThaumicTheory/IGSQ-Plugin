package thaumictheory.igsq.bungee.main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.Communication;
import thaumictheory.igsq.bungee.Yaml;

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
		        DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
				try
				{
			        int dataType = in.readInt();
			        String fileName = in.readUTF();
					String path = in.readUTF();
					switch(dataType) 
					{
						case 0: //String
				        	String dataString = in.readUTF();
				        	Yaml.updateField(path, fileName, dataString);
							break;
						case 1: //Int
				        	int dataInt = in.readInt();
				        	Yaml.updateField(path, fileName, dataInt);
							break;
						case 2: //Boolean
				        	boolean dataBool = in.readBoolean();
				        	Yaml.updateField(path, fileName, dataBool);
							break;
						default:
							break;
					}
					
			        /*
					if(data.equalsIgnoreCase("false") || data.equalsIgnoreCase("true")) Yaml.updateField(path, fileName, Boolean.valueOf(data));
					else if(Integer.getInteger(data) != null) Yaml.updateField(path, fileName, Integer.getInteger(data));
					else Yaml.updateField(path, fileName, data);
					*/
				}
				catch (IOException e)
				{
				}
			}
			if(channel.equals("igsq:ymlreq")) 
			{
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
				try
				{
					int dataType = in.readInt();
			        String fileName = in.readUTF();
					String path = in.readUTF();
					switch(dataType) 
					{
						case 0: //String
							Communication.sendConfigUpdate(path, fileName, Yaml.getFieldString(path, fileName));
							break;
						case 1: //Int
				        	Communication.sendConfigUpdate(path, fileName, Yaml.getFieldInt(path, fileName));
							break;
						case 2: //Boolean
				        	Communication.sendConfigUpdate(path, fileName, Yaml.getFieldBool(path, fileName));
							break;
						default:
							break;
					}
				}
				catch(Exception exception) 
				{
					
				}
			}
		}
	}
}
