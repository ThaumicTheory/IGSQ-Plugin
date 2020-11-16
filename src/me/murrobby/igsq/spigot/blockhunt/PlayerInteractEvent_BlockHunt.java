package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
import me.murrobby.igsq.spigot.Messaging;

public class PlayerInteractEvent_BlockHunt implements Listener
{
	public PlayerInteractEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerInteract_BlockHunt(org.bukkit.event.player.PlayerInteractEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck()) 
		{
			Game_BlockHunt playersGame = Game_BlockHunt.getPlayersGame(event.getPlayer());
			if(playersGame != null) 
			{
				if(playersGame.isDead(event.getPlayer())) event.setCancelled(true);
				else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null && !Common_BlockHunt.isInteractWhitelisted(event.getClickedBlock().getType())) event.setCancelled(true); //only allows certain blocks to be right clicked
				else if(Common_BlockHunt.isCloaked(event.getPlayer())) event.setCancelled(true);
				//else if(event.getItem() != null && event.getItem().getType().isBlock()) event.setCancelled(true); //Stops interactions with blocks in inventory
				else if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) event.setCancelled(true); //Stops interactions with blocks
				else if(event.getItem() != null && event.getItem().getType() == Material.ENDER_EYE && playersGame.isHider(event.getPlayer())) event.setCancelled(true); //stops use of ender eye
				else if(event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && playersGame.isHider(event.getPlayer())) 
				{
					event.setCancelled(true);
					/*if(Common_BlockHunt.isCloaked(event.getPlayer()) && event.getAction().equals(Action.RIGHT_CLICK_AIR))
					{
						if(playersGame.isStage(Stage.IN_GAME) || playersGame.isStage(Stage.PRE_SEEKER)) 
						{
							event.setCancelled(true);
							event.getPlayer().sendMessage(Messaging.chatFormatter("&#FF0000You cannot change block while hiding!"));
						}
					}
					*/
				}
				
				if(playersGame.isHider(event.getPlayer())) 
				{
					if(event.getClickedBlock() != null && event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && playersGame.isBlockPlayable(event.getClickedBlock().getType())) 
					{
						if(playersGame.isStage(Stage.IN_GAME) || playersGame.isStage(Stage.PRE_SEEKER)) 
						{
							Common_BlockHunt.hiderChangeDisguise(event.getPlayer(), event.getClickedBlock().getType());
						}
					}
					if(event.getItem() != null && playersGame.isBlockPlayable(event.getItem().getType()) && Common_BlockHunt.getCloakCooldown(event.getPlayer()) == 0 && (event.getClickedBlock() == null || !Common_BlockHunt.isInteractWhitelisted(event.getClickedBlock().getType())))
					{
						if(playersGame.isStage(Stage.IN_GAME) || playersGame.isStage(Stage.PRE_SEEKER)) 
						{
							if(!Common_BlockHunt.isCloaked(event.getPlayer())) 
							{
								if(Common_BlockHunt.validateCloak(event.getPlayer())) 
								{
									event.getPlayer().sendMessage(Messaging.chatFormatter("&#00FF00You are now hidden."));
									playersGame.addCloak(event.getPlayer());
								}
								else 
								{
									event.getPlayer().sendMessage(Messaging.chatFormatter("&#FFb900You cannot hide here!"));
									Common_BlockHunt.setCloakCooldown(event.getPlayer(), Yaml.getFieldInt("cloakcooldown", "blockhunt")/Yaml.getFieldInt("failcooldown", "blockhunt"));
								}
							}
						}
					}
				}
				else if(playersGame.isSeeker(event.getPlayer())) 
				{
					if(event.getItem() != null && event.getItem().getType() == Material.GOLDEN_SWORD && (event.getAction().equals(Action.LEFT_CLICK_BLOCK)))
					{
						if(playersGame.isStage(Stage.IN_GAME) ) 
						{
							Player hider = playersGame.raycastForCloak(event.getPlayer(), 6);
							if(hider != null) 
							{
			    				hider.sendMessage(Messaging.chatFormatter("&#FF0000You have been revealed by "+ event.getPlayer().getName() +"!"));
			    				event.getPlayer().sendMessage(Messaging.chatFormatter("&#00FF00Hider "+ hider.getName() +" located!" ));
			    				Common_BlockHunt.setCloakCooldown(hider, Yaml.getFieldInt("cloakcooldown", "blockhunt"));
			    				playersGame.removeCloak(hider);
							}
						}
					}
				}
			}
		}
	}
	
}
