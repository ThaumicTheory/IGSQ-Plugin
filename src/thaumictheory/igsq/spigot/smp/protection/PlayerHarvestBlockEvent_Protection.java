package thaumictheory.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerHarvestBlockEvent_Protection implements Listener
{
	public PlayerHarvestBlockEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerHarvestBlock_Protection(org.bukkit.event.player.PlayerHarvestBlockEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			if(Common_Protection.isProtected(event.getPlayer(),event.getHarvestedBlock().getLocation())) event.setCancelled(true);
		}
	}
	
}
