package me.murrobby.igsq.bungee.security;

import me.murrobby.igsq.bungee.YamlPlayerWrapper;
import me.murrobby.igsq.shared.Ranks;
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
    	}, 5, 1, TimeUnit.SECONDS);
	}
	private void DiscordLink() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
			if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
			{
				ResultSet discord_accounts = Database.QueryCommand("SELECT * FROM discord_accounts WHERE id = (SELECT id FROM linked_accounts WHERE uuid = '" +  player.getUniqueId().toString() +"');");
				try
				{
					discord_accounts.next();
					yaml.setID(discord_accounts.getString(1));
					yaml.setUsername(discord_accounts.getString(2));
					yaml.setNickname(discord_accounts.getString(3));
					yaml.setRole(discord_accounts.getString(4));
					yaml.setFounder(discord_accounts.getBoolean(5));
					yaml.setBirthday(discord_accounts.getBoolean(6));
					yaml.setBooster(discord_accounts.getBoolean(7));
					yaml.setSupporter(discord_accounts.getBoolean(8));
					yaml.setDeveloper(discord_accounts.getBoolean(9));
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				yaml.setID("");
				yaml.setUsername("");
				yaml.setNickname("");
				yaml.setRole(Ranks.getRank(1).getPermission());
				yaml.setFounder(false);
				yaml.setBirthday(false);
				yaml.setBooster(false);
				yaml.setSupporter(false);
				yaml.setDeveloper(false);
			}
		}
	}
}
