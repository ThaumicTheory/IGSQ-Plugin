package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;
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
	private EntityType entitytype;
	private Player selectedPlayer;
	private World world;
	
	
	
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
				try {
					Common_Spigot.internalData.set(".event.lightning",true);
					Common_Spigot.internalData.save(Common_Spigot.internalDataFile);
					ExtraLightning(world, event);
					Creepers(selectedPlayer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else 
			{
				try {
					Common_Spigot.internalData.set(".event.lightning",false);
					Common_Spigot.internalData.save(Common_Spigot.internalDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			}
		}
	
	public void ExtraLightning(World world, org.bukkit.event.weather.ThunderChangeEvent event) {
		if(Common_Spigot.getFieldBool(".event.lightning", "internal")){
			thunderTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
			{
		
				@Override
				public void run() 
				{
					for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
					{
						if(selectedPlayer.getWorld() == event.getWorld())
						{
							if(random.nextInt(3) == 1)
							{
								randomX = random.nextInt(16);
								randomZ = random.nextInt(16);
								Location location = selectedPlayer.getLocation();
								location.add(randomX, 0 , randomZ);
								try 
								{
									Block target = Common_Spigot.GetHighestBlock(location);
									location = target.getLocation().add(0,1,0);
									event.getWorld().strikeLightning(location);
								}
								catch(NullPointerException e) 
								{
									//Failed To Find Spot For Lightning
								}
							}
						}
					}
				} 		
			}, 0, 20);
		}
		else {
			Common_Spigot.internalData.set(world.getUID() + ".event.lightning",false);
		}
		
	}
	
	private void Creepers(Player selectedPlayer) {
		if(Common_Spigot.getFieldBool(".event.lightning", "internal"))
		{
			entitytype = EntityType.CREEPER;
			randomX = random.nextInt(16);
			randomZ = random.nextInt(16);
			Location location = selectedPlayer.getLocation();
			location.add(randomX, 100 , randomZ);
			plugin.getServer().getPlayer(selectedPlayer.getUniqueId()).getWorld().spawnEntity(location, entitytype);
		}
		else {
			Common_Spigot.internalData.set(".event.lightning",false);
		}
	}
	
}

	


