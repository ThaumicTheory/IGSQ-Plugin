package thaumictheory.igsq.spigot;

import org.bukkit.World;

public class YamlWorldWrapper 
{
	private String uid;
	public YamlWorldWrapper(World world)
	{
		uid = world.getUID().toString();
	}
	public boolean isRealtime() 
	{ 
		Boolean realTimeValue = Yaml.getFieldBool(uid + ".realtime.enabled", "internal");
		if(realTimeValue == null) return false;
		return realTimeValue;
	}
	public void setRealtime(boolean data) 
	{ 
		Yaml.updateField(uid + ".realtime.enabled", "internal",data);
	}
	public boolean getRealtimeStoredCycle() 
	{ 
		return Yaml.getFieldBool(uid + ".realtime.storeddaycycle", "internal");
	}
	public void setRealtimeStoredCycle(boolean data) 
	{ 
		Yaml.updateField(uid + ".realtime.storeddaycycle", "internal",data);
	}
	
	public boolean isExpertBloodMoon() 
	{ 
		return Yaml.getFieldBool(uid + ".expert.bloodmoon", "internal");
	}
	public void setExpertBloodMoon(boolean data) 
	{ 
		Yaml.updateField(uid + ".expert.bloodmoon", "internal",data);
	}
}
