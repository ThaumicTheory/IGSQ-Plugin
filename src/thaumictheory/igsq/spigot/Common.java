package thaumictheory.igsq.spigot;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import thaumictheory.igsq.shared.Common_Shared;
import thaumictheory.igsq.spigot.main.LoggerHandler_Main;

public class Common {
    /**
     * The universal reference to spigot which allows for {@link org.bukkit.plugin.java.JavaPlugin the plugin} to be referenced anywhere.
     * @apiNote Used where calls to spigot are required such as {@link org.bukkit.scheduler.BukkitScheduler#scheduleSyncRepeatingTask tasks} and {@link org.bukkit.plugin.java.JavaPlugin#getCommand command creation}
     * @see Spigot
     */
	public static Spigot spigot;
	public static LoggerHandler_Main logger;
	public static FutureScheduler future;
    /**
     * illegalChats is a String array of all of the banned words for {@link #filterChat(String, Player) Filter Chat} to compare to.
     * @see #filterChat(String, Player)
     */
	public static  ArrayList<String> illegalChats = new ArrayList<>(Arrays.asList("NIGGER","NOGGER","COON","NIGGA"));
	
    // TODO commenting
    public static Player[] append(Player[] array, Player value)
    {
    	Player[] arrayAppended = new Player[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    public static Player[] depend(Player[] array, int location)
    {
    	if(array.length == 0) return array;
    	Player[] arrayDepended = new Player[array.length-1];
        int hitRemove = 0;
        for (int i = 0;i < array.length;i++)
        {
            if(location != i){
                arrayDepended[i-hitRemove] = array[i];
            }
            else{
                hitRemove++;
            }
        }
        return arrayDepended;
    }
	public static Player[] depend(Player[] array, Player value)
    {
		if(array.length == 0) return array;
		Player[] arrayDepended = new Player[array.length-1];
        int hitRemove = 0;
        
        for (int i = 0;i < array.length;i++)
        {
            if(!value.getUniqueId().equals(array[i].getUniqueId()))
            {
                arrayDepended[i-hitRemove] = array[i];
            }
            else
            {
                hitRemove++;
            }
        }
        return arrayDepended;
    }
    public static Material[] append(Material[] array, Material value)
    {
    	Material[] arrayAppended = new Material[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    
    public static double getTemperature(Player player) 
    {
    	return player.getPlayer().getWorld().getTemperature(player.getPlayer().getLocation().getBlockX(), player.getPlayer().getLocation().getBlockY(), player.getPlayer().getLocation().getBlockZ());
    }
    
    public static Weather getWeatherEstimated(Player player) 
    {
    	if(player.getWorld().isClearWeather()) return Weather.CLEAR;
    	boolean thundering = player.getWorld().isThundering();
    	double temperature = getTemperature(player);
		if(temperature > 1) return Weather.CLOUDY;
		else if(temperature < 0.15) 
		{
			if(thundering) return Weather.SNOWSTORM;
			else return Weather.SNOW;
		}
		else 
		{
			if(thundering) return Weather.STORM;
			else return Weather.RAIN;
		}
    }
    
    public static Time getTime(World world) 
    {
    	if(world.getEnvironment() != Environment.NORMAL) return Time.NONE;
    	if(world.getTime() < 13000 || world.getTime() >= 23000) return Time.DAY;
    	return Time.NIGHT;
    }
    public static DetailedTime getTimeDetailed(World world) 
    {
    	if(world.getEnvironment() != Environment.NORMAL) return DetailedTime.NONE;
    	if(world.getTime() > 23500 || world.getTime() <= 4000) return DetailedTime.POSTRISE;
    	if(world.getTime() <= 7000) return DetailedTime.MIDDAY;
    	if(world.getTime() <= 12500) return DetailedTime.PRESET;
    	if(world.getTime() <= 13500) return DetailedTime.SET;
    	if(world.getTime() <= 16000) return DetailedTime.POSTSET;
    	if(world.getTime() <= 19000) return DetailedTime.MIDNIGHT;
    	if(world.getTime() <= 22500) return DetailedTime.PRERISE;
    	if(world.getTime() <= 23500) return DetailedTime.RISE;
    	return DetailedTime.NONE;
    }
    
    public static Climate getClimate(Player player) 
    {
    	double temperature = getTemperature(player);
    	if(temperature == 2) return Climate.HELL;
		if(temperature > 1.5) return Climate.HEATWAVE;
		if(temperature > 1.2) return Climate.HOT;
		if(temperature > .6) return Climate.NORMAL;
		if(temperature > .4) return Climate.COLD;
		if(temperature > .2) return Climate.FROSTY;
		if(temperature >= 0) return Climate.FREEZING;
		return Climate.NORMAL;
    }
    
   
	
	
	
 // TODO commenting
    public static boolean filterChat(String message,Player player) 
    {
		for(String illegalChat: illegalChats)
		{
			if(message.toUpperCase().contains(illegalChat)) 
			{
				player.sendMessage(Messaging.getFormattedMessage("illegalchat", "<blocked>", illegalChat));
				return false;
			}
		}
    	return true;
    }
    
    
    
    /**
     * gets the highest block From a Location.
     * Overloads {@link #getHighestBlock(Location, int, int) max check distance}, {@link #getHighestBlock(Location) from location height}
     * @apiNote Raycasts downwards until it hits a Block. Returns the block it hit. Ignores Passable.
     * @see org.bukkit.block.Block#isPassable
     * @return <b>Block</b>
     */
    public static Block getHighestBlock(Location location,int startingHeight)
    {
    	return getHighestBlock(location,startingHeight,startingHeight);
    }
    /**
     * gets the highest block From a Location.
     * Overloads {@link #getHighestBlock(Location, int, int) max check distance}, {@link #getHighestBlock(Location, int) starting height}
     * @apiNote Raycasts downwards until it hits a Block. Returns the block it hit. Ignores Passable.
     * @see org.bukkit.block.Block#isPassable
     * @return <b>Block</b>
     */
    public static Block getHighestBlock(Location location)
    {
    	return getHighestBlock(location,location.getBlockY(),location.getBlockY());
    }
    /**
     * gets the highest block From a Location.
     * Overloads {@link #getHighestBlock(Location, int) starting height}, {@link #getHighestBlock(Location) from location height}
     * @apiNote Raycasts downwards until it hits a Block. Returns the block it hit. Ignores Passable.
     * @see org.bukkit.block.Block#isPassable
     * @return <b>Block</b>
     */
    public static Block getHighestBlock(Location location,double startingHeight,int maximumCheck)
    {
    	for(double i = startingHeight;i > startingHeight-maximumCheck;i--) 
    	{
    		location.setY(i);
    		if(!(location.getBlock().isEmpty() || location.getBlock().isPassable())) 
    		{
    			return location.getBlock();
    		}
    	}
    	return null;
    }
	public static Boolean isCurrentChatController(String controller,Player player) 
	{
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
		String current = Common_Shared.removeNull(yaml.getChatController());
		if(current.equalsIgnoreCase(controller)) return true;
		else return false;
	}
	public static Boolean isCurrentNameController(String controller,Player player) 
	{
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
		String current = Common_Shared.removeNull(yaml.getNameController());
		if(current.equalsIgnoreCase(controller)) return true;
		else return false;
	}
	public static Location parseLocationFromString(String input) 
	{
		try 
		{
			String[] locationString = input.split(" ");
			World world = Bukkit.getWorld(locationString[0]);
			Double x = Double.parseDouble(locationString[1]);
			Double y = Double.parseDouble(locationString[2]);
			Double z = Double.parseDouble(locationString[3]);
			Float yaw = Float.parseFloat(locationString[4]);
			Float pitch = Float.parseFloat(locationString[5]);
			return new Location(world,x,y,z,yaw,pitch);
		}
		catch(Exception exception) 
		{
			Messaging.sendException(exception,"Parsed location is not a valid location", "BEDROCK", null);
			return null;
		}
	}
	public static Material[] parseMaterialListFromString(String input) 
	{
		String[] blocksString = input.split(" ");
		Material[] blocks = new Material[0];
		for(String block : blocksString) 
		{
			blocks = append(blocks, Material.valueOf(block.toUpperCase()));
		}
		return blocks;
	}
	public static void setDisplayName(Player player,String displayName) 
	{
		
	}
	public static ProtocolManager protocol = ProtocolLibrary.getProtocolManager();
	/*
	public static Material[] interactWhitelist = 
		{
			Material.ACACIA_BUTTON,
			Material.ACACIA_DOOR,
			Material.ACACIA_FENCE_GATE,
			Material.ACACIA_TRAPDOOR,
			
			Material.OAK_BUTTON,
			Material.OAK_DOOR,
			Material.OAK_FENCE_GATE,
			Material.OAK_TRAPDOOR,
			
			Material.BIRCH_BUTTON,
			Material.BIRCH_DOOR,
			Material.BIRCH_FENCE_GATE,
			Material.BIRCH_TRAPDOOR,
			
			Material.SPRUCE_TRAPDOOR,
			Material.SPRUCE_BUTTON,
			Material.SPRUCE_DOOR,
			Material.SPRUCE_FENCE_GATE,
			
			Material.DARK_OAK_TRAPDOOR,
			Material.DARK_OAK_BUTTON,
			Material.DARK_OAK_DOOR,
			Material.DARK_OAK_FENCE_GATE,
			
			Material.JUNGLE_TRAPDOOR,
			Material.JUNGLE_BUTTON,
			Material.JUNGLE_DOOR,
			Material.JUNGLE_FENCE_GATE,
			
			Material.JUNGLE_TRAPDOOR,
			Material.JUNGLE_BUTTON,
			Material.JUNGLE_DOOR,
			Material.JUNGLE_FENCE_GATE,
			
			Material.CRIMSON_TRAPDOOR,
			Material.CRIMSON_BUTTON,
			Material.CRIMSON_DOOR,
			Material.CRIMSON_FENCE_GATE,
			
			Material.WARPED_TRAPDOOR,
			Material.WARPED_BUTTON,
			Material.WARPED_DOOR,
			Material.WARPED_FENCE_GATE,
			
			Material.STONE_BUTTON,
			Material.LEVER
			
		};
	*/
}
