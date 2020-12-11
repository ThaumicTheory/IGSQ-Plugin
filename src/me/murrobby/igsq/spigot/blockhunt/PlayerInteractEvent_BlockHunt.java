package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Dictionaries;
import me.murrobby.igsq.spigot.YamlWrapper;
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
		if(YamlWrapper.isBlockHunt()) 
		{
			Player_BlockHunt player = Player_BlockHunt.getPlayer((event.getPlayer()));
			if(player != null) 
			{
				if(player.isDead()) event.setCancelled(true);
				else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null && !Common_BlockHunt.isInteractWhitelisted(event.getClickedBlock().getType())) event.setCancelled(true); //only allows certain blocks to be right clicked
				else if(player.isHider() && player.toHider().getGeneric().isCloaked()) event.setCancelled(true);
				//else if(event.getItem() != null && event.getItem().getType().isBlock()) event.setCancelled(true); //Stops interactions with blocks in inventory
				else if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) event.setCancelled(true); //Stops interactions with blocks
				else if(event.getItem() != null && event.getItem().getType() == Material.ENDER_EYE && player.isHider() && event.getAction().equals(Action.RIGHT_CLICK_AIR)) event.setCancelled(true); //stops use of ender eye
				else if(event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && player.isHider() && event.getAction().equals(Action.RIGHT_CLICK_AIR)) event.setCancelled(true);
				//Block Picker
				if(player.isHider())
				{
					if(event.getClickedBlock() != null && event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && player.getGame().isBlockPlayable(event.getClickedBlock().getType())) 
					{
						if(player.getGame().isStage(Stage.IN_GAME) || player.getGame().isStage(Stage.PRE_SEEKER)) 
						{
							player.toHider().getGeneric().changeCloak(event.getClickedBlock().getType());
						}
					}
					//Block Meta Picker
					else if(event.getItem() != null && event.getItem().getType() == Material.ENDER_EYE && (event.getClickedBlock() == null || !Common_BlockHunt.isInteractWhitelisted(event.getClickedBlock().getType())))
					{
						if(player.getGame().isStage(Stage.IN_GAME) || player.getGame().isStage(Stage.PRE_SEEKER)) 
						{
							int metaChange = player.toHider().getGeneric().getBlockMeta();
							int maxMeta = Dictionaries.getMetaCountFromMaterial(player.toHider().getGeneric().getBlock());
							if(event.getPlayer().isSneaking() && maxMeta >= 10)
							{
								if(event.getAction().equals(Action.LEFT_CLICK_AIR ) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) metaChange -= maxMeta/10;
								else metaChange += maxMeta/10;
		
							}
							else if(event.getAction().equals(Action.LEFT_CLICK_AIR ) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) metaChange--;
							else metaChange++;

							player.toHider().getGeneric().setBlockMeta(metaChange);
						}
					}
					//Block Cloaker
					else if(event.getItem() != null && player.getGame().isBlockPlayable(event.getItem().getType()) && player.toHider().getGeneric().getCloakCooldown() == 0 && (event.getClickedBlock() == null || !Common_BlockHunt.isInteractWhitelisted(event.getClickedBlock().getType())))
					{
						if(player.getGame().isStage(Stage.IN_GAME) || player.getGame().isStage(Stage.PRE_SEEKER)) 
						{
							if(!player.toHider().getGeneric().isCloaked()) 
							{
								if(player.toHider().getGeneric().isCloakValid()) 
								{
									event.getPlayer().sendMessage(Messaging.chatFormatter("&#00FF00You are now hidden."));
									player.toHider().setCloak(true);
								}
								else 
								{
									event.getPlayer().sendMessage(Messaging.chatFormatter("&#FFb900You cannot hide here!"));
									player.toHider().getGeneric().setCloakCooldown(YamlWrapper.getBlockHuntCloakCooldown()/YamlWrapper.getBlockHuntFailCooldown());
								}
							}
						}
					}
				}
				else if(player.isSeeker()) 
				{
					//Sword
					if(event.getItem() != null && event.getItem().getType() == Material.GOLDEN_SWORD && (event.getAction().equals(Action.LEFT_CLICK_BLOCK)))
					{
						if(player.getGame().isStage(Stage.IN_GAME) ) 
						{
							Hider_BlockHunt hider = player.toSeeker().raycastForCloak(6);
							if(hider != null) 
							{
			    				hider.getPlayer().sendMessage(Messaging.chatFormatter("&#FF0000You have been revealed by "+ event.getPlayer().getName() +"!"));
			    				event.getPlayer().sendMessage(Messaging.chatFormatter("&#00FF00Hider "+ hider.getPlayer().getName() +" located!" ));
			    				hider.getGeneric().setCloakCooldown(YamlWrapper.getBlockHuntCloakCooldown());
			    				hider.setCloak(false);
							}
						}
					}
				}
			}
		}
	}
	
}
