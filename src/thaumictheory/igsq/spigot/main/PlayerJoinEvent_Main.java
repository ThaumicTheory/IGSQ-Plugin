package thaumictheory.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Communication;
import thaumictheory.igsq.spigot.YamlPlayerWrapper;

public class PlayerJoinEvent_Main implements Listener
{
	public PlayerJoinEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin_Main(PlayerJoinEvent event) 
	{
    	Communication.setDefaultTagData(event.getPlayer());
		new YamlPlayerWrapper(event.getPlayer()).applyDefault();
	}
}
