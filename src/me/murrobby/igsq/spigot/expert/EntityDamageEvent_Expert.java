package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Configuration;
import me.murrobby.igsq.spigot.Messaging;

public class EntityDamageEvent_Expert implements Listener
{
	public EntityDamageEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamage_Expert(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(Common_Expert.expertCheck() && (!event.isCancelled()))
		{
			if(event.getEntityType() == EntityType.PLAYER) 
			{
				Player player = (Player)event.getEntity();
				player.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK,(int) (200*event.getDamage()*((1+player.getHealthScale())-player.getHealth())),0,true));
				Configuration.updateField(player.getUniqueId().toString() + ".damage.last","player" ,player.getTicksLived() );
				if(player.hasPotionEffect(PotionEffectType.LUCK)) 
				{
					player.removePotionEffect(PotionEffectType.LUCK);
				}
				if(event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.ENTITY_SWEEP_ATTACK || event.getCause() == DamageCause.MAGIC || event.getCause() == DamageCause.ENTITY_EXPLOSION || event.getCause() == DamageCause.WITHER || event.getCause() == DamageCause.PROJECTILE) 
				{
					//Entity Based Attacks
					event.setDamage(event.getDamage()*2);
				}
				else if(event.getCause() == DamageCause.STARVATION || event.getCause() == DamageCause.SUFFOCATION ||  event.getCause() == DamageCause.FLY_INTO_WALL || event.getCause() == DamageCause.HOT_FLOOR || event.getCause() == DamageCause.VOID || event.getCause() == DamageCause.FALL) 
				{
					//Enviromental Damage
					event.setDamage(event.getDamage()*2);
				}
				else if(event.getCause() == DamageCause.LIGHTNING || event.getCause() == DamageCause.BLOCK_EXPLOSION  || event.getCause() == DamageCause.DRAGON_BREATH || event.getCause() == DamageCause.CRAMMING || event.getCause() == DamageCause.CONTACT) 
				{
					//Active Damage
					event.setDamage(event.getDamage()*3);
				}
				else if(event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.LAVA)
				{
					//Fire and lava based damage
					event.setDamage(event.getDamage()*2);
					player.setFireTicks(player.getFireTicks()+100);
				}
				else if(event.getCause() == DamageCause.FIRE_TICK) 
				{
					player.setFireTicks(player.getFireTicks()-10);
					event.setDamage(event.getDamage()*0.5f);
					//Fire Tick Modifications
				}
				else if(event.getCause() == DamageCause.DROWNING) 
				{
					event.setDamage(event.getDamage()*2);
				}
				else if(event.getCause() == DamageCause.POISON) 
				{
					if(player.getHealth() > 2) 
					{
						event.setDamage(event.getDamage()*2);
					}
					else 
					{
						
					}
				}
				else 
				{
					
				}
			}
			else if(event.getEntityType() == EntityType.ENDER_DRAGON) 
			{
				EnderDragon enderDragon = (EnderDragon) event.getEntity();
				if(enderDragon.getHealth() - event.getDamage() <= 0 && enderDragon.getCustomName() == null) 
				{
					event.setCancelled(true);
					enderDragon.setCustomName(Messaging.chatFormatter("&#FF5300True Expert Ender Dragon"));
					enderDragon.setHealth(enderDragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					enderDragon.getBossBar().setColor(BarColor.RED);
					enderDragon.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(enderDragon.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue()*2);
					for(Player selectedPlayer : Common.spigot.getServer().getOnlinePlayers()) 
					{
						selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 0);
						if(selectedPlayer.getWorld().getEnvironment() == Environment.THE_END) 
						{
							selectedPlayer.sendTitle(Messaging.chatFormatter("&#7900FFEnder Dragon Phase 2"),Messaging.chatFormatter("&#A600FFThe True Expert Ender Dragon"),10,70,20);
							selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.MUSIC_END, 10000, 2f);
						}
					}
				}
				if(enderDragon.getCustomName() == null) 
				{
					
				}
				else if(enderDragon.getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#FF5300True Expert Ender Dragon")))
				{
					if(event.getCause() == DamageCause.BLOCK_EXPLOSION || event.getCause() == DamageCause.ENTITY_EXPLOSION)
					{
						event.setCancelled(true);
					}
					else if(event.getCause() == DamageCause.PROJECTILE) 
					{
						event.setDamage(event.getDamage()/2);
						enderDragon.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,59,0,false));
					}
				}
			}
			else
			{
				
			}
		}
	}
}
