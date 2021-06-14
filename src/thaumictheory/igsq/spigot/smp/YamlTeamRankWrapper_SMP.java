package thaumictheory.igsq.spigot.smp;

import java.util.UUID;

import thaumictheory.igsq.spigot.Yaml;

public class YamlTeamRankWrapper_SMP 
{
	private String uid;
	public YamlTeamRankWrapper_SMP(UUID uid)
	{
		this.uid = uid.toString();
	}
	public String getOwner() 
	{ 
		return Yaml.getFieldString(uid + ".owner", "teamranks");
	}
	public void setOwner(UUID faction) 
	{ 
		Yaml.updateField(uid + ".owner", "teamranks",faction.toString());
	}
	public String getName() 
	{ 
		return Yaml.getFieldString(uid + ".name", "teamranks");
	}
	public void setName(String name) 
	{
		Yaml.updateField(uid + ".name", "teamranks",name);
	}
	public Boolean getDefault() 
	{ 
		return Yaml.getFieldBool(uid + ".default", "teamranks");
	}
	public void setDefault(boolean def)
	{
		Yaml.updateField(uid + ".default", "teamranks",def);
	}
	public Boolean getGivable() 
	{ 
		return Yaml.getFieldBool(uid + ".givable", "teamranks");
	}
	public void setGivable(boolean givable)
	{
		Yaml.updateField(uid + ".givable", "teamranks",givable);
	}
	public String getPermissions() 
	{ 
		return Yaml.getFieldString(uid + ".permissions", "teamranks");
	}
	public void setPermissions(String permissions) 
	{
		Yaml.updateField(uid + ".permissions", "teamranks",permissions);
	}
	public String getMembers() 
	{ 
		return Yaml.getFieldString(uid + ".members", "teamranks");
	}
	public void setMembers(String name)
	{ 
		Yaml.updateField(uid + ".members", "teamranks",name);
	}
	public void delete() 
	{ 
		Yaml.deleteField(uid, "teamranks");
	}
	
	public void applyDefault() 
	{
		Yaml.addFieldDefault(uid + ".owner", "teamranks", "");
		Yaml.addFieldDefault(uid + ".members", "teamranks", "");
		Yaml.addFieldDefault(uid + ".name", "teamranks", "");
		Yaml.addFieldDefault(uid + ".permissions", "teamranks", "");
		Yaml.addFieldDefault(uid + ".default", "teamranks", false);
		Yaml.addFieldDefault(uid + ".givable", "teamranks", true);
		
	}
}
