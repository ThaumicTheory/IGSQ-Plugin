package thaumictheory.igsq.spigot.smp;

import java.util.UUID;

import thaumictheory.igsq.shared.IGSQ;

public class YamlTeamRankWrapper_SMP 
{
	private String uid;
	public YamlTeamRankWrapper_SMP(UUID uid)
	{
		this.uid = uid.toString();
	}
	public String getOwner() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".owner", "teamrank.yaml");
	}
	public void setOwner(UUID faction) 
	{ 
		IGSQ.getYaml().setField(uid + ".owner", "teamrank.yaml",faction.toString());
	}
	public String getName() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".name", "teamrank.yaml");
	}
	public void setName(String name) 
	{
		IGSQ.getYaml().setField(uid + ".name", "teamrank.yaml",name);
	}
	public boolean getDefault() 
	{ 
		Boolean value = (Boolean) IGSQ.getYaml().getField(uid + ".default", "teamrank.yaml");
		if(value == null) return false;
		return value;
	}
	public void setDefault(boolean def)
	{
		IGSQ.getYaml().setField(uid + ".default", "teamrank.yaml",def);
	}
	public boolean getGivable() 
	{ 
		Boolean value = (Boolean) IGSQ.getYaml().getField(uid + ".givable", "teamrank.yaml");
		if(value == null) return false;
		return value;

	}
	public void setGivable(boolean givable)
	{
		IGSQ.getYaml().setField(uid + ".givable", "teamrank.yaml",givable);
	}
	public String getPermissions() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".permissions", "teamrank.yaml");
	}
	public void setPermissions(String permissions) 
	{
		IGSQ.getYaml().setField(uid + ".permissions", "teamrank.yaml",permissions);
	}
	public String getMembers() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".members", "teamrank.yaml");
	}
	public void setMembers(String name)
	{ 
		IGSQ.getYaml().setField(uid + ".members", "teamrank.yaml",name);
	}
	public void delete() 
	{ 
		IGSQ.getYaml().deleteField(uid, "teamrank.yaml");
	}
	
	public void applyDefault() 
	{
		IGSQ.getYaml().defaultField(uid + ".owner", "teamrank.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".members", "teamrank.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".name", "teamrank.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".permissions", "teamrank.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".default", "teamrank.yaml", false);
		IGSQ.getYaml().defaultField(uid + ".givable", "teamrank.yaml", true);
		
	}
}
