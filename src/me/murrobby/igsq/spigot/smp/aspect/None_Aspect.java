package me.murrobby.igsq.spigot.smp.aspect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.smp.Player_SMP;

public class None_Aspect extends Base_Aspect
{

	public None_Aspect(Player_SMP player) //Player Instance
	{
		generate(player);
	}
	public None_Aspect() //Internal constructor
	{
		generate();
	}
	@Override
	protected void generate() 
	{
		setID(Enum_Aspect.NONE);
	}
	@Override
	public void aspectTick() 
	{

	}
	@Override
	public void aspectSecond() 
	{
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
		}
		getPlayer().getPlayer().openInventory(gui);
	}
}
