package me.murrobby.igsq.expert;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World.Environment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

import java.util.Random;


@SuppressWarnings("unused")
public class EntityTargetEvent_Expert implements Listener
{
	Random random = new Random();
	private Main plugin;
	public EntityTargetEvent_Expert(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityTarget_Expert(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		if(!event.isCancelled())
		{
			if(event.getEntity() instanceof Phantom) 
			{
				if(event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equalsIgnoreCase("Expert Phantom Warrior")) 
				{
					if(!(event.getTarget() instanceof Player)) 
					{
						Phantom phantom = (Phantom) event.getEntity();
						phantom.setHealth(0);
					}
				}
			}
		}
	}
	
}
