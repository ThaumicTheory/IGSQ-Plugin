package me.murrobby.igsq.spigot;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;

import me.murrobby.igsq.spigot.expert.Main_Expert;
import me.murrobby.igsq.spigot.lp.Main_LP;
import me.murrobby.igsq.spigot.main.AsyncPlayerChatEvent_Main;
import me.murrobby.igsq.spigot.main.CreatureSpawnEvent_Main;
import me.murrobby.igsq.spigot.main.EntityDamageEvent_Main;
import me.murrobby.igsq.spigot.main.EntityDeathEvent_Main;
import me.murrobby.igsq.spigot.main.InventoryClickEvent_Main;
import me.murrobby.igsq.spigot.main.LoggerHandler_Main;
import me.murrobby.igsq.spigot.main.PlayerCommandPreprocessEvent_Main;
import me.murrobby.igsq.spigot.main.PlayerJoinEvent_Main;
import me.murrobby.igsq.spigot.main.PlayerQuitEvent_Main;
import me.murrobby.igsq.spigot.security.Main_Security;
import me.murrobby.igsq.spigot.smp.Main_SMP;
import me.murrobby.igsq.spigot.blockhunt.Main_BlockHunt;
import me.murrobby.igsq.spigot.commands.Main_Command;
import me.murrobby.igsq.spigot.commands.Team_Command;

public class Spigot extends JavaPlugin implements PluginMessageListener
{
	public BukkitScheduler scheduler = getServer().getScheduler();
	@Override
	public void onEnable()
	{ 
		Messaging.createLog("Plugin Starting!");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "igsq:yml", this);
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "igsq:sound", this);
		
		new Database(this);
		
		
		new PlayerJoinEvent_Main();
		new InventoryClickEvent_Main();
		new PlayerCommandPreprocessEvent_Main();
		new EntityDeathEvent_Main();
		new AsyncPlayerChatEvent_Main();
		new EntityDamageEvent_Main();
		new PlayerQuitEvent_Main();
		new LoggerHandler_Main();
		new CreatureSpawnEvent_Main();
		
		new Main_Expert();
		new Main_SMP();
		new Main_Security();
		new Main_Command();
		new Team_Command();
		if(this.getServer().getPluginManager().getPlugin("LuckPerms") != null && YamlWrapper.isLuckpermsSupported()) 
		{
			Messaging.createLog("Luckperms Module Enabled.");
			new Main_LP(this);
			YamlWrapper.setDefaultChatController("mainlp");
			YamlWrapper.setDefaultNameController("main");
		}
		else 
		{
			if(YamlWrapper.isLuckpermsSupported()) Messaging.createLog(Level.WARNING,"Luckperms Support is on but the plugin cannot be located.");
			Messaging.createLog("Luckperms Module Disabled.");
			YamlWrapper.setDefaultChatController("main");
			YamlWrapper.setDefaultNameController("none");
		}
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
					Yaml.saveFileChanges("@all");
					Yaml.loadFile("@all");
			} 		
    	}, 600, 600);
		for(Player player : Bukkit.getOnlinePlayers()) Communication.setDefaultTagDataReload(player);
		Messaging.createLog("Plugin Started.");
	}

	public void onLoad()
	{
		Common.spigot = this;
		
		Common.logger = new LoggerHandler_Main();
		Logger.getLogger("Minecraft").addHandler(Common.logger);
		Common.spigot.getLogger().addHandler(Common.logger);
		Messaging.createSafeLog("Plugin Started Preparing.");
		Yaml.createFiles();
		Yaml.loadFile("@all");
		YamlWrapper.applyDefault();
		FutureScheduler.applyDefault();
		Messaging.createLog("Plugin Finished Preparing.");
		
	}
	
	public void onDisable()
	{
		Messaging.createLog("Shutting down.");
		
		for(Player player : Bukkit.getOnlinePlayers()) Communication.deletePlayer(player);
		BlockCluster.cleanup();
		this.getServer().getScheduler().cancelTasks(this);
		Yaml.saveFileChanges("@all");
		Yaml.disgardAndCloseFile("@all");
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
