package thaumictheory.igsq.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class YamlPlayerWrapper
{
	private String uuid;
	public YamlPlayerWrapper(ProxiedPlayer player)
	{
		uuid = player.getUniqueId().toString();
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
		return Yaml.getFieldString(uuid + ".discord.nickname", "player");
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
}