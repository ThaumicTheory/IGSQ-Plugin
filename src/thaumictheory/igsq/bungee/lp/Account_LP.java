package thaumictheory.igsq.bungee.lp;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.Database;
import thaumictheory.igsq.bungee.security.Main_Security;
import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.shared.YamlRoleWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Account_LP
{	
	ScheduledTask discordLinkTask;
	final int taskID;
	
	public Account_LP(int taskID) 
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
				discordLink();
				if(Main_Security.taskID != taskID) 
				{
					discordLinkTask.cancel();
					System.out.println("Task: \"Account Security\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 5, 1, TimeUnit.SECONDS);
	}
	private void discordLink() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
			if(Database.scalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() + "' AND current_status = 'linked';") == 1) 
			{
				ResultSet discord_accounts = Database.queryCommand("SELECT * FROM discord_accounts WHERE id = (SELECT id FROM linked_accounts WHERE uuid = '" +  player.getUniqueId().toString() +"');");
				try
				{
					if(discord_accounts.next()) 
					{
						yaml.setID(discord_accounts.getString(1));
						yaml.setUsername(discord_accounts.getString(2));
						yaml.setNickname(discord_accounts.getString(3));
						int roleBinary = discord_accounts.getInt(4);
						if(roleBinary != yaml.getRoles()) //roles have updated
						{
							for(int i = 0; i < Integer.toBinaryString(roleBinary).length();i++) YamlRoleWrapper.applyDefault(i);
							Common_LP.updateRoles(player, roleBinary);
						}
						yaml.setRoles(roleBinary);
					}
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
				if(yaml.getRoles() != 0) Common_LP.updateRoles(player, 0);
				yaml.setRoles(0);
			}
		}
	}
}
