package thaumictheory.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.YamlWrapper;


public class SlimeSplitEvent_Expert implements Listener
{
	public SlimeSplitEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void SlimeSplit_Expert(org.bukkit.event.entity.SlimeSplitEvent event) 
	{
		if(YamlWrapper.isExpert() && (!event.isCancelled())) 
		{
			if(event.getEntity().getType() == EntityType.SLIME) 
			{
				if(event.getEntity().getCustomName() != null && (event.getEntity().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert King Slime")) || event.getEntity().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert Warrior Slime")))) 
				{
					event.setCancelled(true);
				}
				if(event.getEntity().getCustomName() != null) 
				{
					event.getEntity().setCustomName(null);
				}
			}
			else if(event.getEntity().getType() == EntityType.MAGMA_CUBE) 
			{
				if(event.getEntity().getCustomName() != null && (event.getEntity().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert King Magma Slime")) || event.getEntity().getCustomName().equalsIgnoreCase(Messaging.chatFormatter("&#84FF00Expert Warrior Magma Slime")))) 
				{
					event.setCancelled(true);
				}
				if(event.getEntity().getCustomName() != null) 
				{
					event.getEntity().setCustomName(null);
				}
			}
		}
	}
}
