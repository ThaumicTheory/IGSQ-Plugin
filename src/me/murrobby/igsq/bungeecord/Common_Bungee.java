package me.murrobby.igsq.bungeecord;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


public class Common_Bungee {
	static Main_Bungee bungee;
    public static Configuration configuration;
    public static File configFile;
    
    public static Configuration CreateFile(String fileName) 
    {
    	configFile = new File(bungee.getDataFolder(),fileName);
    	if (!configFile.exists())
    	{
    		 try
             {
    			 configFile.createNewFile();
             }
             catch (IOException e)
    		 {
 				e.printStackTrace();
    		 }
    	}
    	try 
    	{
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(bungee.getDataFolder(), fileName));
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
		}
    	return configuration;
    	
    }
    public static void LoadConfiguration()
    {
        //addField("MYSQL",true);
        addField("MYSQL.username","username");
        addField("MYSQL.password","password");
        addField("MYSQL.database","database");
        try
        {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(bungee.getDataFolder(), "config.yml"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
    }
    public static void addField(String path,String data) 
    {
    	configuration.set(path, data);
    }
    public static void addField(String path,Boolean data) 
    {
    	configuration.set(path, data);
    }
    public static Boolean getFieldBool(String path,String tableName) 
    {
    	tableName += ".yml";
    	try
    	{
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(bungee.getDataFolder(), tableName));
			return configuration.getBoolean(path);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
			return null;
		}

    }
    public static String getFieldString(String path,String tableName) 
    {
    	tableName += ".yml";
    	try
    	{
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(bungee.getDataFolder(), tableName));
			return configuration.getString(path);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
			return null;
		}
    }
    
    public static int getFieldInt(String path,String tableName) 
    {
    	tableName += ".yml";
    	try
    	{
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(bungee.getDataFolder(), tableName));
			return configuration.getInt(path);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
			return -1;
		}

    }
    public static void SaveFile(String fileName) 
    {
    	
    	try 
    	{
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(bungee.getDataFolder(), "config.yml"));
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
		}
    }

}
