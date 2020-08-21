package me.murrobby.igsq.spigot.main;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class AsyncPlayerChatEvent_Main implements Listener
{
	public AsyncPlayerChatEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void AsyncPlayerChat_Main(AsyncPlayerChatEvent event) 
	{
		//Runs only if luckperms is not detected
		if(!event.isCancelled()) 
		{
			if(!Common_Spigot.FilterChat(event.getMessage(), event.getPlayer())) 
			{
				event.setCancelled(true);
			}
			String username = Common_Spigot.getFieldString(event.getPlayer().getUniqueId() + ".discord.nickname", "playerdata");
			if (username.equals("")) username = event.getPlayer().getName();
			event.setFormat((Common_Spigot.ChatColour(Common_Spigot.GetMessage("message", "<server>",Common_Spigot.GetMessage("server"), "<prefix>","", "<player>", username, "<message>", event.getMessage()))));
		}
	}
	
}
