package me.murrobby.igsq.spigot.expert.protection;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.expert.Chunk_Expert;
import me.murrobby.igsq.spigot.expert.Team_Expert;

public class Common_Protection 
{
	public static boolean isProtected(Player player) 
	{
		return isProtected(player,player.getLocation());
	}
	
	public static boolean isProtected(Player player,Location location) 
	{
		Chunk_Expert chunk = Chunk_Expert.getChunkFromLocation(location);
		Team_Expert team = Team_Expert.getPlayersTeam(player);
		if(chunk == null) return false;
		if(team == null) return true;
		if(chunk.isOwnedBy(team)) return false;
		if(chunk.getOwner().isAlly(team)) return false;
		return true;
	}
}
