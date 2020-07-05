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
public class PlayerBedEnterEvent_Expert implements Listener
{
	Random random = new Random();
	private Main plugin;
	public PlayerBedEnterEvent_Expert(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerBedEnter_Expert(org.bukkit.event.player.PlayerBedEnterEvent event) 
	{
		if(!event.isCancelled())
		{
			if(Common.getFieldBool(event.getPlayer().getWorld().getUID() + ".event.bloodmoon", "internal") && event.getPlayer().getWorld().getEnvironment() != Environment.NETHER && event.getPlayer().getWorld().getEnvironment() != Environment.THE_END)	
			{
				event.setCancelled(true);
				event.getPlayer().sendTitle(Common.ChatColour("&cExpert Mode Event Present!"),Common.ChatColour("&4You Cant Sleep Through a Blood Moon!"),10,70,20);
			}
		}
	}
	
}
