package thaumictheory.igsq.spigot;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class YamlPlayerWrapper
{
	private String uuid;
	public YamlPlayerWrapper(OfflinePlayer player)
	{
		uuid = player.getUniqueId().toString();
	}
	public YamlPlayerWrapper(String player)
	{
		uuid = player;
	}
	
	//Discord
	public String getID() 
	{
		return Yaml.getFieldString(uuid + ".discord.id", "player");
	}
	public void setID(String data) 
	{
		Yaml.updateField(uuid + ".discord.id", "player", data);
	}
	public String getUsername() 
	{
		return Yaml.getFieldString(uuid + ".discord.username", "player");
	}
	public void setUsername(String data) 
	{
		Yaml.updateField(uuid + ".discord.username", "player", data);
	}
	public String getNickname() 
	{
		String discordName = Yaml.getFieldString(uuid + ".discord.nickname", "player");
		if(discordName != null && !discordName.equals("")) return discordName;
		else return Bukkit.getPlayer(UUID.fromString(uuid)).getName();
	}
	public void setNickname(String data) 
	{
		Yaml.updateField(uuid + ".discord.nickname", "player", data);
	}
	public String getRole() 
	{
		return Yaml.getFieldString(uuid + ".discord.role", "player");
	}
	public void setRole(String data) 
	{
		Yaml.updateField(uuid + ".discord.role", "player", data);
	}
	public boolean isFounder() 
	{
		return Yaml.getFieldBool(uuid + ".discord.founder", "player");
	}
	public void setFounder(boolean data) 
	{
		Yaml.updateField(uuid + ".discord.founder", "player", data);
	}
	public boolean isBirthday() 
	{
		return Yaml.getFieldBool(uuid + ".discord.birthday", "player");
	}
	public void setBirthday(boolean data) 
	{
		Yaml.updateField(uuid + ".discord.birthday", "player", data);
	}
	public boolean isBooster() 
	{
		return Yaml.getFieldBool(uuid + ".discord.nitroboost", "player");
	}
	public void setBooster(boolean data) 
	{
		Yaml.updateField(uuid + ".discord.nitroboost", "player", data);
	}
	public boolean isSupporter() 
	{
		return Yaml.getFieldBool(uuid + ".discord.supporter", "player");
	}
	public void setSupporter(boolean data) 
	{
		Yaml.updateField(uuid + ".discord.supporter", "player", data);
	}
	public boolean isDeveloper() 
	{
		return Yaml.getFieldBool(uuid + ".discord.developer", "player");
	}
	public void setDeveloper(boolean data) 
	{
		Yaml.updateField(uuid + ".discord.developer", "player", data);
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
		return Yaml.getFieldString(uuid + ".discord.2fa.status", "player");
	}
	public void setStatus(String data) 
	{
		Yaml.updateField(uuid + ".discord.2fa.status", "player", data);
	}
	public String getCode() 
	{
		return Yaml.getFieldString(uuid + ".discord.2fa.code", "player");
	}
	public void setCode(String data) 
	{
		Yaml.updateField(uuid + ".discord.2fa.code", "player", data);
	}
	public String getLastLoginIP() 
	{
		return Yaml.getFieldString(uuid + ".discord.2fa.ip", "player");
	}
	public void setLastLoginIP(String data) 
	{
		Yaml.updateField(uuid + ".discord.2fa.ip", "player", data);
	}
	
	//Internal
	
	public String getPlayerCompassTarget() 
	{
		return Yaml.getFieldString(uuid + ".playercompass.target", "internal");
	}
	public void setPlayerCompassTarget(String data) 
	{
		Yaml.updateField(uuid + ".playercompass.target", "internal", data);
	}
	public int getPlayerCompassAccuracy() 
	{
		return Yaml.getFieldInt(uuid + ".playercompass.accuracy", "internal");
	}
	public void setPlayerCompassAccuracy(int data) 
	{
		Yaml.updateField(uuid + ".playercompass.target", "internal", data);
	}
	
	public String getChatController() 
	{
		return Yaml.getFieldString(uuid + ".controller.chat", "internal");
	}
	public void setChatController(String data) 
	{
		Yaml.updateField(uuid + ".controller.chat", "internal", data);
	}
	public String getNameController() 
	{
		return Yaml.getFieldString(uuid + ".controller.tag", "internal");
	}
	public void setNameController(String data) 
	{
		Yaml.updateField(uuid + ".controller.tag", "internal", data);
	}
	
	public int getLastDamage() 
	{
		return Yaml.getFieldInt(uuid + ".damage.last", "internal");
	}
	public void setLastDamage(int data) 
	{
		Yaml.updateField(uuid + ".damage.last", "internal", data);
	}
	public String getSmpInvites() 
	{
		return Yaml.getFieldString(uuid + ".smp.invites", "player");
	}
	public void setSmpInvites(String data) 
	{
		Yaml.updateField(uuid + ".smp.invites", "player", data);
	}
	
	
	public int getSMPCurrency() 
	{
		return Yaml.getFieldInt(uuid + ".smp.currency", "player");
	}
	public void setSMPCurrency(int data) 
	{
		Yaml.updateField(uuid + ".smp.currency", "player", data);
	}
	public String getSmpAspect() 
	{
		return Yaml.getFieldString(uuid + ".smp.aspect", "player");
	}
	public void setSmpAspect(String data) 
	{
		Yaml.updateField(uuid + ".smp.aspect", "player", data);
	}
	public ErrorLogging getErrorLogSetting() 
	{
		String errorLogSetting = Yaml.getFieldString(uuid + ".errorlog", "player");
		if(errorLogSetting == null) return ErrorLogging.DISABLED;
		return ErrorLogging.valueOf(errorLogSetting);
	}
	public void setErrorLogSetting(ErrorLogging data) 
	{
		Yaml.updateField(uuid + ".errorlog", "player", data.toString());
	}
	
    public void applyDefault() 
    {
    	Yaml.addFieldDefault(uuid + ".damage.last", "internal", 0);
    	Yaml.addFieldDefault(uuid + ".controller.chat", "internal", YamlWrapper.getDefaultChatController());
    	Yaml.addFieldDefault(uuid + ".controller.tag", "internal", YamlWrapper.getDefaultNameController());
    	Yaml.addFieldDefault(uuid + ".playercompass.target","internal","");
    	Yaml.addFieldDefault(uuid + ".playercompass.accuracy", "internal",0);
    	Yaml.addFieldDefault(uuid + ".errorlog", "internal",ErrorLogging.DISABLED.toString());
    	Yaml.addFieldDefault(uuid + ".smp.invites", "player", "");
    	Yaml.addFieldDefault(uuid + ".smp.currency", "player", 0);
    	Yaml.addFieldDefault(uuid + ".smp.aspect", "player", "");
    }
	
	
}
