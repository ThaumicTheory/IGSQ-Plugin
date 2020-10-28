package me.murrobby.igsq.spigot.commands;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

public class PlayerCompassTask_Command
{	
	
	int playerCompassTask = -1;
	final int taskID;
	private static Random random = new Random();
	
	public PlayerCompassTask_Command(int taskID) 
	{
		this.taskID = taskID;
		playerCompassQuery();
	}
	private void playerCompassQuery() 
	{
		playerCompassTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				Boolean expireTask = true;
				for(Player selectedPlayer : Bukkit.getServer().getOnlinePlayers()) if(Yaml.getFieldString(selectedPlayer.getUniqueId().toString() + ".playercompass.target", "internal") != null && !Yaml.getFieldString(selectedPlayer.getUniqueId().toString() + ".playercompass.target", "internal").equalsIgnoreCase("")) expireTask = false;
				playerCompass();
				if(Main_Command.taskID != taskID || expireTask) 
				{
					Common.spigot.scheduler.cancelTask(playerCompassTask);
					System.out.println("Task: \"Player Compass Command\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 0);
	}
	private void playerCompass() 
	{
		for(Player selectedPlayer : Bukkit.getServer().getOnlinePlayers()) 
		{
			if(Yaml.getFieldString(selectedPlayer.getUniqueId().toString() + ".playercompass.target", "internal") != null && !Yaml.getFieldString(selectedPlayer.getUniqueId().toString() + ".playercompass.target", "internal").equalsIgnoreCase("")) 
			{
				Player targetPlayer = Bukkit.getPlayer(UUID.fromString(Yaml.getFieldString(selectedPlayer.getUniqueId().toString() + ".playercompass.target", "internal")));
				int accuracy = Yaml.getFieldInt(selectedPlayer.getUniqueId().toString() + ".playercompass.accuracy", "internal");
				Location targetLocation = targetPlayer.getLocation();
				if(accuracy != 0) 
				{
					targetLocation.setX(targetLocation.getBlockX() + (random.nextInt(accuracy)* (random.nextBoolean() ? 1 : -1)));
					targetLocation.setY(targetLocation.getBlockY() + (random.nextInt(accuracy)* (random.nextBoolean() ? 1 : -1)));
					targetLocation.setZ(targetLocation.getBlockZ() + (random.nextInt(accuracy)* (random.nextBoolean() ? 1 : -1)));
				}
				selectedPlayer.setCompassTarget(targetLocation);
			}
		}
	}
}
