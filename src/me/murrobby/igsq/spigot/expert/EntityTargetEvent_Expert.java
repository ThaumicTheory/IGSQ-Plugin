package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

import java.util.Random;


public class EntityTargetEvent_Expert implements Listener
{
	Random random = new Random();
	public EntityTargetEvent_Expert(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void EntityTarget_Expert(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		if(Common_Spigot.ExpertCheck() && (!event.isCancelled()))
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
