package thaumictheory.igsq.shared;

import java.util.UUID;

import thaumictheory.igsq.spigot.ErrorLogging;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class YamlPlayerWrapper
{
	private String uuid;
	
	public YamlPlayerWrapper(UUID uuid)
	{
		this.uuid = uuid.toString();
	}
	
	public YamlPlayerWrapper(String uuid)
	{
		this.uuid = uuid;
	}
	
	//Discord
	public String getID() 
	{
		return (String) IGSQ.getYaml().getField(uuid + ".discord.id", "player.yaml");
	}
	public void setID(String data) 
	{
		IGSQ.getYaml().setField(uuid + ".discord.id", "player.yaml", data);
	}
	public String getUsername() 
	{
		return (String) IGSQ.getYaml().getField(uuid + ".discord.username", "player.yaml");
	}
	public void setUsername(String data) 
	{
		IGSQ.getYaml().setField(uuid + ".discord.username", "player.yaml", data);
	}
	
	public String getNickname() 
	{
		String discordName = (String) IGSQ.getYaml().getField(uuid + ".discord.nickname", "player.yaml");
		if(discordName != null && !discordName.isBlank()) return discordName;
		else return IGSQ.getSharedImplementation().getPlayerName(uuid);
	}
	public void setNickname(String data) 
	{
		IGSQ.getYaml().setField(uuid + ".discord.nickname", "player.yaml", data);
	}
	public int getRoles() 
	{
		Integer value = (Integer) IGSQ.getYaml().getField(uuid + ".discord.role", "player.yaml");
		if(value == null) return 0;
		return value;
	}
	public void setRoles(Integer data) 
	{
		IGSQ.getYaml().setField(uuid + ".discord.role", "player.yaml", data);
	}
	public boolean isLinked() 
	{
		return getID() != null && !getID().equals("");
	}
	
	//2fa
	public boolean is2fa() 
	{
		return isLinked() && getStatus() != null && !getStatus() .equals("");
	}
	public String getStatus() 
	{
		return (String) IGSQ.getYaml().getField(uuid + ".discord.2fa.status", "player.yaml");
	}
	public void setStatus(String data) 
	{
		IGSQ.getYaml().setField(uuid + ".discord.2fa.status", "player.yaml", data);
	}
	public String getCode() 
	{
		return (String) IGSQ.getYaml().getField(uuid + ".discord.2fa.code", "player.yaml");
	}
	public void setCode(String data) 
	{
		IGSQ.getYaml().setField(uuid + ".discord.2fa.code", "player.yaml", data);
	}
	public String getLastLoginIP() 
	{
		return (String) IGSQ.getYaml().getField(uuid + ".discord.2fa.ip", "player.yaml");
	}
	public void setLastLoginIP(String data) 
	{
		IGSQ.getYaml().setField(uuid + ".discord.2fa.ip", "player.yaml", data);
	}
	
	//Internal
	
	public String getPlayerCompassTarget() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (String) IGSQ.getYaml().getField(uuid + ".playercompass.target", "internal.yaml");
	}
	public void setPlayerCompassTarget(String data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".playercompass.target", "internal.yaml", data);
	}
	public Integer getPlayerCompassAccuracy() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (Integer) IGSQ.getYaml().getField(uuid + ".playercompass.accuracy", "internal.yaml");
	}
	public void setPlayerCompassAccuracy(Integer data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".playercompass.target", "internal.yaml", data);
	}
	
	public String getChatController() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (String) IGSQ.getYaml().getField(uuid + ".controller.chat", "internal.yaml");
	}
	public void setChatController(String data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".controller.chat", "internal.yaml", data);
	}
	public String getNameController() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (String) IGSQ.getYaml().getField(uuid + ".controller.tag", "internal.yaml");
	}
	public void setNameController(String data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".controller.tag", "internal.yaml", data);
	}
	
	public Integer getLastDamage() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (Integer) IGSQ.getYaml().getField(uuid + ".damage.last", "internal.yaml");
	}
	public void setLastDamage(Integer data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".damage.last", "internal.yaml", data);
	}
	public String getSmpInvites() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (String) IGSQ.getYaml().getField(uuid + ".smp.invites", "player.yaml");
	}
	public void setSmpInvites(String data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".smp.invites", "player.yaml", data);
	}
	
	
	public Integer getSMPCurrency() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (Integer) IGSQ.getYaml().getField(uuid + ".smp.currency", "player.yaml");
	}
	public void setSMPCurrency(Integer data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".smp.currency", "player.yaml", data);
	}
	public String getSmpAspect() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		return (String) IGSQ.getYaml().getField(uuid + ".smp.aspect", "player.yaml");
	}
	public void setSmpAspect(String data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".smp.aspect", "player.yaml", data);
	}
	public ErrorLogging getErrorLogSetting() 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return null;
		String value = (String) IGSQ.getYaml().getField(uuid + ".errorlog", "player.yaml");
		if(value == null) return ErrorLogging.DISABLED;
		if(value.equals(ErrorLogging.DETAILED.toString())) return ErrorLogging.DETAILED;
		else if (value.equals(ErrorLogging.BASIC.toString())) return ErrorLogging.BASIC;
		else return ErrorLogging.DISABLED;
	}
	public void setErrorLogSetting(ErrorLogging data) 
	{
		if(!IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) return;
		IGSQ.getYaml().setField(uuid + ".errorlog", "player.yaml", data.toString());
		
	}
	
    public void applyDefault() 
    {
    	if(IGSQ.getSharedImplementation().getInstanceType().equals(InstanceType.SPIGOT)) 
    	{
        	IGSQ.getYaml().defaultField(uuid + ".damage.last", "internal.yaml", 0);
        	IGSQ.getYaml().defaultField(uuid + ".controller.chat", "internal.yaml", YamlWrapper.getDefaultChatController());
        	IGSQ.getYaml().defaultField(uuid + ".controller.tag", "internal.yaml", YamlWrapper.getDefaultNameController());
        	IGSQ.getYaml().defaultField(uuid + ".playercompass.target","internal.yaml","");
        	IGSQ.getYaml().defaultField(uuid + ".playercompass.accuracy", "internal.yaml",0);
        	IGSQ.getYaml().defaultField(uuid + ".errorlog", "internal.yaml",ErrorLogging.DISABLED.toString());
        	IGSQ.getYaml().defaultField(uuid + ".smp.invites", "player.yaml", "");
        	IGSQ.getYaml().defaultField(uuid + ".smp.currency", "player.yaml", 0);
        	IGSQ.getYaml().defaultField(uuid + ".smp.aspect", "player.yaml", "");
    	}
    }
	
	
}
