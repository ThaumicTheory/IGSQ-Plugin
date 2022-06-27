package thaumictheory.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class BlockBreakEvent_Protection implements Listener
{
	public BlockBreakEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void BlockBreak_Protection(org.bukkit.event.block.BlockBreakEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			if(Common_Protection.isProtected(event.getPlayer(),event.getBlock().getLocation())) event.setCancelled(true);
		}
	}
	
}
