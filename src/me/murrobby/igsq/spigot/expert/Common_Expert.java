package me.murrobby.igsq.spigot.expert;

import org.bukkit.boss.BarColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Messaging;

public class Common_Expert {
	
    public static void giveBlindness(Player player,int time) 
    {
    	if(!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
    	{
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,time,0,false));
    	}
    }
    
    public static void updateEnderDragon(EnderDragon enderDragon) 
    {
		if(enderDragon.getBossBar() != null && enderDragon.getBossBar().getTitle().equalsIgnoreCase(Messaging.chatFormatter("&#FF5300True Expert Ender Dragon"))) 
		{
			enderDragon.getBossBar().setTitle("Ender Dragon");
			enderDragon.setCustomName(null);
			enderDragon.getBossBar().setColor(BarColor.PINK);
		}
    }
}
