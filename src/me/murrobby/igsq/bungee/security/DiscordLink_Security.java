package me.murrobby.igsq.bungee.security;

import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.bungee.Database_Bungee;
import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DiscordLink_Security
{	
	Main_Bungee plugin;
	Random random = new Random();
	ScheduledTask discordLinkTask;
	final int taskID;
	
	public DiscordLink_Security(Main_Bungee plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		DiscordLinkQuery();
	}
	private void DiscordLinkQuery() 
	{
		discordLinkTask = plugin.getProxy().getScheduler().schedule(plugin, new Runnable() 
    	{

			@Override
			public void run() 
			{
				DiscordLink();
				if(Main_Security.taskID != taskID) 
				{
					discordLinkTask.cancel();
					System.out.println("Task: \"Discord Link Security\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 5, 5, TimeUnit.SECONDS);
	}
	private void DiscordLink() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
			{
				ResultSet discord_accounts = Database_Bungee.QueryCommand("SELECT * FROM discord_accounts WHERE id = (SELECT id FROM linked_accounts WHERE uuid = '" +  player.getUniqueId().toString() +"');");
				try
				{
					discord_accounts.next();
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.id", "player", discord_accounts.getString(1));
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.username", "player", discord_accounts.getString(2));
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.nickname", "player", discord_accounts.getString(3));
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.role", "player", discord_accounts.getString(4));
					Boolean data = discord_accounts.getBoolean(5);
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.founder", "player", data.toString());
					data = discord_accounts.getBoolean(6);
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.birthday", "player", data.toString());
					data = discord_accounts.getBoolean(7);
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.nitroboost", "player", data.toString());
					data = discord_accounts.getBoolean(8);
					Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.supporter", "player", data.toString());
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.id", "player", "");
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.username", "player","");
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.nickname", "player", "");
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.role", "player", "default");
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.founder", "player", "false");
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.birthday", "player", "false");
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.nitroboost", "player", "false");
				Common_Bungee.updateField(player.getUniqueId().toString() + ".discord.supporter", "player", "false");
			}
		}
	}
}
