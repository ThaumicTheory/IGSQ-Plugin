package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWorldWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.Messaging;

import java.util.Random;


public class CreatureSpawnEvent_Expert implements Listener
{
	Random random = new Random();
	public CreatureSpawnEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void CreatureSpawn_Expert(org.bukkit.event.entity.CreatureSpawnEvent event) 
	{
		if(!event.isCancelled())
		{
			if(YamlWrapper.isExpert()) 
			{
				if(new YamlWorldWrapper(event.getLocation().getWorld()).isExpertBloodMoon())
				{
					SpecialCreatures(event,1,true);
				}
				else 
				{
					SpecialCreatures(event,3,false);
				}
			}
			else 
			{
				if(event.getEntity() instanceof EnderDragon) 
				{
					Common_Expert.updateEnderDragon((EnderDragon) event.getEntity());
				}
			}
		}
	}
	private void SpecialCreatures(org.bukkit.event.entity.CreatureSpawnEvent event,int rarityMultiplier,boolean bloodMoon) 
	{
		if(event.getEntityType() == EntityType.SPIDER || event.getEntityType() == EntityType.CAVE_SPIDER) 
		{
			String spiderType = "";
			if(event.getEntityType() == EntityType.CAVE_SPIDER) 
			{
				spiderType = "Cave ";
			}
			if(random.nextInt(8*rarityMultiplier) == 1) 
			{
				event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,1000000,0,false));
				event.getEntity().setCustomName(Messaging.chatFormatter("&#84FF00Expert Invisible "+ spiderType + "Spider"));
			}
			else if(random.nextInt(6*rarityMultiplier) == 1) 
			{
				event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000000,random.nextInt(5)-1,false));
				event.getEntity().setCustomName(Messaging.chatFormatter("&#84FF00Expert Quick "+ spiderType + "Spider"));
			}
			else if(random.nextInt(6*rarityMultiplier) == 1) 
			{
				event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.JUMP,1000000,random.nextInt(5)-1,false));
				event.getEntity().setCustomName(Messaging.chatFormatter("&#84FF00Expert Springy "+ spiderType + "Spider"));
			}
		}
		else if(event.getEntityType() == EntityType.CREEPER) 
		{
			Creeper creeper = (Creeper) event.getEntity();
			if(random.nextInt(8*rarityMultiplier) == 1) 
			{
				creeper.setPowered(true);
				creeper.setExplosionRadius(3+(random.nextInt(3)*2));
				creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Charged Creeper"));
			}
			else if(random.nextInt(3*rarityMultiplier) == 1) 
			{
				event.getEntity().setRemoveWhenFarAway(true);
				creeper.setExplosionRadius(3+random.nextInt(4));
				if(random.nextInt(7) == 1)
				{
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,1200,0,false));
					creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Confusing Creeper"));
				}
				else if(random.nextInt(6) == 1)
				{
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,1200,0,false));
					creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Blinding Creeper"));
				}
				else if(random.nextInt(5) == 1)
				{
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,1200,3,false));
					creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Hunger Creeper"));
				}
				else if(random.nextInt(4) == 1)
				{
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1200,3,false));
					creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Slowing Creeper"));
				}
				else if(random.nextInt(3) == 1)
				{
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,1200,0,false));
					creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Fatugue Creeper"));
				}
				else if(random.nextInt(2) == 1)
				{
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,1200,0,false));
					creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Weakening Creeper"));
				}
				else 
				{
					creeper.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK,1200,2,false));
					creeper.setCustomName(Messaging.chatFormatter("&#84FF00Expert Unlucking Creeper"));
				}
			}
		}
		else if(event.getEntityType() == EntityType.SLIME) 
		{
			Slime slime = (Slime) event.getEntity();
			if(slime.getCustomName() != null) 
			{
				
			}
			else if(random.nextInt(10*rarityMultiplier) == 1 && bloodMoon) 
			{
				int size = 3+ slime.getSize()+(random.nextInt(10)+1);
				if(size > 7) 
				{
					size = 7;
				}
				slime.setSize(size);
				slime.setCustomName(Messaging.chatFormatter("&#84FF00Expert King Slime"));
			}
			else if(random.nextInt(4*rarityMultiplier) == 1) 
			{
				slime.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,1000000,0,false));
				slime.setCustomName(Messaging.chatFormatter("&#84FF00Expert Slimey Slime"));
			}
			else if(random.nextInt(6*rarityMultiplier) == 1) 
			{
				slime.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,1000000,2,false));
				slime.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000,255,true));
				slime.setCustomName(Messaging.chatFormatter("&#84FF00Expert Rock Slime"));
			}
			else 
			{
			}
		}
		else if(event.getEntityType() == EntityType.MAGMA_CUBE) 
		{
			MagmaCube magmaCube = (MagmaCube) event.getEntity();
			if(magmaCube.getCustomName() != null) 
			{
				
			}
			else if(random.nextInt(15*rarityMultiplier) == 1 && bloodMoon) 
			{
				int size = 5+magmaCube.getSize()+(random.nextInt(10)+1);
				if(size > 10) 
				{
					size = 10;
				}
				magmaCube.setSize(size);
				magmaCube.setCustomName(Messaging.chatFormatter("&#84FF00Expert King Magma Slime"));
			}
			else if(random.nextInt(4*rarityMultiplier) == 1) 
			{
				magmaCube.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,1000000,0,false));
				magmaCube.setCustomName(Messaging.chatFormatter("&#84FF00Expert Flamey Magma Slime"));
			}
			else if(random.nextInt(6*rarityMultiplier) == 1) 
			{
				magmaCube.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,1000000,2,false));
				magmaCube.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000,255,true));
				magmaCube.setCustomName(Messaging.chatFormatter("&#84FF00Expert Rock Magma Slime"));
			}
			else 
			{
			}
		}
		else if(event.getEntity() instanceof EnderDragon) 
		{
			Common_Expert.updateEnderDragon((EnderDragon) event.getEntity());
		}
		if(event.getEntityType() !=EntityType.PLAYER) 
		{
			if(event.getEntity().getCustomName() != null) 
			{
				event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*2);
			}
			else
			{
				event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*1.5);
			}
			event.getEntity().setHealth(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		}
	}
	
}
