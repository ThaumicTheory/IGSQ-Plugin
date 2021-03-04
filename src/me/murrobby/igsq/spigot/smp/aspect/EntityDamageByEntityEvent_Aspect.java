package me.murrobby.igsq.spigot.smp.aspect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlEntityWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.smp.Player_SMP;




public class EntityDamageByEntityEvent_Aspect implements Listener
{
	public EntityDamageByEntityEvent_Aspect()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamagedByEntity_Aspect(org.bukkit.event.entity.EntityDamageByEntityEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP())
		{
			if(!(event.getEntity() instanceof Player)) 
			{
				Player attacker = null;
				if(event.getDamager() instanceof Player) attacker = (Player) event.getDamager();
				else if(event.getDamager() instanceof Tameable && ((Tameable) event.getDamager()).getOwner() != null && ((Tameable) event.getDamager()).getOwner() instanceof Player) attacker = (Player) ((Tameable) event.getDamager()).getOwner();
				if(attacker != null) 
				{
					Player_SMP player = Player_SMP.getSMPPlayer(attacker);
					if(player.getAspect().isEntityNeutral(event.getEntityType())) 
					{
						YamlEntityWrapper yaml = new YamlEntityWrapper((LivingEntity) event.getEntity());
						yaml.setSMPLastHit(event.getEntity().getTicksLived());
						if(yaml.addSMPAgro(attacker.getPlayer().getUniqueId().toString())) Bukkit.getPluginManager().callEvent(new EntityTargetEvent(event.getEntity(), event.getDamager(), TargetReason.CUSTOM));
						for(Entity entity : event.getEntity().getNearbyEntities(10, 10, 10)) 
						{
							if(event.getEntityType().equals(entity.getType())) 
							{
								if(new YamlEntityWrapper((LivingEntity) entity).addSMPAgro(attacker.getPlayer().getUniqueId().toString())) Bukkit.getPluginManager().callEvent(new EntityTargetEvent(entity, event.getDamager(), TargetReason.CUSTOM));
							}
						}
					}
				}
			}
			else
			{
				Player_SMP player = Player_SMP.getSMPPlayer((Player) event.getEntity());
				Player_SMP damager = null;
				if(event.getDamager() instanceof Player) damager = Player_SMP.getSMPPlayer((Player) event.getDamager());
				for(Entity entity : event.getEntity().getNearbyEntities(15, 10, 15)) 
				{
					if((damager == null || !damager.getAspect().isEntityProtective(entity.getType())) && player.getAspect().isEntityProtective(entity.getType()) && !entity.getType().equals(event.getDamager().getType())) 
					{
						Bukkit.getPluginManager().callEvent(new EntityTargetEvent(entity, event.getDamager(), TargetReason.CUSTOM));
						Messaging.createLog("ahhhh");
					}
				}
			}
		}
	}
	
}
