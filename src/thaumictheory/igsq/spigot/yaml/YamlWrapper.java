package thaumictheory.igsq.spigot.yaml;

import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.spigot.ErrorLogging;

public class YamlWrapper 
{
	public static boolean isBlockHunt() 
	{
		Boolean value = (Boolean) IGSQ.getYaml().getField("gameplay.blockhunt", "config.yaml");
		if(value == null) return false;
		return value;
	}
	public static void setBlockHunt(boolean data) 
	{
		IGSQ.getYaml().setField("gameplay.blockhunt", "config.yaml",data);
	}
	public static boolean isExpert() 
	{
		Boolean value = (Boolean) IGSQ.getYaml().getField("gameplay.expert", "config.yaml");
		if(value == null) return false;
		return value;
	} 
	public static void setExpert(boolean data) 
	{
		IGSQ.getYaml().setField("gameplay.expert", "config.yaml",data);
	}
	public static boolean isSMP() 
	{
		Boolean value =  (Boolean) IGSQ.getYaml().getField("gameplay.smp", "config.yaml");
		if(value == null) return false;
		return value;
	} 
	public static void setSMP(boolean data) 
	{
		IGSQ.getYaml().setField("gameplay.smp", "config.yaml",data);
	}
	public static boolean isEggRespawn() 
	{
		Boolean value = (Boolean) IGSQ.getYaml().getField("gameplay.dragoneggrespawn", "config.yaml");
		if(value == null) return false;
		return value;
	}
	public static void setEggRespawn(boolean data) 
	{
		IGSQ.getYaml().setField("gameplay.dragoneggrespawn", "config.yaml",data);
	}
	
	public static Integer getBlockHuntOutOfBoundsTime() 
	{
		return (Integer) IGSQ.getYaml().getField("outofboundstime", "blockhunt.yaml");
	}
	public static void setBlockHuntOutOfBoundsTime(Integer data) 
	{
		IGSQ.getYaml().setField("outofboundstime", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntHideTime() 
	{
		return (Integer) IGSQ.getYaml().getField("hidetime", "blockhunt.yaml");
	}
	public static void setBlockHuntHideTime(Integer data) 
	{
		IGSQ.getYaml().setField("hidetime", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntLobbyTime() 
	{
		return (Integer) IGSQ.getYaml().getField("lobbytime", "blockhunt.yaml");
	}
	public static void setBlockHuntLobbyTime(Integer data) 
	{
		IGSQ.getYaml().setField("lobbytime", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntGameTime() 
	{
		return (Integer) IGSQ.getYaml().getField("gametime", "blockhunt.yaml");
	}
	public static void setBlockHuntGameTime(Integer data) 
	{
		IGSQ.getYaml().setField("gametime", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntMinimumPlayers() 
	{
		return (Integer) IGSQ.getYaml().getField("minimumplayers", "blockhunt.yaml");
	}
	public static void setBlockHuntMinimumPlayers(Integer data) 
	{
		IGSQ.getYaml().setField("minimumplayers", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntBlockPickCooldown()
	{
		return (Integer) IGSQ.getYaml().getField("blockpickcooldown", "blockhunt.yaml");
	}
	public static void setBlockHuntBlockPickCooldown(Integer data) 
	{
		IGSQ.getYaml().setField("blockpickcooldown", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntCloakCooldown()
	{
		return (Integer) IGSQ.getYaml().getField("cloakcooldown", "blockhunt.yaml");
	}
	public static void setBlockHuntCloakCooldown(Integer data) 
	{
		IGSQ.getYaml().setField("cloakcooldown", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntFailCooldown()
	{
		return (Integer) IGSQ.getYaml().getField("failcooldown", "blockhunt.yaml");
	}
	public static void setBlockHuntFailCooldown(Integer data) 
	{
		IGSQ.getYaml().setField("failcooldown", "blockhunt.yaml", data);
	}
	public static Integer getBlockHuntVisibilityRange()
	{
		return (Integer) IGSQ.getYaml().getField("visibilityrange", "blockhunt.yaml");
	}
	public static void setBlockHuntVisibilityRange(Integer data) 
	{
		IGSQ.getYaml().setField("visibilityrange", "blockhunt.yaml", data);
	}
	//Maps
	
	public static String getBlockHuntHubLocation()
	{
		return (String) IGSQ.getYaml().getField("maps.hub.location", "blockhunt.yaml");
	}
	public static void setBlockHuntHubLocation(String data) 
	{
		IGSQ.getYaml().setField("maps.hub.location", "blockhunt.yaml", data);
	}
	
	public static String getBlockHuntMapName(int id)
	{
		return (String) IGSQ.getYaml().getField("maps."+ id + ".name", "blockhunt.yaml");
	}
	public static void setBlockHuntMapName(String data,int id) 
	{
		IGSQ.getYaml().setField("maps."+ id + ".name", "blockhunt.yaml", data);
	}
	public static String getBlockHuntMapPreLobby(int id)
	{
		return (String) IGSQ.getYaml().getField("maps."+ id + ".prelobby", "blockhunt.yaml");
	}
	public static void setBlockHuntMapPreLobby(String data,int id) 
	{
		IGSQ.getYaml().setField("maps."+ id + ".prelobby", "blockhunt.yaml", data);
	}
	
	public static String getBlockHuntMapHider(int id)
	{
		return (String) IGSQ.getYaml().getField("maps."+ id + ".hider", "blockhunt.yaml");
	}
	public static void setBlockHuntMapHider(String data,int id) 
	{
		IGSQ.getYaml().setField("maps."+ id + ".hider", "blockhunt.yaml", data);
	}
	
	public static String getBlockHuntMapPreSeeker(int id)
	{
		return (String) IGSQ.getYaml().getField("maps."+ id + ".preseeker", "blockhunt.yaml");
	}
	public static void setBlockHuntMapPreSeeker(String data,int id) 
	{
		IGSQ.getYaml().setField("maps."+ id + ".preseeker", "blockhunt.yaml", data);
	}
	
	public static String getBlockHuntMapSeeker(int id)
	{
		return (String) IGSQ.getYaml().getField("maps."+ id + ".seeker", "blockhunt.yaml");
	}
	public static void setBlockHuntMapSeeker(String data,int id) 
	{
		IGSQ.getYaml().setField("maps."+ id + ".seeker", "blockhunt.yaml", data);
	}
	
	public static String getBlockHuntMapBlocks(int id)
	{
		return (String) IGSQ.getYaml().getField("maps."+ id + ".blocks", "blockhunt.yaml");
	}
	public static void setBlockHuntMapBlocks(String data,int id) 
	{
		IGSQ.getYaml().setField("maps."+ id + ".blocks", "blockhunt.yaml", data);
	}
	public static String getServerName() 
	{
		return (String) IGSQ.getYaml().getField("server", "internal.yaml");
	}
	public static void setServerName(String data) 
	{
		IGSQ.getYaml().setField("server", "config.yaml",data);
	}
	
	public static String getDefaultChatController() 
	{
		return (String) IGSQ.getYaml().getField("controller.chat", "internal.yaml");
	}
	public static void setDefaultChatController(String data) 
	{
		IGSQ.getYaml().setField("controller.chat", "internal.yaml",data);
	}
	
	public static String getDefaultNameController() 
	{
		return (String) IGSQ.getYaml().getField("controller.tag", "internal.yaml");
	}
	public static void setDefaultNameController(String data) 
	{
		IGSQ.getYaml().setField("controller.tag", "internal.yaml",data);
	}
	
	public static ErrorLogging getErrorLogSetting() 
	{
		String value = (String) IGSQ.getYaml().getField("console.errorlog", "internal.yaml");
		if(value == null || value.equals(ErrorLogging.DETAILED.toString())) return ErrorLogging.DETAILED;
		else if (value.equals(ErrorLogging.BASIC.toString())) return ErrorLogging.BASIC;
		else return ErrorLogging.DISABLED;
	}
	public static void setErrorLogSetting(ErrorLogging data) 
	{
		IGSQ.getYaml().setField("console.errorlog", "internal.yaml", data.toString());
	}
	
    public static void applyDefault()
    {      
    	IGSQ.getYaml().defaultField("gameplay.expert","config.yaml",false);
    	IGSQ.getYaml().defaultField("gameplay.smp","config.yaml",false);
    	IGSQ.getYaml().defaultField("gameplay.dragoneggrespawn","config.yaml",true);
    	IGSQ.getYaml().defaultField("gameplay.blockhunt","config.yaml",false);
    	
    	IGSQ.getYaml().defaultField("message","message.yaml","&#FFD000<server> &#685985| &#C8C8C8<prefix><player><suffix> &#685985| &#d256ff<message>");
    	IGSQ.getYaml().defaultField("illegalitemname","message.yaml","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word For &#FF0000<material>&#CD0000.");
    	IGSQ.getYaml().defaultField("illegalitemnameoverride","message.yaml","&#C8C8C8Normally &#FF0000<blocked> &#C8C8C8Would be A Blocked Word For &#FF0000<material> &#C8C8C8but you bypass this check.");
    	IGSQ.getYaml().defaultField("illegalcommand","message.yaml","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Command.");
    	IGSQ.getYaml().defaultField("illegalchat","message.yaml","&#CD0000Sorry! But &#FF0000<blocked> &#CD0000Is A Blocked Word.");
        
        IGSQ.getYaml().defaultField("blockpickcooldown","blockhunt.yaml",100);
        IGSQ.getYaml().defaultField("cloakcooldown","blockhunt.yaml",200);
        IGSQ.getYaml().defaultField("failcooldown","blockhunt.yaml",2);
        IGSQ.getYaml().defaultField("visibilityrange","blockhunt.yaml",4);
        IGSQ.getYaml().defaultField("lobbytime","blockhunt.yaml",400);
        IGSQ.getYaml().defaultField("gametime","blockhunt.yaml",6000);
        IGSQ.getYaml().defaultField("hidetime","blockhunt.yaml",600);
        IGSQ.getYaml().defaultField("outofboundstime","blockhunt.yaml",100);
        IGSQ.getYaml().defaultField("minimumplayers","blockhunt.yaml",2);
        IGSQ.getYaml().defaultField("maps.hub.location","blockhunt.yaml","world 0 0 0 0f 0f");
        IGSQ.getYaml().defaultField("maps.1.name","blockhunt.yaml","");
        IGSQ.getYaml().defaultField("maps.1.prelobby","blockhunt.yaml","world 0 0 0 0f 0f");
        IGSQ.getYaml().defaultField("maps.1.hider","blockhunt.yaml","world 0 0 0 0f 0f");
        IGSQ.getYaml().defaultField("maps.1.preseeker","blockhunt.yaml","world 0 0 0 0f 0f");
        IGSQ.getYaml().defaultField("maps.1.seeker","blockhunt.yaml","world 0 0 0 0f 0f");
        IGSQ.getYaml().defaultField("maps.1.blocks","blockhunt.yaml","");
        
        IGSQ.getYaml().defaultField("server","internal.yaml","unknown");
        IGSQ.getYaml().defaultField("console.errorlog", "internal.yaml",ErrorLogging.DETAILED.toString());
    }
}
