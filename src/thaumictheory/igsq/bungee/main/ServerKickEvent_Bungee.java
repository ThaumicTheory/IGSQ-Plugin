package thaumictheory.igsq.bungee.main;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.YamlWrapper;

public class ServerKickEvent_Bungee implements Listener
{
	public ServerKickEvent_Bungee()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void ServerKick_Bungee(net.md_5.bungee.api.event.ServerKickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getPlayer().isConnected()) 
			{
				if (!event.getPlayer().getServer().getInfo().getName().equalsIgnoreCase(YamlWrapper.getBackupRedirect())) 
				{
					event.setCancelled(true);
					event.getPlayer().connect(ProxyServer.getInstance().getServerInfo(YamlWrapper.getBackupRedirect()));
				}
			}
		}
	}
}
