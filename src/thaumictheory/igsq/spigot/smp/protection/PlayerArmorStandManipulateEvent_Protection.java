package thaumictheory.igsq.spigot.smp.protection;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerArmorStandManipulateEvent_Protection implements Listener
{
	public PlayerArmorStandManipulateEvent_Protection()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerArmorStandManipulate_Protection(org.bukkit.event.player.PlayerArmorStandManipulateEvent event) 
	{
		if(!event.isCancelled() && YamlWrapper.isSMP()) 
		{
			if(Common_Protection.isProtected(event.getPlayer(),event.getRightClicked().getLocation())) event.setCancelled(true);
		}
	}
	
}
