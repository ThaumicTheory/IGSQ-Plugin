package me.murrobby.igsq.bungee.security;

import me.murrobby.igsq.bungee.Yaml;
import me.murrobby.igsq.bungee.Common;
import me.murrobby.igsq.bungee.Database;
import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class TwoFactorAuthentication_Security
{	
	ScheduledTask twofaTask;
	final int taskID;
	
	public TwoFactorAuthentication_Security(int taskID) 
	{
		this.taskID = taskID;
		TwoFactorAuthenticationQuery();
	}
	private void TwoFactorAuthenticationQuery() 
	{
		twofaTask = Common.bungee.getProxy().getScheduler().schedule(Common.bungee, new Runnable() 
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
			if(Database.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '" + player.getUniqueId().toString() + "';") == 1) 
			{
				if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
				{
					ResultSet discord_2fa = Database.QueryCommand("SELECT * FROM discord_2fa WHERE uuid = '" +  player.getUniqueId().toString() +"';");
					try
					{
						discord_2fa.next();
						String current_status = Common_Shared.removeNull(discord_2fa.getString(2));
						if(current_status.equalsIgnoreCase("expired") || (player.hasPermission("igsq.require2fa") && current_status.equalsIgnoreCase(""))) //If staff 2FA should be enabled but is not already or should check be re-established
						{
							Database.UpdateCommand("UPDATE discord_2fa SET current_status = 'pending' WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							current_status = "pending";
						}
						Yaml.updateField(player.getUniqueId().toString() + ".discord.2fa.status", "player", current_status);
						Yaml.updateField(player.getUniqueId().toString() + ".discord.2fa.code", "player", discord_2fa.getString(3));
						String[] socket = player.getPendingConnection().getSocketAddress().toString().split(":");
						socket[0] = Common_Shared.removeBeforeCharacter(socket[0], '/');
						String ip = Common_Shared.removeNull(discord_2fa.getString(4));
						String serverIP = Common_Shared.removeNull(discord_2fa.getString(4));
						if(socket.length==2 && (!serverIP.equals(socket[0])) && current_status.equalsIgnoreCase("accepted")) 
						{
							Database.UpdateCommand("UPDATE discord_2fa SET current_status = 'pending', ip = NULL WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							ip = "";
						}
						Yaml.updateField(player.getUniqueId().toString() + ".discord.2fa.ip", "player", ip);
					}
					catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else Database.UpdateCommand("DELETE FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';"); //Update Database to remove 2fa link if main link is not found
			}
			else
			{
				Yaml.updateField(player.getUniqueId().toString() + ".discord.2fa.status", "player", "");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.2fa.code", "player", "");
				Yaml.updateField(player.getUniqueId().toString() + ".discord.2fa.ip", "player", "");
				if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) Database.UpdateCommand("INSERT INTO discord_2fa (uuid) VALUES('"+ player.getUniqueId().toString() +"');");//2FA record doesnt exist but should
			}
		}
	}
}
