package me.murrobby.igsq.spigot.smp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.smp.aspect.Base_Aspect;
import me.murrobby.igsq.spigot.smp.aspect.Common_Aspect;

public class InventoryClickEvent_SMP implements Listener
{
	public InventoryClickEvent_SMP()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler
	public void InventoryClick_SMP(org.bukkit.event.inventory.InventoryClickEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(YamlWrapper.isSMP()) 
			{
				if(event.getWhoClicked() instanceof Player) 
				{
					Player_SMP player = Player_SMP.getSMPPlayer((Player) event.getWhoClicked());
					if(Common_Aspect.isPlayerInAspectGui(player))
					{
						event.setCancelled(true);
						if(event.getCurrentItem() != null) 
						{
							for(Base_Aspect aspect : Common_Aspect.getAspects()) 
							{
								if(event.getCurrentItem().getItemMeta().getDisplayName().equals(aspect.getName())) //Aspect Selector
								{
									player.getPlayer().closeInventory();
									player.getYaml().setSmpAspect(aspect.getID().toString());
									player.updateAspect();
									player.getPlayer().sendMessage(Messaging.chatFormatter("&#DAB210You have picked aspect " + aspect.getName()));
									break;
								}
								else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(aspect.getName() + Messaging.chatFormatter(Common_Aspect.ASPECT_GUI_LORE))) //Aspect Lore
								{
									player.getPlayer().closeInventory();
									player.getPlayer().sendMessage(Messaging.chatFormatter("&#DAB210This would open the lore for " + aspect.getName()));
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
