package me.murrobby.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.smp.aspect.Base_Aspect;
import me.murrobby.igsq.spigot.smp.aspect.Common_Aspect;
import me.murrobby.igsq.spigot.smp.aspect.Enum_Aspect;

public class PlayerMoveEvent_SMP implements Listener
{
	public PlayerMoveEvent_SMP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void PlayerMove_SMP(org.bukkit.event.player.PlayerMoveEvent event) 
	{
		Player_SMP player = Player_SMP.getSMPPlayer(event.getPlayer());
		if(player.getAspect().getID().equals(Enum_Aspect.NONE)) 
		{
			event.setCancelled(true);
			int slots = 18;
			int requiredSlots = 0;
			while(slots > requiredSlots && requiredSlots < 54) requiredSlots +=9;
			Inventory gui = Bukkit.createInventory(null, requiredSlots, Messaging.chatFormatter(Common_Aspect.ASPECT_GUI_NAME));
			for(Base_Aspect aspect : Common_Aspect.getAspects()) 
			{
				if(aspect.getName() == null || aspect.getName().equals("")) continue;
				ItemStack aspectItem = new ItemStack(aspect.getLogo());
				
				ItemMeta meta = aspectItem.getItemMeta();
				List<String> lore = new ArrayList<String>();
				
				meta.setDisplayName(aspect.getName());
				for(String loreLine : aspect.getDescription()) lore.add(loreLine);
				
				meta.setLore(lore);
				aspectItem.setItemMeta(meta);
				gui.addItem(aspectItem);
				if(aspect.getLore() == null || aspect.getLore().equals("")) continue;
				ItemStack loreItem = new ItemStack(Material.WRITTEN_BOOK);
				
				BookMeta loreMeta = (BookMeta) loreItem.getItemMeta();
				
				loreMeta.setDisplayName(aspect.getName() + Messaging.chatFormatter(Common_Aspect.ASPECT_GUI_LORE));
				loreMeta.setAuthor(aspect.getSuggester());

				loreItem.setItemMeta(loreMeta);
				gui.addItem(loreItem);
			}
			player.getPlayer().openInventory(gui);
		}
	}
	
}
