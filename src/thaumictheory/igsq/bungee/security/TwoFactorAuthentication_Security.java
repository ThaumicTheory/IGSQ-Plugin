package thaumictheory.igsq.bungee.security;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.Database;
import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlPlayerWrapper;

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
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
			if(Database.scalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '" + player.getUniqueId().toString() + "';") == 1) 
			{
				if(Database.scalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
				{
					ResultSet discord_2fa = Database.queryCommand("SELECT * FROM discord_2fa WHERE uuid = '" +  player.getUniqueId().toString() +"';");
					try
					{
						discord_2fa.next();
						String current_status = IGSQ.removeNull(discord_2fa.getString(2));
						if(current_status.equalsIgnoreCase("expired") || (player.hasPermission("igsq.require2fa") && current_status.equalsIgnoreCase(""))) //If staff 2FA should be enabled but is not already or should check be re-established
						{
							Database.updateCommand("UPDATE discord_2fa SET current_status = 'pending' WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							current_status = "pending";
						}
						yaml.setStatus(current_status);
						yaml.setCode(discord_2fa.getString(3));
						String[] socket = player.getPendingConnection().getSocketAddress().toString().split(":");
						socket[0] = IGSQ.removeBeforeCharacter(socket[0], '/');
						String ip = IGSQ.removeNull(discord_2fa.getString(4));
						String serverIP = IGSQ.removeNull(discord_2fa.getString(4));
						if(socket.length==2 && (!serverIP.equals(socket[0])) && current_status.equalsIgnoreCase("accepted")) 
						{
							Database.updateCommand("UPDATE discord_2fa SET current_status = 'pending', ip = NULL WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							ip = "";
						}
						yaml.setLastLoginIP(ip);
					}
					catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else Database.updateCommand("DELETE FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';"); //Update Database to remove 2fa link if main link is not found
			}
			else
			{
				yaml.setStatus("");
				yaml.setCode("");
				yaml.setLastLoginIP("");
				if(Database.scalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) Database.updateCommand("INSERT INTO discord_2fa (uuid) VALUES('"+ player.getUniqueId().toString() +"');");//2FA record doesnt exist but should
			}
		}
	}
}
