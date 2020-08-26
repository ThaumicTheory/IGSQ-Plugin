package me.murrobby.igsq.bungee;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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
        addFieldDefault("MYSQL.username","username");
        addFieldDefault("MYSQL.password","password");
        addFieldDefault("MYSQL.database","database");
        addFieldDefault("MESSAGE.join","&#00FFFFWelcome <player>!");
        addFieldDefault("MESSAGE.joinvanilla","&#00FFFFNo extra data was found in handshake! Assuming vanilla.");
        addFieldDefault("MESSAGE.joinforge","&#ffb900You are using a forge client with the following mods: <modlist>");
        addFieldDefault("MESSAGE.commandwatch","&#ffb900<server> Command &#CD0000| &#ffb900<player> &#CD0000| &#FF0000<command>");
        addFieldDefault("SUPPORT.luckperms","true");
        addFieldDefault("SUPPORT.protocol.highest","-1");
        addFieldDefault("SUPPORT.protocol.recommended","751");
        addFieldDefault("SUPPORT.protocol.lowest","340");
    }
    public static void addFieldDefault(String path,String data) 
    {
    	String fileName = "config.yml";
    	String existingSetting = getFieldString(path,fileName);
    	if(existingSetting.equals(""))
    	{
    		SetField(path,fileName,data);
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
    		provider.save(configuration, new File(bungee.getDataFolder(),fileName));
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
		}
    }
    
    
    
    /**
     * Converts hex codes & formatting codes for use in chat.
     * @apiNote Looks for &# and then takes in values after it and converts it to hex
     * @see net.md_5.bungee.api.ChatColor#of(String)
     * @return <b>String</b>
     */
    public static String ChatFormatter(String textToColour) 
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
    public static String ChatFormatterConsole(String textToColour) 
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
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #ChatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #Depend(String[],int)}
     * @see #getFieldString
     * @see #GetFormattedMessage(String)
     * @see #GetFormattedMessage(String, String,String)
     * @return <b>BaseComponent[]</b>
     */
	public static BaseComponent[] GetFormattedMessage(String messageName, String[] wildcards)
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Bungee.Depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = getFieldString("MESSAGE." + messageName, "config.yml");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return TextComponent.fromLegacyText(Common_Bungee.ChatFormatter(message));
	}
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts an array of wildcards and their replacements. Implements {@link #ChatFormatterConsole(String)}. This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml. if array is odd last record will be cut {@link #Depend(String[],int)}
     * @see #getFieldString
     * @see #GetFormattedMessageConsole(String)
     * @see #GetFormattedMessageConsole(String, String,String)
     * @return <b>String</b>
     */
	public static String GetFormattedMessageConsole(String messageName, String[] wildcards)
	{
		if(wildcards.length %2 != 0) 
		{
			wildcards = Common_Bungee.Depend(wildcards, wildcards.length-1);
			System.out.println("Formatted Messages wildcards were odd! Removing last record to avoid overflow.");
		}
		String message = getFieldString("MESSAGE." + messageName, "config.yml");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return Common_Bungee.ChatFormatterConsole(message);
	}
	
	
	
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #ChatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessage(String)
     * @see #GetFormattedMessage(String, String[])
     * @return <b>BaseComponent[]</b>
     */
	public static BaseComponent[] GetFormattedMessage(String messageName, String replace,String with)
	{
    	return TextComponent.fromLegacyText(Common_Bungee.ChatFormatter(getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with)));
	}
	 /**
     * Gets a message & replaces wildcards with values defined. this override accepts 1 wildcard. Implements {@link #ChatFormatterConsole(String)}.This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessageConsole(String)
     * @see #GetFormattedMessageConsole(String, String[])
     * @return <b>String</b>
     */
	public static String GetFormattedMessageConsole(String messageName, String replace,String with)
	{
    	return Common_Bungee.ChatFormatterConsole(getFieldString("MESSAGE." + messageName, "config.yml").replace(replace, with));
	}
	
	
	
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #ChatFormatter(String)}.
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessage(String,String,String)
     * @see #GetFormattedMessage(String, String[])
     * @return <b>BaseComponent[]</b>
     */
	public static BaseComponent[] GetFormattedMessage(String messageName)
	{
    	return TextComponent.fromLegacyText(Common_Bungee.ChatFormatter(getFieldString("MESSAGE." + messageName, "config.yml")));
	}
    /**
     * Gets a message & replaces wildcards with values defined. this override accepts 0 wildcards. Implements {@link #ChatFormatterConsole(String)}.This Version is intended for use In Consoles & Legacy Only!
     * @apiNote Takes From MESSAGE. in config.yml
     * @see #getFieldString
     * @see #GetFormattedMessageConsole(String,String,String)
     * @see #GetFormattedMessageConsole(String, String[])
     * @return <b>String</b>
     */
	public static String GetFormattedMessageConsole(String messageName)
	{
    	return Common_Bungee.ChatFormatterConsole(getFieldString("MESSAGE." + messageName, "config.yml"));
	}
	
	
	
	
	
	
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
}
