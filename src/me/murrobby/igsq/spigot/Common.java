package me.murrobby.igsq.spigot;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;

public class Common {
    /**
     * The universal reference to spigot which allows for {@link org.bukkit.plugin.java.JavaPlugin the plugin} to be referenced anywhere.
     * @apiNote Used where calls to spigot are required such as {@link org.bukkit.scheduler.BukkitScheduler#scheduleSyncRepeatingTask tasks} and {@link org.bukkit.plugin.java.JavaPlugin#getCommand command creation}
     * @see Spigot
     */
	public static Spigot spigot;
    /**
     * illegalChats is a String array of all of the banned words for {@link #filterChat(String, Player) Filter Chat} to compare to.
     * @see #filterChat(String, Player)
     */
	public static String[] illegalChats = {"NIGGER","NOGGER","COON","NIGGA"};
	
 
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
     * @apiNote Raycasts downwards until it hits a Block. Returns the block it hit. Ignores Passable.
     * @see org.bukkit.block.Block#isPassable
     * @return <b>Block</b>
     */
    public static Block getHighestBlock(Location location,int startingHeight)
    {
    	for(int i = startingHeight;i > 0;i--) 
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
		String current = Common_Shared.removeNull(Configuration.getFieldString(player.getUniqueId().toString() + ".controller.chat", "internal"));
		if(current.equalsIgnoreCase(controller)) return true;
		else return false;
	}
	public static Boolean isCurrentTagController(String controller,Player player) 
	{
		String current = Common_Shared.removeNull(Configuration.getFieldString(player.getUniqueId().toString() + ".controller.tag", "internal"));
		if(current.equalsIgnoreCase(controller)) return true;
		else return false;
	}
}
