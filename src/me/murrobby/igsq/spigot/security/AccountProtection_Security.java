package me.murrobby.igsq.spigot.security;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Spigot;
import me.murrobby.igsq.spigot.Messaging;

public class AccountProtection_Security
{	
	Spigot plugin;
	int accountProtectionTask = -1;
	Random random = new Random();
	final int taskID;
	
	public AccountProtection_Security(Spigot plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		AccountProtectionQuery();
	}
	private void AccountProtectionQuery() 
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
    	}, 0, 60);
	}
	private void AccountProtection() 
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if(Common_Security.SecurityProtectionQuery(player)) 
			{
				String header = "&#FF00002FA &#CD0000Enabled!";
				if(player.hasPermission("igsq.require2fa")) header = "&#FFFF00Staff &#FF00002FA!";
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,80,0,true));
				if(random.nextInt(3) ==1) player.sendTitle(Messaging.chatFormatter(header),Messaging.chatFormatter("&#00FFFFYou Should be provided with a code from discord!"),10,40,10);
				else if(random.nextInt(2) ==1) player.sendTitle(Messaging.chatFormatter(header),Messaging.chatFormatter("&#FFFF00Type /2fa confirm [code]"),10,40,10);
				else player.sendTitle(Messaging.chatFormatter(header),Messaging.chatFormatter("&#ffb900Make Sure you havn't blocked IGSQbot."),10,40,10);
			}
		}
	}
}
