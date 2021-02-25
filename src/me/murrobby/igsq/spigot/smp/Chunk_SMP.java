package me.murrobby.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;

public class Chunk_SMP 
{
	private static List<Chunk_SMP> chunks = new ArrayList<>();
	private final UUID UID;
	private final YamlChunkWrapper_SMP yaml;
	public Chunk_SMP(Chunk chunk) 
	{
		UUID uid = null;
		do 
		{
			uid = UUID.randomUUID();
		}
		while(getChunkFromID(uid) != null);
		this.UID = uid;
		chunks.add(this);
		this.yaml = new YamlChunkWrapper_SMP(UID);
		yaml.applyDefault();
		setWorld(chunk.getWorld());
		setLocation(chunk.getX(), chunk.getZ());
		longStore();
		
	}
	public Chunk_SMP(UUID uid) 
	{
		this.UID = uid;
		chunks.add(this);
		this.yaml = new YamlChunkWrapper_SMP(UID);
	}
	public UUID getUID() 
	{
		return UID;
	}
	public void setWorld(World world) 
	{
		yaml.setWorld(world.getUID());
	}
	public Team_SMP getOwner() 
	{
		return Team_SMP.getTeamFromID(UUID.fromString(yaml.getOwner()));
	}
	public void setOwner(Team_SMP team) 
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
	public boolean isOwnedBy(Team_SMP team) 
	{
		return team.getUID().equals(getOwner().getUID());
	}
	public void deleteChunk() 
	{
		yaml.delete();
		chunks.remove(this);
		longStore();
	}
	public static void deleteChunk(Team_SMP team) 
	{
		for(Chunk_SMP chunk : chunks) if(chunk.getOwner().equals(team)) 
		{
			chunk.deleteChunk();
			break;
		}
	}
	public static boolean isChunkClaimable(Player player) 
	{
		int x = player.getLocation().getBlockX();
		int z = player.getLocation().getBlockZ();
		if(player.getWorld().getEnvironment().equals(Environment.THE_END)) 
		{
			if(x < 256 && x >= -256 && z < 256 && z >= -256) 
			{
				player.sendMessage(Messaging.chatFormatter("&#FF0000The centre of the end cannot be claimed!"));
				return false;
			}
		}
		return true;
	}
	public static boolean isChunkClaimable(Location location) 
	{
		int x = location.getBlockX();
		int z = location.getBlockZ();
		if(location.getWorld().getEnvironment().equals(Environment.THE_END)) 
		{
			if(x < 256 && x >= -256 && z < 256 && z >= -256) return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	public static Chunk_SMP getChunkFromLocation(Location location) 
	{
		return getChunkFromLocation(location.getChunk().getX(), location.getChunk().getZ(),location.getWorld());
	}
	public static Chunk_SMP getChunkFromLocation(Chunk chunk) 
	{
		return getChunkFromLocation(chunk.getX(), chunk.getZ(),chunk.getWorld());
	}
	public static Chunk_SMP getChunkFromLocation(int x, int z,World world) 
	{
		for(Chunk_SMP chunk : chunks) if(chunk.getLocation().get(0).equals(x) && chunk.getLocation().get(1).equals(z) && chunk.getWorld().equals(world)) return chunk;
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
	public static Chunk_SMP getChunkFromID(UUID uid) 
	{
		for(Chunk_SMP chunk : chunks) if(chunk.getUID().equals(uid)) return chunk;
		return null;
	}
	public static void longStore()
	{
		List<String> names = new ArrayList<>();
		for(Chunk_SMP chunk : chunks) names.add(chunk.getUID().toString());
		YamlChunkWrapper_SMP.setChunks(String.join(" ", names));
	}
	public static void longBuild()
	{
		String chunks = YamlChunkWrapper_SMP.getChunks();
		if(chunks == null || chunks.equalsIgnoreCase("")) 
		{
			YamlChunkWrapper_SMP.setChunks("");
			return;
		}
		for(String team : chunks.split(" ")) new Chunk_SMP(UUID.fromString(team));
	}
}
