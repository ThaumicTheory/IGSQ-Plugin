package thaumictheory.igsq.spigot.yaml;

import org.bukkit.entity.LivingEntity;

import thaumictheory.igsq.shared.IGSQ;

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
		IGSQ.getYaml().deleteField(uid, "entity.yaml");
	}
	public String getSMPAgro() 
	{
		String agro = (String) IGSQ.getYaml().getField(uid + ".smp.neutralagro", "entity.yaml");
		if(agro == null) return "";
		return agro;
	}
	public void setSMPAgro(String data) 
	{
		IGSQ.getYaml().setField(uid + ".smp.neutralagro", "entity.yaml", data);
	}
	public Integer getSMPLastHit() 
	{
		return (Integer) IGSQ.getYaml().getField(uid + ".smp.lasthit", "entity.yaml");
	}
	public void setSMPLastHit(Integer data) 
	{
		IGSQ.getYaml().setField(uid + ".smp.lasthit", "entity.yaml", data);
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
    	IGSQ.getYaml().defaultField(uid + ".smp.neutralagro", "entity.yaml", "");
    	IGSQ.getYaml().defaultField(uid + ".smp.lasthit", "entity.yaml", 0);
    }
	
	
}
