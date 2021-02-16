package me.murrobby.igsq.spigot;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;

public class Messaging {
	 /**
     * Converts hex codes & formatting codes for use in chat.
     * @apiNote Looks for &# and then takes in values after it and converts it to hex
     * @see net.md_5.bungee.api.ChatColor#of(String)
     * @return <b>String</b>
     */
    public static String chatFormatter(String textToFormat) //CF
    {
    	String textToColour = textToFormat.replaceAll("[\\r]", ""); //Replaces Windows Enter Chars to what minecraft supports
    	textToColour = textToColour.replaceAll("[\\t]", "        "); //Replaces Windows tab Chars to what minecraft supports
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
		String message = Yaml.getFieldString(messageName, "message");
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
    	return chatFormatter(Yaml.getFieldString(messageName, "message").replace(replace,with));
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
    	return chatFormatter(Yaml.getFieldString(messageName, "message"));
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
		String message = Yaml.getFieldString(messageName, "message");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return chatFormatterConsole(message);
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
    	return chatFormatterConsole(Yaml.getFieldString(messageName, "message").replace(replace,with));
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
    	return chatFormatterConsole(Yaml.getFieldString(messageName, "message"));
    }
    
    /**
     * display any caught errors to developers online.
     * @apiNote Raycasts downwards until it hits a Block. Returns the block it hit. Ignores Passable.
     * @see org.bukkit.block.Block#isPassable
     * @return <b>Block</b>
     */
    public static void sendException(Exception exception,String reason,String errorCode,CommandSender cause) 
    {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	exception.printStackTrace(pw);
    	String stackTrace = sw.toString();
    	for(Player player : Bukkit.getOnlinePlayers()) 
    	{
    		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
    		if(player.hasPermission("igsq.error")) 
    		{
    			ErrorLogging errorLogStatus = yaml.getErrorLogSetting();
    			if(!errorLogStatus.equals(ErrorLogging.DISABLED)) 
    			{
    	    		if(errorLogStatus.equals(ErrorLogging.DETAILED)) 
    	    		{
    	    			player.sendMessage(chatFormatter("&#FF6161Verbose: " + stackTrace));
    	    		}
    	    		player.sendMessage(chatFormatter("&#FF0000Spigot Error: " + reason));
    	    		player.sendMessage(chatFormatter("&#ffb900Error Code: " + errorCode));
    	    		if(cause != null) 
    	    		{
    	    			player.sendMessage(chatFormatter("&#FFFF00Caused by: " + cause.getName()));
    	    		}
    			}
    		}
    		else if(cause != null && cause instanceof Player) 
    		{
    			player.sendMessage(chatFormatter("&#FF0000Something went wrong. If you see a developer tell them this secret code &#ff00ff" + errorCode));
    		}
    	}
    	ErrorLogging errorLogStatus = YamlWrapper.getErrorLogSetting();
		if(!errorLogStatus.equals(ErrorLogging.DISABLED)) 
		{
    		if(errorLogStatus.equals(ErrorLogging.DETAILED)) 
    		{
    			System.out.println(chatFormatter("&cVerbose: " + stackTrace));
    		}
    		System.out.println(chatFormatterConsole("&4Spigot Error: " + reason));
    		System.out.println(chatFormatterConsole("&6Error Code: " + errorCode));
    		if(cause != null) 
    		{
    			System.out.println(chatFormatterConsole("&eCaused by: " + cause.getName()));
    		}
		}
    }
}
