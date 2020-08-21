package me.murrobby.igsq.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Common_Spigot {
	public static Main_Spigot plugin;
    public static File playerDataFile;
    public static FileConfiguration playerData;
    public static File internalDataFile;
    public static FileConfiguration internalData;
	public static String[] illegalChats = {"NIGGER","NOGGER","COON","NIGGA"};
    // TODO commenting
    public static void createPlayerData() {
        playerDataFile = new File(plugin.getDataFolder(),"playerData.yml");
        if (!playerDataFile.exists()) {
            try {
				playerDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
            plugin.saveResource("playerData.yml", false);
         }

        playerData= new YamlConfiguration();
        try 
        {
            playerData.load(playerDataFile);
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    // TODO commenting
    public static void createInternalData() {
    	internalDataFile = new File(plugin.getDataFolder(),"internalData.yml");
        if (!internalDataFile.exists()) {
            try {
            	internalDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
            plugin.saveResource("internalData.yml", false);
         }

        internalData= new YamlConfiguration();
        try 
        {
        	internalData.load(internalDataFile);
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    // TODO commenting
    public static Boolean ExpertCheck() 
    {
    	return getFieldBool("GAMEPLAY.expert", "config");
    }
    // TODO commenting
    public static void loadConfiguration()
    {
        addField("MYSQL",true);
        addField("MYSQL.username","username");
        addField("MYSQL.password","password");
        addField("MYSQL.database","database");
        addField("MESSAGE.illegalitemname","&cSorry! But &4<blocked> &cIs A Blocked Word For &6<material>&c.");
        addField("MESSAGE.illegalitemnameoverride","&bNormally &a<blocked> &bWould be A Blocked Word For &e<material> &bbut you bypass this check.");
        addField("MESSAGE.illegalcommand","&cSorry! But &4<blocked> &cIs A Blocked Command.");
        addField("MESSAGE.illegalchat","&cSorry! But &4<blocked> &cIs A Blocked Word.");
        addField("GAMEPLAY.expert",false);
        addField("MESSAGE.message","&6(&e<server>&6) &5| &6<prefix><player> &5| &d<message>");
        addField("MESSAGE.server","Server");
        addField("SUPPORT.luckperms",true);
        addField("SUPPORT.nametagedit",true);
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
    // TODO commenting
    public static void addField(String path,String data) 
    {
    	plugin.getConfig().addDefault(path, data);
    }
    // TODO commenting
    public static void addField(String path,Boolean data) 
    {
    	plugin.getConfig().addDefault(path, data);
    }
    // TODO commenting
    public static Boolean getFieldBool(String path,String tableName) 
    {
    	switch(tableName) {
    	  	case "playerdata":
    	  		return playerData.getBoolean(path);
    	  	case "internal":
    	  		return internalData.getBoolean(path);
    	  	default:
    	  		return plugin.getConfig().getBoolean(path);
    	}

    }
    // TODO commenting
    public static String getFieldString(String path,String tableName) 
    {
    	switch(tableName) {
    	  	case "playerdata":
    	  		return playerData.getString(path);
    	  	case "internal":
    	  		return internalData.getString(path);
    	  	default:
    	  		return plugin.getConfig().getString(path);
    	}

    }
    // TODO commenting
    public static int getFieldInt(String path,String tableName) 
    {
    	switch(tableName) {
    	  	case "playerdata":
    	  		return playerData.getInt(path);
    	  	case "internal":
    	  		return internalData.getInt(path);
    	  	default:
    	  		return plugin.getConfig().getInt(path);
    	}

    }
    //Appends a value to the end of array
    public static String[] Append(String[] array, String value)
    {
    	String[] arrayAppended = new String[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    public static String[] ArrayAppend(String[] array, String[] array2) 
    {
    	String[] appendedArray = array;
    	for (String string : array2) 
    	{
    		appendedArray = Append(appendedArray,string);
    	}
    	return appendedArray;
    }
    // TODO commenting
    public static Player[] Append(Player[] array, Player value)
    {
    	Player[] arrayAppended = new Player[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    // TODO commenting
    public static String[] Depend(String[] array, int location)
    {
        String[] arrayDepended = new String[array.length-1];
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
    public static String[] GetBetween(String[] array, int leftSide,int rightSide)
    {
        String[] arrayBetween = new String[0];
        if(rightSide == -1) 
        {
        	rightSide = array.length;
        }
        for (int i = 0;i < array.length;i++)
        {
            if(i >= leftSide && i <= rightSide){
            	arrayBetween = Append(arrayBetween, array[i]);
            }
            else if(i > rightSide)
            {
            	break;
            }
        }
        return arrayBetween;
    }
    // TODO commenting
    public static void Default(Player player) 
    {
    	try {
        	playerData.set(player.getUniqueId().toString() + ".discord.2fa","");
			internalData.set(player.getUniqueId().toString() + ".damage.last",player.getTicksLived());
			playerData.save(playerDataFile);
			internalData.save(internalDataFile);
		} 
    	catch (Exception exception) {
			System.out.println("Could not add player Defaults!");
			player.sendMessage("Something went wrong when creating your defaults!");
		}
    }
    // TODO commenting
    public static String ChatColour(String textToColour) 
    {
    	return ChatColor.translateAlternateColorCodes('&', textToColour);
    }
    // TODO commenting
    public static String GetMessage(String messageName, String replace,String with)
    {
    	return ChatColor.translateAlternateColorCodes('&', getFieldString("MESSAGE." + messageName, "config").replace(replace, with));
    }
    // TODO commenting
    public static String GetMessage(String messageName)
    {
    	return ChatColor.translateAlternateColorCodes('&', getFieldString("MESSAGE." + messageName, "config"));
    }
    // TODO commenting
    public static String GetMessage(String messageName, String replace,String with, String replace2,String with2)
    {
    	return ChatColor.translateAlternateColorCodes('&', getFieldString("MESSAGE." + messageName, "config").replace(replace, with).replace(replace2, with2));
    }
    // TODO commenting
    public static String GetMessage(String messageName, String replace,String with, String replace2,String with2, String replace3,String with3)
    {
    	return ChatColor.translateAlternateColorCodes('&', getFieldString("MESSAGE." + messageName, "config").replace(replace, with).replace(replace2, with2).replace(replace3, with3));
    }
    // TODO commenting
    public static String GetMessage(String messageName, String replace,String with, String replace2,String with2, String replace3,String with3, String replace4,String with4)
    {
    	return ChatColor.translateAlternateColorCodes('&', getFieldString("MESSAGE." + messageName, "config").replace(replace, with).replace(replace2, with2).replace(replace3, with3).replace(replace4, with4));
    }
 // TODO commenting
    public static void GiveBlindness(Player player,int time) 
    {
    	if(!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
    	{
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,time,0,false));
    	}
    }
 // TODO commenting
    public static boolean FilterChat(String message,Player player) 
    {
		for(String illegalChat: illegalChats)
		{
			if(message.toUpperCase().contains(illegalChat)) 
			{
				player.sendMessage(GetMessage("illegalchat", "<blocked>", illegalChat));
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
     * @throws java.lang.NullPointerException
     */
    public static Block GetHighestBlock(Location location) throws NullPointerException
    {
    	for(int i = 255;i > 0;i--) 
    	{
    		location.setY(i);
    		if(!(location.getBlock().isEmpty() || location.getBlock().isPassable())) 
    		{
    			return location.getBlock();
    		}
    	}
    	throw null;
    }
}
