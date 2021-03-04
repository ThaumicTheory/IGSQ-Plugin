package me.murrobby.igsq.spigot.smp.aspect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.smp.Player_SMP;
//import net.md_5.bungee.api.ChatColor;

public abstract class Base_Aspect 
{
	private List<EntityType> protective = new ArrayList<>(); //these entities will protect you if you are attacked
	private List<EntityType> passive = new ArrayList<>(); //these entities will ignore you
	private List<EntityType> neutral = new ArrayList<>(); //these entities will target you if provoked
	private List<EntityType> aggresive = new ArrayList<>(); //these entities will target you
	private List<EntityType> enemy = new ArrayList<>(); //these entities will prioritise targeting you over anything they are doing
	private List<String> perkDescription = new ArrayList<>(); //tells you the perks and the debuffs
	private String name;
	//private ChatColor colour;
	private Material logo;
	private String lore;
	protected Player_SMP player;
	private Enum_Aspect id;
	private int secondTracker = 0;
	private float speed;
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
		this.aggresive.add(entityType);
	}
	
	public List<EntityType> getNeutralEntities() 
	{
		return neutral;
	}
	public void setNeutralEntities(List<EntityType> neutral) 
	{
		this.neutral = neutral;
	}
	public void addNeutralEntity(EntityType entityType) 
	{
		this.neutral.add(entityType);
	}
	
	public List<EntityType> getProtectiveEntities() 
	{
		return protective;
	}
	public void setProtectiveEntities(List<EntityType> protective) 
	{
		this.protective = protective;
	}
	public void addProtectiveEntity(EntityType entityType) 
	{
		this.protective.add(entityType);
	}
	
	public List<EntityType> getEnemyEntities() 
	{
		return enemy;
	}
	public void setEnemyEntities(List<EntityType> enemy) 
	{
		this.enemy = enemy;
	}
	public void addEnemyEntity(EntityType entityType) 
	{
		this.enemy.add(entityType);
	}
	
	public String getName() 
	{
		return Messaging.chatFormatter(name);
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
	public float getMovementSpeed() 
	{
		return speed;
	}
	public void setMovementSpeed(float speed) 
	{
		if(speed > 1) speed = 1;
		if(speed < -1) speed = -1;
		this.speed = speed;
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
		this.perkDescription.add(Messaging.chatFormatter("&#00FF00" + perkDescription));
	}
	public void addBadPerkDescription(String perkDescription) 
	{
		this.perkDescription.add(Messaging.chatFormatter("&#FF6666" + perkDescription));
	}
	public void addTwistedPerkDescription(String perkDescription) 
	{
		this.perkDescription.add(Messaging.chatFormatter("&#FF66FF" + perkDescription));
	}
	/*
	public ChatColor getColour() 
	{
		return colour;
	}
	public void setColour(ChatColor colour) 
	{
		this.colour = colour;
	}
	*/
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
	public void incrementTime() 
	{
		secondTracker+=5;
		if(secondTracker >= 20) secondTracker = 0;
	}
	public boolean isSecond()
	{
		return secondTracker == 0;
	}
	public boolean isEntityPassive(EntityType entityType) 
	{
		return passive.contains(entityType);
	}
	public boolean isEntityAggresive(EntityType entityType) 
	{
		return aggresive.contains(entityType);
	}
	public boolean isEntityNeutral(EntityType entityType) 
	{
		return neutral.contains(entityType);
	}
	public boolean isEntityProtective(EntityType entityType) 
	{
		return protective.contains(entityType);
	}
	
	public boolean isEntityEnemy(EntityType entityType) 
	{
		return enemy.contains(entityType);
	}
	
	public void run() 
	{
		if(getPlayer() != null && getPlayer().getOfflinePlayer().getPlayer() != null) 
		{
			aspectTick();
			if(isSecond()) 
			{
				aspectSecond();
				player.getPlayer().setWalkSpeed(getMovementSpeed());
			}
		}
		incrementTime();
	}
	protected void generate(Player_SMP player) 
	{
		this.player = player;
		generate();
		player.getYaml().setSmpAspect(getID().toString());
	}
	
	public abstract void aspectTick();
	public abstract void aspectSecond();
	
	protected abstract void generate();
	
}
