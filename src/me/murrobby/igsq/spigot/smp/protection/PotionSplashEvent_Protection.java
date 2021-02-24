package me.murrobby.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.smp.Team_SMP;

public class PotionSplashEvent_Protection implements Listener
{
	public PotionSplashEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PotionSplash_Protection(org.bukkit.event.entity.PotionSplashEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			boolean badPotion = Common_Protection.isBadPotion(event.getPotion().getEffects());
			if(event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player)
			{
				Player shooter = (Player) event.getEntity().getShooter();
				Team_SMP shootersTeam = Team_SMP.getPlayersTeam(shooter);
				for(LivingEntity entity : event.getAffectedEntities())
				{
					if(Common_Protection.isProtected(shooter,entity.getLocation())) 
					{
						if(entity instanceof Player) 
						{
							//Stop bad potions from hurting allies or faction members
							Player affectedPlayer = (Player) entity;
							Team_SMP affectedPlayersTeam = Team_SMP.getPlayersTeam(affectedPlayer);
							if(badPotion && shootersTeam != null && (shootersTeam.equals(affectedPlayersTeam) || shootersTeam.isAlly(affectedPlayersTeam))) 
							{
								event.setIntensity(affectedPlayer, 0);
							}
						}
						else if(entity instanceof Tameable) 
						{
							//Allow potions to work on your pets no matter where you are
							Tameable tameableEntity = (Tameable) entity;
							if(tameableEntity.getOwner() == null || !tameableEntity.getOwner().getUniqueId().equals(shooter.getUniqueId()))
							{
								event.setIntensity(entity, 0);
							}
						}
						else event.setIntensity(entity, 0);
						
					}
				}
			}
			
		}
	}
	
}
