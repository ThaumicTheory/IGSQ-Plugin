package thaumictheory.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class BlockDamageEvent_Protection implements Listener
{
	public BlockDamageEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void BlockDamage_Protection(org.bukkit.event.block.BlockDamageEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			if(Common_Protection.isProtected(event.getPlayer(),event.getBlock().getLocation())) event.setCancelled(true);
		}
	}
	
}
