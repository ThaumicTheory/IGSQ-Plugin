package me.murrobby.igsq.spigot.expert.protection;

import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.expert.Chunk_Expert;
import me.murrobby.igsq.spigot.expert.Team_Expert;

public class Common_Protection 
{
	public static boolean isProtected(Player player) 
	{
		Chunk_Expert chunk = Chunk_Expert.getChunkFromLocation(player.getLocation());
		Team_Expert team = Team_Expert.getPlayersTeam(player);
		if(chunk == null) return false;
		if(team == null) return true;
		if(chunk.isOwnedBy(team)) return false;
		if(chunk.getOwner().isAlly(team)) return false;
		return true;
	}
}
