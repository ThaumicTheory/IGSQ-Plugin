package me.murrobby.igsq.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.murrobby.igsq.bungee.Common_Bungee;

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
    public static void loadConfiguration()
    {
        addField("MYSQL",true);
        addField("MYSQL.username","username");
        addField("MYSQL.password","password");
        addField("MYSQL.database","database");
        addField("MESSAGE.illegalitemname","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word For &#FF0000<material>&#CD0000.");
        addField("MESSAGE.illegalitemnameoverride","&#C8C8C8Normally &#FF0000<blocked> &#C8C8C8Would be A Blocked Word For &#FF0000<material> &#C8C8C8but you bypass this check.");
        addField("MESSAGE.illegalcommand","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Command.");
        addField("MESSAGE.illegalchat","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word.");
        addField("MESSAGE.message","&#FFD000<server> &#685985| &#C8C8C8<prefix><player> &#685985| &#ff00a1<message>");
        addField("GAMEPLAY.expert",false);
        addField("GAMEPLAY.dragoneggrespawn",true);
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
<<<<<<< Updated upstream
    public static Player[] Append(Player[] array, Player value)
=======
    public static void applyDefaultConfiguration()
    {
        addFieldDefault("MYSQL","config",true);
        addFieldDefault("MYSQL.username","config","username");
        addFieldDefault("MYSQL.password","config","password");
        addFieldDefault("MYSQL.database","config","jdbc:mysql://localhost:3306/database?useSSL=false");
        
        addFieldDefault("GAMEPLAY.expert","config",false);
        addFieldDefault("GAMEPLAY.dragoneggrespawn","config",true);
        
        addFieldDefault("SUPPORT.luckperms","config",true);
        addFieldDefault("SUPPORT.nametagedit","config",true);
        

        addFieldDefault("server","message","Server");
        addFieldDefault("message","message","&#FFD000<server> &#685985| &#C8C8C8<prefix><player><suffix> &#685985| &#d256ff<message>");
        addFieldDefault("illegalitemname","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word For &#FF0000<material>&#CD0000.");
        addFieldDefault("illegalitemnameoverride","message","&#C8C8C8Normally &#FF0000<blocked> &#C8C8C8Would be A Blocked Word For &#FF0000<material> &#C8C8C8but you bypass this check.");
        addFieldDefault("illegalcommand","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Command.");
        addFieldDefault("illegalchat","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word.");
        for(FileConfiguration configuration : configurations) configuration.options().copyDefaults(true);
    }
    // TODO commenting
    public static Player[] append(Player[] array, Player value)
>>>>>>> Stashed changes
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
    	try 
    	{
			internalData.set(player.getUniqueId().toString() + ".damage.last",player.getTicksLived());;
			internalData.save(internalDataFile);
		} 
    	catch (Exception exception) {
			System.out.println("Could not add player Defaults!");
			player.sendMessage(ChatFormatter("&#CD0000Something went wrong when creating your defaults!"));
		}
    }
    
    
    
    /**
     * Converts hex codes & formatting codes for use in chat.
     * @apiNote Looks for &# and then takes in values after it and converts it to hex
     * @see net.md_5.bungee.api.ChatColor#of(String)
     * @return <b>String</b>
     */
    public static String ChatFormatter(String textToColour) //CF
    {
    	String[] textToColourChars = textToColour.split("");
    	String rebuiltText = "";
    	for(int i = 0;i < textToColourChars.length;i++) if(textToColourChars.length > i + 7 && textToColourChars[i].equals("&") && textToColourChars[i+1].equals("#")) 
		{
			rebuiltText += net.md_5.bungee.api.ChatColor.of("#" + textToColourChars[i+2] + textToColourChars[i+3] + textToColourChars[i+4] + textToColourChars[i+5] + textToColourChars[i+6] + textToColourChars[i+7]).toString();
			i+=7;
		}
		else rebuiltText += textToColourChars[i];
    	return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', rebuiltText);
    }
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #ChatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #Depend(String[],int)}
     * @see #getFieldString
     * @see #GetFormattedMessage(String)
     * @see #GetFormattedMessage(String, String,String)
     * @return <b>String</b>
     */
	public static String GetFormattedMessage(String messageName, String[] wildcards) //[]
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Bungee.Depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = getFieldString("MESSAGE." + messageName, "config");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return ChatFormatter(message);
	}
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #ChatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessage(String)
     * @see #GetFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String GetFormattedMessage(String messageName,String replace,String with) //1
    {
    	return ChatFormatter(getFieldString("MESSAGE." + messageName, "config").replace(replace,with));
    }
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #ChatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessage(String,String,String)
     * @see #GetFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String GetFormattedMessage(String messageName) //0
    {
    	return ChatFormatter(getFieldString("MESSAGE." + messageName, "config"));
    }
    

	
    /**
     * Converts formatting codes for use in console & legacy. ignores Hex Codes.
     * @apiNote Removes &# Hex Colours
     * @see net.md_5.bungee.api.ChatColor#translateAlternateColorCodes(char,String)
     * @return <b>String</b>
     */
    public static String ChatFormatterConsole(String textToColour) //CF Console
    {
    	String[] textToColourChars = textToColour.split("");
    	String rebuiltText = "";
    	for(int i = 0;i < textToColourChars.length;i++) if(textToColourChars.length > i + 7 && textToColourChars[i].equals("&") && textToColourChars[i+1].equals("#")) 
		{
			i+=7;
		}
		else rebuiltText += textToColourChars[i];
    	return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', rebuiltText);
    }
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #ChatFormatterConsole(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #Depend(String[],int)}
     * @see #getFieldString
     * @see #GetFormattedMessageConsole(String)
     * @see #GetFormattedMessageConsole(String, String,String)
     * @return <b>String</b>
     */
	public static String GetFormattedMessageConsole(String messageName, String[] wildcards) //[] Console
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Spigot.Depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = getFieldString("MESSAGE." + messageName, "config");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return Common_Spigot.ChatFormatterConsole(message);
	}
	
	
	
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #ChatFormatter(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessage(String)
     * @see #GetFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String GetFormattedMessageConsole(String messageName,String replace,String with) //1 Console
    {
    	return ChatFormatterConsole(getFieldString("MESSAGE." + messageName, "config").replace(replace,with));
    }
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #ChatFormatter(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessage(String,String,String)
     * @see #GetFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String GetFormattedMessageConsole(String messageName)//0 Console
    {
    	return ChatFormatterConsole(getFieldString("MESSAGE." + messageName, "config"));
    }
	
	
	
 // TODO commenting
    public static boolean FilterChat(String message,Player player) 
    {
		for(String illegalChat: illegalChats)
		{
			if(message.toUpperCase().contains(illegalChat)) 
			{
				player.sendMessage(GetFormattedMessage("illegalchat", "<blocked>", illegalChat));
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
    public static Block GetHighestBlock(Location location,int startingHeight)
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
    
    
    
    /**
     * Removes all text before a given character. If the character is not found the whole string is returned.
     * @apiNote used in commands to remove the command identifier minecraft: etc
     * @return <b>String</b>
     */
    public static String RemoveBeforeCharacter(String string,char target) 
    {
    	Boolean targetFound = false;
    	char[] charArray = string.toCharArray();
    	String rebuiltString = "";
    	for(int i = 0;i < string.length();i++) 
    	{
    		if(!targetFound)
    		{
    			if(charArray[i] == target && !targetFound) targetFound = true;
    		}
    		else rebuiltString += charArray[i];
    	}
    	if(targetFound) return rebuiltString;
    	else return string;
    }
    public static void ExecuteOrder66() 
    {
    	Bukkit.getServer().shutdown();
    }
}
