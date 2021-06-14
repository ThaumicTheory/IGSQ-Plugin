package thaumictheory.igsq.bungee;

import java.security.SecureRandom;

public class YamlWrapper 
{
	public static String getMySQLUsername() 
	{
		return Yaml.getFieldString("mysql.username", "config");
	}
	public static void setMySQLUsername(String data) 
	{
		Yaml.updateField("mysql.username", "config",data);
	}
	public static String getMySQLPassword() 
	{
		return Yaml.getFieldString("mysql.password", "config");
	}
	public static void setMySQLPassword(String data) 
	{
		Yaml.updateField("mysql.password", "config",data);
	}
	public static String getMySQLLocation() 
	{
		return Yaml.getFieldString("mysql.database", "config");
	}
	public static void setMySQLLocation(String data) 
	{
		Yaml.updateField("mysql.database", "config",data);
	}
	public static boolean isLuckpermsSupported() 
	{
		return Yaml.getFieldBool("support.luckperms", "config");
	}
	public static void setLuckpermsSupported(boolean data) 
	{
		Yaml.updateField("gameplay.luckperms", "config",data);
	}
	
	//Bungee
	public static int getHighestProtocol() 
	{
		return Yaml.getFieldInt("support.protocol.highest", "config");
	}
	public static void setHighestProtocol(int data) 
	{
		Yaml.updateField("support.protocol.highest", "config",data);
	}
	
	public static int getRecommendedProtocol() 
	{
		return Yaml.getFieldInt("support.protocol.recommended", "config");
	}
	public static void setRecommendedProtocol(int data) 
	{
		Yaml.updateField("support.protocol.recommended", "config",data);
	}
	
	public static int getLowestProtocol() 
	{
		return Yaml.getFieldInt("support.protocol.lowest", "config");
	}
	public static void setLowestProtocol(int data) 
	{
		Yaml.updateField("support.protocol.lowest", "config",data);
	}
	
	public static String getBackupRedirect() 
	{
		return Yaml.getFieldString("server.backupredirect", "config");
	}
	public static void setBackupRedirect(String data) 
	{
		Yaml.updateField("server.backupredirect", "config",data);
	}
	public static String getModList() 
	{
		return Yaml.getFieldString("modlist", "security");
	}
	public static void setModList(String data) 
	{
		Yaml.updateField("modlist", "security",data);
	}
    //TODO Java Docs
    public static void applyDefault()
    {
        Yaml.addFieldDefault("mysql.username","config","username");
        Yaml.addFieldDefault("mysql.password","config","password");
        Yaml.addFieldDefault("mysql.database","config","jdbc:mysql://localhost:3306/database?useSSL=false");
        
        Yaml.addFieldDefault("support.luckperms","config",true);
        Yaml.addFieldDefault("support.protocol.highest","config",-1);
        Yaml.addFieldDefault("support.protocol.recommended","config",754);
        Yaml.addFieldDefault("support.protocol.lowest","config",340);
        
        Yaml.addFieldDefault("server.backupredirect","config","hub");
        SecureRandom key= new SecureRandom();
        key.nextBytes(new byte[256]);
        Yaml.addFieldDefault("key","security",key.nextInt());
        Yaml.addFieldDefault("modlist","security","");
        
        
        
        Yaml.addFieldDefault("join","message","&#00FFFFWelcome <player>!");
        Yaml.addFieldDefault("joinvanilla","message","&#00FFFFNo extra data was found in handshake! Assuming vanilla.");
        Yaml.addFieldDefault("joinforge","message","&#ffb900You are using a forge client with the following mods: <modlist>");
        Yaml.addFieldDefault("commandwatch","message","&#ffb900<server> Command &#CD0000| &#ffb900<player> &#CD0000| &#FF0000<command>");
        Yaml.addFieldDefault("mention","message","&#FF00FF<mentioner> &#A900FFMentioned You In &#FF00FF<mentionerserver> &#A900FFSaying &#C8C8FF<message>");
    }
	
}
