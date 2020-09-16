package me.murrobby.igsq.bungee.security;

import java.util.Arrays;

import me.murrobby.igsq.bungee.Main_Bungee;
import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageEvent_Security implements Listener
{
	public PluginMessageEvent_Security(Main_Bungee plugin)
	{
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
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
					applyModList(event.getData(),(ProxiedPlayer)event.getSender());
				}
			}
			else if(event.getTag().equalsIgnoreCase("fml:handshake")) 
			{
		        if(event.getData()[0] == 2 && event.getSender() instanceof ProxiedPlayer) //Modlist
				{
					applyModList(event.getData(),(ProxiedPlayer)event.getSender());
				}
			}
		}
	}
	private void applyModList(byte[] modList,ProxiedPlayer player)
    {
        String[] modData = new String[0];  
        for (int i=2; i < modList.length;)
        {
            int skipTo =  i+modList[i]+1;
        	String string = new String(Arrays.copyOfRange(modList,i+1,skipTo));
            modData = Common_Shared.append(modData,string);
            i = skipTo;
        }
        Common_Security.setPlayerModList(modData, player);
    }
}