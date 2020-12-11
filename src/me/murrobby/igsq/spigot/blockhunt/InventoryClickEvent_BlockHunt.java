package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlWrapper;

public class InventoryClickEvent_BlockHunt implements Listener
{
	public InventoryClickEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void InventoryClick_BlockHunt(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(YamlWrapper.isBlockHunt()) 
			{
				if(event.getWhoClicked() instanceof Player) 
				{
					Player player = (Player) event.getWhoClicked();
					Game_BlockHunt gameInstance = Game_BlockHunt.getPlayersGame(player);
					if(gameInstance != null) 
					{
						event.setCancelled(true);
					}
					else if(Common_BlockHunt.isPlayerInGui(player))
					{
						event.setCancelled(true);
						if(event.getCurrentItem() != null) 
						{
							if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Messaging.chatFormatter("&#FFFFFFCREATE A GAME"))) 
							{
								new Game_BlockHunt().joinLobby(player);
								player.closeInventory();
							}
							else
							{
								for(Game_BlockHunt game : Game_BlockHunt.getGameInstances()) 
								{
									if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Messaging.chatFormatter("&#00FF00" + game.getName())))
									{
										game.joinLobby(player);
										player.closeInventory();
										break;
									}
								}
							}
							
						}
					}
				}
			}
		}
	}
	
}
