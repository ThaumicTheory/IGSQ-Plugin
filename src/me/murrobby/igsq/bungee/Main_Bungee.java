package me.murrobby.igsq.bungee;

import java.util.concurrent.TimeUnit;

import me.murrobby.igsq.bungee.commands.Link_Command;
import me.murrobby.igsq.bungee.commands.Test_Command;
import me.murrobby.igsq.bungee.commands.TwoFA_Command;
import me.murrobby.igsq.bungee.lp.Main_LP;
import me.murrobby.igsq.bungee.main.ChatEvent_Bungee;
import me.murrobby.igsq.bungee.main.PostLoginEvent_Bungee;
import me.murrobby.igsq.bungee.main.ServerKickEvent_Bungee;
import me.murrobby.igsq.bungee.security.Main_Security;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main_Bungee extends Plugin
{
	@Override
	public void onEnable()
	{
		this.getProxy().registerChannel("igsq:yml");
		this.getProxy().registerChannel("igsq:sound");
		
		
		Common_Bungee.bungee = this;
		Common_Bungee.createFiles();
		Common_Bungee.loadFile("@all");
		Common_Bungee.applyDefaultConfiguration();	
    	
    	this.getProxy().getScheduler().schedule(this, new Runnable() 
    	{

			@Override
			public void run() 
			{
				Common_Bungee.saveFileChanges("@all");
				Common_Bungee.loadFile("@all");
				String[] servers = new String[0];
		    	for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) 
		    	{
		    		Boolean serverChecked = false;
		    		for (String serversDone : servers) 
		    		{
		    			if(serversDone.equals(player.getServer().getInfo().getName())) 
		    			{
		    				serverChecked = true;
		    				break;
		    			}
		    		}
		    		if((!serverChecked) && player.getServer() != null) 
		    		{
		    			Common_Bungee.sendConfigUpdate("server","internal", player.getServer().getInfo().getName(),player);
		    		}
		    		
		    	}
		    	
			}
    		
    	}, 5, 30, TimeUnit.SECONDS);
    	
		new Database_Bungee(this);
		new PostLoginEvent_Bungee(this);
		new ChatEvent_Bungee(this);
		new ServerKickEvent_Bungee(this);
		
		new Main_Security(this);
		getProxy().getPluginManager().registerCommand(this,new Link_Command());
		getProxy().getPluginManager().registerCommand(this,new TwoFA_Command());
		getProxy().getPluginManager().registerCommand(this,new Test_Command());
		if(this.getProxy().getPluginManager().getPlugin("LuckPerms") != null && Common_Bungee.getFieldString("SUPPORT.luckperms", "config").equalsIgnoreCase("true")) 
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
		Common_Bungee.saveFileChanges("@all");
		Common_Bungee.disgardAndCloseFile("@all");
	}
	
}
