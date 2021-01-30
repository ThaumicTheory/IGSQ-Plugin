package me.murrobby.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.expert.TeamPermissions_Expert;
import me.murrobby.igsq.spigot.expert.TeamRank_Expert;
import me.murrobby.igsq.spigot.expert.Team_Expert;

public class Team_Command implements CommandExecutor, TabCompleter{

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	//private Command command;
	private String label;
	public Team_Command() 
	{
		Common.spigot.getCommand("team").setExecutor(this);
		Common.spigot.getCommand("team").setTabCompleter(this);
	}
	private Boolean team() 
	{
		
		if(args.size() == 0) return false;
		Player player = (Player) sender;
		if(args.get(0).equalsIgnoreCase("found")) //create team
		{
			if(!requireNoFaction(player)) return true;
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name your faction!"));
				return false;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			System.out.println(name);
			if(Team_Expert.getTeamFromName(name) == null) 
			{
				Team_Expert newTeam = new Team_Expert();
				newTeam.setName(name, player);
				newTeam.addMember(player);
				newTeam.setOwner(player);
			}
			else sender.sendMessage(Messaging.chatFormatter("&#FF0000This faction already exists!"));
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
			if(!requirePermission(player, TeamPermissions_Expert.OWNER)) return true;
			Team_Expert.getPlayersTeam(player).deleteTeam(player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("defect")) //forcefully leave team straight into another
		{
			if(!requireFaction(player)) return true;
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("transferowner")) //change the owner of the faction
		{
			if(!requirePermission(player, TeamPermissions_Expert.OWNER)) return true;
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name the person you are transfering ownership to!"));
				return true;
			}
			OfflinePlayer target = null;
			for(OfflinePlayer selectedTarget : Team_Expert.getPlayersTeam(player).getMembers()) 
			{
				if(selectedTarget.getName().equals(args.get(1))) 
				{
					target = selectedTarget;
					break;
				}
			}
			if(target == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CC0000That player could not be found in your faction!"));
				return true;
			}
			Team_Expert.getPlayersTeam(player).setOwner(target, player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("claim")) //claim a chunk of land
		{
			if(!requirePermission(player, TeamPermissions_Expert.CLAIM)) return true;
			Team_Expert.getPlayersTeam(player).addChunk(player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("unclaim")) //unclaim a chunk of land
		{
			if(!requirePermission(player, TeamPermissions_Expert.UNCLAIM)) return true;
			Team_Expert.getPlayersTeam(player).removeChunk(player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("invites")) //look at current invites
		{
			Team_Expert team = Team_Expert.getPlayersTeam(player);
			if(team != null) player.sendMessage(Messaging.chatFormatter("&#FF0000You are already in a faction! To join another you will have to defect!\nThis may cause backlash from your current faction!"));
			
			
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("rank")) //Rank Settings
		{
			if(!requirePermission(player, TeamPermissions_Expert.MODIFY_RANKS)) return true;
			Team_Expert team = Team_Expert.getPlayersTeam(player);
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#ccccccPlease type a sub command [add/remove/modify/list]"));
				return true;
			}
			if(args.get(1).equalsIgnoreCase("add")) 
			{
				if(args.size() == 2) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name your rank!"));
					return true;
				}
				String name =  Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				if(TeamRank_Expert.getRankFromName(name, team) != null) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Your faction already has a rank named " + team));
					return true;
				}
				team.addRank(new TeamRank_Expert(team, name));
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("list")) 
			{
				if(args.size() == 2) 
				{
					for(TeamRank_Expert rank : team.getRanks()) rank.display(player);
				}
			}
			return false;
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
					sender.sendMessage(Messaging.chatFormatter("&#FFFF00"+label+ " [found/pledge/leave/invite/kick/banish/disband/defect/transferowner/claim]"));
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
		this.args = Arrays.asList(args);
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
		List<String> options = new ArrayList<>();
		if(args.length == 1) 
		{
			List<String> types = Arrays.asList("found","pledge","leave","invite","kick","banish","disband","defect","transferowner","claim","unclaim","rank");
			for (String commands : types) 
			{
				if(commands.contains(args[0].toLowerCase())) 
				{
					options.add(commands);
				}
			}
		}
		else if(args.length == 2) 
		{
			if(args[1].equalsIgnoreCase("rank")) 
			{
				List<String> types = Arrays.asList("add","remove","modify");
				for (String commands : types) 
				{
					if(commands.contains(args[0].toLowerCase())) 
					{
						options.add(commands);
					}
				}
			}
		}
		return options;
	}
	private boolean requireFaction(Player player) 
	{
		boolean inTeam = Team_Expert.isInATeam(player);
		if(!inTeam) player.sendMessage(Messaging.chatFormatter("&#FF0000You are not in a faction! This command is for factions only!"));
		return inTeam;
	}
	private boolean requireNoFaction(Player player) 
	{
		boolean inTeam = Team_Expert.isInATeam(player);
		if(inTeam) player.sendMessage(Messaging.chatFormatter("&#FF0000You are already in a faction! This command is for the factionless only!"));
		return !inTeam;
	}
	private boolean requirePermission(Player player,TeamPermissions_Expert permission) 
	{
		if(!requireFaction(player)) return false;
		Team_Expert team = Team_Expert.getPlayersTeam(player);
		boolean hasPermission = team.hasPermission(player, permission);
		if(!hasPermission) player.sendMessage(Messaging.chatFormatter("&#FF0000You are missing permission &#FFb900" + permission.toString() + " &#FF0000to run this command!"));
		return hasPermission;
	}
}
