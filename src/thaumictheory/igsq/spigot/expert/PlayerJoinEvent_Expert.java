package thaumictheory.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerJoinEvent_Expert implements Listener
{
	public PlayerJoinEvent_Expert()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerJoin_Expert(PlayerJoinEvent event) 
	{
		if(YamlWrapper.isExpert())
		{
			if(Bukkit.getOnlinePlayers().size() == 1)
			{
				Main_Expert.refreshExpert();
			}
		}
	}
}
