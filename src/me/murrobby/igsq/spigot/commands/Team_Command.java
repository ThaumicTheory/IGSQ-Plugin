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
				return true;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			if(Team_Expert.getTeamFromName(name) == null) 
			{
				new Team_Expert(name,player);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Faction " + name + " has been created!"));
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
		else if(args.get(0).equalsIgnoreCase("ally")) //ally another faction
		{	
			if(!requirePermission(player, TeamPermissions_Expert.ALLY)) return true;
			Team_Expert team = Team_Expert.getPlayersTeam(player);
			if(args.size() == 2) {
				//list of allies
				return true;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
			Team_Expert ally = Team_Expert.getTeamFromName(name);
			if(ally == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Could not find " + name));
				return true;
			}
			if(ally.equals(team)) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Are you not friends with yourself? ;( "));
				return true;
			}
			

			//add ally request
			if(args.get(1).equalsIgnoreCase("add")) {
				if(team.isAlly(ally)) return true;

				if(!team.isAllyPending(ally)) {
					team.addAllyPending(ally);
					sender.sendMessage(Messaging.chatFormatter("&#ff61f4You sent a request for an Alliance with " + name + "!"));
					return true;
				}
				if(team.isAllyPending(ally)) {
					team.addAlly(ally);
					sender.sendMessage(Messaging.chatFormatter("&#ff61f4You are now Allied with " + name + "!"));
					team.removeAllyPending(ally);
					return true;
				}
			}
			//remove pending ally request
			if(args.get(1).equalsIgnoreCase("remove")) {
				if(team.isAlly(ally)) {
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You can't just do that!"));
					return true;
				}
				if(team.isAllyPending(ally)) {
					team.removeAllyPending(ally);
					sender.sendMessage(Messaging.chatFormatter("&#ff61f4You have removed the request for an Alliance with " + name + "."));
					return true;
				}
				sender.sendMessage(Messaging.chatFormatter("&#FF0000You are not allied, or pending allies, with " + ally.getName()));
			}
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
			if(!requireFaction(player)) return true;
			Team_Expert team = Team_Expert.getPlayersTeam(player);
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#ccccccPlease type a sub command [add/remove/modify/list]"));
				return true;
			}
			if(args.get(1).equalsIgnoreCase("add")) 
			{
				if(!requirePermission(player, TeamPermissions_Expert.MODIFY_RANKS)) return true;
				if(args.size() == 2) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name your rank!"));
					return true;
				}
				String name =  Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				if(TeamRank_Expert.getRankFromName(name, team) != null) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Your faction already has a rank named " + name));
					return true;
				}
				TeamRank_Expert teamRank = new TeamRank_Expert(team, name);
				teamRank.setPermissions(team.getDefaultRank().getPermissions());
				team.addRank(teamRank);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Rank &#00cc00" + name + " &#00FF00was successfully created!"));
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("remove")) 
			{
				if(!requirePermission(player, TeamPermissions_Expert.MODIFY_RANKS)) return true;
				if(args.size() == 2) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name the rank!"));
					return true;
				}
				String name =  Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				TeamRank_Expert rank = TeamRank_Expert.getRankFromName(name, team);
				if(rank == null) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Your faction doesn't have a rank named " + name));
					return true;
				}
				if(!rank.canDelete(player)) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000You cannot delete this rank! Its either a protected rank, the current default rank or has a permission you dont have!"));
					return true;
				}
				team.removeRank(rank);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Rank &#00cc00" + rank.getName() + " &#00FF00was successfully removed!"));
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("list")) 
			{
				if(!requirePermission(player, TeamPermissions_Expert.READ_PERMISSIONS)) return true;
				if(args.size() == 2) 
				{
					for(TeamRank_Expert rank : team.getRanks()) rank.display(player);
					return true;
				}
				String name = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				TeamRank_Expert rank = TeamRank_Expert.getRankFromName(name, team);
				if(rank == null) sender.sendMessage(Messaging.chatFormatter("&#CC0000Could not find the rank named " + name));
				else rank.display(player);
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("default")) 
			{
				if(args.size() == 2) 
				{
					if(!requirePermission(player, TeamPermissions_Expert.READ_PERMISSIONS)) return true;
					for(TeamRank_Expert rank : team.getRanks()) 
					{
						if(rank.isDefault()) 
						{
							sender.sendMessage(Messaging.chatFormatter("&#FFFF00The default rank is " + rank.getName() + "!"));
							break;
						}
					}
					return true;
				}
				if(!requirePermission(player, TeamPermissions_Expert.OWNER)) return true;
				String name = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				TeamRank_Expert rank = TeamRank_Expert.getRankFromName(name, team);
				if(rank == null) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Could not find the rank named " + name));
					return true;
				}
				else if(rank.isDefault()) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#cccccc" +rank.getName() +" is already the default rank!"));
					return true;
				}
				else if(!rank.isGivable()) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000The rank " + rank.getName() + " is protected!"));
					return true;
				}
				rank.setDefault();
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("addpermission")) 
			{
				if(args.size() == 2) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000Please select the permission to add and the rank to add it to!"));
					return true;
				}
				if(args.size() == 3) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000Please select the rank to modify also!"));
					return true;
				}
				String permissionString = args.get(2).toUpperCase();
				TeamPermissions_Expert permission = TeamPermissions_Expert.valueOf(permissionString);
				String rankName = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' '),' ');
				TeamRank_Expert rank = TeamRank_Expert.getRankFromName(rankName, team);
				if(permission == null)
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Could not find the permission named " + permissionString));
					return true;
				}
				if(rank == null) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Could not find the rank named " + rankName));
					return true;
				}
				if(rank.getPermissions().contains(permission))
				{
					sender.sendMessage(Messaging.chatFormatter("&#cccccc"+ rank.getName() +" already has " + permissionString));
					return true;
				}
				if(permission.isHidden())
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000" +permissionString+ " is a protected permission!"));
					return true;
				}
				if(!rank.isGivable())
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000" +rank.getName()+ " is a protected rank!"));
					return true;
				}
				rank.addPermission(permission);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Permission " + permissionString + " Was added to " + rank.getName()));
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("removepermission")) 
			{
				if(args.size() == 2) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000Please select the permission to remove and the rank to remove it from!"));
					return true;
				}
				if(args.size() == 3) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000Please select the rank to modify also!"));
					return true;
				}
				String permissionString = args.get(2).toUpperCase();
				TeamPermissions_Expert permission = TeamPermissions_Expert.valueOf(permissionString);
				String rankName = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' '),' ');
				TeamRank_Expert rank = TeamRank_Expert.getRankFromName(rankName, team);
				if(permission == null)
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Could not find the permission named " + permissionString));
					return true;
				}
				if(rank == null) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Could not find the rank named " + rankName));
					return true;
				}
				if(!rank.getPermissions().contains(permission))
				{
					sender.sendMessage(Messaging.chatFormatter("&#cccccc"+ rank.getName() +" does not have " + permissionString));
					return true;
				}
				if(permission.isHidden())
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000" +permissionString+ " is a protected permission!"));
					return true;
				}
				if(!rank.isGivable())
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000" +rank.getName()+ " is a protected rank!"));
					return true;
				}
				rank.removePermission(permission);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Permission " + permissionString + " removed from " + rank.getName()));
				return true;
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
		Player player= null;
		Team_Expert team = null;
		if(sender instanceof Player) 
		{
			player = (Player) sender;
			team = Team_Expert.getPlayersTeam(player);
		}
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
			if(args[0].equalsIgnoreCase("rank")) 
			{
				List<String> types = Arrays.asList("add","remove","default","list","addpermission","removepermission");
				for (String commands : types) 
				{
					if(commands.contains(args[1].toLowerCase())) 
					{
						options.add(commands);
					}
				}
			}
		}
		else if(args.length == 3) 
		{
			if(args[0].equalsIgnoreCase("rank") && (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("default")) && team != null) 
			{
				List<TeamRank_Expert> ranks = team.getRanks();
				List<String> types = new ArrayList<>();
				for(TeamRank_Expert rank : ranks) types.add(rank.getName());
				for (String commands : types) 
				{
					if(commands.contains(args[2].toLowerCase())) 
					{
						options.add(commands);
					}
				}
			}
			else if(args[0].equalsIgnoreCase("rank") && (args[1].equalsIgnoreCase("addpermission") || args[1].equalsIgnoreCase("removepermission")) && team != null) 
			{
				List<String> types = new ArrayList<>();
				for(TeamPermissions_Expert rank : TeamPermissions_Expert.values()) types.add(rank.toString());
				for (String commands : types) 
				{
					if(commands.contains(args[2].toUpperCase())) 
					{
						options.add(commands);
					}
				}
			}
		}
		else if(args.length == 4) 
		{
			if(args[0].equalsIgnoreCase("rank") && (args[1].equalsIgnoreCase("addpermission") || args[1].equalsIgnoreCase("removepermission")) && team != null) 
			{
				List<TeamRank_Expert> ranks = team.getRanks();
				List<String> types = new ArrayList<>();
				for(TeamRank_Expert rank : ranks) types.add(rank.getName());
				for (String commands : types) 
				{
					if(commands.contains(args[3].toLowerCase())) 
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
