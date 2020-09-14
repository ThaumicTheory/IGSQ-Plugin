package me.murrobby.igsq.bungee;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


public class Common_Bungee {
	static Main_Bungee bungee;

    public static String[] fileNames = {"config","player","internal","message"};
    private static String[] syncedFiles = {"player"};
    private static File[] files;
    private static Configuration[] configurations;
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    
    
    
    
    
    public static void sendSound(ProxiedPlayer player,String sound,float volume,float pitch) 
    {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		DataOutputStream out = new DataOutputStream(stream);
    		try
    		{
    			out.writeUTF(Common_Shared.removeNull(sound));
    			out.writeFloat(volume);
    			out.writeFloat(pitch);
    			player.getServer().getInfo().sendData("igsq:sound", stream.toByteArray());
    		}
    		catch (IOException e)
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
    public static void sendConfigUpdate(String path,String fileName,String data,ProxiedPlayer player) 
    {
    			ByteArrayOutputStream stream = new ByteArrayOutputStream();
        		DataOutputStream out = new DataOutputStream(stream);
        		try
        		{
        			out.writeUTF(Common_Shared.removeNull(fileName));
        			out.writeUTF(Common_Shared.removeNull(path));
        			out.writeUTF(Common_Shared.removeNull(data));
        			player.getServer().getInfo().sendData("igsq:yml", stream.toByteArray());
        		}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    	}
    public static void sendConfigUpdate(String path,String fileName,String data) 
    {
    	String[] servers = new String[0];
    	for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) 
    	{
    		Boolean serverChecked = false;
    		for (String serversDone : servers) 
    		{
    			if(player.getServer() != null && serversDone.equals(player.getServer().getInfo().getName())) 
    			{
    				serverChecked = true;
    				break;
    			}
    		}
    		if((!serverChecked) && player.getServer() != null) 
    		{
				servers = Common_Shared.append(servers, player.getServer().getInfo().getName());
    			ByteArrayOutputStream stream = new ByteArrayOutputStream();
        		DataOutputStream out = new DataOutputStream(stream);
        		try
        		{
        			out.writeUTF(Common_Shared.removeNull(fileName));
        			out.writeUTF(Common_Shared.removeNull(path));
        			out.writeUTF(Common_Shared.removeNull(data));
        			player.getServer().getInfo().sendData("igsq:yml", stream.toByteArray());
        		}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    		}
    	}
    }
    //TODO Java Docs
    public static void createFiles() 
    {
		 try
         {
	    	if (!bungee.getDataFolder().exists()) 
	    	{
	            bungee.getDataFolder().mkdir();
	    	}
	    	files = new File[fileNames.length];
	    	configurations = new Configuration[fileNames.length];
	    	for (int i = 0; i < fileNames.length;i++) 
	    	{
	    		files[i] = new File(bungee.getDataFolder(),fileNames[i] + ".yml");
	    		files[i].createNewFile();
	    	}
	    	
         }
         catch (Exception e)
		 {
			e.printStackTrace();
		 }
    	
    }
    //TODO Java Docs
    public static void applyDefaultConfiguration()
    {
        addFieldDefault("MYSQL.username","config","username");
        addFieldDefault("MYSQL.password","config","password");
        addFieldDefault("MYSQL.database","config","jdbc:mysql://localhost:3306/database?useSSL=false");
        
        addFieldDefault("SUPPORT.luckperms","config","true");
        addFieldDefault("SUPPORT.protocol.highest","config","-1");
        addFieldDefault("SUPPORT.protocol.recommended","config","753");
        addFieldDefault("SUPPORT.protocol.lowest","config","340");
        
        
        
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
            			sendConfigUpdate(path,fileName,data);
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
            			sendConfigUpdate(path,fileName,data);
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
    
    /**
     * Converts hex codes & formatting codes for use in chat.
     * @apiNote Looks for &# and then takes in values after it and converts it to hex
     * @see net.md_5.bungee.api.ChatColor#of(String)
     * @return <b>String</b>
     */
    public static String chatFormatter(String textToColour) 
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
     * Converts formatting codes for use in console & legacy. ignores Hex Codes.
     * @apiNote Removes &# Hex Colours
     * @see net.md_5.bungee.api.ChatColor#translateAlternateColorCodes(char,String)
     * @return <b>String</b>
     */
    public static String chatFormatterConsole(String textToColour) 
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
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #chatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #depend(String[],int)}
     * @see #getFieldString
     * @see #getFormattedMessage(String)
     * @see #getFormattedMessage(String, String,String)
     * @return <b>BaseComponent[]</b>
     */
	public static BaseComponent[] getFormattedMessage(String messageName, String[] wildcards)
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Shared.depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = getFieldString(messageName, "message");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return TextComponent.fromLegacyText(Common_Bungee.chatFormatter(message));
	}
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #chatFormatterConsole(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #depend(String[],int)}
     * @see #getFieldString
     * @see #getFormattedMessageConsole(String)
     * @see #getFormattedMessageConsole(String, String,String)
     * @return <b>String</b>
     */
	public static String getFormattedMessageConsole(String messageName, String[] wildcards)
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Shared.depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = getFieldString(messageName, "message");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return Common_Bungee.chatFormatterConsole(message);
	}
	
	
	
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #chatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessage(String)
     * @see #getFormattedMessage(String, String[])
     * @return <b>BaseComponent[]</b>
     */
	public static BaseComponent[] getFormattedMessage(String messageName, String replace,String with)
	{
    	return TextComponent.fromLegacyText(Common_Bungee.chatFormatter(getFieldString(messageName, "message").replace(replace, with)));
	}
	 /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #chatFormatterConsole(String)}.This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessageConsole(String)
     * @see #getFormattedMessageConsole(String, String[])
     * @return <b>String</b>
     */
	public static String getFormattedMessageConsole(String messageName, String replace,String with)
	{
    	return Common_Bungee.chatFormatterConsole(getFieldString(messageName, "message").replace(replace, with));
	}
	
	
	
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #chatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessage(String,String,String)
     * @see #getFormattedMessage(String, String[])
     * @return <b>BaseComponent[]</b>
     */
	public static BaseComponent[] getFormattedMessage(String messageName)
	{
    	return TextComponent.fromLegacyText(Common_Bungee.chatFormatter(getFieldString(messageName, "message")));
	}
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #chatFormatterConsole(String)}.This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #getFormattedMessageConsole(String,String,String)
     * @see #getFormattedMessageConsole(String, String[])
     * @return <b>String</b>
     */
	public static String getFormattedMessageConsole(String messageName)
	{
    	return Common_Bungee.chatFormatterConsole(getFieldString(messageName, "message"));
	}
}
