package me.murrobby.igsq.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
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
    public static final String[] FILE_NAMES = {"config","player","internal","message","blockhunt","teams","chunks","teamranks"};
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
     * Creates all the files if they dont already exist. Creates instance of all files in {@link #FILE_NAMES}
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
	    	File locale = new File(Common.spigot.getDataFolder().getPath() + "\\locale");
	    	if(!locale.exists()) 
	    	{
	    		locale.mkdir();
	    	}
	    	files = new File[FILE_NAMES.length];
	    	configurations = new YamlConfiguration[FILE_NAMES.length];
	    	for (int i = 0; i < FILE_NAMES.length;i++) 
	    	{
	    		files[i] = new File(Common.spigot.getDataFolder(),FILE_NAMES[i] + ".yml");
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
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(FILE_NAMES[i].equalsIgnoreCase(fileName)) 
    		{
    			getConfigurations()[i].addDefault(path, data);
    			break;
    		}
    	}
    }
    //TODO Java Docs
    public static String getFieldString(String path,String fileName) 
    {
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(FILE_NAMES[i].equalsIgnoreCase(fileName)) 
    		{
    			return getConfigurations()[i].getString(path);
    		}
    	}
    	return null;
    }
    //TODO Java Docs
    public static Boolean getFieldBool(String path,String fileName) 
    {
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(FILE_NAMES[i].equalsIgnoreCase(fileName)) 
    		{
    			return getConfigurations()[i].getBoolean(path);
    		}
    	}
    	return false;
    }
    //TODO Java Docs
    public static int getFieldInt(String path,String fileName) 
    {
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(FILE_NAMES[i].equalsIgnoreCase(fileName)) 
    		{
    			return getConfigurations()[i].getInt(path);
    		}
    	}
    	return -1;
    }
    
    //TODO Java Docs
    public static void updateField(String path,String fileName,String data) 
    {
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(FILE_NAMES[i].equalsIgnoreCase(fileName))
    		{
    			getConfigurations()[i].set(path, data);
    			break;
    		}
    	}
    }
    public static void updateField(String path,String fileName,Boolean data) 
    {
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(FILE_NAMES[i].equalsIgnoreCase(fileName))
    		{
    			getConfigurations()[i].set(path, data);
    			break;
    		}
    	}
    }
    //TODO Java Docs
    public static void updateField(String path,String fileName,int data) 
    {
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(FILE_NAMES[i].equalsIgnoreCase(fileName))
    		{
    			getConfigurations()[i].set(path, data);
    			break;
    		}
    	}
    }
    //TODO Java Docs
    public static void deleteField(String path,String fileName) 
    {
    	Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
		    	for(int i = 0; i < FILE_NAMES.length;i++) 
		    	{
		    		if(FILE_NAMES[i].equalsIgnoreCase(fileName))
		    		{
		    			getConfigurations()[i].set(path, null);
		    			break;
		    		}
		    	}
			}
    	},600);
    }
  //TODO Java Docs
    public static void loadFile(String fileName) 
    {
    	try 
    	{
        	for(int i = 0; i < FILE_NAMES.length;i++) 
        	{
        		if(fileName.equalsIgnoreCase("@all")) 
        		{
        			getConfigurations()[i] = new YamlConfiguration();
        			getConfigurations()[i].load(files[i]);
        		}
        		else if(FILE_NAMES[i].equalsIgnoreCase(fileName))
        		{
        			getConfigurations()[i] = new YamlConfiguration();
        			getConfigurations()[i].load(files[i]);
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
	    	for(int i = 0; i < FILE_NAMES.length;i++) 
			{
				if(fileName.equalsIgnoreCase("@all")) 
				{
					getConfigurations()[i].save(files[i]);
				}
				else if(FILE_NAMES[i].equalsIgnoreCase(fileName))
				{
					getConfigurations()[i].save(files[i]);
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
    	for(int i = 0; i < FILE_NAMES.length;i++) 
    	{
    		if(fileName.equalsIgnoreCase("@all")) 
    		{
    			getConfigurations()[i] = null;
    			files[i] = null;
    		}
    		else if(FILE_NAMES[i].equalsIgnoreCase(fileName))
    		{
    			getConfigurations()[i] = null;
    			files[i] = null;
    			break;
    		}
    	}
    }
    
    // TODO commenting
	public static void interpretMessage(String message, Player player)
	{
		System.out.println("Message: " + message);
		String[] args = message.split(".");
		if(args.length == 3)
		{
    		if(args[0].equalsIgnoreCase(FILE_NAMES[1])) 
    		{
    			updateField(player.getUniqueId() +"." +args[1], args[0], args[2]);
    		}
		}
		else System.out.println("Length was not the right size was " + args.length + " needed 3.");
	}
	public static FileConfiguration[] getConfigurations() {
		return configurations;
	}

}
