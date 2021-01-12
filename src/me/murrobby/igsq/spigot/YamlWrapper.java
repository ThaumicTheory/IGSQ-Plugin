package me.murrobby.igsq.spigot;

import org.bukkit.configuration.file.FileConfiguration;

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
	public static boolean isBlockHunt() 
	{
		return Yaml.getFieldBool("gameplay.blockhunt", "config");
	}
	public static void setBlockHunt(boolean data) 
	{
		Yaml.updateField("gameplay.blockhunt", "config",data);
	}
	public static boolean isExpert() 
	{
		return Yaml.getFieldBool("gameplay.expert", "config");
	} 
	public static void setExpert(boolean data) 
	{
		Yaml.updateField("gameplay.expert", "config",data);
	}
	public static boolean isEggRespawn() 
	{
		return Yaml.getFieldBool("gameplay.dragoneggrespawn", "config");
	}
	public static void setEggRespawn(boolean data) 
	{
		Yaml.updateField("gameplay.dragoneggrespawn", "config",data);
	}
	public static boolean isLuckpermsSupported() 
	{
		return Yaml.getFieldBool("support.luckperms", "config");
	}
	public static void setLuckpermsSupported(boolean data) 
	{
		Yaml.updateField("gameplay.luckperms", "config",data);
	}
	
	
	public static int getBlockHuntOutOfBoundsTime() 
	{
		return Yaml.getFieldInt("outofboundstime", "blockhunt");
	}
	public static void setBlockHuntOutOfBoundsTime(int data) 
	{
		Yaml.updateField("outofboundstime", "blockhunt", data);
	}
	public static int getBlockHuntHideTime() 
	{
		return Yaml.getFieldInt("hidetime", "blockhunt");
	}
	public static void setBlockHuntHideTime(int data) 
	{
		Yaml.updateField("hidetime", "blockhunt", data);
	}
	public static int getBlockHuntLobbyTime() 
	{
		return Yaml.getFieldInt("lobbytime", "blockhunt");
	}
	public static void setBlockHuntLobbyTime(int data) 
	{
		Yaml.updateField("lobbytime", "blockhunt", data);
	}
	public static int getBlockHuntGameTime() 
	{
		return Yaml.getFieldInt("gametime", "blockhunt");
	}
	public static void setBlockHuntGameTime(int data) 
	{
		Yaml.updateField("gametime", "blockhunt", data);
	}
	public static int getBlockHuntMinimumPlayers() 
	{
		return Yaml.getFieldInt("minimumplayers", "blockhunt");
	}
	public static void setBlockHuntMinimumPlayers(int data) 
	{
		Yaml.updateField("minimumplayers", "blockhunt", data);
	}
	public static int getBlockHuntBlockPickCooldown()
	{
		return Yaml.getFieldInt("blockpickcooldown", "blockhunt");
	}
	public static void setBlockHuntBlockPickCooldown(int data) 
	{
		Yaml.updateField("blockpickcooldown", "blockhunt", data);
	}
	public static int getBlockHuntCloakCooldown()
	{
		return Yaml.getFieldInt("cloakcooldown", "blockhunt");
	}
	public static void setBlockHuntCloakCooldown(int data) 
	{
		Yaml.updateField("cloakcooldown", "blockhunt", data);
	}
	public static int getBlockHuntFailCooldown()
	{
		return Yaml.getFieldInt("failcooldown", "blockhunt");
	}
	public static void setBlockHuntFailCooldown(int data) 
	{
		Yaml.updateField("failcooldown", "blockhunt", data);
	}
	public static int getBlockHuntVisibilityRange()
	{
		return Yaml.getFieldInt("visibilityrange", "blockhunt");
	}
	public static void setBlockHuntVisibilityRange(int data) 
	{
		Yaml.updateField("visibilityrange", "blockhunt", data);
	}
	//Maps
	
	public static String getBlockHuntHubLocation()
	{
		return Yaml.getFieldString("maps.hub.location", "blockhunt");
	}
	public static void setBlockHuntHubLocation(String data) 
	{
		Yaml.updateField("maps.hub.location", "blockhunt", data);
	}
	
	public static String getBlockHuntMapName(int id)
	{
		return Yaml.getFieldString("maps."+ id + ".name", "blockhunt");
	}
	public static void setBlockHuntMapName(String data,int id) 
	{
		Yaml.updateField("maps."+ id + ".name", "blockhunt", data);
	}
	public static String getBlockHuntMapPreLobby(int id)
	{
		return Yaml.getFieldString("maps."+ id + ".prelobby", "blockhunt");
	}
	public static void setBlockHuntMapPreLobby(String data,int id) 
	{
		Yaml.updateField("maps."+ id + ".prelobby", "blockhunt", data);
	}
	
	public static String getBlockHuntMapHider(int id)
	{
		return Yaml.getFieldString("maps."+ id + ".hider", "blockhunt");
	}
	public static void setBlockHuntMapHider(String data,int id) 
	{
		Yaml.updateField("maps."+ id + ".hider", "blockhunt", data);
	}
	
	public static String getBlockHuntMapPreSeeker(int id)
	{
		return Yaml.getFieldString("maps."+ id + ".preseeker", "blockhunt");
	}
	public static void setBlockHuntMapPreSeeker(String data,int id) 
	{
		Yaml.updateField("maps."+ id + ".preseeker", "blockhunt", data);
	}
	
	public static String getBlockHuntMapSeeker(int id)
	{
		return Yaml.getFieldString("maps."+ id + ".seeker", "blockhunt");
	}
	public static void setBlockHuntMapSeeker(String data,int id) 
	{
		Yaml.updateField("maps."+ id + ".seeker", "blockhunt", data);
	}
	
	public static String getBlockHuntMapBlocks(int id)
	{
		return Yaml.getFieldString("maps."+ id + ".blocks", "blockhunt");
	}
	public static void setBlockHuntMapBlocks(String data,int id) 
	{
		Yaml.updateField("maps."+ id + ".blocks", "blockhunt", data);
	}
	public static String getServerName() 
	{
		return Yaml.getFieldString("server", "internal");
	}
	public static void setServerName(String data) 
	{
		Yaml.updateField("server", "config",data);
	}
	
	public static String getDefaultChatController() 
	{
		return Yaml.getFieldString("controller.chat", "internal");
	}
	public static void setDefaultChatController(String data) 
	{
		Yaml.updateField("controller.chat", "internal",data);
	}
	
	public static String getDefaultNameController() 
	{
		return Yaml.getFieldString("controller.tag", "internal");
	}
	public static void setDefaultNameController(String data) 
	{
		Yaml.updateField("controller.tag", "internal",data);
	}
	
	public static ErrorLogging getErrorLogSetting() 
	{
		return ErrorLogging.valueOf(Yaml.getFieldString("console.errorlog", "internal"));
	}
	public static void setErrorLogSetting(ErrorLogging data) 
	{
		Yaml.updateField("console.errorlog", "internal", data.toString());
	}
	
    public static void applyDefault()
    {
        Yaml.addFieldDefault("mysql.username","config","username");
        Yaml.addFieldDefault("mysql.password","config","password");
        Yaml.addFieldDefault("mysql.database","config","jdbc:mysql://localhost:3306/database?useSSL=false");
        
        Yaml.addFieldDefault("gameplay.expert","config",false);
        Yaml.addFieldDefault("gameplay.dragoneggrespawn","config",true);
        Yaml.addFieldDefault("gameplay.blockhunt","config",false);
        
        Yaml.addFieldDefault("support.luckperms","config",true);
        

        Yaml.addFieldDefault("message","message","&#FFD000<server> &#685985| &#C8C8C8<prefix><player><suffix> &#685985| &#d256ff<message>");
        Yaml.addFieldDefault("illegalitemname","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word For &#FF0000<material>&#CD0000.");
        Yaml.addFieldDefault("illegalitemnameoverride","message","&#C8C8C8Normally &#FF0000<blocked> &#C8C8C8Would be A Blocked Word For &#FF0000<material> &#C8C8C8but you bypass this check.");
        Yaml.addFieldDefault("illegalcommand","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Command.");
        Yaml.addFieldDefault("illegalchat","message","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word.");
        
        Yaml.addFieldDefault("blockpickcooldown","blockhunt",100);
        Yaml.addFieldDefault("cloakcooldown","blockhunt",200);
        Yaml.addFieldDefault("failcooldown","blockhunt",2);
        Yaml.addFieldDefault("visibilityrange","blockhunt",4);
        Yaml.addFieldDefault("lobbytime","blockhunt",400);
        Yaml.addFieldDefault("gametime","blockhunt",6000);
        Yaml.addFieldDefault("hidetime","blockhunt",600);
        Yaml.addFieldDefault("outofboundstime","blockhunt",100);
        Yaml.addFieldDefault("minimumplayers","blockhunt",2);
        Yaml.addFieldDefault("maps.hub.location","blockhunt","world 0 0 0 0f 0f");
        Yaml.addFieldDefault("maps.1.name","blockhunt","");
        Yaml.addFieldDefault("maps.1.prelobby","blockhunt","world 0 0 0 0f 0f");
        Yaml.addFieldDefault("maps.1.hider","blockhunt","world 0 0 0 0f 0f");
        Yaml.addFieldDefault("maps.1.preseeker","blockhunt","world 0 0 0 0f 0f");
        Yaml.addFieldDefault("maps.1.seeker","blockhunt","world 0 0 0 0f 0f");
        Yaml.addFieldDefault("maps.1.blocks","blockhunt","");
        
        Yaml.addFieldDefault("server","internal","unknown");
        for(FileConfiguration configuration : Yaml.getConfigurations()) configuration.options().copyDefaults(true);
    }
}
