package thaumictheory.igsq.spigot;

import org.bukkit.entity.LivingEntity;

public class YamlEntityWrapper
{
	private String uid;
	public YamlEntityWrapper(LivingEntity entity)
	{
		uid = entity.getUniqueId().toString();
	}
	public YamlEntityWrapper(String uid)
	{
		this.uid = uid;
	}
	
	public void delete() 
	{
		Yaml.deleteField(uid, "entity");
	}
	public String getSMPAgro() 
	{
		String agro = Yaml.getFieldString(uid + ".smp.neutralagro", "entity");
		if(agro == null) return "";
		return agro;
	}
	public void setSMPAgro(String data) 
	{
		Yaml.updateField(uid + ".smp.neutralagro", "entity", data);
	}
	public int getSMPLastHit() 
	{
		Integer lastHit = Yaml.getFieldInt(uid + ".smp.lasthit", "entity");
		if(lastHit == null) return 0;
		return lastHit;
	}
	public void setSMPLastHit(int data) 
	{
		Yaml.updateField(uid + ".smp.lasthit", "entity", data);
	}
	public boolean addSMPAgro(String uid) 
	{
		for(String agro : getSMPAgro().split(" ")) if(agro.equalsIgnoreCase(uid)) return false;
		if(getSMPAgro() == null || getSMPAgro().equals("")) setSMPAgro(uid);
		else setSMPAgro(getSMPAgro() + " " + uid);
		return true;
	}
    public void applyDefault() 
    {
    	Yaml.addFieldDefault(uid + ".smp.neutralagro", "entity", "");
    	Yaml.addFieldDefault(uid + ".smp.lasthit", "entity", 0);
    }
	
	
}
