package thaumictheory.igsq.bungee.security;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import thaumictheory.igsq.bungee.Common;

public class TabCompleteEvent_Security implements Listener
{
	public TabCompleteEvent_Security()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void TabComplete_Security(net.md_5.bungee.api.event.TabCompleteEvent event) 
	{
		//Runs on bungee whenever a chat message is sent
		if(event.getSender() instanceof ProxiedPlayer) 
		{
			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			if((!Common_Security.isWhitelistedCommand2FA(event.getCursor(),player)) && Common_Security.securityProtectionQuery(player)) event.setCancelled(true);
		}
	}
}
