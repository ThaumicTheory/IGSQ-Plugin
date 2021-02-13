package me.murrobby.igsq.spigot.expert;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class UI_Expert 
{
	public static List<UI_Expert> uis = new ArrayList<>();
	enum Relationship 
	{
	    NONE("&#009900"),
	    FACTION("&#00FF00"),
	    ALLIED("&#ff61f4"),
	    NEUTRAL("&#AAAAAA"),
	    CONSIDERED_ENEMY("&#444444"),
	    ENEMY("&#FF0000");

		private String colour;
		Relationship(String colour) 
		{
			this.colour = colour;
		}
		public String getColour() 
		{
			return colour;
		}
	}
	enum State 
	{
	    CHUNK;
	}

	private Player player;
	private String chunkName = "Wilderness";
	private Relationship relationship = Relationship.NONE;
	private State state = State.CHUNK;

	public UI_Expert(Player player) 
	{
		this.player = player;
		uis.add(this);
	}
	public void display() 
	{
		String message = "";
		if(state.equals(State.CHUNK)) 
		{
			chunk();
			message = relationship.getColour() + chunkName;
		}
		
		player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messaging.chatFormatter(message)));
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
	public Player getPlayer() 
	{
		return player;
	}
	public void delete() 
	{
		uis.remove(this);
	}
	
	public static UI_Expert getUIFromPlayer(Player player) 
	{
		for(UI_Expert ui : uis) if(ui.getPlayer().getUniqueId().equals(player.getUniqueId())) return ui;
		return null;
	}
	
	public static List<UI_Expert> getUIs() 
	{
		return uis;
	}
}
