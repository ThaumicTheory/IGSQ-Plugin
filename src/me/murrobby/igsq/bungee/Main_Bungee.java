package me.murrobby.igsq.bungee;

import java.util.concurrent.TimeUnit;

import me.murrobby.igsq.bungee.commands.Link_Command;
import me.murrobby.igsq.bungee.commands.TwoFA_Command;
import me.murrobby.igsq.bungee.lp.Main_LP;
import me.murrobby.igsq.bungee.main.ChatEvent_Bungee;
import me.murrobby.igsq.bungee.main.PostLoginEvent_Bungee;
import me.murrobby.igsq.bungee.main.ServerKickEvent_Bungee;
import me.murrobby.igsq.bungee.security.Main_Security;
import net.md_5.bungee.api.plugin.Plugin;

public class Main_Bungee extends Plugin
{
	@Override
	public void onEnable()
	{
		Common_Bungee.bungee = this;
		Common_Bungee.CreateFiles();
		Common_Bungee.LoadFile("all");
		Common_Bungee.LoadConfiguration();
    	
    	this.getProxy().getScheduler().schedule(this, new Runnable() 
    	{

			@Override
			public void run() 
			{
				Common_Bungee.SaveFileChanges("all");
				Common_Bungee.LoadFile("all");
			}
			
    		
    	}, 5, 30, TimeUnit.SECONDS);
    	
		new Database_Bungee(this);
		new PostLoginEvent_Bungee(this);
		new ChatEvent_Bungee(this);
		new ServerKickEvent_Bungee(this);
		
		new Main_Security(this);
		getProxy().getPluginManager().registerCommand(this,new Link_Command());
		getProxy().getPluginManager().registerCommand(this,new TwoFA_Command());
		if(this.getProxy().getPluginManager().getPlugin("LuckPerms") != null && Common_Bungee.GetFieldString("SUPPORT.luckperms", "config").equalsIgnoreCase("true")) 
		{
			System.out.println("Luckperms Module Enabled.");
			new Main_LP(this);
		}
		else 
		{
			System.out.println("Luckperms Module Disabled.");
		}
	}

	public void onLoad()
	{
		
	}
	
	public void onDisable()
	{
		this.getProxy().getScheduler().cancel(this);
		this.getProxy().getPluginManager().unregisterListeners(this);
		this.getProxy().getPluginManager().unregisterCommands(this);
		Common_Bungee.SaveFileChanges("all");
		Common_Bungee.DisgardAndCloseFile("all");
	}
	
}
