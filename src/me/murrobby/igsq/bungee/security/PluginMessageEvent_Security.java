package me.murrobby.igsq.bungee.security;

import java.util.ArrayList;
import java.util.Arrays;

import me.murrobby.igsq.bungee.Common;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageEvent_Security implements Listener
{
	public PluginMessageEvent_Security()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void PluginMessage_Security(net.md_5.bungee.api.event.PluginMessageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getTag().equalsIgnoreCase("FML|HS")) 
			{
		        if(event.getData()[0] == 2 && event.getSender() instanceof ProxiedPlayer) //Modlist
				{
		        	ProxiedPlayer player = (ProxiedPlayer) event.getSender();
					applyModList(event.getData(),player);
				}
			}
			else if(event.getTag().equalsIgnoreCase("fml:handshake")) 
			{
				/*
		        if(event.getData()[0] == 2 && event.getSender() instanceof ProxiedPlayer) //Modlist
				{
					applyModList(event.getData(),(ProxiedPlayer)event.getSender());
				}
				*/
			}
		}
	}
	private void applyModList(byte[] modList,ProxiedPlayer player)
    {
        ArrayList<String> modData = new ArrayList<>();  
        for (int i=2; i < modList.length;)
        {
            int skipTo =  i+modList[i]+1;
        	String string = new String(Arrays.copyOfRange(modList,i+1,skipTo));
            modData.add(string);
            i = skipTo;
        }
        Common_Security.setPlayerModList(modData, player);
    }
}
