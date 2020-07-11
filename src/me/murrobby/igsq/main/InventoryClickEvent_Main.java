package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main_Spigot;

import java.util.Random;

public class InventoryClickEvent_Main implements Listener
{
	Random random = new Random();
	public InventoryClickEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	String[] illegalNameTagWords = {"EXPERT","NIGGER","NOGGER","COON","NIGGA"};
	@EventHandler
	public void InventoryClick_Main(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(event.getClickedInventory().getType() == InventoryType.ANVIL) 
		{
		if(event.getCurrentItem().getType() == Material.NAME_TAG && event.getCurrentItem().getItemMeta().getDisplayName() != null) 
			{
				for(String illegalNameTagWord : illegalNameTagWords)
				{
			       if(event.getCurrentItem().getItemMeta().getDisplayName().toUpperCase().contains(illegalNameTagWord))
			       {
			    	   if(event.getWhoClicked().hasPermission("IGSQ.NameTagOverride")) 
			    	   {
			    		   event.getWhoClicked().sendMessage(Common.GetMessage("illegalnametagnameoverride","<blocked>",illegalNameTagWord));
			    	   }
			    	   else 
			    	   {
			    		   event.getWhoClicked().sendMessage(Common.GetMessage("illegalnametagname","<blocked>",illegalNameTagWord));
				    	   event.setCancelled(true);
			    	   }
			       }
				}
			}
		}
	}
	
}
