package me.murrobby.igsq.spigot.blockhunt;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import me.murrobby.igsq.spigot.Messaging;

public class GameTick_BlockHunt
{	
	int gameTickTask = -1;
	int minPlayers = YamlWrapper.getBlockHuntMinimumPlayers();
	Random random = new Random();
	int second = 0;
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
				if(Main_BlockHunt.taskID != taskID || (!YamlWrapper.isBlockHunt()) || Game_BlockHunt.getGameInstances().length == 0) 
				{
					Common.spigot.scheduler.cancelTask(gameTickTask);
					System.out.println("Task: \"Game Tick BlockHunt\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 1);
	}
	private void gameTick() 
	{
		for(Game_BlockHunt gameInstance : Game_BlockHunt.getGameInstances()) 
		{
			//Bukkit.broadcastMessage(gameInstance.getName());
			for(Player_BlockHunt player : gameInstance.getPlayers()) 
			{
				//Bukkit.broadcastMessage(gameInstance.getName() + " : " + player.getPlayer().getName());
				inGame(player,gameInstance);
				inGameAndPreSeeker(player,gameInstance);
				preSeeker(player,gameInstance);
				lobby(player,gameInstance);
				
				if(!player.isDead()) 
				{
					if(player.getOutOfBoundsTime() > YamlWrapper.getBlockHuntOutOfBoundsTime() || (player.isHider() && player.getOutOfBoundsTime() > YamlWrapper.getBlockHuntOutOfBoundsTime()/2)) 
					{
						player.kill();
					}
					else if(Common.getHighestBlock(player.getPlayer().getLocation(), player.getPlayer().getLocation().getY(),3) != null && Common.getHighestBlock(player.getPlayer().getLocation(), player.getPlayer().getLocation().getY(),3).getType() == Material.BARRIER) 
					{
						if(player.isHider() && gameInstance.isStage(Stage.PRE_SEEKER) || gameInstance.isStage(Stage.IN_GAME))
						{
							if(player.isHider()) player.getPlayer().sendTitle(Messaging.chatFormatter("&#FF0000You will die if you remain in this area"),Messaging.chatFormatter("&#cc0000"+ (YamlWrapper.getBlockHuntOutOfBoundsTime()/2 - (player.getOutOfBoundsTime()))/20),0,5,10);
							else player.getPlayer().sendTitle(Messaging.chatFormatter("&#FF0000You will die if you remain in this area"),Messaging.chatFormatter("&#cc0000"+ (YamlWrapper.getBlockHuntOutOfBoundsTime() - (player.getOutOfBoundsTime()))/20),0,5,10);
							player.setOutOfBoundsTime(player.getOutOfBoundsTime()+1);
						}
						else if(gameInstance.isStage(Stage.IN_LOBBY))
						{
							player.getPlayer().sendTitle(Messaging.chatFormatter("&#FF0000A Player has left the area of operation"),Messaging.chatFormatter("&#cc0000Putting you back into the playable area!"),10,20,10);
							player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,40,0,true,false));
							player.getPlayer().teleport(gameInstance.getMap().getLobbyLocation());
						}
					}
					else if(player.getOutOfBoundsTime() > 0) player.setOutOfBoundsTime(0);
				}
				
			}
			if(gameInstance.isStage(Stage.IN_LOBBY) && (gameInstance.getPlayerCount() >= minPlayers || gameInstance.isTestMode())) gameInstance.decrementTimer();
			else if(gameInstance.isStage(Stage.IN_GAME) || gameInstance.isStage(Stage.PRE_SEEKER)) gameInstance.decrementTimer();
			if(gameInstance.getPlayerCount() == 0) 
			{
				gameInstance.delete();
			}
			if(!gameInstance.isTestMode() && (gameInstance.isStage(Stage.IN_GAME) || gameInstance.isStage(Stage.PRE_SEEKER))) 
			{
				if(gameInstance.getAliveHiderCount() == 0) gameInstance.end(EndReason.HIDERS_DEAD);
				else if(gameInstance.getAliveSeekerCount() == 0) gameInstance.end(EndReason.SEEKERS_DEAD);
				else if(gameInstance.getAlivePlayerCount() == 0) gameInstance.end(EndReason.NOT_SPECIFIED);
			}
			
		}
		if(second == 20) 
		{
			Common_BlockHunt.updateGui();
			second = 0;
		}
		else second++;
	}
	private void inGame(Player_BlockHunt player,Game_BlockHunt gameInstance) 
	{
		if(gameInstance.isStage(Stage.IN_GAME))
		{
			if(gameInstance.getTimer() <= 0) gameInstance.end(EndReason.TIME_UP); 
			player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFF"+ Common_Shared.getTimeFromTicks(gameInstance.getTimer()))));
			
		}
	} 
	private void inGameAndPreSeeker(Player_BlockHunt player,Game_BlockHunt gameInstance) 
	{
		if(gameInstance.isStage(Stage.IN_GAME) || gameInstance.isStage(Stage.PRE_SEEKER)) 
		{
			//Cloak update & check hider is still there
			if(player.isHider() && player.toHider().getGeneric().isCloaked())
			{
				if(player.getPlayer().getLocation().getBlockX() != player.toHider().getGeneric().getCloakLocation().getBlockX() || player.getPlayer().getLocation().getBlockY() != player.toHider().getGeneric().getCloakLocation().getBlockY() || player.getPlayer().getLocation().getBlockZ() != player.toHider().getGeneric().getCloakLocation().getBlockZ()) 
				{
					player.getPlayer().sendMessage(Messaging.chatFormatter("&#C8C8C8You are no longer hidden."));
					player.toHider().setCloak(false);
					player.toHider().getGeneric().setCloakCooldown(YamlWrapper.getBlockHuntCloakCooldown());
				}
				else 
				{
					for(Player_BlockHunt selectedPlayer : gameInstance.getPlayers()) 
					{
						if(!selectedPlayer.getPlayer().getUniqueId().equals(player.getPlayer().getUniqueId())) 
						{
							selectedPlayer.getPlayer().sendBlockChange(player.getPlayer().getLocation().getBlock().getLocation(), Bukkit.createBlockData(player.toHider().getGeneric().getBlock()));
						}
					}
				}
			}
			
			//Player visibility and sound
			if(player.isPlayerVisible(YamlWrapper.getBlockHuntVisibilityRange())) gameInstance.showPlayer(player.getPlayer());
			else
			{
				gameInstance.hidePlayer(player.getPlayer());
				if(player.isHider() && !player.isDead()) 
				{
					//player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), Bukkit.createBlockData(Material.valueOf(Yaml.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
					int entityID = (int) (Math.random() * Integer.MAX_VALUE);
					try 
					{
						Player_BlockHunt[] players = gameInstance.getPlayers();
						if(gameInstance.isStage(Stage.PRE_SEEKER)) players = gameInstance.getHiders();
						for(Player_BlockHunt selectedPlayer : players) 
						{
							if(selectedPlayer.getPlayer().getUniqueId().equals(player.getPlayer().getUniqueId()) && player.toHider().getGeneric().isCloaked()) ProtocolLibrary.getProtocolManager().sendServerPacket(player.getPlayer(), player.toHider().getGeneric().dynamicCloakPacket(entityID, true));
							else if((player.toHider().getGeneric().getSeeSelfTime() > 0 ||!selectedPlayer.getPlayer().getUniqueId().equals(player.getPlayer().getUniqueId())) && !player.toHider().getGeneric().isCloaked() ) ProtocolLibrary.getProtocolManager().sendServerPacket(selectedPlayer.getPlayer(), player.toHider().getGeneric().dynamicCloakPacket(entityID, false));
						}
					}
					catch (InvocationTargetException e) 
					{
						
						e.printStackTrace();
					}
					Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
			    	{

						@Override
						public void run() 
						{
							PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
							try 
							{
								Player_BlockHunt[] players = gameInstance.getPlayers();
								packet.getIntegerArrays().write(0, new int[] {entityID});
								if(gameInstance.isStage(Stage.PRE_SEEKER)) players = gameInstance.getHiders();
								for(Player_BlockHunt selectedPlayer : players) 
								{
									ProtocolLibrary.getProtocolManager().sendServerPacket(selectedPlayer.getPlayer(), packet);
								}
							}
							catch (InvocationTargetException e) 
							{
								
								e.printStackTrace();
							}
						}
			    	},1);
				}
			}
			player.getPlayer().setSilent(player.isPlayerSilent());
			
			//Cooldowns
			if(player.isHider()) 
			{
				Hider_BlockHunt hider = player.toHider();
				if(hider.getGeneric().getBlockPickerCooldown()> 0) 
				{
					if(hider.getGeneric().getBlockPickerCooldown()%20 == 0) 
					{
						hider.getGeneric().updateBlockPickerItem();
					}
					hider.getGeneric().setBlockPickerCooldown(hider.getGeneric().getBlockPickerCooldown()-1);
					if(hider.getGeneric().getBlockPickerCooldown() == 0) 
					{
						hider.getGeneric().updateBlockPickerItem();
					}
				}
				
				if(hider.getGeneric().getCloakCooldown()> 0) 
				{
					if(hider.getGeneric().getCloakCooldown()%20 == 0) 
					{
						hider.getGeneric().updateCloakItem();
					}
					hider.getGeneric().setCloakCooldown(hider.getGeneric().getCloakCooldown()-1);
					if(hider.getGeneric().getCloakCooldown() == 0) 
					{
						hider.getGeneric().updateCloakItem();
					}
				}
				
				if(hider.getGeneric().getSeeSelfTime() > 0) 
				{
					hider.getGeneric().setSeeSelfTime(hider.getGeneric().getSeeSelfTime()-1);
				}
			}
		}
	}
	private void preSeeker(Player_BlockHunt player,Game_BlockHunt gameInstance) 
	{
		if(gameInstance.isStage(Stage.PRE_SEEKER)) 
		{
			if(gameInstance.getTimer() <= 0) 
			{
				gameInstance.startSeek();
				String subTitle = "";
				if(player.isSeeker()) subTitle = "&#FFCCCCYou like seeking, right? Everybody likes seeking. Well, let's go get some.";
				else if (player.isHider()) subTitle = "&#CCCCFFAnd to your right, something huge hurtling towards you OH GOD RUN! THAT'S NOT SUPPOSED TO BE THERE.";
				else subTitle = "&#CCCCCCOh, that's funny, is it? Because we've been at this twelve hours and you haven't found them either, so I don't know why you're laughing.";
				
				
				player.getPlayer().sendTitle(Messaging.chatFormatter("&#FF0000Seekers have been released"),Messaging.chatFormatter(subTitle),20,120,10);
				
			}
			player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((Messaging.chatFormatter("&#FF0000"+ Common_Shared.getTimeFromTicks(gameInstance.getTimer())))));
				
		} 
	}
	private void lobby(Player_BlockHunt player,Game_BlockHunt gameInstance) 
	{
		if (gameInstance.isStage(Stage.IN_LOBBY)) //Lobby
		{
			if(gameInstance.getPlayerCount() >= minPlayers || gameInstance.isTestMode()) 
			{
				if(gameInstance.getTimer() <= 200) player.getPlayer().sendTitle(Messaging.chatFormatter("&#00FF00" +gameInstance.getTimer()/20),Messaging.chatFormatter("&#00FFFFSeconds Until The Game Starts!"),0,5,10);
				else player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((Messaging.chatFormatter("&#FFFF00"+ Common_Shared.getTimeFromTicks(gameInstance.getTimer())))));
			}
			else 
			{
				player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((Messaging.chatFormatter("&#FF0000" + (minPlayers - gameInstance.getPlayerCount()) + " &#00FFFFPlayers until the Countdown Starts!"))));
				gameInstance.setTimer(YamlWrapper.getBlockHuntLobbyTime());
			}
			
			if(gameInstance.getTimer() <= 0) 
			{
				gameInstance.start();
				if(player.isSeeker()) player.getPlayer().sendTitle(Messaging.chatFormatter("&#FF0000The Hiders are hiding."),Messaging.chatFormatter("&#cc0000From here we transport you to the new INFECTED area!"),20,120,10);
				else if (player.isHider()) player.getPlayer().sendTitle(Messaging.chatFormatter("&#CCCCFFThe Seekers are coming."),Messaging.chatFormatter("&#8888FFIt's probably nothing. Try hiding while I look for a way out."),20,120,10);
				else player.getPlayer().sendTitle(Messaging.chatFormatter("&#FFFFFFHider phase has begun."),Messaging.chatFormatter("&#CCCCCCWhile I do need you to be in the room so I can see them, I want to be clear. There is no reason whatsoever for you to look at them."),20,120,10);
			}
		}
	}
}
