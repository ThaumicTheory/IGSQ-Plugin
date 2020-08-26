package me.murrobby.igsq.spigot.expert;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common_Spigot;

public class Common_Expert {
	
	public Common_Expert() 
	{
	}
    public static void GiveBlindness(Player player,int time) 
    {
    	if(!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
    	{
    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,time,0,false));
    	}
    }
    public static Boolean ExpertCheck() 
    {
    	return Common_Spigot.getFieldBool("GAMEPLAY.expert", "config");
    }
}
