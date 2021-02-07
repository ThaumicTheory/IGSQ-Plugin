package me.murrobby.igsq.spigot.expert;

import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class UI_Expert 
{
	private Player player;
	private String currentChunkName = "Wilderness";

	public UI_Expert(Player player) 
	{
		this.player = player;
	}
	public void display() 
	{
		player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFF")));
	}
	
	private void chunk() 
	{
		
	}
}
