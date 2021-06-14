package thaumictheory.igsq.bungee.security;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.Messaging;
import thaumictheory.igsq.bungee.YamlWrapper;

public class PreLoginEvent_Security implements Listener
{
	public PreLoginEvent_Security()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void PreLogin_Security(net.md_5.bungee.api.event.PreLoginEvent event) 
	{
		//Integer highestProtocol = Integer.parseInt(Yaml.getFieldString("SUPPORT.protocol.highest", "config"));
		//Integer lowestProtocol = Integer.parseInt(Yaml.getFieldString("SUPPORT.protocol.lowest", "config"));
		int highestProtocol = YamlWrapper.getHighestProtocol();
		int lowestProtocol = YamlWrapper.getLowestProtocol();
		int playerProtocol = event.getConnection().getVersion();
		if(playerProtocol < lowestProtocol && lowestProtocol != -1) 
		{
			event.setCancelReason(TextComponent.fromLegacyText(Messaging.chatFormatter("&cYour Client running protocol "+ playerProtocol + ", is lower than the lowest supported protocol "+ lowestProtocol +".")));
			event.setCancelled(true);
		}
		else if(playerProtocol > highestProtocol && highestProtocol != -1) 
		{
			event.setCancelReason(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000Your Client running protocol "+ playerProtocol + ", is higher than the highest supported protocol "+ highestProtocol +".")));
			event.setCancelled(true);
		}
	}
	
}
