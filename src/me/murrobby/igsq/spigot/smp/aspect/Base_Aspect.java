package me.murrobby.igsq.spigot.smp.aspect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import me.murrobby.igsq.spigot.smp.Player_SMP;

public abstract class Base_Aspect 
{
	private List<EntityType> passive = new ArrayList<>(); //these entities wil not attack you
	private List<EntityType> aggresive = new ArrayList<>(); //these entities will attack you even if they are "neutral" like wolf etc
	private List<String> perkDescription = new ArrayList<>(); //tells you the perks and the debuffs
	private String name;
	private ChatColor colour;
	private Material logo;
	private String lore;
	protected Player_SMP player;
	private Enum_Aspect id;
	public List<EntityType> getPassiveEntities() 
	{
		return passive;
	}
	public void setPassiveEntities(List<EntityType> passive) 
	{
		this.passive = passive;
	}
	public void addPassiveEntity(EntityType entityType) 
	{
		this.passive.add(entityType);
	}
	
	public List<EntityType> getAggresiveEntities() 
	{
		return aggresive;
	}
	public void setAggresiveEntities(List<EntityType> aggresive) 
	{
		this.aggresive = aggresive;
	}
	public void addAgressiveEntity(EntityType entityType) 
	{
		this.passive.add(entityType);
	}
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getLore() 
	{
		return lore;
	}
	public void setLore(String lore) 
	{
		this.lore = lore;
	}
	public List<String> getDescription() 
	{
		return perkDescription;
	}
	public void setPerkDescription(List<String> perkDescription) 
	{
		this.perkDescription = perkDescription;
	}
	public void addGoodPerkDescription(String perkDescription) 
	{
		this.perkDescription.add("&a" + perkDescription);
	}
	public void addBadPerkDescription(String perkDescription) 
	{
		this.perkDescription.add("&c" + perkDescription);
	}
	public void addTwistedPerkDescription(String perkDescription) 
	{
		this.perkDescription.add("&d" + perkDescription);
	}
	public ChatColor getColour() 
	{
		return colour;
	}
	public void setColour(ChatColor colour) 
	{
		this.colour = colour;
	}
	public Material getLogo() 
	{
		return logo;
	}
	public void setLogo(Material logo) 
	{
		this.logo = logo;
	}
	public Player_SMP getPlayer() 
	{
		return player;
	}
	public void setPlayer(Player_SMP player) 
	{
		this.player = player;
	}
	public Enum_Aspect getID() 
	{
		return id;
	}
	public void setID(Enum_Aspect id) 
	{
		this.id = id;
	}
	public boolean isEntityPassive(EntityType entityType) 
	{
		return passive.contains(entityType);
	}
	public boolean isEntityAggresive(EntityType entityType) 
	{
		return aggresive.contains(entityType);
	}
	
	public void run() 
	{
		if(getPlayer().getOfflinePlayer().getPlayer() != null) aspect();
	}
	
	public abstract void aspect();
}
