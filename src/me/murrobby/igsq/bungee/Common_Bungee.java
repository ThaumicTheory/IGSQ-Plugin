package me.murrobby.igsq.bungee;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


public class Common_Bungee {
	static Main_Bungee bungee;
    public static File configFile;
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
	@Deprecated public static String[] ranks = {"council","mod2","mod","elite3","elite2","elite","epic3","epic2","epic","soaring","flying","rising","default"};
	@Deprecated public static String[] rankPrefixes = {"&5[&dCouncil&5] &d","&6[&dSenior &eStaff&6] &e","&6[&5Trial &eStaff&6] &e","&3[&dElite III&3] &d","&3[&dElite II&3] &d","&3[&dElite I&3] &d","&3[&aEpic III&3] &a","&3[&aEpic II&3] &a","&3[&aEpic I&3] &a","&a[&2Soaring&a] &2","&3[&6Flying&3] &6","&3[&9Rising&3] &9","&3[&bSquirrel&3] &b"};
    
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
        addFieldDefault("SUPPORT.luckperms","config.yml","true");
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
    public static String ChatColour(String textToColour) 
    {
    	return ChatColor.translateAlternateColorCodes('&', textToColour);
    }
	public static String GetMessage(String messageName, String replace,String with, String replace2,String with2,String replace3,String with3,String replace4,String with4)
	{
    	return  getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with).replace(replace2, with2).replace(replace3, with3).replace(replace4, with4);
	}
	public static String GetMessage(String messageName, String replace,String with, String replace2,String with2,String replace3,String with3)
	{
    	return getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with).replace(replace2, with2).replace(replace3, with3);
	}
	public static String GetMessage(String messageName, String replace,String with, String replace2,String with2)
	{
    	return getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with).replace(replace2, with2);
	}
	public static String GetMessage(String messageName, String replace,String with)
	{
    	return getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with);
	}
	public static String GetMessage(String messageName)
	{
    	return getFieldString("MESSAGE." + messageName, "config.yml");
	}
    /**
     * gets the name of the Players highest rank.
     * @deprecated Use {@link me.murrobby.igsq.bungee.lp.Common_LP#GetRank(ProxiedPlayer)} instead.
     * @apiNote Will always show the highest rank they are in. ranks are defined by the "group." prefix.
     * @see #GetRank
     * @return <b>String</b>
     * @throws java.lang.NullPointerException
     */
    public static String GetRankString(ProxiedPlayer player) throws NullPointerException
    {
    	for(String rank : ranks) 
    	{
    		if(player.hasPermission("group." + rank)) 
    		{
    			return rank;
    		}
    	}
    	throw null;
    }
    /**
     * gets the ID of the Players highest rank. 
     * @deprecated Use {@link me.murrobby.igsq.bungee.lp.Common_LP#GetRank(ProxiedPlayer)} instead.
     * @apiNote Will always show the highest rank they are in. ranks are defined by the "group." prefix.
     * @see #GetRankString
     * @return <b>int</b>
     * @throws java.lang.NullPointerException
     */
    @Deprecated
    public static int GetRank(ProxiedPlayer player) throws NullPointerException
    {
    	int rankID = 0;
    	for(String rank : ranks) 
    	{
    		if(player.hasPermission("group." + rank)) 
    		{
    			return rankID;
    		}
    		rankID++;
    	}
    	throw null;
    }
    /**
     * gets the Players highest ranks prefix. method overrides available {@link #GetRankPrefix(int)}.
     * @deprecated Use {@link me.murrobby.igsq.bungee.lp.Common_LP#GetPrefix(ProxiedPlayer)} instead.
     * @apiNote Will always show the highest rank thats they are in's prefix.
     * @see #GetRankPrefix(int)
     * @return <b>String</b>
     */
    @Deprecated
    public static String GetRankPrefix(ProxiedPlayer player) 
    {
    	int rankID = GetRank(player);
    	return rankPrefixes[rankID];
    }
    /**
     * gets the ranks prefix. method overrides available {@link #GetRankPrefix(String)}.
     * @deprecated Use {@link me.murrobby.igsq.bungee.lp.Common_LP#GetPrefix(ProxiedPlayer)} instead.
     * @see #GetRankPrefix(String)
     * @return <b>String</b>
     */
    @Deprecated
    public static String GetRankPrefix(int rankID)
    {
    	return rankPrefixes[rankID];
    }

}
