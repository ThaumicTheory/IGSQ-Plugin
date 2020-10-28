package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;

public class Main_Command implements CommandExecutor, TabCompleter{
	private CommandSender sender;
	private String[] args = new String[0];
	
	public static int taskID;
	
	public Main_Command()
	{
		Common.spigot.getCommand("igsq").setExecutor(this);
		Common.spigot.getCommand("igsq").setTabCompleter(this);
		
		new TimeSkipEvent_Command();
		
		Start_Command();
	}
	public static void Start_Command() //Tasks will close if the game is turned off therefor they will need to be rerun for enabling the game
	{
		taskID++;
		new RealTimeTask_Command(taskID);
		new PlayerCompassTask_Command(taskID);
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
  	  			return new Version_Command(sender,this.args).result;
  	  		case "nightvision":
  	  		case "nv":
  	  			return new NightVision_Command(sender,this.args).result;
  	  		case "block":
  	  			return new Block_Command(sender,this.args).result;
  	  		case "entity":
  	  			return new Entity_Command(sender,this.args).result;
  	  		case "error":
  	  			return new Error_Command(sender,this.args).result;
  	  		case "blockhunt":
  	  			return new BlockHunt_Command(sender,this.args).result;
  	  		case "realtime":
  	  			return new RealTime_Command(sender,this.args).result;
  	  		case "expert":
  	  			return new Expert_Command(sender,this.args).result;
  	  		case "playercompass":
  	  			return new PlayerCompass_Command(sender,this.args).result;
  	  		default:
  	  			help();
  	  			return false;
    	}
	}
	private Boolean help() 
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
			String[] types = {"help","block","nightvision","nv","entity","expert","realtime","version","error","blockhunt","playercompass"};
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
				String[] types = {"start","forceseeker","forcehider","end","joinlobby"};
				for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);
			}
			else if(args[0].equalsIgnoreCase("realtime")) 
			{
				String[] types = {"true","false"};
				for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);
			}
			else if(args[0].equalsIgnoreCase("playercompass")) 
			{
				for (Player selectedPlayer : Bukkit.getOnlinePlayers()) if(player.canSee(selectedPlayer) && selectedPlayer.getName().contains(args[1])) options.add(selectedPlayer.getName());
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
			if(args[0].equalsIgnoreCase("playercompass")) 
			{
				String[] types = {"0","10","20","30","40","50","60","70","80","90","100","150","200","300","400","500","1000"};
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
