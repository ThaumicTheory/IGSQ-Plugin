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
    	}, 6, 1, TimeUnit.SECONDS);
	}
	private void TwoFactorAuthentication() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '" + player.getUniqueId().toString() + "';") == 1) 
			{
				if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
				{
					ResultSet discord_2fa = Database_Bungee.QueryCommand("SELECT * FROM discord_2fa WHERE uuid = '" +  player.getUniqueId().toString() +"';");
					try
					{
						discord_2fa.next();
						String current_status = Common_Bungee.removeNull(discord_2fa.getString(2));
						if(current_status.equalsIgnoreCase("expired") || (player.hasPermission("igsq.require2fa") && current_status.equalsIgnoreCase(""))) //If staff 2FA should be enabled but is not already or should check be re-established
						{
							Database_Bungee.UpdateCommand("UPDATE discord_2fa SET current_status = 'pending' WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							current_status = "pending";
						}
						Common_Bungee.UpdateField(player.getUniqueId().toString() + ".discord.2fa.status", "playerData", current_status);
						Common_Bungee.UpdateField(player.getUniqueId().toString() + ".discord.2fa.code", "playerData", discord_2fa.getString(3));
						String[] socket = player.getPendingConnection().getSocketAddress().toString().split(":");
						socket[0] = Common_Bungee.RemoveBeforeCharacter(socket[0], '/');
						String ip = Common_Bungee.removeNull(discord_2fa.getString(4));
						String serverIP = Common_Bungee.removeNull(discord_2fa.getString(4));
						if(socket.length==2 && (!serverIP.equals(socket[0])) && current_status.equalsIgnoreCase("accepted")) 
						{
							Database_Bungee.UpdateCommand("UPDATE discord_2fa SET current_status = 'pending', ip = NULL WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							ip = "";
						}
						Common_Bungee.UpdateField(player.getUniqueId().toString() + ".discord.2fa.ip", "playerData", ip);
					}
					catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else Database_Bungee.UpdateCommand("DELETE FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';"); //Update Database to remove 2fa link if main link is not found
			}
			else
			{
				Common_Bungee.UpdateField(player.getUniqueId().toString() + ".discord.2fa.status", "playerData", "");
				Common_Bungee.UpdateField(player.getUniqueId().toString() + ".discord.2fa.code", "playerData", "");
				Common_Bungee.UpdateField(player.getUniqueId().toString() + ".discord.2fa.ip", "playerData", "");
				if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) Database_Bungee.UpdateCommand("INSERT INTO discord_2fa (uuid) VALUES('"+ player.getUniqueId().toString() +"');");//2FA record doesnt exist but should
			}
		}
	}
}
