package thaumictheory.igsq.spigot.smp.aspect;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlWrapper;
import thaumictheory.igsq.spigot.smp.Player_SMP;

public class EntityAirChangeEvent_Aspect implements Listener
{
	public EntityAirChangeEvent_Aspect()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityAirChange_Aspect(org.bukkit.event.entity.EntityAirChangeEvent event) 
	{
		if(YamlWrapper.isSMP() && (!event.isCancelled())) 
		{
			if(event.getEntityType() == EntityType.PLAYER) 
			{
				Player_SMP player = Player_SMP.getSMPPlayer((OfflinePlayer) event.getEntity());
				if(player.getAspect().getID().equals(Enum_Aspect.WATER)) 
				{
					event.setAmount(273);
				}
				
			}
		}
	}
	
}
