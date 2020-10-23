package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
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
				if(Main_BlockHunt.taskID != taskID || (!Common_BlockHunt.blockhuntCheck()) || Common_BlockHunt.stage.equals(Stage.NO_GAME)) 
				{
					Common.spigot.scheduler.cancelTask(gameTickTask);
					System.out.println("Task: \"Game Tick BlockHunt\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 1);
	}
	private void gameTick() 
	{
		int minPlayers = Yaml.getFieldInt("minimumplayers", "blockhunt");
		secondTick = !secondTick;
		for(Player player : Common_BlockHunt.players) 
		{
			if(Common_BlockHunt.stage.equals(Stage.IN_GAME)) 
			{
				if(Common_BlockHunt.isHider(player)) 
				{
					if(Common_BlockHunt.isCloaked(player)) 
					{
						if(player.getLocation().getBlockX() != Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.x", "internal") || player.getLocation().getBlockY() != Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.y", "internal") || player.getLocation().getBlockZ() != Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.z", "internal")) 
						{
							player.sendMessage(Messaging.chatFormatter("&#C8C8C8You are no longer hidden."));
							Common_BlockHunt.removeCloak(player);
							Common_BlockHunt.setCloakCooldown(player, Yaml.getFieldInt("cloakcooldown", "blockhunt"));
						}
						else 
						{
							for(Player selectedPlayer : Common_BlockHunt.players) 
							{
								if(!selectedPlayer.getUniqueId().equals(player.getUniqueId())) 
								{
									selectedPlayer.sendBlockChange(player.getLocation().getBlock().getLocation(), Bukkit.createBlockData(Material.valueOf(Yaml.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
								}
							}
						}
					}
				}
				if(Common_BlockHunt.isPlayerVisible(player, Yaml.getFieldInt("visibilityrange", "blockhunt"))) Common_BlockHunt.showPlayer(player);
				else
				{
					Common_BlockHunt.hidePlayer(player);
					if(secondTick && Common_BlockHunt.isHider(player) && !Common_BlockHunt.isCloaked(player)) player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), Bukkit.createBlockData(Material.valueOf(Yaml.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
				}
				player.setSilent(Common_BlockHunt.isPlayerSilent(player));
				if(!secondTick) 
				{
					int selectedItemAfterAssist = Common_BlockHunt.inventoryAssistTick(player);
					if(selectedItemAfterAssist != -1) player.getInventory().setHeldItemSlot(selectedItemAfterAssist);
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
				
				if(Common_BlockHunt.timer <= 0) Common_BlockHunt.end(EndReason.TIME_UP); 
				
			}
			else if (Common_BlockHunt.stage.equals(Stage.IN_LOBBY)) //Lobby
			{
				if(Common_BlockHunt.playerCount >= minPlayers) 
				{
					player.sendTitle(Messaging.chatFormatter("&#00FF00" +Math.round((double)Common_BlockHunt.timer*50)/1000),Messaging.chatFormatter("&#00FFFFSeconds Until The Game Starts!"),0,5,10);
				}
				else 
				{
					player.sendTitle(Messaging.chatFormatter("&#FF0000"+ (minPlayers - Common_BlockHunt.playerCount)),Messaging.chatFormatter("&#00FFFFPlayer(s) Until The Countdown Starts!"),0,5,10);
					Common_BlockHunt.timer = Yaml.getFieldInt("lobbytime", "blockhunt");
				}
				
				if(Common_BlockHunt.timer <= 0) Common_BlockHunt.start(); 
			}
			
		}
		if(Common_BlockHunt.stage.equals(Stage.IN_LOBBY) && Common_BlockHunt.playerCount >= minPlayers) Common_BlockHunt.timer--;
		else if(Common_BlockHunt.stage.equals(Stage.IN_GAME)) Common_BlockHunt.timer--;
	}
}
