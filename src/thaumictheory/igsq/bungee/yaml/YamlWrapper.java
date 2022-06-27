package thaumictheory.igsq.bungee.yaml;

import java.security.SecureRandom;

import thaumictheory.igsq.shared.IGSQ;

public class YamlWrapper 
{
	public static String getMySQLUsername() 
	{
		return (String) IGSQ.getYaml().getField("mysql.username", "config.yaml");
	}
	public static void setMySQLUsername(String data) 
	{
		IGSQ.getYaml().setField("mysql.username", "config.yaml",data);
	}
	public static String getMySQLPassword() 
	{
		return (String) IGSQ.getYaml().getField("mysql.password", "config.yaml");
	}
	public static void setMySQLPassword(String data) 
	{
		IGSQ.getYaml().setField("mysql.password", "config.yaml",data);
	}
	public static String getMySQLLocation() 
	{
		return (String) IGSQ.getYaml().getField("mysql.database", "config.yaml");
	}
	public static void setMySQLLocation(String data) 
	{
		IGSQ.getYaml().setField("mysql.database", "config.yaml",data);
	}
	
	//Bungee
	public static Integer getHighestProtocol() 
	{
		return (Integer) IGSQ.getYaml().getField("support.protocol.highest", "config.yaml");
	}
	public static void setHighestProtocol(int data) 
	{
		IGSQ.getYaml().setField("support.protocol.highest", "config.yaml",data);
	}
	
	public static Integer getRecommendedProtocol() 
	{
		return (Integer) IGSQ.getYaml().getField("support.protocol.recommended", "config.yaml");
	}
	public static void setRecommendedProtocol(int data) 
	{
		IGSQ.getYaml().setField("support.protocol.recommended", "config.yaml",data);
	}
	
	public static Integer getLowestProtocol() 
	{
		return (Integer) IGSQ.getYaml().getField("support.protocol.lowest", "config.yaml");
	}
	public static void setLowestProtocol(int data) 
	{
		IGSQ.getYaml().setField("support.protocol.lowest", "config.yaml",data);
	}
	
	public static String getBackupRedirect() 
	{
		return (String) IGSQ.getYaml().getField("server.backupredirect", "config.yaml");
	}
	public static void setBackupRedirect(String data) 
	{
		IGSQ.getYaml().setField("server.backupredirect", "config.yaml",data);
	}
    //TODO Java Docs
    public static void applyDefault()
    {
        IGSQ.getYaml().defaultField("mysql.username","config.yaml","username");
        IGSQ.getYaml().defaultField("mysql.password","config.yaml","password");
        IGSQ.getYaml().defaultField("mysql.database","config.yaml","jdbc:mysql://localhost:3306/database?useSSL=false");
        
        IGSQ.getYaml().defaultField("support.protocol.highest","config.yaml",-1);
        IGSQ.getYaml().defaultField("support.protocol.recommended","config.yaml",759);
        IGSQ.getYaml().defaultField("support.protocol.lowest","config.yaml",340);
        
        IGSQ.getYaml().defaultField("server.backupredirect","config.yaml","hub");
        SecureRandom key = new SecureRandom();
        key.nextBytes(new byte[256]);
        IGSQ.getYaml().defaultField("key","security.yaml",key.nextInt());
        
        
        
        IGSQ.getYaml().defaultField("join","message.yaml","&#00FFFFWelcome <player>!");
        IGSQ.getYaml().defaultField("commandwatch","message.yaml","&#ffb900<server> Command &#CD0000| &#ffb900<player> &#CD0000| &#FF0000<command>");
        IGSQ.getYaml().defaultField("mention","message.yaml","&#FF00FF<mentioner> &#A900FFMentioned You In &#FF00FF<mentionerserver> &#A900FFSaying &#C8C8FF<message>");
    }
	
}
