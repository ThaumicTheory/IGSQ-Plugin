package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import me.murrobby.igsq.spigot.Messaging;

public class GameTick_BlockHunt
{	
	int gameTickTask = -1;
	int minPlayers = Yaml.getFieldInt("minimumplayers", "blockhunt");
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
				if(Main_BlockHunt.taskID != taskID || (!Common_BlockHunt.blockhuntCheck()) || Game_BlockHunt.getGameInstances().length == 0) 
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
		for(Game_BlockHunt gameInstance : Game_BlockHunt.getGameInstances()) 
		{
			for(Player player : gameInstance.getPlayers()) 
			{
				inGame(player,gameInstance);
				inGameAndPreSeeker(player,gameInstance);
				lobby(player,gameInstance);
				preSeeker(player,gameInstance);
				
				if(!gameInstance.isDead(player)) 
				{
					int barrierKillValue = Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.barrier", "internal");
					if(barrierKillValue > Yaml.getFieldInt("outofboundstime", "blockhunt")) 
					{
						gameInstance.killPlayer(player);
					}
					else if(Common.getHighestBlock(player.getLocation(), player.getLocation().getY()-0.1,1) != null && Common.getHighestBlock(player.getLocation(), player.getLocation().getY()-0.1,1).getType() == Material.BARRIER) 
					{
						if(gameInstance.isHider(player) && gameInstance.isStage(Stage.PRE_SEEKER) || gameInstance.isStage(Stage.IN_GAME))
						{
							player.sendTitle(Messaging.chatFormatter("&#FF0000You will die if you remain in this area"),Messaging.chatFormatter("&#cc0000"+ (Yaml.getFieldInt("outofboundstime", "blockhunt") - (barrierKillValue))/20),0,5,10);
							Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.barrier", "internal", barrierKillValue+1);
						}
						else if(gameInstance.isStage(Stage.IN_LOBBY))
						{
							player.sendTitle(Messaging.chatFormatter("&#FF0000A Player has left the area of operation"),Messaging.chatFormatter("&#cc0000Putting you back into the playable area!"),10,20,10);
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,40,0,true,false));
							player.teleport(gameInstance.getMap().getLobbyLocation());
						}
					}
					else if(barrierKillValue > 0) 
					{
						Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.barrier", "internal", barrierKillValue-1);
					}
				}
				
			}
			
			if(gameInstance.isStage(Stage.IN_LOBBY) && gameInstance.getPlayerCount() >= minPlayers) gameInstance.decrementTimer();
			else if(gameInstance.isStage(Stage.IN_GAME) || gameInstance.isStage(Stage.PRE_SEEKER)) gameInstance.decrementTimer();
			if(gameInstance.getPlayerCount() == 0) 
			{
				gameInstance.delete();
			}
		}
	}
	private void inGame(Player player,Game_BlockHunt gameInstance) 
	{
		if(gameInstance.isStage(Stage.IN_GAME))
		{
			if(gameInstance.getTimer() <= 0) gameInstance.end(EndReason.TIME_UP); 
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFF"+ Common_Shared.getTimeFromTicks(gameInstance.getTimer()))));
			
		}
	} 
	private void inGameAndPreSeeker(Player player,Game_BlockHunt gameInstance) 
	{
		if(gameInstance.isStage(Stage.IN_GAME) || gameInstance.isStage(Stage.PRE_SEEKER)) 
		{
			//Cloak update & check hider is still there
			if(Common_BlockHunt.isCloaked(player))
			{
				if(player.getLocation().getBlockX() != Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.x", "internal") || player.getLocation().getBlockY() != Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.y", "internal") || player.getLocation().getBlockZ() != Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.z", "internal")) 
				{
					player.sendMessage(Messaging.chatFormatter("&#C8C8C8You are no longer hidden."));
					gameInstance.removeCloak(player);
					Common_BlockHunt.setCloakCooldown(player, Yaml.getFieldInt("cloakcooldown", "blockhunt"));
				}
				else 
				{
					for(Player selectedPlayer : gameInstance.getPlayers()) 
					{
						if(!selectedPlayer.getUniqueId().equals(player.getUniqueId())) 
						{
							selectedPlayer.sendBlockChange(player.getLocation().getBlock().getLocation(), Bukkit.createBlockData(Material.valueOf(Yaml.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
						}
					}
				}
			}
			
			//Player visibility and sound
			if(gameInstance.isPlayerVisible(player, Yaml.getFieldInt("visibilityrange", "blockhunt"))) gameInstance.showPlayer(player);
			else
			{
				gameInstance.hidePlayer(player);
				if(secondTick && gameInstance.isHider(player) && !Common_BlockHunt.isCloaked(player) && !player.isSneaking() && !gameInstance.isDead(player)) player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), Bukkit.createBlockData(Material.valueOf(Yaml.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
			}
			player.setSilent(gameInstance.isPlayerSilent(player));
			
			//Inventory management
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
			
		}
	}
	private void preSeeker(Player player,Game_BlockHunt gameInstance) 
	{
		if(gameInstance.isStage(Stage.PRE_SEEKER)) 
		{
			if(gameInstance.getTimer() <= 0) 
			{
				gameInstance.startSeek();
				String subTitle = "";
				if(gameInstance.isSeeker(player)) subTitle = "&#FFCCCCYou like seeking, right? Everybody likes seeking. Well, let's go get some.";
				else if (gameInstance.isHider(player)) subTitle = "&#CCCCFFAnd to your right, something huge hurtling towards you OH GOD RUN! THAT'S NOT SUPPOSED TO BE THERE.";
				else subTitle = "&#CCCCCCOh, that's funny, is it? Because we've been at this twelve hours and you haven't found them either, so I don't know why you're laughing.";
				
				
				player.sendTitle(Messaging.chatFormatter("&#FF0000Seekers have been released"),Messaging.chatFormatter(subTitle),20,120,10);
				
			}
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((Messaging.chatFormatter("&#FF0000"+ Common_Shared.getTimeFromTicks(gameInstance.getTimer())))));
				
		} 
	}
	private void lobby(Player player,Game_BlockHunt gameInstance) 
	{
		if (gameInstance.isStage(Stage.IN_LOBBY)) //Lobby
		{
			if(gameInstance.getPlayerCount() >= minPlayers) 
			{
				if(gameInstance.getTimer() <= 200) player.sendTitle(Messaging.chatFormatter("&#00FF00" +gameInstance.getTimer()/20),Messaging.chatFormatter("&#00FFFFSeconds Until The Game Starts!"),0,5,10);
				else player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((Messaging.chatFormatter("&#FFFF00"+ Common_Shared.getTimeFromTicks(gameInstance.getTimer())))));
			}
			else 
			{
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((Messaging.chatFormatter("&#FF0000" + (minPlayers - gameInstance.getPlayerCount()) + " &#00FFFFPlayers until the Countdown Starts!"))));
				gameInstance.setTimer(Yaml.getFieldInt("lobbytime", "blockhunt"));
			}
			
			if(gameInstance.getTimer() <= 0) 
			{
				gameInstance.start();
				if(gameInstance.isSeeker(player)) player.sendTitle(Messaging.chatFormatter("&#FF0000The Hiders are hiding."),Messaging.chatFormatter("&#cc0000From here we transport you to the new INFECTED area!"),20,120,10);
				else if (gameInstance.isHider(player)) player.sendTitle(Messaging.chatFormatter("&#CCCCFFThe Seekers are coming."),Messaging.chatFormatter("&#8888FFIt's probably nothing. Try hiding while I look for a way out."),20,120,10);
				else player.sendTitle(Messaging.chatFormatter("&#FFFFFFHider phase has begun."),Messaging.chatFormatter("&#CCCCCCWhile I do need you to be in the room so I can see them, I want to be clear. There is no reason whatsoever for you to look at them."),20,120,10);
			}
		}
	}
}
