package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class GameTick_BlockHunt
{	
	Main_Spigot plugin;
	int gameTickTask = -1;
	Random random = new Random();
	Boolean secondTick = false;
	final int taskID;
	
	public GameTick_BlockHunt(Main_Spigot plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		gameTickQuery();
	}
	private void gameTickQuery() 
	{
		gameTickTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
    	{

			@Override
			public void run() 
			{
				gameTick() ;
				if(Main_BlockHunt.taskID != taskID || (!Common_BlockHunt.blockhuntCheck()) || Common_BlockHunt.stage < 0) 
				{
					plugin.scheduler.cancelTask(gameTickTask);
					System.out.println("Task: \"Game Tick BlockHunt\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 1);
	}
	private void gameTick() 
	{
		for(Player player : Common_BlockHunt.players) 
		{
			secondTick = !secondTick;
			if(Common_BlockHunt.isHider(player)) 
			{
				if(secondTick) player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), Bukkit.createBlockData(Material.valueOf(Common_Spigot.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
			}
		}
	}
}
