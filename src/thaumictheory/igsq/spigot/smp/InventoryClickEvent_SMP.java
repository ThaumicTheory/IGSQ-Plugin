package thaumictheory.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.smp.aspect.Base_Aspect;
import thaumictheory.igsq.spigot.smp.aspect.Common_Aspect;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

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
									player.getPlayer().setAllowFlight(false);
									player.getPlayer().setFlying(false);
									break;
								}
								else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(aspect.getName() + Messaging.chatFormatter(Common_Aspect.ASPECT_GUI_LORE))) //Aspect Lore
								{
									player.getPlayer().closeInventory();
									ItemStack loreItem = new ItemStack(Material.WRITTEN_BOOK);
									BookMeta loreMeta = (BookMeta) loreItem.getItemMeta();
									
									loreMeta.setTitle(aspect.getSuggester());
									loreMeta.setAuthor(aspect.getSuggester());
									List<String> bookContents = new ArrayList<>();
									String pageContents = "";
									for(String sentence : aspect.getLore().replaceAll("[\\r]", "").replaceAll("[\\t]", "        ").split("\\."))
									{
										while (sentence.startsWith(" ") && !sentence.equals(" ")) 
										{
											sentence = sentence.substring(1);
										}
										if(sentence.equals(" ")) continue;
										if(pageContents.equals("")) 
										{
											while(sentence.startsWith("\n") && !sentence.equals("\n")) 
											{
												sentence = sentence.substring(2);
											}
											if(sentence.equals("\n")) continue;
										}
										if((pageContents + sentence).length() >= 256) 
										{
											bookContents.add(pageContents);
											while (sentence.length() >= 256) 
											{
												bookContents.add(sentence.substring(0, 256));
												sentence = sentence.substring(256);
												while (sentence.startsWith(" ") && !sentence.equals(" ")) 
												{
													sentence = sentence.substring(1);
												}
												if(sentence.equals(" ")) continue;
											}
											while(sentence.startsWith("\n") && !sentence.equals("\n")) 
											{
												sentence = sentence.substring(2);
											}
											if(sentence.equals("\n")) pageContents = "";
											else
											{
												pageContents = sentence + ".";
											}
										}
										else pageContents += sentence + ".";
									}
									bookContents.add(pageContents);
									loreMeta.setPages(bookContents);
									
									loreItem.setItemMeta(loreMeta);
									
									player.getPlayer().openBook(loreItem);
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
