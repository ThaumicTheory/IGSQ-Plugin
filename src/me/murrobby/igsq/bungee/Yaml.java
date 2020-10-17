package me.murrobby.igsq.bungee;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Yaml 
{
	 public static String[] fileNames = {"config","player","internal","message"};
    private static String[] syncedFiles = {"player"};
    private static File[] files;
    private static Configuration[] configurations;
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    
    //TODO Java Docs
    public static void createFiles() 
    {
		 try
         {
	    	if (!Common.bungee.getDataFolder().exists()) 
	    	{
	    		Common.bungee.getDataFolder().mkdir();
	    	}
	    	files = new File[fileNames.length];
	    	configurations = new Configuration[fileNames.length];
	    	for (int i = 0; i < fileNames.length;i++) 
	    	{
	    		files[i] = new File(Common.bungee.getDataFolder(),fileNames[i] + ".yml");
	    		files[i].createNewFile();
	    	}
	    	
         }
         catch (Exception e)
		 {
			e.printStackTrace();
		 }
    	
    }
    //TODO Java Docs
    public static void applyDefault()
    {
        addFieldDefault("MYSQL.username","config","username");
        addFieldDefault("MYSQL.password","config","password");
        addFieldDefault("MYSQL.database","config","jdbc:mysql://localhost:3306/database?useSSL=false");
        
        addFieldDefault("SUPPORT.luckperms","config","true");
        addFieldDefault("SUPPORT.protocol.highest","config","-1");
        addFieldDefault("SUPPORT.protocol.recommended","config","753");
        addFieldDefault("SUPPORT.protocol.lowest","config","340");
        
        addFieldDefault("SERVER.backupredirect","config","hub");
        
        
        
        addFieldDefault("join","message","&#00FFFFWelcome <player>!");
        addFieldDefault("joinvanilla","message","&#00FFFFNo extra data was found in handshake! Assuming vanilla.");
        addFieldDefault("joinforge","message","&#ffb900You are using a forge client with the following mods: <modlist>");
        addFieldDefault("commandwatch","message","&#ffb900<server> Command &#CD0000| &#ffb900<player> &#CD0000| &#FF0000<command>");
        addFieldDefault("mention","message","&#FF00FF<mentioner> &#A900FFMentioned You In &#FF00FF<mentionerserver> &#A900FFSaying &#C8C8FF<message>");
    }
    //TODO Java Docs
    public static void addFieldDefault(String path,String fileName,String data) 
    {
    	String existingSetting = getFieldString(path,fileName);
    	if(existingSetting.equals(""))
    	{
    		updateField(path,fileName,data);
    	}
    }
    //TODO Java Docs
    public static String getFieldString(String path,String fileName) 
    {
    	for(int i = 0; i < fileNames.length;i++) 
    	{
    		if(fileNames[i].equalsIgnoreCase(fileName)) 
    		{
    			String data = configurations[i].getString(path);
    			for (String string : syncedFiles) 
    			{
    				if(string.equalsIgnoreCase(fileName)) 
    				{
            			Communication.sendConfigUpdate(path,fileName,data);
            			break;
    				}
    			}
    			return data;
    		}
    	}
    	return null;
    }
    //TODO Java Docs
    public static boolean getFieldBool(String path,String fileName) 
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
    			for (String string : syncedFiles) 
    			{
    				if(string.equalsIgnoreCase(fileName)) 
    				{
    					Communication.sendConfigUpdate(path,fileName,data);
            			break;
    				}
    			}
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
        			configurations[i] = provider.load(files[i]);
        		}
        		else if(fileNames[i].equalsIgnoreCase(fileName))
        		{
        			configurations[i] = provider.load(files[i]);
        			break;
        		}
        	}
		}
    	catch (IOException e)
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
        			provider.save(configurations[i], files[i]);
        		}
        		else if(fileNames[i].equalsIgnoreCase(fileName))
        		{
        			provider.save(configurations[i], files[i]);
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
}
