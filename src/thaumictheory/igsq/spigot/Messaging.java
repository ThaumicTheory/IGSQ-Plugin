package thaumictheory.igsq.spigot;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class Messaging {
	 /**
     * Converts hex codes & formatting codes for use in chat.
     * @apiNote Looks for &# and then takes in values after it and converts it to hex
     * @see net.md_5.bungee.api.ChatColor#of(String)
     * @return <b>String</b>
     */
    public static String chatFormatter(String textToFormat) //CF
    {
    	if(textToFormat == null) return "";
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
			createLog(Level.WARNING,"Formatted Messages wildcards were odd! It should be even. Ignoring this request!");
			return null;
		}
		String message = (String) IGSQ.getYaml().getField(messageName, "message.yaml");
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
    	return chatFormatter(((String) IGSQ.getYaml().getField(messageName, "message.yaml")).replace(replace,with));
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
    	return chatFormatter((String) IGSQ.getYaml().getField(messageName, "message.yaml"));
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
			createLog(Level.WARNING,"Formatted Messages wildcards were odd! It should be even. Ignoring this request!");
			return null;
		}
		String message = (String) IGSQ.getYaml().getField(messageName, "message.yaml");
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
    	return chatFormatterConsole(((String) IGSQ.getYaml().getField(messageName, "message.yaml")).replace(replace,with));
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
    	return chatFormatterConsole((String) IGSQ.getYaml().getField(messageName, "message.yaml"));
    }
    
	public static ChatColor getLastLegacyColourByText(String text) 
	{
		if(text.length() < 2) return ChatColor.WHITE;
		String[] chars = text.split("");
		for(int i = chars.length-2; i <= 0; i--)
		{
			if(chars[i].equals("&")) 
			{
				return ChatColor.getByChar(chars[i+1]);
			}
		}
		return ChatColor.WHITE;
	}
    /**
     * display any caught errors to developers online.
     * @return <b>Block</b>
     */
    public static void sendException(Exception exception,String reason,String errorCode,CommandSender cause) 
    {
    	if(exception == null) return;
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	exception.printStackTrace(pw);
    	String stackTrace = sw.toString();
    	for(Player player : Bukkit.getOnlinePlayers()) 
    	{
    		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
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
    public static void sendException(Throwable exception,String message) 
    {
    	String stackTrace = null;
    	if(exception != null) 
    	{
        	StringWriter sw = new StringWriter();
        	PrintWriter pw = new PrintWriter(sw);
        	exception.printStackTrace(pw);
        	stackTrace = sw.toString();
    	}
    	for(Player player : Bukkit.getOnlinePlayers()) 
    	{
    		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
    		if(player.hasPermission("igsq.error")) 
    		{
    			ErrorLogging errorLogStatus = yaml.getErrorLogSetting();
    			if(!errorLogStatus.equals(ErrorLogging.DISABLED)) 
    			{
    				if(message != null) player.sendMessage(chatFormatter("&#FF0000Spigot Error: " + message));
    	    		if(errorLogStatus.equals(ErrorLogging.DETAILED)) 
    	    		{
    	    			if(stackTrace != null) player.sendMessage(chatFormatter("&#FF6161Verbose: " + stackTrace));
    	    		}
    			}
    		}
    	}
    }
    public static void sendLog(String message) 
    {
    	for(Player player : Bukkit.getOnlinePlayers()) 
    	{
    		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
    		if(player.hasPermission("igsq.error")) 
    		{
    			ErrorLogging errorLogStatus = yaml.getErrorLogSetting();
    			if(!errorLogStatus.equals(ErrorLogging.DISABLED)) 
    			{
    				if(errorLogStatus.equals(ErrorLogging.DETAILED)) 
    	    		{
    					if(message != null) player.sendMessage(chatFormatter(message));
    	    		}
    			}
    		}
    	}
    }
    public static String getLogColour(Level level) 
    {
    	if(level.equals(Level.WARNING)) return "&e";
    	if(level.equals(Level.SEVERE)) return "&c";
    	return "&7";
    }
    public static void createLog(Level level,String message) 
    {
    	Common.spigot.getLogger().logp(level, Common.spigot.getName(), null, message);
    }
    public static void createLog(String message) 
    {
    	createLog(Level.INFO, message);
    }
    
    public static void createSafeLog(Level level,String message) 
    {
    	createLog(level, "[@] " + message);
    }
    public static void createSafeLog(String message) 
    {
    	createSafeLog(Level.INFO, message);
    }
}
