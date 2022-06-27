package thaumictheory.igsq.spigot.yaml;

import org.bukkit.World;

import thaumictheory.igsq.shared.IGSQ;

public class YamlWorldWrapper 
{
	private String uid;
	public YamlWorldWrapper(World world)
	{
		uid = world.getUID().toString();
	}
	public Boolean isRealtime() 
	{ 
		Boolean value = (Boolean) IGSQ.getYaml().getField(uid + ".realtime.enabled", "internal.yaml");
		if(value == null) return false;
		return value;
	}
	public void setRealtime(boolean data) 
	{ 
		IGSQ.getYaml().setField(uid + ".realtime.enabled", "internal.yaml",data);
	}
	public boolean getRealtimeStoredCycle() 
	{ 
		Boolean value = (Boolean) IGSQ.getYaml().getField(uid + ".realtime.storeddaycycle", "internal.yaml");
		if(value == null) return false;
		return value;
	}
	public void setRealtimeStoredCycle(boolean data) 
	{ 
		IGSQ.getYaml().setField(uid + ".realtime.storeddaycycle", "internal.yaml",data);
	}
	
	public boolean isExpertBloodMoon() 
	{ 
		Boolean value = (Boolean) IGSQ.getYaml().getField(uid + ".expert.bloodmoon", "internal.yaml");
		if(value == null) return false;
		return value;
	}
	public void setExpertBloodMoon(boolean data) 
	{ 
		IGSQ.getYaml().setField(uid + ".expert.bloodmoon", "internal.yaml",data);
	}
}
