package me.murrobby.igsq.bungee;

import java.util.concurrent.TimeUnit;

import me.murrobby.igsq.bungee.commands.Link_Command;
import me.murrobby.igsq.bungee.commands.Test_Command;
import me.murrobby.igsq.bungee.commands.TwoFA_Command;
import me.murrobby.igsq.bungee.lp.Main_LP;
import me.murrobby.igsq.bungee.main.ChatEvent_Bungee;
import me.murrobby.igsq.bungee.main.PluginMessageEvent_Bungee;
import me.murrobby.igsq.bungee.main.PostLoginEvent_Bungee;
import me.murrobby.igsq.bungee.main.ServerKickEvent_Bungee;
import me.murrobby.igsq.bungee.security.Main_Security;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Bungee extends Plugin
{
	@Override
	public void onEnable()
	{
		this.getProxy().registerChannel("igsq:yml");
		this.getProxy().registerChannel("igsq:ymlreq");
		this.getProxy().registerChannel("igsq:sound");
		
		
		Common.bungee = this;
		Yaml.createFiles();
		Yaml.loadFile("@all");
		YamlWrapper.applyDefault();	
    	
    	this.getProxy().getScheduler().schedule(this, new Runnable() 
    	{

			@Override
			public void run() 
			{
				Yaml.saveFileChanges("@all");
				Yaml.loadFile("@all");
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
		    			Communication.sendConfigUpdate("server","internal", player.getServer().getInfo().getName(),player);
		    		}
		    		
		    	}
		    	
			}
    		
    	}, 5, 30, TimeUnit.SECONDS);
    	
		new Database();
		new PostLoginEvent_Bungee();
		new ChatEvent_Bungee();
		new ServerKickEvent_Bungee();
		new PluginMessageEvent_Bungee();
		
		new Main_Security();
		getProxy().getPluginManager().registerCommand(this,new Link_Command());
		getProxy().getPluginManager().registerCommand(this,new TwoFA_Command());
		getProxy().getPluginManager().registerCommand(this,new Test_Command());
		if(this.getProxy().getPluginManager().getPlugin("LuckPerms") != null && YamlWrapper.isLuckpermsSupported()) 
		{
			System.out.println("Luckperms Module Enabled.");
			new Main_LP();
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
		Yaml.saveFileChanges("@all");
		Yaml.disgardAndCloseFile("@all");
	}
	
}
