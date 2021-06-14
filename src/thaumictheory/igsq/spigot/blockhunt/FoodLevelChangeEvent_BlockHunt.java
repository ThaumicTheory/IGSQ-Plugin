package thaumictheory.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlWrapper;

public class FoodLevelChangeEvent_BlockHunt implements Listener
{
	public FoodLevelChangeEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void FoodLevelChange_BlockHunt(org.bukkit.event.entity.FoodLevelChangeEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(YamlWrapper.isBlockHunt()) 
			{
				if(event.getEntity() instanceof Player) 
				{
					Player player = (Player) event.getEntity();
					if(Game_BlockHunt.getPlayersGame(player) != null) 
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
}
