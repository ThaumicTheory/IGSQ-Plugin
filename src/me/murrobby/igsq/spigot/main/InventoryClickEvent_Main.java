package me.murrobby.igsq.spigot.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class InventoryClickEvent_Main implements Listener
{
	public InventoryClickEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	String[] illegalNameTagWords = {};
	String[] illegalWords;
	@EventHandler
	public void InventoryClick_Main(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getClickedInventory() == null || event.getCurrentItem() == null) 
			{
				
			}
			else 
			{
				if(event.getClickedInventory().getType() == InventoryType.ANVIL) 
				{
					if(event.getCurrentItem().getType() == Material.NAME_TAG) 
					{
						illegalWords = Common_Spigot.ArrayAppend(illegalNameTagWords, Common_Spigot.illegalChats);
					}
					else 
					{
						illegalWords = Common_Spigot.illegalChats;
					}
					for(String illegalWord : illegalWords)
					{
				       if(event.getCurrentItem().getItemMeta().getDisplayName().toUpperCase().contains(illegalWord))
				       {
				    	   if(event.getWhoClicked().hasPermission("IGSQ.ItemNameOverride")) 
				    	   {
				    		   event.getWhoClicked().sendMessage(Common_Spigot.GetFormattedMessage("illegalitemnameoverride",new String[] {"<blocked>",illegalWord,"<material>",event.getCurrentItem().getType().toString()}));
				    	   }
				    	   else 
				    	   {
				    		   event.getWhoClicked().sendMessage(Common_Spigot.GetFormattedMessage("illegalitemname",new String[] {"<blocked>",illegalWord,"<material>",event.getCurrentItem().getType().toString()}));
					    	   event.setCancelled(true);
				    	   }
				       }
					}
				}
			}
		}
	}
	
}
