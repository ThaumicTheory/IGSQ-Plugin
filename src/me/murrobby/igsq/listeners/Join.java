package me.murrobby.igsq.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Database;
import me.murrobby.igsq.Main;

public class Join implements Listener
{
	@SuppressWarnings("unused")
	private Main plugin;
	private String playerUUID;
	private String username;
	private int usernameUpdate;
	private Boolean playedBefore;
	public Join(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) 
	{
		Player player = event.getPlayer();
		username = player.getDisplayName();
		playerUUID = player.getUniqueId().toString();
		playedBefore = player.hasPlayedBefore();
		
		//Update mc_accounts database if required
		usernameUpdate = Database.ScalarCommand("SELECT count(*) FROM mc_accounts WHERE uuid = '"+ playerUUID +"' AND username = '"+ username +"';");
		if(usernameUpdate == 1) 
		{
			System.out.println("DATABASE LOG ("+ usernameUpdate + "): No action needed for "+ playerUUID + ", already updated and in minecraft accounts Database!");
		}
		else if(usernameUpdate == 0) 
		{
			System.out.println("DATABASE LOG ("+ usernameUpdate + "): Action queued for "+ playerUUID + "!");
			usernameUpdate = Database.ScalarCommand("SELECT count(*) FROM mc_accounts WHERE uuid = '"+ playerUUID +"';");
			if(usernameUpdate == 1) 
			{
				System.out.println("DATABASE LOG ("+ usernameUpdate + "): Action completed "+ playerUUID + " has been updated in the database!");
				Database.UpdateCommand("UPDATE mc_accounts SET SET username = '"+ username +"' WHERE uuid = '"+ playerUUID +"';");
			}
			else if(usernameUpdate == 0) 
			{
				System.out.println("DATABASE LOG ("+ usernameUpdate + "): Action completed "+ playerUUID + " has been added to the database!");
				Database.UpdateCommand("INSERT INTO mc_accounts VALUES ('"+ playerUUID +"','" + username +"');");
			}
			else 
			{
				System.out.println("DATABASE ERROR ("+ usernameUpdate + "): Failed Update Check "+ playerUUID + " in minecraft accounts Database!");
			}
		}
		else 
		{
			System.out.println("DATABASE ERROR ("+ usernameUpdate + "): Failed Update Check "+ playerUUID + " in minecraft accounts Database!");
		}
		//welcome message
		if(playedBefore) 
		{
			player.sendMessage(Common.GetMessage("join1") + username + Common.GetMessage("join2"));
		}
		else 
		{
			player.sendMessage(Common.GetMessage("firstjoin1") + username + Common.GetMessage("firstjoin2"));
			Common.Default(player);
		}
		if(player.hasPermission("igsq.discord2FA")) 
		{
			//player.sendMessage("You are REQUIRED to use Discord 2FA!");
		}
		else 
		{
			//player.sendMessage("You can use Discord 2FA to increase security!");
		}
		if(Common.getFieldInt(player.getUniqueId().toString() + ".damage.last", "internal") == 0) 
		{
			Common.Default(player);
		}
	}
	
}
