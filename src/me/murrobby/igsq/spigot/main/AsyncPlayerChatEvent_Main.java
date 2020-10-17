package me.murrobby.igsq.spigot.main;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
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
			if(!Common.filterChat(event.getMessage(), event.getPlayer())) 
			{
				event.setCancelled(true);
			}
			if(Common.isCurrentChatController("main", event.getPlayer())) 
			{
				String username = Yaml.getFieldString(event.getPlayer().getUniqueId() + ".discord.nickname", "player");
				if (username.equals("")) username = event.getPlayer().getName();
				event.setFormat((Messaging.chatFormatter(Messaging.getFormattedMessage("message", new String[] {"<server>",Yaml.getFieldString("server","internal"), "<prefix>","", "<player>", username,"<suffix>","", "<message>", event.getMessage()}))));
			}
		}
	}
	
}
