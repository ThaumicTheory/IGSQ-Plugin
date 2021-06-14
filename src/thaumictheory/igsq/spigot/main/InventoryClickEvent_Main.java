package thaumictheory.igsq.spigot.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;

public class InventoryClickEvent_Main implements Listener
{
	public InventoryClickEvent_Main()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	ArrayList<String> illegalNameTagWords = new ArrayList<>();
	ArrayList<String> illegalWords = new ArrayList<>();
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
						//illegalWords = Common_Shared.arrayAppend(illegalNameTagWords, Common.illegalChats);
						illegalWords.addAll(illegalNameTagWords);
					}
					else 
					{
						illegalWords.addAll(Common.illegalChats);
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
