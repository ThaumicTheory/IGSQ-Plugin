package me.murrobby.igsq.spigot;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;

import me.murrobby.igsq.spigot.expert.Main_Expert;
import me.murrobby.igsq.spigot.lp.Main_LP;
import me.murrobby.igsq.spigot.main.AsyncPlayerChatEvent_Main;
import me.murrobby.igsq.spigot.main.EntityDeathEvent_Main;
import me.murrobby.igsq.spigot.main.InventoryClickEvent_Main;
import me.murrobby.igsq.spigot.main.PlayerCommandPreprocessEvent_Main;
import me.murrobby.igsq.spigot.main.PlayerJoinEvent_Main;
import me.murrobby.igsq.spigot.security.Main_Security;
import me.murrobby.igsq.spigot.commands.Main_Command;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Main_Spigot extends JavaPlugin implements PluginMessageListener{
	public BukkitScheduler scheduler = getServer().getScheduler();
	@Override
	public void onEnable()
	{ 
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "igsq:yml", this);
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "igsq:sound", this);
		Common_Spigot.spigot = this;
		Common_Spigot.createFiles();
		Common_Spigot.loadFile("@all");
		Common_Spigot.applyDefaultConfiguration();
		/*
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
					    	  	case "mention":
					    	  		player.sendMessage(Common_Spigot.chatFormatter("&#FF00FF" + arg3 + " &#A900FFMentioned You In &#FF00FF" + arg2 + " &#A900FFSaying &#C8C8FF" + arg1));
					    	  		break;
					    	  	default:
					    	  		break;
					    	}
						}
					}
				}
				catch (Exception e) 
				{
					Common_Spigot.sendException(e,"Command communicator error","OAK_LOG",null);
				}
			} 		
    	}, 0, 20);
    	*/
		scheduler.scheduleSyncRepeatingTask(this, new Runnable()
    	{

			@Override
			public void run() 
			{
				Common_Spigot.saveFileChanges("@all");
				Common_Spigot.loadFile("@all");
			} 		
    	}, 20, 600);
		
		
		new Database_Spigot(this);
		
		
		
		new PlayerJoinEvent_Main(this);
		new InventoryClickEvent_Main(this);
		new PlayerCommandPreprocessEvent_Main(this);
		new EntityDeathEvent_Main(this);
		
		new Main_Expert(this);
		new Main_Security(this);
		new Main_Command(this);
		Boolean nametagEdit = false;
		if(this.getServer().getPluginManager().getPlugin("NametagEdit") != null && Common_Spigot.getFieldBool("SUPPORT.nametagedit", "config")) 
		{
			System.out.println("NametagEdit Hook in Luckperms Module Enabled.");
			nametagEdit = true;
		}
		else System.out.println("NametagEdit Hook in Luckperms Module Disabled.");
		if(this.getServer().getPluginManager().getPlugin("LuckPerms") != null && Common_Spigot.getFieldBool("SUPPORT.luckperms", "config")) 
		{
			System.out.println("Luckperms Module Enabled.");
			new Main_LP(this,nametagEdit);
		}
		else 
		{
			System.out.println("Luckperms Module Disabled.");
			new AsyncPlayerChatEvent_Main(this);
		}
	}

	public void onLoad()
	{
	}
	
	public void onDisable()
	{
		this.getServer().getScheduler().cancelTasks(this);
		this.getServer().getPluginManager().disablePlugin(this);
		Common_Spigot.saveFileChanges("@all");
		Common_Spigot.disgardAndCloseFile("@all");
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		if(channel.equals("igsq:yml")) 
		{
	        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			try
			{
				String fileName = in.readUTF();
				String path = in.readUTF();
				String data = in.readUTF();
				if(data.equalsIgnoreCase("false") || data.equalsIgnoreCase("true")) Common_Spigot.updateField(path, fileName, Boolean.valueOf(data));
				else if(Integer.getInteger(data) != null) Common_Spigot.updateField(path, fileName, Integer.getInteger(data));
				else Common_Spigot.updateField(path, fileName, data);
			}
			catch (IOException e)
			{
				Common_Spigot.sendException(e,"Plugin Messaging Channel For Configuration Failed.","REDSTONE_LAMP", player);
			}
		}
		else if(channel.equals("igsq:sound")) 
		{
	        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			try
			{
				String sound = in.readUTF();
				float volume = in.readFloat();
				float pitch = in.readFloat();
				player.playSound(player.getLocation(), Sound.valueOf(sound.toUpperCase()), volume, pitch);
			}
			catch (IOException e)
			{
				Common_Spigot.sendException(e,"Plugin Messaging Channel For Sound Failed.","GLOWSTONE", player);
			}
		}
	}
	
}
