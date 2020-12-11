package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlWrapper;


public class EntityTargetEvent_Expert implements Listener
{
	public EntityTargetEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityTarget_Expert(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		if(YamlWrapper.isExpert() && (!event.isCancelled()))
		{
			if(event.getEntity() instanceof Phantom) 
			{
				if(event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert Phantom Warrior"))) 
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
