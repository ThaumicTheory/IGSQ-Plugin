package me.murrobby.igsq.spigot.expert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;

public class Chunk_Expert 
{
	private static List<Chunk_Expert> chunks = new ArrayList<>();
	private final UUID UID;
	private final YamlChunkWrapper_Expert yaml;
	public Chunk_Expert(Chunk chunk) 
	{
		UUID uid = null;
		do 
		{
			uid = UUID.randomUUID();
		}
		while(getChunkFromID(uid) != null);
		this.UID = uid;
		chunks.add(this);
		this.yaml = new YamlChunkWrapper_Expert(UID);
		yaml.applyDefault();
		setWorld(chunk.getWorld());
		setLocation(chunk.getX(), chunk.getZ());
		longStore();
		
	}
	public Chunk_Expert(UUID uid) 
	{
		this.UID = uid;
		chunks.add(this);
		this.yaml = new YamlChunkWrapper_Expert(UID);
	}
	public UUID getUID() 
	{
		return UID;
	}
	public void setWorld(World world) 
	{
		yaml.setWorld(world.getUID());
	}
	public Team_Expert getOwner() 
	{
		return Team_Expert.getTeamFromID(UUID.fromString(yaml.getOwner()));
	}
	public void setOwner(Team_Expert team) 
	{
		yaml.setOwner(team.getUID());
	}
	public World getWorld() 
	{
		return Bukkit.getWorld(UUID.fromString(yaml.getWorld()));
	}
	public void setLocation(int x,int z) 
	{
		yaml.setLocation(x, z);
	}
	public List<Integer> getLocation() 
	{
		String[] locationString = yaml.getLocation().split(" ");
		List<Integer> location = new ArrayList<>();
		for(String coord : locationString) location.add(Integer.parseInt(coord));
		return location;

	}
	public Chunk getChunk() 
	{
		List<Integer> location = getLocation();
		return getWorld().getChunkAt(location.get(0),location.get(1));
	}
	public boolean isOwnedBy(Team_Expert team) 
	{
		return team.getUID().equals(getOwner().getUID());
	}
	public void deleteChunk(Player changer) 
	{
		if(changer != null && !getOwner().isOwner(changer)) 
		{
			changer.sendMessage(Messaging.chatFormatter("&#FF0000Only the owner can remove a chunk"));
			return;
		}
		if (changer != null) changer.sendMessage(Messaging.chatFormatter("&#00FF00Chunk removed successfully."));
		yaml.delete(getUID().toString());
		chunks.remove(this);
		longStore();
	}
	
	
	
	
	
	
	
	public static Chunk_Expert getChunkFromLocation(Location location) 
	{
		return getChunkFromLocation(location.getChunk().getX(), location.getChunk().getZ(),location.getWorld());
	}
	public static Chunk_Expert getChunkFromLocation(Chunk chunk) 
	{
		return getChunkFromLocation(chunk.getX(), chunk.getZ(),chunk.getWorld());
	}
	public static Chunk_Expert getChunkFromLocation(int x, int z,World world) 
	{
		for(Chunk_Expert chunk : chunks) if(chunk.getLocation().get(0).equals(x) && chunk.getLocation().get(1).equals(z) && chunk.getWorld().equals(world)) return chunk;
		return null;
	}
	public static boolean isChunkOwned(Chunk chunk) 
	{
		return isChunkOwned(chunk.getX(), chunk.getZ(), chunk.getWorld());
	}
	public static boolean isChunkOwned(Location location) 
	{
		return isChunkOwned(location.getChunk().getX(), location.getChunk().getZ(), location.getWorld());
	}
	public static boolean isChunkOwned(int x, int z,World world) 
	{
		return getChunkFromLocation(x,z,world) != null;
	}
	public static Chunk_Expert getChunkFromID(UUID uid) 
	{
		for(Chunk_Expert chunk : chunks) if(chunk.getUID().equals(uid)) return chunk;
		return null;
	}
	public static void longStore()
	{
		List<String> names = new ArrayList<>();
		for(Chunk_Expert chunk : chunks) names.add(chunk.getUID().toString());
		YamlTeamWrapper_Expert.setTeams(String.join(" ", names));
	}
	public static void longBuild()
	{
		String chunks = YamlChunkWrapper_Expert.getChunks();
		if(chunks == null || chunks.equalsIgnoreCase("")) 
		{
			YamlChunkWrapper_Expert.setChunks("");
			return;
		}
		for(String team : chunks.split(" ")) new Team_Expert(UUID.fromString(team));
	}
}
