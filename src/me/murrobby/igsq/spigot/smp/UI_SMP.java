package me.murrobby.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class UI_SMP 
{
	public static List<UI_SMP> uis = new ArrayList<>();
	enum Relationship 
	{
	    NONE("&#009900"),
	    FACTION("&#00FF00"),
	    ALLIED("&#ff61f4"),
	    NEUTRAL("&#AAAAAA"),
	    ADMIN_CLAIM("&#FFFF00"),
	    ADMIN_CLAIM_ACCESS("&#FFFFFF"),
	    PROTECTED("&#FF00FF"),
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

	public UI_SMP(Player player) 
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
		Chunk_SMP chunk = Chunk_SMP.getChunkFromLocation(player.getLocation());
		if(chunk == null) 
		{
			chunkName = "Wilderness";
			relationship = Relationship.NONE;
			return;
		}
		chunkName = chunk.getOwner().getName();
		if(chunk.getOwner().equals(Common_SMP.getAdminTeam())) 
		{
			relationship = Relationship.ADMIN_CLAIM;
			chunkName = "Spawn";
			if(player.hasPermission("igsq.adminclaim")) relationship = Relationship.ADMIN_CLAIM_ACCESS;
			return;
		}
		if(!Chunk_SMP.isChunkClaimable(player)) 
		{
			chunkName = "Protected";
			relationship = Relationship.PROTECTED;
		}
		if(chunk.getOwner().equals(Team_SMP.getPlayersTeam(player))) 
		{
			relationship = Relationship.FACTION;
			return;
		}
		if(chunk.getOwner().isAlly(Team_SMP.getPlayersTeam(player))) 
		{
			relationship = Relationship.ALLIED;
			return;
		}
		if(Team_SMP.getPlayersTeam(player).isEnemy(chunk.getOwner())) 
		{
			relationship = Relationship.ENEMY;
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
	
	public static UI_SMP getUIFromPlayer(Player player) 
	{
		for(UI_SMP ui : uis) if(ui.getPlayer().getUniqueId().equals(player.getUniqueId())) return ui;
		return null;
	}
	
	public static List<UI_SMP> getUIs() 
	{
		return uis;
	}
}
