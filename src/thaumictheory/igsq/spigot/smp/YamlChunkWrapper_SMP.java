package thaumictheory.igsq.spigot.smp;

import java.util.UUID;

import thaumictheory.igsq.shared.IGSQ;

public class YamlChunkWrapper_SMP 
{
	private String uid;
	public YamlChunkWrapper_SMP(UUID UID)
	{
		this.uid = UID.toString();
	}
	public String getWorld() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".world", "chunk.yaml");
	}
	public void setWorld(UUID worldUID) 
	{ 
		IGSQ.getYaml().setField(uid + ".world", "chunk.yaml",worldUID.toString());
	}
	
	public String getLocation() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".location", "chunk.yaml");
	}
	public void setLocation(int x,int z) 
	{ 
		IGSQ.getYaml().setField(uid + ".location", "chunk.yaml",x + " " + z);
	}
	
	public String getOwner() 
	{ 
		return (String) IGSQ.getYaml().getField(uid + ".owner", "chunk.yaml");
	}
	public void setOwner(UUID faction) 
	{ 
		IGSQ.getYaml().setField(uid + ".owner", "chunk.yaml",faction.toString());
	}
	public static String getChunks() 
	{ 
		return (String) IGSQ.getYaml().getField("chunk.yaml", "chunk.yaml");
	}
	public static void setChunks(String chunks) 
	{
		IGSQ.getYaml().setField("chunk.yaml", "chunk.yaml",chunks);
	}
	public void delete() 
	{ 
		IGSQ.getYaml().deleteField(uid, "chunk.yaml");
	}
	
	public void applyDefault() 
	{
		IGSQ.getYaml().defaultField(uid + ".owner", "chunk.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".location", "chunk.yaml", "");
		IGSQ.getYaml().defaultField(uid + ".world", "chunk.yaml", "");
		
	}
}
