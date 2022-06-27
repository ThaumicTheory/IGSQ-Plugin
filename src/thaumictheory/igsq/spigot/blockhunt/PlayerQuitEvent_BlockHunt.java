package thaumictheory.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerQuitEvent_BlockHunt implements Listener
{
	public PlayerQuitEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerQuit_BlockHunt(org.bukkit.event.player.PlayerQuitEvent event) 
	{
		if(YamlWrapper.isBlockHunt()) 
		{
			Player_BlockHunt player = Player_BlockHunt.getPlayer(event.getPlayer());
			if(player != null) 
			{
				player.delete();
			}
		}
	}
	
}
