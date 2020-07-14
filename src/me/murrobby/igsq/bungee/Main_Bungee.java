package me.murrobby.igsq.bungee;

import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

import me.murrobby.igsq.bungee.main.ChatEvent_Bungee;
import me.murrobby.igsq.bungee.main.PostLoginEvent_Bungee;
import net.md_5.bungee.api.plugin.Plugin;

public class Main_Bungee extends Plugin
{
	@Override
	public void onEnable()
	{
		Common_Bungee.bungee = this;
		Common_Bungee.CreateFile("config.yml");
		Common_Bungee.LoadConfiguration();
		Common_Bungee.CreateFile("playerData.yml");
		Common_Bungee.CreateFile("internal.yml");
    	this.getProxy().getScheduler().schedule(this, new Runnable() 
    	{

			@Override
			public void run() {
				ResultSet mc_accounts = Database_Bungee.QueryCommand("SELECT * FROM mc_accounts;");
				try {
					while(mc_accounts.next()) 
					{
						ResultSet discord_2fa = Database_Bungee.QueryCommand("SELECT * FROM discord_2fa WHERE uuid = '"+ mc_accounts.getString(1) +"';");
						String uuid = mc_accounts.getString(1);
						String twoFAStatus = "off";
						if(discord_2fa.next()) 
						{
							twoFAStatus = discord_2fa.getString(2);
						}
						Common_Bungee.SetField(uuid + ".2fa","playerData.yml", twoFAStatus);
					}
				}
				catch (Exception e)
				{
					System.out.println("DATABASE ERROR: " + e.toString());
				}				
				
			}
			
    		
    	}, 5, 10, TimeUnit.SECONDS);
    	
		new Database_Bungee(this);
		new PostLoginEvent_Bungee(this);
		//new ServerConnectedEvent_Bungee(this);
		new ChatEvent_Bungee(this);
	}

	public void onLoad()
	{
		
	}
	
	public void onDisable()
	{
		this.getProxy().getScheduler().cancel(this);
		this.getProxy().getPluginManager().unregisterListeners(this);
	}
	
}
