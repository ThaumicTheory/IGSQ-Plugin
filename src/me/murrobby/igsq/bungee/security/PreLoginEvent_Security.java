package me.murrobby.igsq.bungee.security;

import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginEvent_Security implements Listener
{
	public PreLoginEvent_Security(Main_Bungee plugin)
	{
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void PreLogin_Security(net.md_5.bungee.api.event.PreLoginEvent event) 
	{
		Integer highestProtocol = Integer.parseInt(Common_Bungee.GetFieldString("SUPPORT.protocol.highest", "config"));
		Integer lowestProtocol = Integer.parseInt(Common_Bungee.GetFieldString("SUPPORT.protocol.lowest", "config"));
		int playerProtocol = event.getConnection().getVersion();
		if(playerProtocol < lowestProtocol && lowestProtocol != -1) 
		{
			event.setCancelReason(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&cYour Client running protocol "+ playerProtocol + ", is lower than the lowest supported protocol "+ lowestProtocol +".")));
			event.setCancelled(true);
		}
		else if(playerProtocol > highestProtocol && highestProtocol != -1) 
		{
			event.setCancelReason(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#CD0000Your Client running protocol "+ playerProtocol + ", is higher than the highest supported protocol "+ highestProtocol +".")));
			event.setCancelled(true);
		}
	}
	
}
