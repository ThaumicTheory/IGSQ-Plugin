package me.murrobby.igsq.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

import java.util.Random;


@SuppressWarnings("unused")
public class EntityDamageByEntityEvent_Main implements Listener
{
	Random random = new Random();
	private Main plugin;
	public EntityDamageByEntityEvent_Main(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityDamagedByEntity_Main(org.bukkit.event.entity.EntityDamageByEntityEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
			
	}
	
}
