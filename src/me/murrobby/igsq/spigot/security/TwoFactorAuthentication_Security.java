package me.murrobby.igsq.spigot.security;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Database_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

import java.sql.ResultSet;
import java.util.Random;

public class TwoFactorAuthentication_Security
{	
	Main_Spigot plugin;
	Random random = new Random();
	int twofaTask = -1;
	final int taskID;
	
	public TwoFactorAuthentication_Security(Main_Spigot plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		EnderDragonQuery();
	}
	private void EnderDragonQuery() 
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
		ResultSet discord_2fa = Database_Spigot.QueryCommand("SELECT * FROM discord_2fa;");
		try 
		{
			if(discord_2fa.next()) 
			{
				String uuid = discord_2fa.getString(1);
				String current_status = discord_2fa.getString(2);
				Common_Spigot.playerData.set(uuid + ".2fa",current_status);
			}
			Common_Spigot.playerData.save(Common_Spigot.playerDataFile);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
