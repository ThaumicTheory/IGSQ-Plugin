package me.murrobby.igsq.listeners;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

@SuppressWarnings("unused")
public class EntityDamageEvent_Main implements Listener
{
	private Main plugin;
	public EntityDamageEvent_Main(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityDamage_Main(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
	
}
