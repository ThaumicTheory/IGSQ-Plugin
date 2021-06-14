package thaumictheory.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.YamlWrapper;

import java.util.Random;


public class EntityDamageByEntityEvent_Expert implements Listener
{
	Random random = new Random();
	public EntityDamageByEntityEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamagedByEntity_Expert(org.bukkit.event.entity.EntityDamageByEntityEvent event) 
	{
		if(!event.isCancelled())
		{
			if(YamlWrapper.isExpert()) 
			{
				if(event.getEntityType() == EntityType.PLAYER) 
				{
					Player player = (Player) event.getEntity();
					if(event.getDamager().getType() == EntityType.ENDERMAN || event.getDamager().getType() == EntityType.ENDERMITE || event.getDamager().getType() == EntityType.ENDER_CRYSTAL) 
					{
						if(random.nextInt(200) < event.getDamage()*10) 
						{
							Common_Expert.giveBlindness(player,(int) ((event.getDamage()*5 * random.nextInt(3))+20));
						}
					}
					else if(event.getDamager().getType() == EntityType.ENDER_DRAGON) 
					{
						if(event.getDamager().getCustomName() == null) 
						{
							
						}
						else if(event.getDamager().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#FF5300True Expert Ender Dragon"))) 
						{
							if(random.nextInt(200) < event.getDamage()*10) 
							{
								Common_Expert.giveBlindness(player,(int) ((event.getDamage()*10 * random.nextInt(5))+20));
							}
						}
					}
					else if(event.getDamager().getType() == EntityType.SLIME) 
					{
						if(event.getDamager().getCustomName() != null && event.getDamager().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert Slimey Slime"))) 
						{
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(int) ((event.getDamage()*5 * random.nextInt(3))+20),(int) (event.getDamage()*2* random.nextInt(3))));
						}
						if(random.nextInt(200) < event.getDamage()*10) 
						{
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(int) ((event.getDamage()*2 * random.nextInt(3))+20),(int) (event.getDamage()/2* random.nextInt(3))));
						}
					}
					else if(event.getDamager().getType() == EntityType.MAGMA_CUBE) 
					{
						if(event.getDamager().getCustomName() != null && event.getDamager().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert Flamey Magma Slime"))) 
						{
							player.setFireTicks((int) (player.getFireTicks()+((event.getDamage()*25 * random.nextInt(5))))+20);
						}
						if(random.nextInt(200) < event.getDamage()*10) 
						{
							player.setFireTicks((int) (player.getFireTicks()+((event.getDamage()*10 * random.nextInt(5))))+20);
						}
					}
					else if(event.getDamager() instanceof Phantom) 
					{
						Phantom phantom = (Phantom) event.getDamager();
						if(phantom.getCustomName() != null && phantom.getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert Phantom Warrior"))) 
						{
							Common_Expert.giveBlindness(player,(int) ((event.getDamage()*10 * random.nextInt(5))+20));
							for(Entity nearbyEntity : phantom.getNearbyEntities(32, 32, 32)) 
							{
								if(nearbyEntity instanceof Player) 
								{
									Player playerEffect = (Player) nearbyEntity;
									playerEffect.spawnParticle(Particle.SMOKE_NORMAL,phantom.getLocation(), 100,1,1,1,0);
									playerEffect.playSound(phantom.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 2);
									
								}
							}
						}
						else if(random.nextInt(200) < event.getDamage()*20) 
						{
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,(int) ((event.getDamage()*5 * random.nextInt(5))+20),0));
						}
					}
				}
				else if(event.getEntityType() == EntityType.SLIME) 
				{
					Slime slime = (Slime) event.getEntity();
					if(event.getDamager().getType() == EntityType.PLAYER) 
					{
						if(event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert King Slime"))) 
						{
							Slime spawnSlime = (Slime) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.SLIME);
							spawnSlime.setCustomName(Messaging.chatFormatter("&#84FF00Expert Warrior Slime"));
							spawnSlime.setSize(slime.getSize()/2);
						}
					}
				}
				else if(event.getEntityType() == EntityType.MAGMA_CUBE) 
				{
					MagmaCube slime = (MagmaCube) event.getEntity();
					if(event.getDamager().getType() == EntityType.PLAYER) 
					{
						if(event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert King Magma Slime"))) 
						{
							int randomNum = random.nextInt(4);
							for(int i = 0;i < randomNum;i++) 
							{
								MagmaCube spawnSlime = (MagmaCube) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.MAGMA_CUBE);
								spawnSlime.setCustomName(Messaging.chatFormatter("&#84FF00Expert Warrior Magma Slime"));
								spawnSlime.setSize(slime.getSize()/2);
							}
							
						}
					}
				}
				else if(event.getEntityType() == EntityType.PHANTOM) 
				{
					if(event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equalsIgnoreCase("Expert Phantom Warrior") && event.getDamager().getType() == EntityType.ENDER_DRAGON) 
					{
						event.setCancelled(true);
					}
				}
				else if(event.getEntityType() == EntityType.ENDER_DRAGON) 
				{
					EnderDragon enderDragon = (EnderDragon) event.getEntity();
					if(enderDragon.getCustomName() == null) 
					{
						
					}
					else if(enderDragon.getCustomName().equalsIgnoreCase("True Expert Ender Dragon"))
					{
						if(event.getDamager().getType() == EntityType.ARROW) 
						{
							Arrow arrow = (Arrow) event.getDamager();
							if(arrow.getShooter() instanceof Player) 
							{
								Player player = (Player) arrow.getShooter();
								Common_Expert.giveBlindness(player,59);
							}
						}
					}
				}
				else if(event.getEntityType() == EntityType.PLAYER) 
				{
					event.setDamage(event.getDamage()/2);
				}
			}
		}
	}
	
}
