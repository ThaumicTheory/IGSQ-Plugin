package me.murrobby.igsq.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
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
    	return Common_Spigot.getFieldBool("GAMEPLAY.expert", "config");
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
        addField("MESSAGE.commandwatch","&eCommand &5| &6 <player> &5| &c<command>");
        addField("GAMEPLAY.expert",false);
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
            	arrayBetween = Common_Spigot.Append(arrayBetween, array[i]);
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
        	Common_Spigot.playerData.set(player.getUniqueId().toString() + ".2fa",false);
			Common_Spigot.internalData.set(player.getUniqueId().toString() + ".damage.last",player.getTicksLived());
			Common_Spigot.playerData.save(Common_Spigot.playerDataFile);
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
    	return ChatColor.translateAlternateColorCodes('&', Common_Spigot.getFieldString("MESSAGE." + messageName, "config").replace(replace, with));
    }
    // TODO commenting
    public static String GetMessage(String messageName, String replace,String with, String replace2,String with2)
    {
    	return ChatColor.translateAlternateColorCodes('&', Common_Spigot.getFieldString("MESSAGE." + messageName, "config").replace(replace, with).replace(replace2, with2));
    }
    public static void GiveBlindness(Player player,int time) 
    {
    	if(!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
    	{
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,time,0,false));
    	}
    }
    public static boolean FilterChat(String message,Player player) 
    {
		for(String illegalChat: illegalChats)
		{
			if(message.toUpperCase().contains(illegalChat)) 
			{
				player.sendMessage(Common_Spigot.GetMessage("MESSAGE.illegalchat", "<blocked>", illegalChat));
				return false;
			}
		}
    	return true;
    }
}
