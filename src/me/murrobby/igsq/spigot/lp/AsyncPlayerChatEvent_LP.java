package me.murrobby.igsq.spigot.lp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class AsyncPlayerChatEvent_LP implements Listener
{
	public AsyncPlayerChatEvent_LP(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void AsyncPlayerChat_Main(AsyncPlayerChatEvent event) 
	{
		//Player Chat Capturing
		if(!event.isCancelled()) 
		{
			if(!Common_Spigot.FilterChat(event.getMessage(), event.getPlayer())) 
			{
				event.setCancelled(true);
			}
			event.setFormat((Common_Spigot.ChatColour(Common_Spigot.GetMessage("message", "<server>",Common_Spigot.GetMessage("server"), "<prefix>",Common_LP.GetPrefix(event.getPlayer()) , "<player>", event.getPlayer().getDisplayName(), "<message>", event.getMessage()))));
		}
	}
	
}
