package me.murrobby.igsq.spigot.expert;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import java.util.Random;



import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;
import net.md_5.bungee.event.EventHandler;

public class Thunder_Expert {
	
	Random random = new Random();
	private int thunderTask = -1;
	private int randomX;
	private int randomZ;
	private Main_Spigot plugin;
	EntityType entitytype;
	
	@EventHandler
	public void ThunderExpert(World world, org.bukkit.event.weather.ThunderChangeEvent event) {
		if(Common_Spigot.getFieldBool(".lightning", "internal"))
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
							if(random.nextInt(3) == 1)
							{
								randomX = random.nextInt(16);
								randomZ = random.nextInt(16);
								Location location = selectedPlayer.getLocation();
								location.add(randomX, 0 , randomZ);
								
								entitytype = EntityType.CREEPER;
								randomX = random.nextInt(16);
								randomZ = random.nextInt(16);
								location.add(randomX, 100 , randomZ);
								plugin.getServer().getPlayer(selectedPlayer.getUniqueId()).getWorld().spawnEntity(location, entitytype);
								
								
								try 
								{
									Block target = Common_Spigot.GetHighestBlock(location,255);
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
			Common_Spigot.internalData.set(world.getUID() + ".thunder",false);
			plugin.scheduler.cancelTask(thunderTask);
		}
		
	}
}
