package thaumictheory.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.YamlWrapper;
import thaumictheory.igsq.spigot.smp.Main_SMP;
import thaumictheory.igsq.spigot.smp.Player_SMP;
import thaumictheory.igsq.spigot.smp.aspect.Enum_Aspect;

public class SMP_Command {

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	public Boolean result;
	public SMP_Command(CommandSender sender,List<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = smpQuery();
	}
	private Boolean smp() 
	{
		Boolean currentSetting = YamlWrapper.isSMP();
		if(args.size() == 1 && sender instanceof Player && currentSetting && args.get(0).equalsIgnoreCase("resetaspect")) 
		{
			Player_SMP player = Player_SMP.getSMPPlayer((Player) sender);
			player.getYaml().setSmpAspect(Enum_Aspect.NONE.toString());
			player.updateAspect();
			return true;
		}
		if((args.size() == 0 || args.get(0).equalsIgnoreCase("true")) && !currentSetting) 
		{
			YamlWrapper.setSMP(true);
     		sender.sendMessage(Messaging.chatFormatter("&#dab210SMP &#00FF00Enabled&#dab210!"));
			Main_SMP.startSMP();
			return true;
		}
		else if((args.size() == 0 || args.get(0).equalsIgnoreCase("false")) && currentSetting) 
		{
			YamlWrapper.setSMP(false);
     		sender.sendMessage(Messaging.chatFormatter("&#dab210SMP &#C8C8C8Disabled&#dab210!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("false") || args.get(0).equalsIgnoreCase("true")) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#FFb900SMP is already " + (currentSetting ? "on" : "off") + "."));
			return true;
		}
		else return false;
		
	}
	private Boolean smpQuery() 
	{
			if(Common_Command.requirePermission("igsq.smp",sender)) 
			{
				if(smp()) 
				{
					return true;
				}
				else 
				{
	                sender.sendMessage(Messaging.chatFormatter("&#FFFF00smp [true/false]"));
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
