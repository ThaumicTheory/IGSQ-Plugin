package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main_Spigot;

import java.util.Random;


@SuppressWarnings("unused")
public class InventoryClickEvent_Main implements Listener
{
	Random random = new Random();
	private Main_Spigot plugin;
	public InventoryClickEvent_Main(Main_Spigot plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	String[] illegalNameTagWords = {"EXPERT","NIGGER","NOGGER","COON","NIGGA"};
	@EventHandler
	public void InventoryClick_Main(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(event.getClickedInventory().getType() == InventoryType.ANVIL) 
		{
			if(event.getCurrentItem().getType() == Material.NAME_TAG) 
			{
				for(String illegalNameTagWord : illegalNameTagWords)
				{
			       if(event.getCurrentItem().getItemMeta().getDisplayName().toUpperCase().contains(illegalNameTagWord))
			       {
			    	   if(event.getWhoClicked().hasPermission("IGSQ.NameTagOverride")) 
			    	   {
			    		   event.getWhoClicked().sendMessage(Common.GetMessage("illegalnametagnameoverride1") + illegalNameTagWord + Common.GetMessage("illegalnametagnameoverride2"));
			    	   }
			    	   else 
			    	   {
			    		   event.getWhoClicked().sendMessage(Common.GetMessage("illegalnametagname1") + illegalNameTagWord + Common.GetMessage("illegalnametagname2"));
				    	   event.setCancelled(true);
			    	   }
			       }
				}
			}
		}
	}
	
}
