package me.murrobby.igsq.spigot;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.murrobby.igsq.spigot.expert.Main_Expert;
import me.murrobby.igsq.spigot.main.AsyncPlayerChatEvent_Main;
import me.murrobby.igsq.spigot.main.InventoryClickEvent_Main;
import me.murrobby.igsq.spigot.main.PlayerCommandPreprocessEvent_Main;
import me.murrobby.igsq.spigot.main.PlayerJoinEvent_Main;
import me.murrobby.igsq.spigot.security.Main_Security;
import me.murrobby.igsq.spigot.commands.Main_Command;

import java.sql.ResultSet;
import java.util.Random;
import java.util.UUID;

public class Main_Spigot extends JavaPlugin{
	public BukkitScheduler scheduler = getServer().getScheduler();
	Random random = new Random();
	@Override
	public void onEnable()
	{
		Common_Spigot.plugin = this;
		Common_Spigot.loadConfiguration();
		Common_Spigot.createPlayerData();
		Common_Spigot.createInternalData();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable()
    	{

			@Override
			public void run() 
			{
				ResultSet player_command_communicator = Database_Spigot.QueryCommand("SELECT * FROM player_command_communicator;");
				try 
				{
					while(player_command_communicator.next()) 
					{
						int command_number = player_command_communicator.getInt(1);
						String command = player_command_communicator.getString(2);
						Player player = Bukkit.getPlayer(UUID.fromString(player_command_communicator.getString(3)));
						String arg1 = player_command_communicator.getString(4);
						String arg2 = player_command_communicator.getString(5);
						String arg3 = player_command_communicator.getString(6);

						if(player != null) //Player is Online
						{
							Database_Spigot.UpdateCommand("DELETE FROM player_command_communicator WHERE command_number = " + command_number + ";");
					    	switch(command.toLowerCase())
					    	{
					    	  	case "sound":
					    	  		player.playSound(player.getLocation(), Sound.valueOf(arg1), Float.parseFloat(arg2), Float.parseFloat(arg3));
					    	  		break;
					    	  	case "message":
					    	  		player.sendMessage(arg1);
					    	  		break;
					    	  	default:
					    	  		break;
					    	}
						}
					}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			} 		
    	}, 0, 20);
		
		
		new Database_Spigot(this);
		
		new Main_Command(this);
		
		
		new PlayerJoinEvent_Main(this);
		new AsyncPlayerChatEvent_Main(this);
		new InventoryClickEvent_Main(this);
		new PlayerCommandPreprocessEvent_Main(this);
		
		new Main_Expert(this);
		new Main_Security(this);
	}

	public void onLoad()
	{
	}
	
	public void onDisable()
	{
		this.getServer().getScheduler().cancelTasks(this);
		this.getServer().getPluginManager().disablePlugin(this);
	}
	
}
