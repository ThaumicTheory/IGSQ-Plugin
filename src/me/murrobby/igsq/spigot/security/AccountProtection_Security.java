package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Main_Spigot;

public class AccountProtection_Security
{	
	Main_Spigot plugin;
	int accountProtectionTask = -1;
	final int taskID;
	
	public AccountProtection_Security(Main_Spigot plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		TwoFactorAuthenticationQuery();
	}
	private void TwoFactorAuthenticationQuery() 
	{
		accountProtectionTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
    	{

			@Override
			public void run() 
			{
				AccountProtection() ;
				if(Main_Security.taskID != taskID) 
				{
					plugin.scheduler.cancelTask(accountProtectionTask);
					System.out.println("Task: \"Account Protection Security\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 20);
	}
	private void AccountProtection() 
	{
		for (Player player : Bukkit.getOnlinePlayers()) if(Common_Security.SecurityProtectionQuery(player)) 
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,60,0,true));
		}
	}
}
