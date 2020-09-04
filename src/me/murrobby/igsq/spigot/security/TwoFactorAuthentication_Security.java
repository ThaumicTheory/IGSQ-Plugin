package me.murrobby.igsq.spigot.security;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Database_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TwoFactorAuthentication_Security
{	
	Main_Spigot plugin;
	int twofaTask = -1;
	final int taskID;
	
	public TwoFactorAuthentication_Security(Main_Spigot plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		TwoFactorAuthenticationQuery();
	}
	private void TwoFactorAuthenticationQuery() 
	{
		twofaTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
    	{

			@Override
			public void run() 
			{
				TwoFactorAuthentication();
				if(Main_Security.taskID != taskID) 
				{
					plugin.scheduler.cancelTask(twofaTask);
					System.out.println("Task: \"Two Factor Authentication Security\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 20);
	}
	private void TwoFactorAuthentication() 
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if(Database_Spigot.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '" + player.getUniqueId().toString() + "';") == 1) 
			{
				ResultSet discord_2fa = Database_Spigot.QueryCommand("SELECT current_status,code FROM discord_2fa WHERE uuid = '" +  player.getUniqueId().toString() +"';");
				try
				{
					discord_2fa.next();
					Common_Spigot.updateField(player.getUniqueId().toString() + ".discord.2fa.status","player",discord_2fa.getString(1));
					Common_Spigot.updateField(player.getUniqueId().toString() + ".discord.2fa.code","player",discord_2fa.getString(2));
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				Common_Spigot.updateField(player.getUniqueId().toString() + ".discord.2fa.status","player","");
				Common_Spigot.updateField(player.getUniqueId().toString() + ".discord.2fa.code","player","");
			}
		}
	}
}
