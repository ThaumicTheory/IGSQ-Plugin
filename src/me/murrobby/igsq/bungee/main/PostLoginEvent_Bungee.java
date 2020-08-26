package me.murrobby.igsq.bungee.main;

import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.bungee.Database_Bungee;
import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginEvent_Bungee implements Listener
{
	public PostLoginEvent_Bungee(Main_Bungee plugin)
	{
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void PostLogin_Bungee(net.md_5.bungee.api.event.PostLoginEvent event) 
	{
		//Runs on bungee connect complete
		ProxiedPlayer player = event.getPlayer();
		String username = player.getDisplayName();
		String playerUUID = player.getUniqueId().toString();
		
		//Update mc_accounts database if required
		int usernameUpdate = Database_Bungee.ScalarCommand("SELECT count(*) FROM mc_accounts WHERE uuid = '"+ playerUUID +"' AND username = '"+ username +"';");
		if(usernameUpdate == 1) System.out.println("DATABASE LOG ("+ usernameUpdate + "): No action needed for "+ playerUUID + ", already updated and in minecraft accounts Database!");
		else if(usernameUpdate == 0) 
		{
			System.out.println("DATABASE LOG ("+ usernameUpdate + "): Action queued for "+ playerUUID + "!");
			usernameUpdate = Database_Bungee.ScalarCommand("SELECT count(*) FROM mc_accounts WHERE uuid = '"+ playerUUID +"';");
			if(usernameUpdate == 1) 
			{
				System.out.println("DATABASE LOG ("+ usernameUpdate + "): Action completed "+ playerUUID + " has been updated in the database!");
				Database_Bungee.UpdateCommand("UPDATE mc_accounts SET username = '"+ username +"' WHERE uuid = '"+ playerUUID +"';");
			}
			else if(usernameUpdate == 0) 
			{
				System.out.println("DATABASE LOG ("+ usernameUpdate + "): Action completed "+ playerUUID + " has been added to the database!");
				Database_Bungee.UpdateCommand("INSERT INTO mc_accounts VALUES ('"+ playerUUID +"','" + username +"');");
				Database_Bungee.UpdateCommand("INSERT INTO discord_2fa VALUES ('"+ playerUUID +"','kick');");
			}
			else System.out.println("DATABASE ERROR ("+ usernameUpdate + "): Failed Update Check "+ playerUUID + " in minecraft accounts Database!");
		}
		else System.out.println("DATABASE ERROR ("+ usernameUpdate + "): Failed Update Check "+ playerUUID + " in minecraft accounts Database!");
		//welcome message
		player.sendMessage(Common_Bungee.GetFormattedMessage("join","<player>",username));
		if(player.isForgeUser()) player.sendMessage(Common_Bungee.GetFormattedMessage("joinforge","<modlist>",player.getModList().toString()));
		else player.sendMessage(Common_Bungee.GetFormattedMessage("joinvanilla"));
	}
	
}
