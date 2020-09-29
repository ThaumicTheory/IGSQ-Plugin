package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class PlayerInteractEvent_BlockHunt implements Listener
{
	public PlayerInteractEvent_BlockHunt(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerInteract_BlockHunt(org.bukkit.event.player.PlayerInteractEvent event) 
	{
		if(Common_BlockHunt.blockhuntCheck()) 
		{
			if(Common_BlockHunt.isPlayer(event.getPlayer())) 
			{
				if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) event.setCancelled(true);
				else if(event.getItem() != null && event.getItem().getType() == Material.ENDER_EYE && Common_BlockHunt.isHider(event.getPlayer())) event.setCancelled(true); //If Hider Stop Successfull Use of Eye
				
				if(Common_BlockHunt.isHider(event.getPlayer())) 
				{
					if(event.getClickedBlock() != null && Common_BlockHunt.isBlockPlayable(event.getClickedBlock().getType())) 
					{
						Common_Spigot.updateField(event.getPlayer().getUniqueId().toString() +".blockhunt.block", "internal", event.getClickedBlock().getType().toString());
						Common_BlockHunt.hiderBlockVisuals(event.getPlayer());
					}
				}
			}
		}
	}
	
}
