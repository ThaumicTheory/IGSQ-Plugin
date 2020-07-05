package me.murrobby.igsq.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

@SuppressWarnings("unused")
public class EntityAirChangeEvent_Main implements Listener
{
	private Main plugin;
	public EntityAirChangeEvent_Main(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityAirChange_Main(org.bukkit.event.entity.EntityAirChangeEvent event) 
	{
		if(!event.isCancelled()) 
		{
			
		}
	}
	
}
