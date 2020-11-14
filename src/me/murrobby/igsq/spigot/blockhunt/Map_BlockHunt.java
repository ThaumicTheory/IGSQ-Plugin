package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

public class Map_BlockHunt 
{
	private static Random random = new Random();
	
    private int mapID = 0;
    private String mapName;
    
	private Material[] blocks = {};
	private Location lobbyLocation;
	private Location hiderSpawnLocation;
	private Location seekerSpawnLocation;
	private Location seekerWaitLocation;
    
	private static Location hubLocation = Common.parseLocationFromString(Yaml.getFieldString("maps.hub.location" , "blockhunt"));
	private static Map_BlockHunt[] maps = {};
    
    public Map_BlockHunt(int mapID) 
    {
    	this.mapID = mapID;
    	loadMap();
    }
    
    public Map_BlockHunt() 
    {
    	this.mapID = random.nextInt(getCount())+1;
    	loadMap();
    }
    
	private void loadMap() 
	{
		mapName = Yaml.getFieldString("maps."+ mapID +".name" , "blockhunt");
		lobbyLocation = Common.parseLocationFromString(Yaml.getFieldString("maps."+ mapID +".prelobby" , "blockhunt"));
		hiderSpawnLocation = Common.parseLocationFromString(Yaml.getFieldString("maps."+ mapID +".hider" , "blockhunt"));
		seekerWaitLocation = Common.parseLocationFromString(Yaml.getFieldString("maps."+ mapID +".preseeker" , "blockhunt"));
		seekerSpawnLocation = Common.parseLocationFromString(Yaml.getFieldString("maps."+ mapID +".seeker" , "blockhunt"));
		blocks = Common.parseMaterialListFromString(Yaml.getFieldString("maps."+ mapID +".blocks" , "blockhunt"));
		maps = append(maps, this);
	}
	
	public String getName() 
	{
		return mapName;
	}
	
	public int getID() 
	{
		return mapID;
	}
	
	public Material[] getBlocks() 
	{
		return blocks;
	}
	public Location getLobbyLocation() 
	{
		return lobbyLocation;
	}
	public Location getHiderSpawnLocation() 
	{
		return hiderSpawnLocation;
	}
	public Location getSeekerSpawnLocation() 
	{
		return seekerSpawnLocation;
	}
	public Location getSeekerWaitLocation() 
	{
		return seekerWaitLocation;
	}
	
    public Boolean isBlockPlayable(Material material) 
    {
    	for (Material allowedMaterial : blocks) 
    	{
    		if(allowedMaterial.equals(material)) return true;
    	}
    	return false;
    }
	
	
	
	public static Location getHubLocation() 
	{
		return hubLocation;
	}
	
	public static int getCount() 
	{
		String mapName = "";
		int incrementer = 1;
		int mapCount = -1;
		do 
		{
			mapCount++;
			mapName = Yaml.getFieldString("maps."+ incrementer++ +".name", "blockhunt");
		}while (mapName != null && !mapName.equals(""));
		return mapCount;
	}
	
	public static Map_BlockHunt getMapByID(int id) 
	{
		for(Map_BlockHunt map : maps) 
		{
			if(map.getID() == id) return map;
		}
		return null;
	}
	
	
	
	
	
	
	private static Map_BlockHunt[] append(Map_BlockHunt[] array, Map_BlockHunt value) 
	{
		Map_BlockHunt[] arrayAppended = new Map_BlockHunt[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
	}
}
