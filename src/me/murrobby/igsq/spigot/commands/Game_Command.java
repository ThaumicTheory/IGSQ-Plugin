package me.murrobby.igsq.spigot.commands;

import org.bukkit.command.CommandSender;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;
import me.murrobby.igsq.spigot.game.Common_Game;

public class Game_Command {

	private Main_Command commands;
	private CommandSender sender;
	public Boolean result;
	public Game_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		this.commands = commands;
		
		this.sender = sender;
		result = GameQuery();
	}
	private Boolean Game() 
	{
		Common_Game.start();
		return true;
		
	}
	private Boolean GameQuery() 
	{
			if(commands.RequirePermission("igsq.game.start")) 
			{
				if(Game()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common_Spigot.chatFormatter("&#FFFF00game [start]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Common_Spigot.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
}
