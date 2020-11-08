package me.murrobby.igsq.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Yaml 
{
    /**
     * filenames is a String array of all of the fileNames to be created into {@link java.io.File}
     * @apiNote Used in {@link #createFiles()} to instansiate the filesnames.
     * @see java.io.File
     */
    public static String[] fileNames = {"config","player","internal","message","blockhunt"};
    /**
     * files is a {@link java.io.File File} array of all of the files that can be used.
     * @apiNote Used in {@link #loadFile(String)} to get data from file & in {@link #saveFileChanges(String)} to save data to file
     */
    private static File[] files;
    /**
     * configurations is a {@link org.bukkit.configuration.file.FileConfiguration FileConfiguration} array of all of the cached file contents to be saved onto file {@link #files file}.
     * @apiNote Used in {@link #getFieldString(String, String) getting} & {@link #updateField(String, String, String) setting} file contents aswell as {@link #loadFile(String) loading} & {@link #saveFileChanges(String) saving}.
     * @see java.io.File
     */
    private static FileConfiguration[] configurations;
    
    
    
    
    
    
    /**
     * Creates all the files if they dont already exist. Creates instance of all files in {@link #fileNames}
     * @apiNote also creates default {@link #configurations}
     * @see java.io.File
     * @see org.bukkit.configuration.file.FileConfiguration
     */
    public static void createFiles() 
    {
		 try
         {
	    	if (!Common.spigot.getDataFolder().exists()) 
	    	{
	    		Common.spigot.getDataFolder().mkdir();
	    	}
	    	files = new File[fileNames.length];
	    	configurations = new YamlConfiguration[fileNames.length];
	    	for (int i = 0; i < fileNames.length;i++) 
	    	{
	    		files[i] = new File(Common.spigot.getDataFolder(),fileNames[i] + ".yml");
	    		files[i].createNewFile();
	    		
	    	}
	    	
         }
         catch (Exception exception)
		 {
        	 Messaging.sendException(exception,"Failed to create Files.","REDSTONE",null);
		 }
    	
    }
    //TODO Java Docs
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
    		Messaging.sendException(e,"Failed to load file","EMERALD_BLOCK",null);
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
    		Messaging.sendException(e,"Failed to save file changes","HAY",null);
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
    
    public static void applyDefault()
    {
        addFieldDefault("MYSQL","config",true);
        addFieldDefault("MYSQL.username","config","username");
        addFieldDefault("MYSQL.password","config","password");
        addFieldDefault("MYSQL.database","config","jdbc:mysql://localhost:3306/database?useSSL=false");
        
        addFieldDefault("GAMEPLAY.expert","config",false);
        addFieldDefault("GAMEPLAY.dragoneggrespawn","config",true);
        addFieldDefault("GAMEPLAY.blockhunt","config",false);
        
        addFieldDefault("SUPPORT.luckperms","config",true);
        addFieldDefault("SUPPORT.nametagedit","config",true);
        

        addFieldDefault("message","message","&#FFD000<server> &#685985| &#C8C8C8<prefix><player><suffix> &#685985| &#d256ff<message>");
        addFieldDefault("illegalitemname","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word For &#FF0000<material>&#CD0000.");
        addFieldDefault("illegalitemnameoverride","message","&#C8C8C8Normally &#FF0000<blocked> &#C8C8C8Would be A Blocked Word For &#FF0000<material> &#C8C8C8but you bypass this check.");
        addFieldDefault("illegalcommand","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Command.");
        addFieldDefault("illegalchat","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word.");
        
        addFieldDefault("blockpickcooldown","blockhunt",100);
        addFieldDefault("cloakcooldown","blockhunt",200);
        addFieldDefault("failcooldown","blockhunt",2);
        addFieldDefault("visibilityrange","blockhunt",4);
        addFieldDefault("lobbytime","blockhunt",400);
        addFieldDefault("gametime","blockhunt",6000);
        addFieldDefault("hidetime","blockhunt",600);
        addFieldDefault("outofboundstime","blockhunt",100);
        addFieldDefault("minimumplayers","blockhunt",2);
        addFieldDefault("maps.hub.location","blockhunt","world 0 0 0 0f 0f");
        addFieldDefault("maps.1.name","blockhunt","");
        addFieldDefault("maps.1.prelobby","blockhunt","world 0 0 0 0f 0f");
        addFieldDefault("maps.1.hider","blockhunt","world 0 0 0 0f 0f");
        addFieldDefault("maps.1.preseeker","blockhunt","world 0 0 0 0f 0f");
        addFieldDefault("maps.1.seeker","blockhunt","world 0 0 0 0f 0f");
        addFieldDefault("maps.1.blocks","blockhunt","");
        
        addFieldDefault("server","internal","unknown");
        for(FileConfiguration configuration : configurations) configuration.options().copyDefaults(true);
    }
    // TODO commenting
    public static void applyDefault(Player player) 
    {
    	addFieldDefault(player.getUniqueId().toString() + ".damage.last", "player", player.getTicksLived());
    	addFieldDefault(player.getUniqueId().toString() + ".controller.chat", "internal", Yaml.getFieldString("controller.chat", "internal"));
    	addFieldDefault(player.getUniqueId().toString() + ".controller.tag", "internal", Yaml.getFieldString("controller.tag", "internal"));
		addFieldDefault(player.getUniqueId().toString() + ".playercompass.target","internal","");
		addFieldDefault(player.getUniqueId().toString() + ".playercompass.accuracy", "internal",0);
		
		for(FileConfiguration configuration : configurations) configuration.options().copyDefaults(true);
    }
    
    
	public static void interpretMessage(String message, Player player)
	{
		System.out.println("Message: " + message);
		String[] args = message.split(".");
		if(args.length == 3)
		{
    		if(args[0].equalsIgnoreCase(fileNames[1])) 
    		{
    			updateField(player.getUniqueId() +"." +args[1], args[0], args[2]);
    		}
		}
		else System.out.println("Length was not the right size was " + args.length + " needed 3.");
	}

}
