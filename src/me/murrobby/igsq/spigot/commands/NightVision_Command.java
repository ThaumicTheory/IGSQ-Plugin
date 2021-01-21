package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Messaging;

public class NightVision_Command {
	
	private CommandSender sender;
	public Boolean result;
	private ArrayList<String> args = new ArrayList<>();
	private Player player;
	
	public NightVision_Command(CommandSender sender,ArrayList<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = NightVisionQuery();
	}
	
	private Boolean NightVision() 
	{
	String display;
	if(args.size() == 0) 
	{
		display = "Yourself";
		player = (Player)sender;
	}
	else 
	{
		try
		{
			player = Bukkit.getPlayer(args.get(0));
			display = player.getName();
		}
		catch(Exception exception) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#CD0000Player Could not be found!"));
			return false;
		}
	}
	if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
	{
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		sender.sendMessage(Messaging.chatFormatter("&#0000FFRemoved nightvision from " + display + "!"));
	}
	else 
	{
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000000,0,true,false));
		sender.sendMessage(Messaging.chatFormatter("&#00FFFFGave nightvision too " + display + "!"));
	}
	return true;
		
	}
	

	private boolean NightVisionQuery() 
	{
		if(sender instanceof Player && Common_Command.requirePermission("igsq.nightvision",sender)) 
		{
				if(NightVision()) 
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
					sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
		  			return false;
				}
		}
	}