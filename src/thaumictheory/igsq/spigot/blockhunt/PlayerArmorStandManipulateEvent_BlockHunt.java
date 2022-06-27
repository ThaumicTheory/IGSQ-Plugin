package thaumictheory.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerArmorStandManipulateEvent_BlockHunt implements Listener
{
	public PlayerArmorStandManipulateEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerArmorStandManipulate_BlockHunt(org.bukkit.event.player.PlayerArmorStandManipulateEvent event) 
	{
		if(YamlWrapper.isBlockHunt() && (!event.isCancelled()))
		{
			if(Game_BlockHunt.getPlayersGame(event.getPlayer()) != null) 
			{
				event.setCancelled(true);
			}
		}
	}
	
}
