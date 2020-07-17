package me.murrobby.igsq.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class NightVision_Command {
	
	private Main_Command commands;
	private CommandSender sender;
	public Boolean result;
	private String[] args;
	private Player player;
	
	public NightVision_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		this.commands = commands;
		this.sender = sender;
		this.args = args;
		result = NightVisionQuery();
	}
	
	private Boolean NightVision() 
	{
	String display;
	if(args.length == 0) 
	{
		display = "Yourself";
		player = (Player)sender;
	}
	else 
	{
		try
		{
			player = Bukkit.getPlayer(args[0]);
			display = player.getName();
		}
		catch(Exception exception) 
		{
			sender.sendMessage(Common_Spigot.ChatColour("&cPlayer Could not be found!"));
			return false;
		}
	}
	if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
	{
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		sender.sendMessage(Common_Spigot.ChatColour("&3Removed nightvision from " + display + "!"));
	}
	else 
	{
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000000,255,true));
		sender.sendMessage(Common_Spigot.ChatColour("&bGave nightvision too " + display + "!"));
	}
	return true;
		
	}
	

	private boolean NightVisionQuery() 
	{
		if(commands.IsPlayer() && commands.RequirePermission("igsq.nightvision")) 
		{
				if(NightVision()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common_Spigot.ChatColour("&cSomething Went Wrong When Executing this Command!"));
					return false;
				}
				
				}
				else 
				{
					sender.sendMessage(Common_Spigot.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
		  			return false;
				}
		}
	}