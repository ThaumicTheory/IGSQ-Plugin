package me.murrobby.igsq.spigot.blockhunt;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.Messaging;

public class AsyncPlayerChatEvent_BlockHunt implements Listener
{
	public AsyncPlayerChatEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void AsyncPlayerChat_BlockHunt(AsyncPlayerChatEvent event) 
	{
		//Player Chat Capturing
		if(!event.isCancelled() && YamlWrapper.isBlockHunt()) 
		{
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(event.getPlayer());
			if(Common.isCurrentChatController("blockhunt", event.getPlayer())) 
			{
				
				String name = event.getPlayer().getName();
				if(yaml.isLinked()) name = yaml.getNickname();
				event.setCancelled(true);
				Game_BlockHunt game = Game_BlockHunt.getPlayersGame(event.getPlayer());
				if(game != null) 
				{
					Player_BlockHunt player = game.getPlayer(event.getPlayer());
					if(player.isHider()) for(Hider_BlockHunt hider : game.getHiders()) hider.getPlayer().sendMessage(Messaging.chatFormatter("&#0099ff[&#66ccffHider&#0099ff] &#66ccff" + name + " &#0099ff| &#66ccff") + event.getMessage());
					else if(player.isSeeker()) for(Seeker_BlockHunt seeker : game.getSeekers()) seeker.getPlayer().sendMessage(Messaging.chatFormatter("&#990000[&#ff0000Seeker&#990000] &#ff0000" + name + " &#990000| &#ff0000") + event.getMessage());
					else for(Player_BlockHunt selectedPlayer : game.getPlayers()) selectedPlayer.getPlayer().sendMessage(Messaging.chatFormatter("&#990000[&#ff0000Session&#990000] &#ff0000" + name + " &#990000| &#ff0000") + event.getMessage());
				}
			}
		}
	}
}
