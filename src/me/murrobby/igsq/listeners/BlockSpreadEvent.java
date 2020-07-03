package me.murrobby.igsq.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;
import org.bukkit.Material;
import java.util.Random;

@SuppressWarnings("unused")
public class BlockSpreadEvent implements Listener
{
	private Main plugin;
	Random random = new Random();
	public BlockSpreadEvent(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void BlockSpread(org.bukkit.event.block.BlockSpreadEvent event) 
	{
		if(Common.getFieldBool("GAMEPLAY.expert", "config")) 
		{
			if(event.getSource().getBlockData().getMaterial() == Material.FIRE) 
			{
				if(random.nextInt(25) == 1) 
				{
					event.getSource().setType(Material.SOUL_FIRE);
				}
				else if(random.nextInt(3) == 1) 
				{
					event.getSource().setType(Material.FIRE);
				}
			}
		}
	}
	
}
