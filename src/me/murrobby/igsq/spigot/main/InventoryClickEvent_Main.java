package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;

public class InventoryClickEvent_Main implements Listener
{
	public InventoryClickEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	String[] illegalNameTagWords = {};
	String[] illegalWords;
	@EventHandler
	public void InventoryClick_Main(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getClickedInventory() != null && event.getCurrentItem() != null) 
			{
				if(event.getClickedInventory().getType() == InventoryType.ANVIL) 
				{
					if(event.getCurrentItem().getType() == Material.NAME_TAG) 
					{
						illegalWords = Common_Shared.arrayAppend(illegalNameTagWords, Common.illegalChats);
					}
					else 
					{
						illegalWords = Common.illegalChats;
					}
					for(String illegalWord : illegalWords)
					{
				       if(event.getCurrentItem().getItemMeta().getDisplayName().toUpperCase().contains(illegalWord))
				       {
				    	   if(event.getWhoClicked().hasPermission("IGSQ.ItemNameOverride"))
				    	   {
				    		   event.getWhoClicked().sendMessage(Messaging.getFormattedMessage("illegalitemnameoverride",new String[] {"<blocked>",illegalWord,"<material>",event.getCurrentItem().getType().toString()}));
				    	   }
				    	   else 
				    	   {
				    		   event.getWhoClicked().sendMessage(Messaging.getFormattedMessage("illegalitemname",new String[] {"<blocked>",illegalWord,"<material>",event.getCurrentItem().getType().toString()}));
					    	   event.setCancelled(true);
				    	   }
				       }
					}
				}
			}
		}
	}
}
