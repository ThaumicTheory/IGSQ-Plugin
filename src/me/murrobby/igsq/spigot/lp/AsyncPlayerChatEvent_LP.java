package me.murrobby.igsq.spigot.lp;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
import me.murrobby.igsq.spigot.Messaging;

public class AsyncPlayerChatEvent_LP implements Listener
{
	public AsyncPlayerChatEvent_LP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void AsyncPlayerChat_Main(AsyncPlayerChatEvent event) 
	{
		//Player Chat Capturing
		if(!event.isCancelled()) 
		{
			if(Common.isCurrentChatController("mainlp", event.getPlayer())) 
			{
				String username = Yaml.getFieldString(event.getPlayer().getUniqueId() + ".discord.nickname", "player");
				if (username.equals("")) username = event.getPlayer().getName();
				event.setFormat((Messaging.getFormattedMessage("message", new String[] {"<server>",Yaml.getFieldString("server","internal"), "<prefix>",Common_LP.getPrefix(event.getPlayer()) , "<player>", username,"<suffix>",Common_Shared.removeNull(Common_LP.getSuffix(event.getPlayer())), "<message>", event.getMessage()})));
			}
		}
	}
}
