package me.murrobby.igsq.spigot.expert.protection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.expert.Team_Expert;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;

public class EntityDamageByEntityEvent_Protection implements Listener
{
	public EntityDamageByEntityEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamageByEntity_Security(org.bukkit.event.entity.EntityDamageByEntityEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isExpert()) 
		{
			if(event.getDamager() instanceof Player) 
			{
				Player damager = (Player) event.getDamager();
				if(event.getEntity() instanceof Player)
				{
					Player damaged = (Player) event.getEntity();
					if(Team_Expert.isInATeam(damager) && Team_Expert.isInATeam(damaged)) 
					{
						Team_Expert damagerTeam = Team_Expert.getPlayersTeam(damager);
						Team_Expert damagedTeam = Team_Expert.getPlayersTeam(damaged);
						if(damagerTeam.equals(damagedTeam)) 
						{
							damager.sendMessage(Messaging.chatFormatter("&#00FF00You cannot hurt people in your faction!"));
							event.setCancelled(true);
						}
						else if(damagerTeam.isAlly(damagedTeam)) 
						{
							damager.sendMessage(Messaging.chatFormatter("&#ff61f4You cannot hurt people you are in an alliance with!"));
							event.setCancelled(true);
						}
					}
				}
				else if(Common_Protection.isProtected(damager,event.getEntity().getLocation())) event.setCancelled(true);
			}
		}
	}
	
}
