package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.Messaging;

public class PlayerCompass_Command {

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	public Boolean result;
	public PlayerCompass_Command(CommandSender sender,List<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = playerCompassQuery();
	}
	private Boolean playerCompass() 
	{
		Player player = (Player) sender;
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
		if((args.size() == 0)) 
		{
			yaml.setPlayerCompassAccuracy(0);
			yaml.setPlayerCompassTarget("");
			return false;
		}
		else 
		{
			Player targetPlayer = Bukkit.getPlayer(args.get(0));
			if(targetPlayer == null) 
			{
				player.sendMessage(Messaging.chatFormatter("&#FF0000Player could not be found!"));
				return false;
			}
			int randomInt = 0;
			if(args.size() == 2) 
			{
				try 
				{
					randomInt = Integer.parseInt(args.get(1));
				}
				catch(Exception exception) 
				{
					player.sendMessage(Messaging.chatFormatter("&#FF0000Accuracy Value Could Not Be Found!"));
					return false;
				}	
			}
			yaml.setPlayerCompassAccuracy(randomInt);
			yaml.setPlayerCompassTarget(targetPlayer.getUniqueId().toString());
			Main_Command.Start_Command();
			return true;
		}
		
	}
	private Boolean playerCompassQuery() 
	{
			if(Common_Command.requirePermission("igsq.compass",sender) && sender instanceof Player) 
			{
				if(playerCompass()) 
				{
					return true;
				}
				else 
				{
	                sender.sendMessage(Messaging.chatFormatter("&#FFFF00playercompass [player] [Accuracy Number]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
}
