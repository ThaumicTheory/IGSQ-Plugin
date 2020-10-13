package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Configuration;
import me.murrobby.igsq.spigot.Spigot;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.expert.Main_Expert;

public class Main_Command implements CommandExecutor, TabCompleter{
	private Spigot plugin;
	private Player player;
	private CommandSender sender;
	private String[] args = new String[0];
	
	private int realtimeTask = -1;
	
	public Main_Command(Spigot plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("igsq").setExecutor(this);
		plugin.getCommand("igsq").setTabCompleter(this);
	}
	@Override 
	
	public boolean onCommand(CommandSender sender,Command command,String label,String[] args) 
	{
		this.sender = sender;
		if(args.length == 0) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#CD0000Please Specify a command! Type &#FF0000/igsq Help &#CD0000to see available commands"));
			return false;
		}
		//Detect which arguments are used in the /igsq command
		this.args = Common_Shared.depend(args, 0);
    	switch(args[0].toLowerCase()) 
    	{
  	  		case "version":
  	  			Version_Command version = new Version_Command(sender,this.args);
  	  			return version.result;
  	  		case "nightvision":
  	  		case "nv":
  	  			NightVision_Command nightvision = new NightVision_Command(sender,this.args);
  	  			return nightvision.result;
  	  		case "block":
  	  			Block_Command block = new Block_Command(sender,this.args);
  	  			return block.result;
  	  		case "entity":
  	  		Entity_Command entity = new Entity_Command(sender,this.args);
  	  			return entity.result;
  	  		case "error":
  	  		Error_Command error = new Error_Command(sender,this.args);
  	  			return error.result;
  	  		case "blockhunt":
  	  		BlockHunt_Command game = new BlockHunt_Command(sender,this.args);
  	  			return game.result;
  	  		case "realtime":
  	  			return RealTimeQuery();
  	  		case "expert":
  	  			return ExpertDifficultyQuery();
  	  		default:
  	  			Help();
  	  			return false;
    	}
	}
	//Permission checking function
	private boolean RealTimeQuery()
	{
		
		if(Common_Command.requirePermission("igsq.realtime",sender)) 
		{
			if(RealTime()) 
			{
				return true;
			}
			else 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CD0000Something Went Wrong When Executing this Command!"));
				return false;
			}
		}
		else 
		{
			sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to not having the required permission"));
  			return false;
		}
	}
	private boolean RealTime() {
		World world = player.getWorld();
		if(Configuration.getFieldBool("Modules.realtime", "internal").equals(false))
		{
			realtimeTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
	    	{

				@Override
				public void run() 
				{
					Calendar calendar = Calendar.getInstance();
					int seconds = calendar.get(Calendar.SECOND);
					int minutes = calendar.get(Calendar.MINUTE);
					int hours = calendar.get(Calendar.HOUR_OF_DAY);
					long totalSeconds = (long) ((double)seconds + ((double)minutes*60) + ((double)hours*3600));
					long mctime = (long)((double)totalSeconds/72*20);
					world.setTime((mctime)-5000);
				} 		
	    	}, 0, 20);
			Configuration.updateField("Modules.realtime", "config", true);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
			sender.sendMessage(Messaging.chatFormatter("&#00FFFFRealtime mode Turned On!"));
			return true;
		}
		else
		{
			Configuration.updateField("Modules.realtime", "config", false);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,true);
			plugin.scheduler.cancelTask(realtimeTask);
			sender.sendMessage(Messaging.chatFormatter("&#0000FFRealtime mode Turned Off!"));
			return true;
		}
		
	}
	private Boolean Help() 
	{
		sender.sendMessage(Messaging.chatFormatter("&#FF8A00+&#FFFF00----&#FFCA00IGSQ HELP&#FFFF00----&#FF8A00+"));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00Version &#C8C8C8- &#FFCA00Returns current IGSQ plugin version."));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00Block &#C8C8C8- &#FFCA00Allows you to create blocks bellow you."));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00Nightvision &#C8C8C8- &#FFCA00Gives a player night vision."));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00Entity &#C8C8C8- &#FFCA00Allows you to create entities bellow you."));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00Realtime &#C8C8C8- &#FFCA00Allows you to daylight cycle to real server time."));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00Expert &#C8C8C8- &#FFCA00Change the server between and from expert mode."));
		sender.sendMessage(Messaging.chatFormatter("&#FF8A00+&#FFFF00----&6COMMAND KEY HELP&e----&#FF8A00+"));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00* &#C8C8C8- &#FFCA00Default"));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00\" &#C8C8C8- &#FFCA00Abreviation"));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00[ &#C8C8C8- &#FFCA00Required"));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00{ &#C8C8C8- &#FFCA00Optional"));
		sender.sendMessage(Messaging.chatFormatter("&#FFFF00... &#C8C8C8- &#FFCA00Follows previous block"));
		return true;
	}

	private boolean ExpertDifficultyQuery() 
    {
        if(Common_Command.requirePermission("igsq.difficulty",sender))
        {
            if(ExpertDifficulty()) 
            {
                return true;
            }
            else 
            {
                sender.sendMessage(Messaging.chatFormatter("&#FFFF00expert [true/false]"));
                return false;
            }
        }
        else 
        {
            sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
            return false;
        }
    }

    private boolean ExpertDifficulty() //turn expert mode on or off
    {
    	 try
         {
         	boolean enabled = Boolean.parseBoolean(args[0]);
        	plugin.getConfig().set("GAMEPLAY.expert", enabled);
         	plugin.saveConfig();
         	if(enabled) 
         	{
         		player.sendMessage(Messaging.chatFormatter("&#84FF00Expert Mode &#00FF00Enabled&#84FF00!"));
         		Main_Expert.Start_Expert();
         	}
         	else 
         	{
         		player.sendMessage(Messaging.chatFormatter("&#84FF00Expert Mode &#C8C8C8Disabled&#84FF00!"));
         	}
         }
         catch(Exception exception)
         {
             sender.sendMessage(Messaging.chatFormatter("&#CD0000This Boolean is not valid!"));
             return false;
         }
         return true;
    }
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) 
	{
		Player player= null;
		if(sender instanceof Player) 
		{
			player = (Player) sender;
		}
		List<String> options = new ArrayList<String>();
		if(args.length == 1) 
		{
			String[] types = {"help","block","nightvision","nv","entity","expert","realtime","version","error","blockhunt"};
			for (String commands : types) if(commands.contains(args[0].toLowerCase())) options.add(commands);
		}
		else if(args.length == 2) 
		{
			if(args[0].equalsIgnoreCase("block")) 
			{
				for (Material material : Material.values()) 
				{
					if(material.isBlock() && material.name().contains(args[1].toUpperCase())) options.add(material.name().toLowerCase());
				}
			}
			else if(args[0].equalsIgnoreCase("entity")) 
			{
				for (EntityType entityType : EntityType.values()) 
				{
					if(entityType.isSpawnable() && entityType.name().contains(args[1].toUpperCase())) options.add(entityType.name().toLowerCase());
				}
			}
			else if(args[0].equalsIgnoreCase("version")) 
			{
				String[] types = {"build","version","description"};
				for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);
			}
			else if(args[0].equalsIgnoreCase("expert")) 
			{
				String[] types = {"true","false"};
				for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);
			}

			else if(args[0].equalsIgnoreCase("nightvision") || args[0].equalsIgnoreCase("nv")) 
			{
				for (Player selectedPlayer : Bukkit.getOnlinePlayers()) if(player.canSee(selectedPlayer) && selectedPlayer.getName().contains(args[1])) options.add(selectedPlayer.getName());
			}
			else if(args[0].equalsIgnoreCase("error")) 
			{
				String[] types = {"test","log"};
				for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);
			}
			else if(args[0].equalsIgnoreCase("blockhunt")) 
			{
				String[] types = {"start","forceseeker","forcehider","end"};
				for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);
			}
		}
		else if(args.length == 3) 
		{
			if(args[0].equalsIgnoreCase("block")) 
			{
				String[] types = {"trap","fake","real"};
				for (String commands : types) if(commands.contains(args[2].toLowerCase())) options.add(commands);
			}
			else if(args[0].equalsIgnoreCase("error") && args[1].equalsIgnoreCase("log")) 
			{
				String[] types = {"enabled","disabled","verbose"};
				for (String commands : types) if(commands.contains(args[2].toLowerCase())) options.add(commands);
			}
		}
		else if(args.length == 4) 
		{
			if(args[0].equalsIgnoreCase("block")) 
			{
				String[] types = {"@all"};
				for (String commands : types) if(commands.contains(args[3].toLowerCase())) options.add(commands);
				for (Player selectedPlayer : Bukkit.getOnlinePlayers()) if(player.canSee(selectedPlayer) && selectedPlayer.getName().contains(args[3])) options.add(selectedPlayer.getName());
			}
		}
		
		
		//Custom Tileable Block Option
		if(args.length >= 5 && (!args[3].startsWith("@")) && args[0].equalsIgnoreCase("block")) 
		{
			for (Player selectedPlayer : Bukkit.getOnlinePlayers()) 
			{
				Boolean alreadySelected = false;
				for(String arg : args) 
				{
					if(arg.equals(selectedPlayer.getName())) 
					{
						alreadySelected = true;
						break;
					}
				}
				if(player.canSee(selectedPlayer) && (!alreadySelected) && selectedPlayer.getName().contains(args[args.length-1])) options.add(selectedPlayer.getName());
			}
		}
		return options;
	}
}
