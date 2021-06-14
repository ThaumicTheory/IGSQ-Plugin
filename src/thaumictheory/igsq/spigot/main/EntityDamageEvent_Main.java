package thaumictheory.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlPlayerWrapper;

public class EntityDamageEvent_Main implements Listener
{
	public EntityDamageEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void EntityDamage_BlockHunt(org.bukkit.event.entity.EntityDamageEvent event) 
	{
		if(!event.isCancelled())
		{
			if(event.getEntityType() == EntityType.PLAYER) 
			{
				Player player = (Player) event.getEntity();
				YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
				yaml.setLastDamage(player.getTicksLived());
			}
		}
	}
}
