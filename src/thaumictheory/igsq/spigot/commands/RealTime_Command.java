package thaumictheory.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.YamlWorldWrapper;

public class RealTime_Command {

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	public Boolean result;
	public RealTime_Command(CommandSender sender,List<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = realTimeQuery();
	}
	private Boolean realTime() 
	{
		World world = ((Player)sender).getWorld();
		YamlWorldWrapper yaml = new YamlWorldWrapper(world);
		Boolean currentSetting = yaml.isRealtime();
		if((args.size() == 0 || args.get(0).equalsIgnoreCase("true")) && !currentSetting) 
		{
			yaml.setRealtime(true);
			sender.sendMessage(Messaging.chatFormatter("&#00FFFFRealtime mode Turned On!"));
			yaml.setRealtimeStoredCycle(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE));
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
			Main_Command.Start_Command();
			return true;
		}
		else if((args.size() == 0 || args.get(0).equalsIgnoreCase("false")) && currentSetting) 
		{
			yaml.setRealtime(false);
			sender.sendMessage(Messaging.chatFormatter("&#0000FFRealtime mode Turned Off!"));
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, yaml.getRealtimeStoredCycle());
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("false") || args.get(0).equalsIgnoreCase("true")) 
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
