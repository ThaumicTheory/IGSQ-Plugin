package thaumictheory.igsq.spigot.smp.aspect;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.smp.Player_SMP;
import thaumictheory.igsq.spigot.yaml.YamlEntityWrapper;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;




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
			Random random = new Random();
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
						if(yaml.addSMPAgro(attacker.getPlayer().getUniqueId().toString())) Bukkit.getPluginManager().callEvent(new EntityTargetEvent(event.getEntity(), event.getDamager(), TargetReason.CLOSEST_PLAYER));
						for(Entity entity : event.getEntity().getNearbyEntities(10, 10, 10)) 
						{
							if(event.getEntityType().equals(entity.getType())) 
							{
								if(new YamlEntityWrapper((LivingEntity) entity).addSMPAgro(attacker.getPlayer().getUniqueId().toString())) Bukkit.getPluginManager().callEvent(new EntityTargetEvent(entity, event.getDamager(), TargetReason.CLOSEST_PLAYER));
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
				
				double initialDamage = event.getDamage();
				for(Entity entity : event.getEntity().getNearbyEntities(5, 5, 5)) 
				{
					if(entity instanceof LivingEntity)
					if((damager == null || !damager.getAspect().isEntityProtective(entity.getType())) && player.getAspect().isEntityProtective(entity.getType()) && !entity.getType().equals(event.getDamager().getType())) 
					{
						if(damager != null) Bukkit.getPluginManager().callEvent(new EntityTargetEvent(entity, event.getDamager(), TargetReason.CLOSEST_PLAYER));
						LivingEntity defender = (LivingEntity) entity;
						if(defender.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) event.setDamage(event.getDamage()-(defender.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue()/8));
						if(initialDamage/4 >  event.getDamage()) event.setDamage(initialDamage/4); //dont do less than quater damage
						for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
						{
							selectedPlayer.spawnParticle(Particle.CLOUD, defender.getLocation().getX(), defender.getLocation().getY(), defender.getLocation().getZ(), 10, 0.5, 0.5, .5,0);
							selectedPlayer.playSound(event.getDamager().getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 2);
						}
						if(damager == null && random.nextInt(5) == 0) 
						{
							if(defender.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) defender.attack(event.getDamager());
							for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
							{
								selectedPlayer.playSound(event.getDamager().getLocation(), Sound.ENCHANT_THORNS_HIT, 1, 1);
								selectedPlayer.spawnParticle(Particle.DRAGON_BREATH, event.getDamager().getLocation().getX(), event.getDamager().getLocation().getY(), event.getDamager().getLocation().getZ(), 4, 0.5, 0.5, .5,0);
							}
						}
					}
				}
			}
		}
	}
	
}
