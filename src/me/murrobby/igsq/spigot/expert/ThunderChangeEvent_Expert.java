package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.Random;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class ThunderChangeEvent_Expert implements Listener
{
	private Main_Spigot plugin;
	private int thunderTask = -1;
	Random random = new Random();
	private int randomX;
	private int randomZ;
	
	
	public ThunderChangeEvent_Expert(Main_Spigot plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	
	@EventHandler
	public void ThunderChange_Expert(org.bukkit.event.weather.ThunderChangeEvent event)
	{
		if(Common_Spigot.ExpertCheck() && (!event.isCancelled()))
		{
			if(event.toThunderState())
			{
				thunderTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
		    	{

					@Override
					public void run() 
					{
						for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
						{
							if(selectedPlayer.getWorld() == event.getWorld())
							{
								if(random.nextInt(20) == 1)
								{
									randomX = random.nextInt(16);
									randomZ = random.nextInt(16);
									Location location = selectedPlayer.getLocation();
									location.add(randomX, 0 , randomZ);
									Entity strike = selectedPlayer.getWorld().strikeLightning(location);
								}
							}
						}
					} 		
		    	}, 0, 20);
			}
			else if(thunderTask != -1){
				plugin.scheduler.cancelTask(thunderTask);
			}
		}
		else if(thunderTask != -1) {
			plugin.scheduler.cancelTask(thunderTask);
		}
		
	}
	
}
