package thaumictheory.igsq.spigot.yaml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import thaumictheory.igsq.shared.IYaml;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;

public class Yaml implements IYaml
{
    private static final String[] PATHS = {"config.yaml","player.yaml","internal.yaml","message.yaml","blockhunt.yaml","team.yaml","chunk.yaml","teamrank.yaml","entity.yaml","future.yaml","role.yaml"};
    /**
     * files is a {@link java.io.File File} array of all of the files that can be used.
     * @apiNote Used in {@link #loadFile(String)} to get data from file & in {@link #saveFile(String)} to save data to file
     */
    private static File[] files;
    /**
     * configurations is a {@link org.bukkit.configuration.file.FileConfiguration FileConfiguration} array of all of the cached file contents to be saved onto file {@link #files file}.
     * @apiNote Used in {@link #getFieldString(String, String) getting} & {@link #setField(String, String, String) setting} file contents aswell as {@link #loadFile(String) loading} & {@link #saveFile(String) saving}.
     * @see java.io.File
     */
    private static FileConfiguration[] configurations;
    
    
    /**
     * @apiNote also creates default {@link #configurations}
     * @see java.io.File
     * @see org.bukkit.configuration.file.FileConfiguration
     */
    @Override
    public void createFiles() 
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
	    	files = new File[getPaths().length];
	    	configurations = new YamlConfiguration[getPaths().length];
	    	for (int i = 0; i < getPaths().length;i++) 
	    	{
	    		files[i] = new File(Common.spigot.getDataFolder(),getPaths()[i]);
	    		files[i].createNewFile();
	    		
	    	}
	    	
         }
         catch (Exception exception)
		 {
        	 Messaging.sendException(exception,"Failed to create Files.","REDSTONE",null);
		 }
    	
    }
    
    @Override
    public boolean defaultField(String node,String path,Object data) 
    {
    	for(int i = 0; i < getPaths().length;i++) 
    	{
	    	if(configurations[i] == null) 
	    	{
	    		Messaging.createSafeLog(Level.WARNING,"Something wanted to put the value "+ data.toString() +" if no value existed, into " + node +" in " + path + " while the yaml was not loaded!");
	    		return false;
	    	}
    		if(getPaths()[i].equalsIgnoreCase(path)) 
    		{
    			configurations[i].addDefault(node, data);
    			configurations[i].options().copyDefaults(true);
    			return true;
    		}
    	}
    	return false;
    }
	@Override
	public Object getField(String node, String path) 
	{
    	for(int i = 0; i < getPaths().length;i++) 
    	{
    		if(getPaths()[i].equalsIgnoreCase(path)) 
    		{
    	    	if(configurations[i] == null) 
    	    	{
    	    		Messaging.createSafeLog(Level.WARNING,"Something asked for an Object from " + node +" in " + path + " while the yaml was not loaded!");
    	    		return null;
    	    	}
    			return configurations[i].get(node);
    		}
    	}
    	return null;
	}

    @Deprecated
    public static String getFieldString(String path,String fileName) 
    {
    	for(int i = 0; i < PATHS.length;i++) 
    	{
    		if(PATHS[i].equalsIgnoreCase(fileName)) 
    		{
    	    	if(configurations[i] == null) 
    	    	{
    	    		Messaging.createSafeLog(Level.WARNING,"Something asked for the String from " + path +" in " + fileName + " while the yaml was not loaded!");
    	    		return null;
    	    	}
    			return configurations[i].getString(path);
    		}
    	}
    	return null;
    }
    @Deprecated
    public static Boolean getFieldBool(String path,String fileName) 
    {
    	for(int i = 0; i < PATHS.length;i++) 
    	{
    		if(PATHS[i].equalsIgnoreCase(fileName)) 
    		{
    	    	if(configurations[i] == null) 
    	    	{
    	    		Messaging.createSafeLog(Level.WARNING,"Something asked for the Boolean from " + path +" in " + fileName + " while the yaml was not loaded!");
    	    		return null;
    	    	}
    			return configurations[i].getBoolean(path);
    		}
    	}
    	return false;
    }
    
    @Deprecated
    public static Integer getFieldInt(String path,String fileName) 
    {
    	for(int i = 0; i < PATHS.length;i++) 
    	{
    		if(PATHS[i].equalsIgnoreCase(fileName)) 
    		{
    	    	if(configurations[i] == null) 
    	    	{
    	    		Messaging.createSafeLog(Level.WARNING,"Something asked for the Integer from " + path +" in " + fileName + " while the yaml was not loaded!");
    	    		return -1;
    	    	}
    			return configurations[i].getInt(path);
    		}
    	}
    	return -1;
    }
    @Override
    public String[] getPaths() 
    {
		return PATHS;
    }
    
    @Override
    public boolean setField(String node,String path,Object data) 
    {
    	for(int i = 0; i < getPaths().length;i++) 
    	{
	    	if(configurations[i] == null) 
	    	{
	    		Messaging.createSafeLog(Level.WARNING,"Something wanted to put the value "+ data.toString() +" into " + node +" in " + path + " while the yaml was not loaded!");
	    		return false;
	    	}
    		if(getPaths()[i].equalsIgnoreCase(path))
    		{
    			configurations[i].set(node, data);
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public boolean loadFile(String path) 
    {
    	try 
    	{
        	for(int i = 0; i < getPaths().length;i++) 
        	{
        		if(getPaths()[i].equalsIgnoreCase(path))
        		{
        			configurations[i] = new YamlConfiguration();
        			configurations[i].load(files[i]);
        			return true;
        		}
        	}
        	return false;
		}
    	catch (Exception e)
    	{
    		Messaging.sendException(e,"Failed to load file","EMERALD_BLOCK",null);
    		return false;
		}
    }
    
    @Override
    public boolean saveFile(String path) 
    {
		try 
		{
	    	for(int i = 0; i < getPaths().length;i++) 
			{
    	    	if(configurations[i] == null || files[i] == null) 
    	    	{
    	    		Messaging.createSafeLog(Level.WARNING,"Something tried to save the file " + path + " while the yaml was not loaded!");
    	    		return false;
    	    	}
				else if(getPaths()[i].equalsIgnoreCase(path))
				{
					configurations[i].save(files[i]);
					return true;
				}
			}
		}
		catch (IOException e) 
		{
    		Messaging.sendException(e,"Failed to save file changes","HAY",null);
    		return false;
		}
		return false;
    }
    @Override
    public boolean discardFile(String path) 
    {
    	for(int i = 0; i < getPaths().length;i++) 
    	{
    		if(getPaths()[i].equalsIgnoreCase(path))
    		{
    			configurations[i] = null;
    			files[i] = null;
    			return true;
    		}
    	}
    	return false;
    }
    
    
    
	public void interpretMessage(String message, Player player)
	{
		//System.out.println("Message: " + message);
		String[] args = message.split(".");
		if(args.length == 3)
		{
    		if(args[0].equalsIgnoreCase(getPaths()[1])) 
    		{
    			setField(player.getUniqueId() +"." +args[1], args[0], args[2]);
    		}
		}
	}
}
