package me.murrobby.igsq.spigot.expert;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.entity.EnderDragon.Phase;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlWrapper;

import java.util.Random;

public class EnderDragon_Expert
{	
	Random random = new Random();
	int enderDragonTask = -1;
	final int taskID;
	
	public EnderDragon_Expert(int taskID) 
	{
		this.taskID = taskID;
		EnderDragonQuery();
	}
	private void EnderDragonQuery() 
	{
		enderDragonTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				EnderDragon();
				if(Main_Expert.taskID != taskID || !YamlWrapper.isExpert()) 
				{
					Common.spigot.scheduler.cancelTask(enderDragonTask);
					System.out.println("Task: \"Ender Dragon Expert\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 20);
	}
	private void EnderDragon() 
	{
		for(World selectedWorld : Common.spigot.getServer().getWorlds()) 
		{
			try 
			{
				if(selectedWorld.getEnvironment() == Environment.THE_END) 
				{
					EnderDragon enderDragon = null;
					try
					{
						enderDragon = selectedWorld.getEnderDragonBattle().getEnderDragon();
					}
					catch(Exception exception) 
					{
						
					}
					if(enderDragon != null) 
					{
						if(enderDragon.getCustomName() == null) 
						{
							if(enderDragon.getPhase() == Phase.FLY_TO_PORTAL) 
							{
								for(Entity nearbyEntity : enderDragon.getNearbyEntities(50, 50, 50)) 
								{
									if(nearbyEntity.getType() == EntityType.PLAYER) 
									{
										if(random.nextInt(8)== 1) 
										{
											Player player = (Player) nearbyEntity;
											Phantom phantom = (Phantom) selectedWorld.spawnEntity(enderDragon.getLocation(), EntityType.PHANTOM);
											phantom.setCustomName(Messaging.chatFormatter("&#84FF00Expert Phantom Warrior"));
											phantom.setTarget(player);
											phantom.setHealth(1);
										}
									}

								}
							}
						}
						else if(enderDragon.getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#FF5300True Expert Ender Dragon")))
						{
							if(!YamlWrapper.isExpert()) // Enderdragon name chage
							{
								Common_Expert.updateEnderDragon(enderDragon);
							}
							else if(enderDragon.getPhase() == Phase.LAND_ON_PORTAL || enderDragon.getPhase() == Phase.SEARCH_FOR_BREATH_ATTACK_TARGET) 
							for(Entity nearbyEntity : enderDragon.getNearbyEntities(20, 20, 20)) 
							{
								if(nearbyEntity.getType() == EntityType.PLAYER) 
								{
									Player player = (Player) nearbyEntity;

									if(enderDragon.getPhase() == Phase.LAND_ON_PORTAL) 
									{
										player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,199,2,true));
										player.spawnParticle(Particle.DRAGON_BREATH,player.getLocation(), 20,.5,.5,.5,.25);
									}
									else if((enderDragon.getPhase() == Phase.SEARCH_FOR_BREATH_ATTACK_TARGET || enderDragon.getPhase() == Phase.BREATH_ATTACK) && player.hasPotionEffect(PotionEffectType.LEVITATION)) 
									{
										player.removePotionEffect(PotionEffectType.LEVITATION);
										player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,5,128,true));
										player.spawnParticle(Particle.END_ROD,player.getLocation(), 20,.5,.5,.5,.25);
										player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1,2);
									}

								}

							}
							if(enderDragon.getPhase() == Phase.FLY_TO_PORTAL) 
							{
								for(Entity nearbyEntity : enderDragon.getNearbyEntities(50, 50, 50)) 
								{
									if(nearbyEntity.getType() == EntityType.PLAYER) 
									{
										if(random.nextInt(4)== 1) 
										{
											Player player = (Player) nearbyEntity;
											Phantom phantom = (Phantom) selectedWorld.spawnEntity(enderDragon.getLocation(), EntityType.PHANTOM);
											phantom.setCustomName(Messaging.chatFormatter("&#84FF00Expert Phantom Warrior"));
											phantom.setTarget(player);
											phantom.setHealth(3);
										}
									}

								}
							}
							else if(enderDragon.getPhase() == Phase.DYING) 
							{
								for(Entity nearbyEntity : enderDragon.getNearbyEntities(100, 100, 100)) 
								{
									if(nearbyEntity instanceof Phantom) 
									{
										Phantom phantom = (Phantom) nearbyEntity;
										if(phantom.getCustomName() != null && phantom.getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert Phantom Warrior"))) 
										{
											phantom.setHealth(0);
										}
									}
								}
							}
							for(Entity nearbyEntity : enderDragon.getNearbyEntities(8, 8, 8)) 
							{
								if(nearbyEntity.getType() == EntityType.PLAYER) 
								{

									Player player = (Player) nearbyEntity;
									Common_Expert.giveBlindness(player,59);

								}

							}
							for(Entity nearbyEntity : enderDragon.getNearbyEntities(5, 5, 5)) 
							{
								if(nearbyEntity.getType() == EntityType.PLAYER) 
								{

									Player player = (Player) nearbyEntity;
									player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,59,0,false));

								}

							}
							if(enderDragon.getPhase() == Phase.STRAFING || enderDragon.getPhase() == Phase.CIRCLING) 
							{
								for(Entity nearbyEntity : enderDragon.getNearbyEntities(50, 50, 50)) 
								{
									if(nearbyEntity.getType() == EntityType.PLAYER) 
									{

										Player player = (Player) nearbyEntity;
										Common_Expert.giveBlindness(player,59);
										player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,39,0,false));

									}

								}
								enderDragon.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,39,0,false));
							}
						}
					}
				}
			}
			catch(Exception exception) 
			{
				System.out.println("EnderDragon World:" + exception.toString());
			}
		}
	}
}
