package thaumictheory.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayerMoveEvent_BlockHunt implements Listener
{
	public PlayerMoveEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerMove_BlockHunt(org.bukkit.event.player.PlayerMoveEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(YamlWrapper.isBlockHunt()) 
			{
				Game_BlockHunt playersGame = Game_BlockHunt.getPlayersGame(event.getPlayer());
				if(playersGame == null && event.getTo().getBlock().getType().equals(Material.NETHER_PORTAL) && !Common_BlockHunt.isPlayerInGui(event.getPlayer())) 
				{
					event.getPlayer().setVelocity(event.getTo().toVector().subtract(event.getFrom().toVector()).multiply(-5));
					Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
			    	{

						@Override
						public void run() 
						{
							Common_BlockHunt.updateGui(event.getPlayer());
						}
			    	},5);
				}
			}
		}
	}
}
