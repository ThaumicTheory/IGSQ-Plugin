package me.murrobby.igsq.spigot.expert;

import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class UI_Expert 
{
	enum Relationship 
	{
	    NONE,
	    FACTION,
	    ALLIED,
	    NEUTRAL,
	    CONSIDERED_ENEMY,
	    ENEMY;
	}

	private Player player;
	private String chunkName = "Wilderness";
	private Relationship relationship = Relationship.NONE;

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
		Chunk_Expert chunk = Chunk_Expert.getChunkFromLocation(player.getLocation());
		if(chunk == null) 
		{
			chunkName = "Wilderness";
			relationship = Relationship.NONE;
			return;
		}
		chunkName = chunk.getOwner().getName();
		if(!Team_Expert.isInATeam(player)) 
		{
			relationship = Relationship.NONE;
			return;
		}
		if(chunk.getOwner().equals(Team_Expert.getPlayersTeam(player))) 
		{
			relationship = Relationship.FACTION;
			return;
		}
		if(chunk.getOwner().isAlly(Team_Expert.getPlayersTeam(player))) 
		{
			relationship = Relationship.ALLIED;
			return;
		}
		if(Team_Expert.getPlayersTeam(player).isEnemy(chunk.getOwner())) 
		{
			relationship = Relationship.ENEMY;
			return;
		}
		if(chunk.getOwner().isEnemy(Team_Expert.getPlayersTeam(player))) 
		{
			relationship = Relationship.CONSIDERED_ENEMY;
			return;
		}
		relationship = Relationship.NEUTRAL;
		
	}
}
