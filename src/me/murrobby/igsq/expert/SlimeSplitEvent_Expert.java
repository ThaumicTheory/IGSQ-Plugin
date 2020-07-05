package me.murrobby.igsq.expert;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

import java.util.Random;


@SuppressWarnings("unused")
public class SlimeSplitEvent_Expert implements Listener
{
	Random random = new Random();
	private Main plugin;
	public SlimeSplitEvent_Expert(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void SlimeSplit_Expert(org.bukkit.event.entity.SlimeSplitEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getEntity().getType() == EntityType.SLIME) 
			{
				if(event.getEntity().getCustomName() != null && (event.getEntity().getCustomName().equalsIgnoreCase("Expert King Slime") || event.getEntity().getCustomName().equalsIgnoreCase("Expert Warrior Slime"))) 
				{
					event.setCancelled(true);
				}
				if(event.getEntity().getCustomName() != null) 
				{
					event.getEntity().setCustomName(null);
				}
			}
			else if(event.getEntity().getType() == EntityType.MAGMA_CUBE) 
			{
				if(event.getEntity().getCustomName() != null && (event.getEntity().getCustomName().equalsIgnoreCase("Expert King Magma Slime") || event.getEntity().getCustomName().equalsIgnoreCase("Expert Warrior Magma Slime"))) 
				{
					event.setCancelled(true);
				}
				if(event.getEntity().getCustomName() != null) 
				{
					event.getEntity().setCustomName(null);
				}
			}
		}
	}
	
}
