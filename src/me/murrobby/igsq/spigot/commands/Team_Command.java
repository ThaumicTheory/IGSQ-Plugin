package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.expert.Team_Expert;

public class Team_Command implements CommandExecutor, TabCompleter{

	private CommandSender sender;
	private ArrayList<String> args = new ArrayList<>();
	//private Command command;
	private String label;
	private Boolean team() 
	{
		
		if(args.size() == 0) return false;
		Player player = (Player) sender;
		if(args.get(0).equalsIgnoreCase("found")) //create team
		{
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name your faction!"));
				return false;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			if(Team_Expert.getTeamFromName(name) == null) 
			{
				Team_Expert newTeam = new Team_Expert();
				newTeam.addMember(player);
				newTeam.setName(name, player);
			}
			else sender.sendMessage(Messaging.chatFormatter("&#FF0000This team already exists!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("pledge")) //join team
		{
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("leave")) //request to leave team peacefully
		{
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("invite")) //invite others to the team
		{
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("kick")) //remove someone from the team
		{
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("banish")) //remove someone from the team and ban them from ever joining again
		{
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("disband")) //request team delete
		{
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("defect")) //forcefully leave team straight into another
		{
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		
	
		return false;
		
	}
	private Boolean teamQuery() 
	{
			if(Common_Command.requirePermission("igsq.team",sender) && sender instanceof Player) 
			{
				if(team()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FFFF00"+label+ " [found/pledge/leave/invite/kick/banish/disband/defect]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		this.sender = sender;
		//this.command = command;
		this.label = label;
		this.args = (ArrayList<String>) Arrays.asList(args);
		teamQuery();
		return false;
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		/*
		Player player= null;
		if(sender instanceof Player) 
		{
			player = (Player) sender;
		}
		*/
		List<String> options = new ArrayList<String>();
		if(args.length == 1) 
		{
			ArrayList<String> types = (ArrayList<String>) Arrays.asList("found","pledge","leave","invite","kick","banish","disband","defect");
			for (String commands : types) if(commands.contains(args[0].toLowerCase())) options.add(commands);
		}
		return options;
	}
}
