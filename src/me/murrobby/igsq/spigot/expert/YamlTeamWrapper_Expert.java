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
		return Yaml.getFieldString(uid + ".name", "teams");
	}
	public void setName(String name) 
	{ 
		Yaml.updateField(uid + ".name", "teams",name);
	}
	
	public String getOwner() 
	{ 
		return Yaml.getFieldString(uid + ".owner", "teams");
	}
	public void setOwner(String owner) 
	{ 
		Yaml.updateField(uid + ".owner", "teams",owner);
	}
	
	public String getMembers() 
	{ 
		return Yaml.getFieldString(uid + ".members", "teams");
	}
	public void setMembers(String name)
	{ 
		Yaml.updateField(uid + ".members", "teams",name);
	}
	
	public String getRanks() 
	{ 
		return Yaml.getFieldString(uid + ".ranks", "teams");
	}
	public void setRanks(String ranks)
	{ 
		Yaml.updateField(uid + ".ranks", "teams",ranks);
	}
	public static String getTeams() 
	{ 
		return Yaml.getFieldString("teams", "teams");
	}
	public static void setTeams(String teams) 
	{
		Yaml.updateField("teams", "teams",teams);
	}
	public void delete(String name) 
	{ 
		Yaml.deleteField(uid, "teams");
	}
	public void applyDefault() 
	{
		Yaml.addFieldDefault(uid + ".members", "teams", "");
		Yaml.addFieldDefault(uid + ".name", "teams", "");
		Yaml.addFieldDefault(uid + ".owner", "teams", "");
		Yaml.addFieldDefault(uid + ".ranks", "teams", "");
	}
}
