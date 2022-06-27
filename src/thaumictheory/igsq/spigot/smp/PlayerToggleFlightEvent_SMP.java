package thaumictheory.igsq.spigot.smp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerToggleFlightEvent_SMP implements Listener
{
	public PlayerToggleFlightEvent_SMP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerToggleFlight_SMP(org.bukkit.event.player.PlayerToggleFlightEvent event) 
	{
		if(YamlWrapper.isSMP() && !event.isCancelled())
		{
			Player_SMP player = Player_SMP.getSMPPlayer(event.getPlayer());
			player.setWantFly(event.isFlying());
		}
	}
	
}
