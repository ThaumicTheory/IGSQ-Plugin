package me.murrobby.igsq.spigot.main;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.Messaging;

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
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(event.getPlayer());
			if(!Common.filterChat(event.getMessage(), event.getPlayer())) 
			{
				event.setCancelled(true);
			}
			if(Common.isCurrentChatController("main", event.getPlayer())) 
			{
				String name = event.getPlayer().getName();
				if(yaml.isLinked()) name = yaml.getNickname();
				event.setFormat((Messaging.chatFormatter(Messaging.getFormattedMessage("message", new String[] {"<server>",YamlWrapper.getServerName(), "<prefix>","", "<player>", name,"<suffix>","", "<message>", event.getMessage()}))));
			}
		}
	}
	
}
