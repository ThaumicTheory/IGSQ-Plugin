package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;

public class EntityAirChangeEvent_Expert implements Listener
{
	public EntityAirChangeEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityAirChange_Expert(org.bukkit.event.entity.EntityAirChangeEvent event) 
	{
		if(Common_Expert.expertCheck() && (!event.isCancelled())) 
		{
			if(event.getEntityType() == EntityType.PLAYER) 
			{
				Player player = (Player) event.getEntity();
				int oxygen = event.getAmount();
				if(oxygen <= -20) 
				{
					//Level 6 NDE
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,(300 - oxygen),1,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(300 - oxygen),3,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,(300 - oxygen),2,false));
					Common_Expert.giveBlindness(player,(300 - oxygen)+20);
					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,(300 - oxygen)*4,0,false));
				}
				else if(oxygen <= 50) 
				{
					if(oxygen == 50) 
					{
					}
					if(oxygen < 0) 
					{
						Common_Expert.giveBlindness(player,-oxygen);
					}
					//Level 5 You need to resurface now
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,(300 - oxygen)/2,0,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(300 - oxygen),2,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,(300 - oxygen),1,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,(300 - oxygen)*3,0,false));
				}
				else if(oxygen <= 100) 
				{
					if(oxygen == 100) 
					{
					}
					//Level 4 You are really fatigued
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(300 - oxygen),1,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,(300 - oxygen),0,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,(300 - oxygen)*2,0,false));
				}
				else if(oxygen <= 150) 
				{
					if(oxygen == 150) 
					{
					}
					//Level 3 You are a bit tired..
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(300 - oxygen),0,false));
					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,(300 - oxygen),0,false));
				}
				else if(oxygen <= 200)
				{
					if(oxygen == 200) 
					{
						
					}
					//Level 2 Losing energy
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20,0,false));
				}
				else if(oxygen <= 250)
				{
					if(oxygen == 250) 
					{
						
					}
					//Level 1 Normal
				}
				else if(oxygen <= 300)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE,5,0,false));
					//Level 0 Energetic
				}
			}
		}
	}
	
}
