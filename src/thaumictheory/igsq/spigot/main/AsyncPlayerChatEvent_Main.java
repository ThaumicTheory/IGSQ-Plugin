package thaumictheory.igsq.spigot.main;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import thaumictheory.igsq.shared.Role;
import thaumictheory.igsq.shared.RoleType;
import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class AsyncPlayerChatEvent_Main implements Listener
{
	public AsyncPlayerChatEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void AsyncPlayerChat_Main(AsyncPlayerChatEvent event) 
	{
		if(!event.isCancelled()) 
		{
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(event.getPlayer().getUniqueId());
			if(!Common.filterChat(event.getMessage(), event.getPlayer())) 
			{
				event.setCancelled(true);
			}
			if(Common.isCurrentChatController("main", event.getPlayer())) 
			{
				String name = event.getPlayer().getName();
				if(yaml.isLinked()) name = yaml.getNickname();
				event.setFormat((Messaging.chatFormatter(Messaging.getFormattedMessage("message", new String[] {"<server>",YamlWrapper.getServerName(), "<prefix>",Role.getHighestChatPrefix(event.getPlayer().getUniqueId(),RoleType.EITHER), "<player>", name,"<suffix>",Role.getHighestChatSuffix(event.getPlayer().getUniqueId(),RoleType.EITHER), "<message>", event.getMessage()}))));
			}
		}
	}
	
}
