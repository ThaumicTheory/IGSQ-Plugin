package thaumictheory.igsq.spigot;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;

import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlRoleWrapper;
import thaumictheory.igsq.spigot.blockhunt.Main_BlockHunt;
import thaumictheory.igsq.spigot.commands.Main_Command;
import thaumictheory.igsq.spigot.commands.Team_Command;
import thaumictheory.igsq.spigot.expert.Main_Expert;
import thaumictheory.igsq.spigot.main.LoggerHandler_Main;
import thaumictheory.igsq.spigot.main.Main_Main;
import thaumictheory.igsq.spigot.security.Main_Security;
import thaumictheory.igsq.spigot.smp.BlockCluster_SMP;
import thaumictheory.igsq.spigot.smp.Main_SMP;
import thaumictheory.igsq.spigot.yaml.Yaml;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class Spigot extends JavaPlugin implements PluginMessageListener
{
	public BukkitScheduler scheduler = getServer().getScheduler();
	@Override
	public void onEnable()
	{ 
		Messaging.createLog("Plugin Starting!");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "igsq:yaml", this);
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "igsq:sound", this);
		
		new Main_Main();
		
		new Main_Expert();
		new Main_SMP();
		new Main_Security();
		new Main_Command();
		new Team_Command();
		YamlWrapper.setDefaultChatController("main");
		YamlWrapper.setDefaultNameController("main");
		if(YamlWrapper.isBlockHunt()) 
		{
			Messaging.createLog("BlockHunt enabled.");
			new Main_BlockHunt();
		}
		else 
		{
			Messaging.createLog("BlockHunt disabled.");
		}
		
		scheduler.scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run() 
			{
				IGSQ.getYaml().saveAllFiles();
				IGSQ.getYaml().loadAllFiles();
			} 		
    	}, 600, 600);
		for(Player player : Bukkit.getOnlinePlayers()) Communication.setDefaultTagDataReload(player);
		Messaging.createLog("Plugin Started.");
	}

	public void onLoad()
	{
		new IGSQ(new Yaml(),new SpigotImplementor());
		Common.spigot = this;
		Common.logger = new LoggerHandler_Main();
		Logger.getLogger("Minecraft").addHandler(Common.logger);
		Common.spigot.getLogger().addHandler(Common.logger);
		Messaging.createSafeLog("Plugin Started Preparing.");
		IGSQ.getYaml().createFiles();
		IGSQ.getYaml().loadAllFiles();
		YamlWrapper.applyDefault();
		FutureScheduler.applyDefault();
		YamlRoleWrapper.applyDefault(0);
		Messaging.createLog("Plugin Finished Preparing.");
		
	}
	
	public void onDisable()
	{
		Messaging.createLog("Shutting down.");
		
		for(Player player : Bukkit.getOnlinePlayers()) Communication.deletePlayer(player);
		BlockCluster_SMP.cleanup();
		this.getServer().getScheduler().cancelTasks(this);
		IGSQ.getYaml().saveAllFiles();
		IGSQ.getYaml().discardAllFiles();
		this.getServer().getPluginManager().disablePlugin(this);
		
		Logger.getLogger("Minecraft").removeHandler(Common.logger);
		Common.spigot.getLogger().removeHandler(Common.logger);
		Messaging.createSafeLog("Shutdown Complete.");
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		Communication.onPluginMessageReceived(channel, player, message);
	}
	
}
