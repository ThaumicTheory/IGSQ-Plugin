package thaumictheory.igsq.spigot.smp;

import thaumictheory.igsq.shared.IGSQ;

public class YamlTeamWrapper_SMP 
{
	private String uid;
	public YamlTeamWrapper_SMP(Team_SMP team)
	{
		uid = team.getUID().toString();
	}
	public String getName() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".name", "team.yaml");
	}
	public void setName(String name) 
	{ 
		IGSQ.getYaml().setField(uid + ".name", "team.yaml",name);
	}
	
	public String getOwner() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".owner", "team.yaml");
	}
	public void setOwner(String owner) 
	{ 
		IGSQ.getYaml().setField(uid + ".owner", "team.yaml",owner);
	}
	
	public String getMembers() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".members", "team.yaml");
	}
	public void setMembers(String name)
	{ 
		IGSQ.getYaml().setField(uid + ".members", "team.yaml",name);
	}
	
	public String getRanks() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".ranks", "team.yaml");
	}
	public void setRanks(String ranks)
	{
		IGSQ.getYaml().setField(uid + ".ranks", "team.yaml",ranks);
	}
	public static String getTeams() 
	{ 
		return (String) IGSQ.getYaml().getField("team.yaml", "team.yaml");
	}
	public static void setTeams(String teams) 
	{
		IGSQ.getYaml().setField("team.yaml", "team.yaml", teams);
	}
	public String getAlly() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".ally", "team.yaml");
	}
	public void setAlly(String ally) 
	{ 
		IGSQ.getYaml().setField(uid + ".ally", "team.yaml", ally);
	}
	public String getAllyPending() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".allypending", "team.yaml");
	}
	public void setAllyPending(String allypending) 
	{ 
		IGSQ.getYaml().setField(uid + ".allypending", "team.yaml", allypending);
	}
	public String getEnemy() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".enemy", "team.yaml");
	}
	public void setEnemy(String enemy) 
	{ 
		IGSQ.getYaml().setField(uid + ".enemy", "team.yaml", enemy);
	} 
	public String getLeavePending() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".leavepending", "team.yaml");
	}
	public void setLeavePending(String leavepending) 
	{ 
		IGSQ.getYaml().setField(uid + ".leavepending", "team.yaml", leavepending);
	}
	public String getBanned() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".leavepending", "team.yaml");
	}
	public void setBanned(String banned) 
	{ 
		IGSQ.getYaml().setField(uid + ".leavepending", "team.yaml", banned);
	}
	public void delete() 
	{ 
		IGSQ.getYaml().deleteField(uid, "team.yaml");
	}
	public void applyDefault() 
	{
		IGSQ.getYaml().defaultField(uid + ".ranks", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".members", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".name", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".owner", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".ally", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".allypending", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".enemy", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".leavepending", "team.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".banned", "team.yaml", "");
		
	}
}
