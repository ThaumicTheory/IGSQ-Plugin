package me.murrobby.igsq.spigot.expert;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Configuration;

public class Common_Expert {
	
    public static void giveBlindness(Player player,int time) 
    {
    	if(!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
    	{
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,time,0,false));
    	}
    }
    public static Boolean expertCheck() 
    {
    	return Configuration.getFieldBool("GAMEPLAY.expert", "config");
    }
}
