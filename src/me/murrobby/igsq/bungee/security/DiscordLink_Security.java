package me.murrobby.igsq.bungee.security;

import me.murrobby.igsq.bungee.Yaml;
import me.murrobby.igsq.bungee.Common;
import me.murrobby.igsq.bungee.Database;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class DiscordLink_Security
{	
	ScheduledTask discordLinkTask;
	final int taskID;
	
	public DiscordLink_Security(int taskID) 
	{
		this.taskID = taskID;
		DiscordLinkQuery();
	}
	private void DiscordLinkQuery() 
	{
		discordLinkTask = Common.bungee.getProxy().getScheduler().schedule(Common.bungee, new Runnable() 
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
			if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
			{
				ResultSet discord_accounts = Database.QueryCommand("SELECT * FROM discord_accounts WHERE id = (SELECT id FROM linked_accounts WHERE uuid = '" +  player.getUniqueId().toString() +"');");
				try
				{
					discord_accounts.next();
					Yaml.updateField(player.getUniqueId().toString() + ".discord.id", "player", discord_accounts.getString(1));
					Yaml.updateField(player.getUniqueId().toString() + ".discord.username", "player", discord_accounts.getString(2));
					Yaml.updateField(player.getUniqueId().toString() + ".discord.nickname", "player", discord_accounts.getString(3));
					Yaml.updateField(player.getUniqueId().toString() + ".discord.role", "player", discord_accounts.getString(4));
					Boolean data = discord_accounts.getBoolean(5);
					Yaml.updateField(player.getUniqueId().toString() + ".discord.founder", "player", data.toString());
					data = discord_accounts.getBoolean(6);
					Yaml.updateField(player.getUniqueId().toString() + ".discord.birthday", "player", data.toString());
					data = discord_accounts.getBoolean(7);
					Yaml.updateField(player.getUniqueId().toString() + ".discord.nitroboost", "player", data.toString());
					data = discord_accounts.getBoolean(8);
					Yaml.updateField(player.getUniqueId().toString() + ".discord.supporter", "player", data.toString());
					data = discord_accounts.getBoolean(9);
					Yaml.updateField(player.getUniqueId().toString() + ".discord.developer", "player", data.toString());
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				Yaml.updateField(player.getUniqueId().toString() + ".discord.id", "player", "");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.username", "player","");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.nickname", "player", "");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.role", "player", "default");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.founder", "player", "false");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.birthday", "player", "false");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.nitroboost", "player", "false");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.supporter", "player", "false");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.developer", "player", "false");
			}
		}
	}
}
