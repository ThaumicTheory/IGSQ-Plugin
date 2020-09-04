package me.murrobby.igsq.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;

public class Common_Spigot {
	public static Main_Spigot spigot;
    private static String[] fileNames = {"config","player","internal","message"};
    private static File[] files;
    private static FileConfiguration[] configurations;
	@Deprecated public static File playerDataFile;
    @Deprecated public static FileConfiguration playerData;
    @Deprecated public static File internalDataFile;
    @Deprecated public static FileConfiguration internalData;
	public static String[] illegalChats = {"NIGGER","NOGGER","COON","NIGGA"};
    // TODO commenting
	
    //TODO Java Docs
    public static void createFiles() 
    {
		 try
         {
	    	if (!spigot.getDataFolder().exists()) 
	    	{
	    		spigot.getDataFolder().mkdir();
	    	}
	    	files = new File[fileNames.length];
	    	configurations = new YamlConfiguration[fileNames.length];
	    	for (int i = 0; i < fileNames.length;i++) 
	    	{
	    		files[i] = new File(spigot.getDataFolder(),fileNames[i] + ".yml");
	    		files[i].createNewFile();
	    		
	    	}
	    	
         }
         catch (Exception e)
		 {
			e.printStackTrace();
		 }
    	
    }
    public static void addFieldDefault(String path,String fileName,Object data) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName)) 
    		{
    			configurations[i].addDefault(path, data);
    			break;
    		}
    	}
    }
    //TODO Java Docs
    public static String getFieldString(String path,String fileName) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName)) 
    		{
    			return configurations[i].getString(path);
    		}
    	}
    	return null;
    }
    //TODO Java Docs
    public static Boolean getFieldBool(String path,String fileName) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName)) 
    		{
    			return configurations[i].getBoolean(path);
    		}
    	}
    	return false;
    }
    //TODO Java Docs
    public static int getFieldInt(String path,String fileName) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName)) 
    		{
    			return configurations[i].getInt(path);
    		}
    	}
    	return -1;
    }
    
    //TODO Java Docs
    public static void updateField(String path,String fileName,String data) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName))
    		{
    			configurations[i].set(path, data);
    			break;
    		}
    	}
    }
    public static void updateField(String path,String fileName,Boolean data) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName))
    		{
    			configurations[i].set(path, data);
    			break;
    		}
    	}
    }
    //TODO Java Docs
    public static void updateField(String path,String fileName,int data) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName))
    		{
    			configurations[i].set(path, data);
    			break;
    		}
    	}
    }
  //TODO Java Docs
    public static void loadFile(String fileName) 
    {
    	try 
    	{
        	for(int i = 0; i < fileNames.length;i++) 
        	{
        		if(fileName.equalsIgnoreCase("@all")) 
        		{
        			configurations[i] = new YamlConfiguration();
        			configurations[i].load(files[i]);
        		}
        		else if(fileNames[i].equalsIgnoreCase(fileName))
        		{
        			configurations[i] = new YamlConfiguration();
        			configurations[i].load(files[i]);
        			break;
        		}
        	}
		}
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    }
    public static void saveFileChanges(String fileName) 
    {
		try 
		{
	    	for(int i = 0; i < fileNames.length;i++) 
			{
				if(fileName.equalsIgnoreCase("@all")) 
				{
					configurations[i].save(files[i]);
				}
				else if(fileNames[i].equalsIgnoreCase(fileName))
				{
					configurations[i].save(files[i]);
					break;
				}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    }
    //TODO Java Docs
    public static void disgardAndCloseFile(String fileName) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileName.equalsIgnoreCase("@all")) 
    		{
    			configurations[i] = null;
    			files[i] = null;
    		}
    		else if(fileNames[i].equalsIgnoreCase(fileName))
    		{
    			configurations[i] = null;
    			files[i] = null;
    			break;
    		}
    	}
    }
	@Deprecated
    public static void createPlayerData() {
        playerDataFile = new File(spigot.getDataFolder(),"playerData.yml");
        if (!playerDataFile.exists()) {
            try {
				playerDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
            spigot.saveResource("playerData.yml", false);
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
	@Deprecated
    public static void createInternalData() {
    	internalDataFile = new File(spigot.getDataFolder(),"internalData.yml");
        if (!internalDataFile.exists()) {
            try {
            	internalDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
            spigot.saveResource("internalData.yml", false);
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
        addFieldDefault("message","message","&#FFD000<server> &#685985| &#C8C8C8<prefix><player> &#685985| &#ff00a1<message>");
        addFieldDefault("illegalitemname","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word For &#FF0000<material>&#CD0000.");
        addFieldDefault("illegalitemnameoverride","message","&#C8C8C8Normally &#FF0000<blocked> &#C8C8C8Would be A Blocked Word For &#FF0000<material> &#C8C8C8but you bypass this check.");
        addFieldDefault("illegalcommand","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Command.");
        addFieldDefault("illegalchat","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word.");
        for(FileConfiguration configuration : configurations) configuration.options().copyDefaults(true);
    }
    // TODO commenting
    @Deprecated
    public static void addField(String path,String data) 
    {
    	spigot.getConfig().addDefault(path, data);
    }
    @Deprecated
    public static void addField(String path,Boolean data) 
    {
    	spigot.getConfig().addDefault(path, data);
    }
    // TODO commenting
    @Deprecated
    public static Boolean GetFieldBool(String path,String tableName) 
    {
    	switch(tableName) {
    	  	case "playerdata":
    	  		return playerData.getBoolean(path);
    	  	case "internal":
    	  		return internalData.getBoolean(path);
    	  	default:
    	  		return spigot.getConfig().getBoolean(path);
    	}

    }
    // TODO commenting
    @Deprecated
    public static String GetFieldString(String path,String tableName) 
    {
    	switch(tableName) {
    	  	case "playerdata":
    	  		return playerData.getString(path);
    	  	case "internal":
    	  		return internalData.getString(path);
    	  	default:
    	  		return spigot.getConfig().getString(path);
    	}

    }
    // TODO commenting
    @Deprecated
    public static int GetFieldInt(String path,String tableName) 
    {
    	switch(tableName) {
    	  	case "playerdata":
    	  		return playerData.getInt(path);
    	  	case "internal":
    	  		return internalData.getInt(path);
    	  	default:
    	  		return spigot.getConfig().getInt(path);
    	}

    }
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
    // TODO commenting
    @Deprecated
    public static void Default(Player player) 
    {
    	try 
    	{
			internalData.set(player.getUniqueId().toString() + ".damage.last",player.getTicksLived());;
			internalData.save(internalDataFile);
		} 
    	catch (Exception exception) {
			System.out.println("Could not add player Defaults!");
			player.sendMessage(chatFormatter("&#CD0000Something went wrong when creating your defaults!"));
		}
    }
    public static void applyDefault(Player player) 
    {
    	addFieldDefault(player.getUniqueId().toString() + ".damage.last", "player", player.getTicksLived());
    }
    
    
    
    /**
     * Converts hex codes & formatting codes for use in chat.
     * @apiNote Looks for &# and then takes in values after it and converts it to hex
     * @see net.md_5.bungee.api.ChatColor#of(String)
     * @return <b>String</b>
     */
    public static String chatFormatter(String textToColour) //CF
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
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #chatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #depend(String[],int)}
     * @see #getFieldString
     * @see #getFormattedMessage(String)
     * @see #getFormattedMessage(String, String,String)
     * @return <b>String</b>
     */
	public static String getFormattedMessage(String messageName, String[] wildcards) //[]
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Shared.depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = GetFieldString("MESSAGE." + messageName, "config");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return chatFormatter(message);
	}
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #chatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessage(String)
     * @see #getFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String getFormattedMessage(String messageName,String replace,String with) //1
    {
    	return chatFormatter(GetFieldString("MESSAGE." + messageName, "config").replace(replace,with));
    }
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #chatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessage(String,String,String)
     * @see #getFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String getFormattedMessage(String messageName) //0
    {
    	return chatFormatter(GetFieldString("MESSAGE." + messageName, "config"));
    }
    

	
    /**
     * Converts formatting codes for use in console & legacy. ignores Hex Codes.
     * @apiNote Removes &# Hex Colours
     * @see net.md_5.bungee.api.ChatColor#translateAlternateColorCodes(char,String)
     * @return <b>String</b>
     */
    public static String chatFormatterConsole(String textToColour) //CF Console
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
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #chatFormatterConsole(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #depend(String[],int)}
     * @see #getFieldString
     * @see #getFormattedMessageConsole(String)
     * @see #getFormattedMessageConsole(String, String,String)
     * @return <b>String</b>
     */
	public static String getFormattedMessageConsole(String messageName, String[] wildcards) //[] Console
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Shared.depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = GetFieldString("MESSAGE." + messageName, "config");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return Common_Spigot.chatFormatterConsole(message);
	}
	
	
	
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #chatFormatter(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessage(String)
     * @see #getFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String getFormattedMessageConsole(String messageName,String replace,String with) //1 Console
    {
    	return chatFormatterConsole(GetFieldString("MESSAGE." + messageName, "config").replace(replace,with));
    }
    
    
    
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #chatFormatter(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessage(String,String,String)
     * @see #getFormattedMessage(String, String[])
     * @return <b>String</b>
     */
    public static String getFormattedMessageConsole(String messageName)//0 Console
    {
    	return chatFormatterConsole(GetFieldString("MESSAGE." + messageName, "config"));
    }
	
	
	
 // TODO commenting
    public static boolean filterChat(String message,Player player) 
    {
		for(String illegalChat: illegalChats)
		{
			if(message.toUpperCase().contains(illegalChat)) 
			{
				player.sendMessage(getFormattedMessage("illegalchat", "<blocked>", illegalChat));
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
}
