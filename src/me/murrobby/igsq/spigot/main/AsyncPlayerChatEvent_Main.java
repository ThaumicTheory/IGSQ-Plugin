package me.murrobby.igsq.spigot.main;

import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Database_Spigot;
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
			String username = event.getPlayer().getName();
			if(Database_Spigot.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ event.getPlayer().getUniqueId().toString() +"' AND current_status = 'linked';") == 1) 
			{
				ResultSet username_RS = Database_Spigot.QueryCommand("SELECT username FROM discord_accounts WHERE id = (SELECT id FROM linked_accounts WHERE uuid = '"+ event.getPlayer().getUniqueId().toString() +"');");
				try
				{
					username_RS.next();
					username = username_RS.getString(1);
				}
				catch (Exception exception)
				{
					
					exception.printStackTrace();
				}
			}
			event.setFormat((Common_Spigot.ChatColour(Common_Spigot.GetMessage("message", "<server>",Common_Spigot.GetMessage("server"), "<prefix>","", "<player>", username, "<message>", event.getMessage()))));
		}
	}
	
}
