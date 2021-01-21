package me.murrobby.igsq.spigot.expert;

import me.murrobby.igsq.spigot.Yaml;

public class YamlTeamWrapper_Expert 
{
	private String uid;
	public YamlTeamWrapper_Expert(Team_Expert team)
	{
		uid = team.getUID().toString();
	}
	public String getName() 
	{ 
		return Yaml.getFieldString(uid + ".name", "team");
	}
	public void setName(String name) 
	{ 
		Yaml.updateField(uid + ".name", "team",name);
	}
	
	public String getMembers() 
	{ 
		return Yaml.getFieldString(uid + ".members", "team");
	}
	public void setMembers(String name)
	{ 
		Yaml.updateField(uid + ".name", "team",name);
	}
	public void delete(String name) 
	{ 
		Yaml.deleteField(uid, "team");
	}
}
