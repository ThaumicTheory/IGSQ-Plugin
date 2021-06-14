package thaumictheory.igsq.spigot.smp;

import java.util.UUID;

import thaumictheory.igsq.spigot.Yaml;

public class YamlChunkWrapper_SMP 
{
	private String uid;
	public YamlChunkWrapper_SMP(UUID UID)
	{
		this.uid = UID.toString();
	}
	public String getWorld() 
	{ 
		return Yaml.getFieldString(uid + ".world", "chunks");
	}
	public void setWorld(UUID worldUID) 
	{ 
		Yaml.updateField(uid + ".world", "chunks",worldUID.toString());
	}
	
	public String getLocation() 
	{ 
		return Yaml.getFieldString(uid + ".location", "chunks");
	}
	public void setLocation(int x,int z) 
	{ 
		Yaml.updateField(uid + ".location", "chunks",x + " " + z);
	}
	
	public String getOwner() 
	{ 
		return Yaml.getFieldString(uid + ".owner", "chunks");
	}
	public void setOwner(UUID faction) 
	{ 
		Yaml.updateField(uid + ".owner", "chunks",faction.toString());
	}
	public static String getChunks() 
	{ 
		return Yaml.getFieldString("chunks", "chunks");
	}
	public static void setChunks(String chunks) 
	{
		Yaml.updateField("chunks", "chunks",chunks);
	}
	public void delete() 
	{ 
		Yaml.deleteField(uid, "chunks");
	}
	
	public void applyDefault() 
	{
		Yaml.addFieldDefault(uid + ".owner", "chunks", "");
		Yaml.addFieldDefault(uid + ".location", "chunks", "");
		Yaml.addFieldDefault(uid + ".world", "chunks", "");
		
	}
}
