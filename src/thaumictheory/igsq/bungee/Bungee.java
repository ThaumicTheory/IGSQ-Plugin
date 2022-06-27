package thaumictheory.igsq.bungee;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import thaumictheory.igsq.bungee.commands.Link_Command;
import thaumictheory.igsq.bungee.commands.Test_Command;
import thaumictheory.igsq.bungee.commands.TwoFA_Command;
import thaumictheory.igsq.bungee.lp.Main_LP;
import thaumictheory.igsq.bungee.main.ChatEvent_Bungee;
import thaumictheory.igsq.bungee.main.PluginMessageEvent_Bungee;
import thaumictheory.igsq.bungee.main.PostLoginEvent_Bungee;
import thaumictheory.igsq.bungee.main.ServerKickEvent_Bungee;
import thaumictheory.igsq.bungee.security.Main_Security;
import thaumictheory.igsq.bungee.yaml.Yaml;
import thaumictheory.igsq.bungee.yaml.YamlWrapper;
import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlRoleWrapper;

public class Bungee extends Plugin
{
	@Override
	public void onEnable()
	{
		this.getProxy().registerChannel("igsq:yaml");
		this.getProxy().registerChannel("igsq:yamlreq");
		this.getProxy().registerChannel("igsq:sound");
		
		
		Common.bungee = this;
		IGSQ.getYaml().createFiles();
		IGSQ.getYaml().loadAllFiles();
		YamlWrapper.applyDefault();
		YamlRoleWrapper.applyDefault(0);
    	
    	this.getProxy().getScheduler().schedule(this, new Runnable() 
    	{

			@Override
			public void run() 
			{
				IGSQ.getYaml().saveAllFiles();
				IGSQ.getYaml().loadAllFiles();
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
		    			Communication.sendTargetedConfigUpdate("server","internal.yaml", player.getServer().getInfo().getName(),player);
		    		}
		    		
		    	}
		    	
			}
    		
    	}, 5, 30, TimeUnit.SECONDS);
    	
		new Database();
		new PostLoginEvent_Bungee();
		new ChatEvent_Bungee();
		new ServerKickEvent_Bungee();
		new PluginMessageEvent_Bungee();
		
		new Main_LP();
		new Main_Security();
		getProxy().getPluginManager().registerCommand(this,new Link_Command());
		getProxy().getPluginManager().registerCommand(this,new TwoFA_Command());
		getProxy().getPluginManager().registerCommand(this,new Test_Command());
	}

	public void onLoad()
	{
		new IGSQ(new Yaml(), new BungeeImplementor());
	}
	
	public void onDisable()
	{
		this.getProxy().getScheduler().cancel(this);
		this.getProxy().getPluginManager().unregisterListeners(this);
		this.getProxy().getPluginManager().unregisterCommands(this);
		IGSQ.getYaml().saveAllFiles();
		IGSQ.getYaml().discardAllFiles();
	}
	
}
