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
import me.murrobby.igsq.spigot.YamlEntityWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.smp.Player_SMP;




public class ProjectileHitEvent_Aspect implements Listener
{
	public ProjectileHitEvent_Aspect()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamagedByEntity_Aspect(org.bukkit.event.entity.ProjectileHitEvent event) 
	{
		if(YamlWrapper.isSMP())
		{
			if(event.getHitEntity() == null || (event.getHitEntity() instanceof Tameable && ((Tameable) event.getEntity()).getOwner() != null)) return;
			if(event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player && event.getHitEntity() instanceof LivingEntity && !(event.getHitEntity() instanceof Player))
			{
				Player_SMP attacker = Player_SMP.getSMPPlayer((Player) event.getEntity().getShooter());
				if(attacker.getAspect().isEntityNeutral(event.getHitEntity().getType())) 
				{
					YamlEntityWrapper yaml = new YamlEntityWrapper((LivingEntity) event.getHitEntity());
					yaml.setSMPLastHit(event.getEntity().getTicksLived());
					if(yaml.addSMPAgro(attacker.getPlayer().getUniqueId().toString())) Bukkit.getPluginManager().callEvent(new EntityTargetEvent(event.getHitEntity(), (Entity) attacker.getPlayer(), TargetReason.CUSTOM));
					for(Entity entity : event.getEntity().getNearbyEntities(10, 10, 10)) 
					{
						if(event.getHitEntity().getType().equals(entity.getType())) 
						{
							if(new YamlEntityWrapper((LivingEntity) entity).addSMPAgro(attacker.getPlayer().getUniqueId().toString())) Bukkit.getPluginManager().callEvent(new EntityTargetEvent(entity, (Entity) attacker.getPlayer(), TargetReason.CUSTOM));
						}
					}
				}
			}
		}
	}
	
}
