package me.murrobby.igsq.bungee;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


public class Common_Bungee {
	static Main_Bungee bungee;
    public static File configFile;
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    
    public static void CreateFile(String fileName) 
    {
		 try
         {
	    	if (!bungee.getDataFolder().exists()) 
	    	{
	            bungee.getDataFolder().mkdir();
	    	}
		    	
		    configFile = new File(bungee.getDataFolder(),fileName);
			configFile.createNewFile();
         }
         catch (IOException e)
		 {
			e.printStackTrace();
		 }
    	
    }
    public static void LoadConfiguration()
    {
        //addField("MYSQL",true);
        addFieldDefault("MYSQL.username","config.yml","username");
        addFieldDefault("MYSQL.password","config.yml","password");
        addFieldDefault("MYSQL.database","config.yml","database");
        addFieldDefault("MESSAGE.join","config.yml","&aWelcome &b<player>!");
        addFieldDefault("MESSAGE.joinvanilla","config.yml","&3No extra data was found in handshake! Assuming vanilla.");
        addFieldDefault("MESSAGE.joinforge","config.yml","&6You are using a forge client with the following mods: <modlist>");
        addFieldDefault("MESSAGE.commandwatch","config.yml","&e<server> Command &5| &6<player> &5| &c<command>");
    }
    public static void addFieldDefault(String path,String fileName,String data) 
    {
    	String existingSetting = getFieldString(path,fileName);
    	if(existingSetting.equals(""))
    	{
    		SetField(path,fileName,data);
    	}
    	else 
    	{
    		System.out.println("Config option" + path + " Is already " + existingSetting + " . No Default Is needed!");
    	}
    }
    public static Boolean getFieldBool(String path,String fileName) 
    {
    	try
    	{
			return provider.load(new File(bungee.getDataFolder(), fileName)).getBoolean(path);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
			return null;
		}

    }
    public static String getFieldString(String path,String fileName) 
    {
    	try
    	{
			return provider.load(new File(bungee.getDataFolder(), fileName)).getString(path);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
			return null;
		}
    }
    
    public static int getFieldInt(String path,String fileName) 
    {
    	try
    	{
			return provider.load(new File(bungee.getDataFolder(), fileName)).getInt(path);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
			return -1;
		}

    }
    public static void SetField(String path,String fileName,String data) 
    {
    	Configuration configuration;
    	try
    	{
			configuration = provider.load(new File(bungee.getDataFolder(), fileName));
			configuration.set(path, data);
			Common_Bungee.SaveFile(fileName,configuration);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
		}
    }
    public static void SaveFile(String fileName,Configuration configuration) 
    {
    	
    	try 
    	{
    		provider.save(configuration, new File(bungee.getDataFolder(),fileName));
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
		}
    }
	public static String GetMessage(String messageName, String replace,String with, String replace2,String with2,String replace3,String with3)
	{
    	return ChatColor.translateAlternateColorCodes('&', Common_Bungee.getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with).replace(replace2, with2).replace(replace3, with3));
	}
	public static String GetMessage(String messageName, String replace,String with, String replace2,String with2)
	{
    	return ChatColor.translateAlternateColorCodes('&', Common_Bungee.getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with).replace(replace2, with2));
	}
	public static String GetMessage(String messageName, String replace,String with)
	{
    	return ChatColor.translateAlternateColorCodes('&', Common_Bungee.getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with));
	}
	public static String GetMessage(String messageName)
	{
    	return ChatColor.translateAlternateColorCodes('&', Common_Bungee.getFieldString("MESSAGE." + messageName, "config.yml"));
	}

}
