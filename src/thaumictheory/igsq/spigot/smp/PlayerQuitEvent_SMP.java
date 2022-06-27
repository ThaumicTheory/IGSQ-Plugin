package thaumictheory.igsq.spigot.smp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerQuitEvent_SMP implements Listener
{
	public PlayerQuitEvent_SMP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerQuit_SMP(org.bukkit.event.player.PlayerQuitEvent event) 
	{
		if(YamlWrapper.isSMP())
		{
			if(UI_SMP.getUIFromPlayer(event.getPlayer()) != null) UI_SMP.getUIFromPlayer(event.getPlayer()).delete();
		}
	}
	
}
