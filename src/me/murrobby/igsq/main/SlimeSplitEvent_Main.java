package me.murrobby.igsq.main;

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
public class SlimeSplitEvent_Main implements Listener
{
	Random random = new Random();
	private Main plugin;
	public SlimeSplitEvent_Main(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void SlimeSplit_Main(org.bukkit.event.entity.SlimeSplitEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
	
}
