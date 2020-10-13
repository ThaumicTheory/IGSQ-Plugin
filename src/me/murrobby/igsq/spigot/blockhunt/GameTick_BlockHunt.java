package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Configuration;
import me.murrobby.igsq.spigot.Messaging;

public class GameTick_BlockHunt
{	
	int gameTickTask = -1;
	Random random = new Random();
	Boolean secondTick = false;
	final int taskID;
	
	public GameTick_BlockHunt(int taskID) 
	{
		this.taskID = taskID;
		gameTickQuery();
	}
	private void gameTickQuery() 
	{
		gameTickTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				gameTick() ;
				if(Main_BlockHunt.taskID != taskID || (!Common_BlockHunt.blockhuntCheck()) || Common_BlockHunt.stage < 0) 
				{
					Common.spigot.scheduler.cancelTask(gameTickTask);
					System.out.println("Task: \"Game Tick BlockHunt\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 1);
	}
	private void gameTick() 
	{
		secondTick = !secondTick;
		for(Player player : Common_BlockHunt.players) 
		{
			if(Common_BlockHunt.stage == 1) 
			{
				if(Common_BlockHunt.isHider(player)) 
				{
					if(Common_BlockHunt.isCloaked(player)) 
					{
						if(player.getLocation().getBlockX() != Configuration.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.x", "internal") || player.getLocation().getBlockY() != Configuration.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.y", "internal") || player.getLocation().getBlockZ() != Configuration.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.z", "internal")) 
						{
							player.sendMessage(Messaging.chatFormatter("&#C8C8C8You are no longer hidden."));
							Common_BlockHunt.removeCloak(player);
							Common_BlockHunt.setCloakCooldown(player, Configuration.getFieldInt("cloakcooldown", "blockhunt"));
						}
						else 
						{
							for(Player selectedPlayer : Common_BlockHunt.players) 
							{
								if(!selectedPlayer.getUniqueId().equals(player.getUniqueId())) 
								{
									selectedPlayer.sendBlockChange(player.getLocation().getBlock().getLocation(), Bukkit.createBlockData(Material.valueOf(Configuration.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
								}
							}
						}
					}
				}
				if(Common_BlockHunt.isPlayerVisible(player, Configuration.getFieldInt("visibilityrange", "blockhunt"))) Common_BlockHunt.showPlayer(player);
				else
				{
					Common_BlockHunt.hidePlayer(player);
					if(secondTick && Common_BlockHunt.isHider(player) && !Common_BlockHunt.isCloaked(player)) player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), Bukkit.createBlockData(Material.valueOf(Configuration.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
				}
				player.setSilent(Common_BlockHunt.isPlayerSilent(player));
				if(!secondTick) 
				{
					int selectedItemAfterAssist = Common_BlockHunt.inventoryAssistTick(player);
					if(selectedItemAfterAssist != -1) player.getInventory().setHeldItemSlot(selectedItemAfterAssist);
				}
			}
			
			//Cooldowns
			if(Common_BlockHunt.getBlockPickerCooldown(player)> 0) 
			{
				if(Common_BlockHunt.getBlockPickerCooldown(player)%20 == 0) 
				{
					Common_BlockHunt.updateBlockPickerItem(player,false);
				}
				Common_BlockHunt.setBlockPickerCooldown(player, Common_BlockHunt.getBlockPickerCooldown(player)-1);
				if(Common_BlockHunt.getBlockPickerCooldown(player) == 0) 
				{
					Common_BlockHunt.updateBlockPickerItem(player,false);
				}
			}
			
			if(Common_BlockHunt.getCloakCooldown(player)> 0) 
			{
				if(Common_BlockHunt.getCloakCooldown(player)%20 == 0) 
				{
					Common_BlockHunt.updateCloakItem(player);
				}
				Common_BlockHunt.setCloakCooldown(player, Common_BlockHunt.getCloakCooldown(player)-1);
				if(Common_BlockHunt.getCloakCooldown(player) == 0) 
				{
					Common_BlockHunt.updateCloakItem(player);
				}
			}
		}
	}
}
