package thaumictheory.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class SpongeAbsorbEvent_Protection implements Listener
{
	public SpongeAbsorbEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void SpongeAbsorb_Protection(org.bukkit.event.block.SpongeAbsorbEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			for(BlockState block : event.getBlocks()) if(Common_Protection.isProtected(event.getBlock().getLocation(), block.getLocation(),true))
			{
				event.setCancelled(true);
				break;
			}
		}
	}
	
}
