package thaumictheory.igsq.bungee;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import thaumictheory.igsq.shared.IGSQ;

public class Messaging 
{
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
			System.out.println("Formatted Messages wildcards were odd! It should be even. Ignoring this request!");
			return null;
		}
		String message = (String) IGSQ.getYaml().getField(messageName, "message.yaml");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return TextComponent.fromLegacyText(chatFormatter(message));
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
			System.out.println("Formatted Messages wildcards were odd! It should be even. Ignoring this request!");
			return null;
		}
		String message = (String) IGSQ.getYaml().getField(messageName, "message.yaml");
		for(int i = 0; wildcards.length > i;i++) message = message.replace(wildcards[i], wildcards[++i]);
    	return chatFormatterConsole(message);
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
    	return TextComponent.fromLegacyText(chatFormatter(((String) IGSQ.getYaml().getField(messageName, "message.yaml")).replace(replace, with)));
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
    	return chatFormatterConsole(((String) IGSQ.getYaml().getField(messageName, "message.yaml")).replace(replace, with));
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
    	return TextComponent.fromLegacyText(chatFormatter((String) IGSQ.getYaml().getField(messageName, "message.yaml")));
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
    	return chatFormatterConsole((String) IGSQ.getYaml().getField(messageName, "message.yaml"));
	}
}
