package me.murrobby.igsq.spigot.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common;

public class Pro_Command implements CommandExecutor{
	
	public Pro_Command() 
	{
		Common.spigot.getCommand("cowabunga").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			player.setLevel(1000);
			Date date = new Date();
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
            String strDate = dateFormat.format(date);  
			sender.sendMessage("I dont associate with programmers, especcialy at " + strDate);
			System.out.println("I really don't");
		}
		return false;
	}
}
