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

public class TwoFactorAuthentication_Security
{	
	Main_Bungee plugin;
	Random random = new Random();
	ScheduledTask twofaTask;
	final int taskID;
	
	public TwoFactorAuthentication_Security(Main_Bungee plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		TwoFactorAuthenticationQuery();
	}
	private void TwoFactorAuthenticationQuery() 
	{
		twofaTask = plugin.getProxy().getScheduler().schedule(plugin, new Runnable() 
    	{

			@Override
			public void run() 
			{
				TwoFactorAuthentication();
				if(Main_Security.taskID != taskID) 
				{
					twofaTask.cancel();
					System.out.println("Task: \"Two Factor Authentication Security\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 5, 5, TimeUnit.SECONDS);
	}
	private void TwoFactorAuthentication() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
			{
				ResultSet discord_accounts = Database_Bungee.QueryCommand("SELECT * FROM discord_accounts WHERE id = (SELECT id FROM linked_accounts WHERE uuid = '" +  player.getUniqueId().toString() +"');");
				try
				{
					discord_accounts.next();
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.id", "playerData.yml", discord_accounts.getString(1));
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.username", "playerData.yml", discord_accounts.getString(2));
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.nickname", "playerData.yml", discord_accounts.getString(3));
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.role", "playerData.yml", discord_accounts.getString(4));
					Boolean data = discord_accounts.getBoolean(5);
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.founder", "playerData.yml", data.toString());
					data = discord_accounts.getBoolean(6);
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.birthday", "playerData.yml", data.toString());
					data = discord_accounts.getBoolean(7);
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.nitroboost", "playerData.yml", data.toString());
					data = discord_accounts.getBoolean(8);
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.supporter", "playerData.yml", data.toString());
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.id", "playerData.yml", "");
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.username", "playerData.yml","");
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.nickname", "playerData.yml", "");
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.role", "playerData.yml", "");
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.founder", "playerData.yml", "");
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.birthday", "playerData.yml", "");
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.nitroboost", "playerData.yml", "");
				Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.supporter", "playerData.yml", "");
			}
			if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '" + player.getUniqueId().toString() + "';") == 1) 
			{
				ResultSet discord_2fa = Database_Bungee.QueryCommand("SELECT current_status FROM discord_2fa WHERE uuid = '" +  player.getUniqueId().toString() +"';");
				try
				{
					discord_2fa.next();
					Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.2fa", "playerData.yml", discord_2fa.getString(1));
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else Common_Bungee.SetField(player.getUniqueId().toString() + ".discord.2fa", "playerData.yml", "");
		}
	}
}
