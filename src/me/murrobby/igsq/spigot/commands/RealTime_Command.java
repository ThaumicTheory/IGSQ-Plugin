package me.murrobby.igsq.spigot.commands;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Yaml;
import me.murrobby.igsq.spigot.Messaging;

public class RealTime_Command {

	private CommandSender sender;
	private String[] args = new String[0];
	public Boolean result;
	public RealTime_Command(CommandSender sender,String[] args) 
	{
		this.sender = sender;
		this.args = args;
		result = realTimeQuery();
	}
	private Boolean realTime() 
	{
		World world = ((Player)sender).getWorld();
		Boolean currentSetting = Yaml.getFieldBool(world.getUID().toString() + ".realtime.enabled", "internal");
		if((args.length == 0 || args[0].equalsIgnoreCase("true")) && !currentSetting) 
		{
			Yaml.updateField(world.getUID().toString() + ".realtime.enabled", "internal", true);
			sender.sendMessage(Messaging.chatFormatter("&#00FFFFRealtime mode Turned On!"));
			Yaml.updateField(world.getUID().toString() + ".Modules.realtime.storeddaycycle", "internal", world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
			Main_Command.Start_Command();
			return true;
		}
		else if((args.length == 0 || args[0].equalsIgnoreCase("false")) && currentSetting) 
		{
			Yaml.updateField(world.getUID().toString() + ".realtime.enabled", "internal", false);
			sender.sendMessage(Messaging.chatFormatter("&#0000FFRealtime mode Turned Off!"));
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, Yaml.getFieldBool(world.getUID().toString() + ".realtime.storeddaycycle", "internal"));
			return true;
		}
		else if(args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("true")) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#FFb900Realtime is already " + (currentSetting ? "on" : "off") + "."));
			return true;
		}
		else return false;
		
	}
	private Boolean realTimeQuery() 
	{
			if(Common_Command.requirePermission("igsq.realtime",sender) && sender instanceof Player) 
			{
				if(realTime()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FFFF00realtime [true/false]"));
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
