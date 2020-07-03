package me.murrobby.igsq.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

public class Chat implements Listener
{
	private Main plugin;
	public Chat(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) 
	{
		for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
		{
			if(Enabled(selectedPlayer) && SourceCheck(event.isAsynchronous(),selectedPlayer) && SelfCheck(selectedPlayer,event.getPlayer()) && event.getMessage().contains(selectedPlayer.getName()))
			{
				try
				{
					selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.valueOf(Common.getFieldString(selectedPlayer.getUniqueId() + ".notification.sound", "playerdata")), 1, Common.getFieldInt(selectedPlayer.getUniqueId() + ".notification.pitch", "playerdata"));
				}
				catch(Exception exception) 
				{
					Common.playerData.set(selectedPlayer.getUniqueId().toString() + ".notification",true);
					Common.playerData.set(selectedPlayer.getUniqueId().toString() + ".notification.enabled",true);
					Common.playerData.set(selectedPlayer.getUniqueId().toString() + ".notification.allowself",false);
					Common.playerData.set(selectedPlayer.getUniqueId().toString() + ".notification.allowconsole",false);
					selectedPlayer.sendMessage(Common.ChatColour("&cSomething Went Wrong when pinging You!"));
		        	Common.playerData.set(selectedPlayer.getUniqueId().toString() + ".notification.sound","BLOCK_NOTE_BLOCK_PLING");
		        	Common.playerData.set(selectedPlayer.getUniqueId().toString() + ".notification.pitch", 1f);
					try 
					{
						Common.playerData.save(Common.playerDataFile);
						selectedPlayer.sendMessage(Common.ChatColour("&eWe Have reset your notification settings!"));
					}
					catch(Exception exception1)
					{
						selectedPlayer.sendMessage(Common.ChatColour("&cWe could not reset your notification settings!"));
					}
				}
			}
		}
	}
	private boolean SourceCheck(boolean async,Player player) 
	{
		if(async)
		{
			return true;
		}
		else 
		{
			return Common.getFieldBool(player.getUniqueId() + ".notification.allowconsole", "playerdata");
		}
	}
	private boolean Enabled(Player player) 
	{
		return Common.getFieldBool(player.getUniqueId() + ".notification.enabled", "playerdata");
	}
	@SuppressWarnings("unused")
	private boolean SelfCheck(Player player, Player initiatedPlayer) 
	{
		if(player != initiatedPlayer) 
		{
			return true;
		}
		else 
		{
			return Common.getFieldBool(player.getUniqueId() + ".notification.allowself", "playerdata");
		}
		
	}
	
}
