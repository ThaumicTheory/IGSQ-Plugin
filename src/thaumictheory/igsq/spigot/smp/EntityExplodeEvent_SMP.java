package thaumictheory.igsq.spigot.smp;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import thaumictheory.igsq.spigot.BlockCluster;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlWrapper;


public class EntityExplodeEvent_SMP implements Listener
{
	public EntityExplodeEvent_SMP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityExplode_SMP(org.bukkit.event.entity.EntityExplodeEvent event) 
	{
		if(YamlWrapper.isSMP() && (!event.isCancelled()))
		{
			event.setCancelled(true);
			ArrayList<Block> clusterBlocks = new ArrayList<Block>();
			for(Block block : event.blockList()) 
			{
				if(block.getType() == Material.TNT) 
				{
					block.setType(Material.AIR);
					Location entityLocation = block.getLocation();
					entityLocation.setX(entityLocation.getX()+0.5);
					entityLocation.setZ(entityLocation.getZ()+0.5);
					block.getLocation().getWorld().spawnEntity(entityLocation, EntityType.PRIMED_TNT);
				}
				else if(!block.getType().isInteractable() && !BlockCluster.isInACluster(block.getLocation())) 
				{
					clusterBlocks.add(block);
				}
			}
			for(Player player : Bukkit.getOnlinePlayers()) 
			{
				player.playSound(event.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 0.8f);
				player.spawnParticle(Particle.EXPLOSION_HUGE, event.getLocation(), 3);
			}
			for(Entity entity : event.getLocation().getWorld().getNearbyEntities(event.getLocation(), 10, 10, 10))
			{
				if(entity.getType() == EntityType.PRIMED_TNT || entity.getType() == EntityType.PLAYER) 
				{
					Vector direction = entity.getLocation().subtract(event.getLocation()).toVector().normalize();
					double force = (1/event.getLocation().distance(entity.getLocation()));
					entity.setVelocity(entity.getVelocity().add(direction.multiply(force)));
				}
			}
			if(event.blockList().size() > 0) new BlockCluster(clusterBlocks).setInitially(Material.AIR);
			
		}
	}	
}
