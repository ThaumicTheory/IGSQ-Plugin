package thaumictheory.igsq.spigot.smp.aspect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.smp.Player_SMP;
import thaumictheory.igsq.spigot.yaml.YamlEntityWrapper;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;


public class EntityTargetEvent_Aspect implements Listener
{
	public EntityTargetEvent_Aspect()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityTarget_Aspect(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		if(YamlWrapper.isSMP() && (!event.isCancelled()))
		{
			if(!(event.getEntity() instanceof LivingEntity)) return; //dont do anything to non Living entities's targets
			YamlEntityWrapper yaml = new YamlEntityWrapper((LivingEntity) event.getEntity());
			if(event.getEntity().getTicksLived()-yaml.getSMPLastHit() > 600 && !yaml.getSMPAgro().equals("")) 
			{
				yaml.setSMPAgro("");
			}
			
			if(event.getReason().equals(TargetReason.CUSTOM)) return; //Dont do anything to custom events
			if(event.getEntity() instanceof Tameable && ((Tameable) event.getEntity()).getOwner() != null) return; //dont do anything to tames
			if(event.getTarget() != null) 
			{
				if(event.getTarget() instanceof Player) //Cancel for Passive
				{
					Player_SMP player = Player_SMP.getSMPPlayer((Player) event.getTarget());
					if(yaml.getSMPAgro() == null) yaml.applyDefault();
					for(String target : yaml.getSMPAgro().split(" ")) if(player.getPlayer().getUniqueId().toString().equals(target)) return;
					if(player.getAspect().isEntityPassive(event.getEntityType()) || player.getAspect().isEntityProtective(event.getEntityType()) || player.getAspect().isEntityNeutral(event.getEntityType())) event.setCancelled(true);
				}
				else enemy(event); //swap target to player to be agressive
			}
			else agressive(event); //target player to be agressive
		}
		
	}
	private void agressive(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		double distance = 0;
		Player closestPlayer = null;
		for(Entity nearbyEntity : event.getEntity().getNearbyEntities(10, 5, 10)) 
		{
			if(nearbyEntity instanceof Player) 
			{
				Player_SMP player = Player_SMP.getSMPPlayer((Player) nearbyEntity);
				if(player.getAspect().isEntityAggresive(event.getEntityType()) || player.getAspect().isEntityEnemy(event.getEntityType()))
				{
					double playersDistance = player.getPlayer().getLocation().distance(event.getEntity().getLocation());
					if(closestPlayer == null || distance > playersDistance) 
					{
						closestPlayer = player.getPlayer();
						distance = playersDistance;
					}
			
				}
			}
		}
		if(closestPlayer != null) 
		{
			new YamlEntityWrapper((LivingEntity) event.getEntity()).setSMPAgro("");
			event.setTarget((Entity) closestPlayer);
		}
	}
	private void enemy(org.bukkit.event.entity.EntityTargetEvent event) 
	{
		double distance = 0;
		Player closestPlayer = null;
		for(Entity nearbyEntity : event.getEntity().getNearbyEntities(10, 5, 10)) 
		{
			if(nearbyEntity instanceof Player) 
			{
				Player_SMP player = Player_SMP.getSMPPlayer((Player) nearbyEntity);
				if(player.getAspect().isEntityEnemy(event.getEntityType()))
				{
					double playersDistance = player.getPlayer().getLocation().distance(event.getEntity().getLocation());
					if(closestPlayer == null || distance > playersDistance) 
					{
						closestPlayer = player.getPlayer();
						distance = playersDistance;
					}
			
				}
			}
		}
		if(closestPlayer != null) 
		{
			new YamlEntityWrapper((LivingEntity) event.getEntity()).setSMPAgro("");
			event.setTarget((Entity) closestPlayer);
		}
	}
}
