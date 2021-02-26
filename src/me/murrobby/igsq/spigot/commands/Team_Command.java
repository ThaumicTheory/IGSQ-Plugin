package me.murrobby.igsq.spigot.commands;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.smp.Chunk_SMP;
import me.murrobby.igsq.spigot.smp.Common_SMP;
import me.murrobby.igsq.spigot.smp.TeamPermissions_SMP;
import me.murrobby.igsq.spigot.smp.TeamRank_SMP;
import me.murrobby.igsq.spigot.smp.Team_SMP;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Team_Command implements CommandExecutor, TabCompleter{

	private CommandSender sender;
	private List<String> args = new ArrayList<>();
	//private Command command;
	private String label;
	public Team_Command() 
	{
		Common.spigot.getCommand("faction").setExecutor(this);
		Common.spigot.getCommand("faction").setTabCompleter(this);
	}
	private Boolean team() 
	{
		
		if(args.size() == 0) return false;
		Player player = (Player) sender;
		if(args.get(0).equalsIgnoreCase("found")) //create team
		{
			if(!requireNoTeam(player)) return true;
			
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name your faction!"));
				return true;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			if(Team_SMP.getTeamFromName(name) == null) 
			{
				if(Common_SMP.isProtectedName(name)) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000This name is not allowed!"));
					return true;
				}
				new Team_SMP(name,player);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Faction " + name + " has been created!"));
			}
			else sender.sendMessage(Messaging.chatFormatter("&#FF0000This faction already exists!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("pledge")) //join team
		{	
			if(!(args.size() > 1)) {
				if(!requireNoTeam(player)) return true;
				
				if(Team_SMP.isInATeam(player)) player.sendMessage(Messaging.chatFormatter("&#FF0000You are already in a faction! To join another you will have to defect!\nThis may cause backlash from your current faction!"));
				
				player.sendMessage(Messaging.chatFormatter("&#00FF00----------------Lists of Invites----------------"));
				String expertInvites = new YamlPlayerWrapper(player).getSmpInvitesField();
				if(expertInvites == null || expertInvites.equals("")) player.sendMessage(Messaging.chatFormatter("&#FF0000You don't have any invites... Create your own by doing /faction found [FactionName] !"));
				
				for(String invites : expertInvites.split(" "))
				{
					Team_SMP teamInv = Team_SMP.getTeamFromID(UUID.fromString(invites));
					TextComponent message = new TextComponent(teamInv.getName());
					message.setColor(net.md_5.bungee.api.ChatColor.of("#00FF00"));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/faction pledge " + teamInv.getName()));
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Join team" + teamInv.getName())));
					player.spigot().sendMessage(message);
				}
				player.sendMessage(Messaging.chatFormatter("&#00FF00------------------------------------------------"));
				
				
				return true;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			Team_SMP team = Team_SMP.getTeamFromName(name);
			for(Team_SMP invites : new YamlPlayerWrapper(player).getSmpInvites()) {
				if(team.equals(invites)) {
					team.addMember(player);
					team.getDefaultRank().addMember(player);
					team.removeInvite(player);
				}
			}
			
			
		}
		else if(args.get(0).equalsIgnoreCase("leave")) //request to leave team peacefully
		{
			if(!requireTeam(player)) return true;
		
			for(UUID leavePending : Team_SMP.getPlayersTeam(player).getLeavePending()) {
				if(leavePending.equals(player.getUniqueId())) {
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You already requested to leave!"));
					return true;
				}
			}
			Team_SMP.getPlayersTeam(player).addLeavePending(player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("invite")) //invite others to the team
		{
			if(!requirePermission(player, TeamPermissions_SMP.INVITE)) return true;
			
			if(args.size() == 1) {
				sender.sendMessage(Messaging.chatFormatter("&#FF0000You need to enter a name!"));
				return true;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			Player invPlayer = Bukkit.getPlayer(name);
			if(invPlayer == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Could not find " + name));
				return true;
			}
			if(Team_SMP.getPlayersTeam(player).equals(Team_SMP.getPlayersTeam(invPlayer))){
				sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThey are already here, didn't you see?"));
				return true;
			}
			for(OfflinePlayer bannedPlayer : Team_SMP.getPlayersTeam(player).getBanMembers()) {
				if(bannedPlayer.getUniqueId().equals(invPlayer.getUniqueId())) {
					sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis person has been banned!"));
					return true;
				}
			}
			for(Team_SMP team : Team_SMP.getInvites(invPlayer)) {
				if(team != null && team.equals(Team_SMP.getPlayersTeam(player))) {
					sender.sendMessage(Messaging.chatFormatter("&#CCCCCCYou already invited this person!"));
					return true;
				}
			}
			if(!requirePermission(player, TeamPermissions_SMP.INVITE_FACTIONED)) return true;

			Team_SMP team = Team_SMP.getPlayersTeam(player);
			team.addInvite(invPlayer);
			sender.sendMessage(Messaging.chatFormatter("&#00FF00Invited " + invPlayer.getName() + "!"));
			TextComponent message = new TextComponent("You have been invited to join " + team.getName());
			message.setColor(net.md_5.bungee.api.ChatColor.of("#00FF00"));
			message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/faction pledge " + team.getName()));
			message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Join team" + team.getName())));
			invPlayer.spigot().sendMessage(message);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("ally")) //ally another faction
		{	
			if(!requirePermission(player, TeamPermissions_SMP.ALLY)) return true;

			Team_SMP team = Team_SMP.getPlayersTeam(player);
			if(args.size() == 2) {
				sender.sendMessage(Messaging.chatFormatter("&#FF0000----------------Lists of Allies----------------" ));
				if(!(team.getAllies().size() == 0)) {
					for(Team_SMP ally : team.getAllies()) {
						sender.sendMessage(Messaging.chatFormatter("&#FF0000" + ally.getName() ));
					}
				}
				else {
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You dont have Allies... go get some by doing /faction ally add [FactionName]" ));
				}
				sender.sendMessage(Messaging.chatFormatter("&#FF0000-----------------------------------------------" ));
				return true;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
			Team_SMP ally = Team_SMP.getTeamFromName(name);
			if(ally == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Could not find " + name));
				return true;
			}
			if(ally.equals(team)) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CCCCCCAre you not friends with yourself? ;( "));
				return true;
			}
			

			//add ally request
			if(args.get(1).equalsIgnoreCase("add")) 
			{
				if(Common_SMP.isProtectedName(ally.getName())) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You cant ally with an internal faction."));
					return true;
				}
				if(team.isAlly(ally)) {
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You are already allied!"));
					return true;
				}

				if(!team.isAllyPending(ally) ) {
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
		else if(args.get(0).equalsIgnoreCase("enemy")) //enemy another faction
		{	
			if(!requirePermission(player, TeamPermissions_SMP.ENEMY)) return true;
			Team_SMP team = Team_SMP.getPlayersTeam(player);
			if(args.size() == 2) {
				sender.sendMessage(Messaging.chatFormatter("&#FF0000----------------Lists of Enemies----------------" ));
				if(!(team.getAllies().size() == 0)) {
					for(Team_SMP enemy : team.getEnemies()) {
						sender.sendMessage(Messaging.chatFormatter("&#FF0000" + enemy.getName() ));
					}
				}
				else {
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You dont have Enemies... We dont recommend it, but you can get enemies by /faction enemy add [FactionName]" ));
				}
				sender.sendMessage(Messaging.chatFormatter("&#FF0000-----------------------------------------------" ));
				return true;
			}
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
			Team_SMP enemy = Team_SMP.getTeamFromName(name);
			if(enemy == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Could not find " + name));
				return true;
			}
			if(enemy.equals(team)) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CCCCCCYou dont like yourself? ;( "));
				return true;
			}
			

			//add enemy
			if(args.get(1).equalsIgnoreCase("add")) 
			{
				if(Common_SMP.isProtectedName(enemy.getName())) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You cant enemy with an internal faction."));
					return true;
				}
				if(team.isEnemy(enemy)) {
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You are enemies already!"));
					return true;
				}
				team.addEnemy(enemy);
				if(!enemy.isEnemy(team)) enemy.addEnemy(team);
				sender.sendMessage(Messaging.chatFormatter("&#ff61f4You are now Enemies with " + name + "!"));
				return true;

			}
			//remove enemy
			if(args.get(1).equalsIgnoreCase("remove")) {
				if(team.isEnemy(enemy)) {
					team.removeEnemy(enemy);
					sender.sendMessage(Messaging.chatFormatter("&#FF0000You are no longer allies with " + enemy.getName() + " !"));
					return true;
				}
				sender.sendMessage(Messaging.chatFormatter("&#FF0000You are not Enemies with " + enemy.getName()));
			}
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("kick")) //remove someone from the team
		{
			if(!requirePermission(player, TeamPermissions_SMP.KICK)) return true;

			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			Player kickPlayer = Bukkit.getPlayer(name);
			if(kickPlayer == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Could not find " + name));
				return true;
			}
			if(Team_SMP.getPlayersTeam(player).equals(Team_SMP.getPlayersTeam(kickPlayer))){
				Team_SMP.getPlayersTeam(player).removeMember(kickPlayer);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00" + name + " has been kicked from your Faction!"));
				return true;
			}
			sender.sendMessage(Messaging.chatFormatter("&#FF0000" + name + " is not in your Faction!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("banish")) //remove someone from the team and ban them from ever joining again
		{
			if(!requirePermission(player, TeamPermissions_SMP.BAN)) return true;
			
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			Player banPlayer = Bukkit.getPlayer(name);
			if(banPlayer == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Could not find " + name));
				return true;
			}
			Team_SMP.getPlayersTeam(player).addBanMember(banPlayer);
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(banPlayer);
			for(String expertInvite : yaml.getSmpInvitesField().split(" ")){
				Team_SMP teamInv = Team_SMP.getTeamFromID(UUID.fromString(expertInvite));
				if(Team_SMP.getPlayersTeam(player).equals(teamInv)) yaml.removeSmpInvite(Team_SMP.getPlayersTeam(player));
			}
			if(Team_SMP.getPlayersTeam(player).equals(Team_SMP.getPlayersTeam(banPlayer))){
				Team_SMP.getPlayersTeam(player).removeMember(banPlayer);
				return true;
			}
			sender.sendMessage(Messaging.chatFormatter("&#00FF00" + name + " has been banned from your Faction!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("unbanish")) //remove someone from the team and ban them from ever joining again
		{
			if(!requirePermission(player, TeamPermissions_SMP.BAN)) return true;
			
			String name = Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' ');
			Player banPlayer = Bukkit.getPlayer(name);
			if(banPlayer == null) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FF0000Could not find " + name));
				return true;
			}
			Team_SMP.getPlayersTeam(player).removeBanMember(banPlayer);
			sender.sendMessage(Messaging.chatFormatter("&#00FF00" + name + " has been unbanned from your Faction!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("disband")) //request team delete
		{
			if(!requirePermission(player, TeamPermissions_SMP.OWNER)) return true;
			
			Team_SMP.getPlayersTeam(player).deleteTeam(player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("defect")) //forcefully leave team straight into another
		{
			if(!requireTeam(player)) {
				sender.sendMessage(Messaging.chatFormatter("&#FF0000You can't leave a faction if you are not in one!"));
				return true;
			}
			sender.sendMessage(Messaging.chatFormatter("&#CCCCCCThis command is not coded yet!"));
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("transferowner")) //change the owner of the faction
		{
			if(!requirePermission(player, TeamPermissions_SMP.OWNER)) return true;
		
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name the person you are transfering ownership to!"));
				return true;
			}
			OfflinePlayer target = null;
			for(OfflinePlayer selectedTarget : Team_SMP.getPlayersTeam(player).getMembers()) 
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
			Team_SMP.getPlayersTeam(player).setOwner(target, player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("claim")) //claim a chunk of land
		{
			if(!requirePermission(player, TeamPermissions_SMP.CLAIM)) return true;
			
			Team_SMP.getPlayersTeam(player).addChunk(player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("adminclaim")) //claim a chunk of land
		{
			if(!Common_Command.requirePermission("igsq.adminclaim", player)) {
				sender.sendMessage(Messaging.chatFormatter("&#FF0000You do not have access to admin claims"));
				return true;
			}
			Chunk_SMP currentChunk = Chunk_SMP.getChunkFromLocation(player.getLocation());
			if(currentChunk != null) 
			{
				currentChunk.deleteChunk();
			}
			Common_SMP.getAdminTeam().addChunk(player.getLocation());
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("unclaim")) //unclaim a chunk of land
		{
			if(!requirePermission(player, TeamPermissions_SMP.UNCLAIM)) return true;
			
			Team_SMP.getPlayersTeam(player).removeChunk(player);
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("adminunclaim")) //unclaim a chunk of land
		{
			if(!Common_Command.requirePermission("igsq.adminclaim", player)) {
				sender.sendMessage(Messaging.chatFormatter("&#FF0000You do not have access to admin claims"));
				return true;
			}
			Chunk_SMP currentChunk = Chunk_SMP.getChunkFromLocation(player.getLocation());
			if(currentChunk != null) 
			{
				currentChunk.deleteChunk();
			}
			return true;
		}
		else if(args.get(0).equalsIgnoreCase("invites")) //look at current invites
		{
			
			
			
			if(!requireTeam(player)) {
				try {
				player.sendMessage(Messaging.chatFormatter("&#00FF00----------------List of Invites----------------"));
				player.sendMessage(Messaging.chatFormatter("&#FF0000" + playerYaml.getSmpInvitesField()));
				player.sendMessage(Messaging.chatFormatter("&#FF0000" + playerYaml.getSmpInvites()));
				if(Team_SMP.getPlayersTeam(player) == null || playerYaml.getSmpInvitesField().equals("") || playerYaml.getSmpInvites() == null) {
					player.sendMessage(Messaging.chatFormatter("&#FF0000You dont have any invites"));
					player.sendMessage(Messaging.chatFormatter("&#00FF00-----------------------------------------------"));
					return true;
				}

				for(Team_SMP invite : playerYaml.getSmpInvites()) {
					TextComponent message = new TextComponent("You have been invited to join " + invite.getName());
					message.setColor(net.md_5.bungee.api.ChatColor.of("#00FF00"));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/faction pledge " + invite.getName()));
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Join team" + invite.getName())));
					player.spigot().sendMessage(message);
				}
				player.sendMessage(Messaging.chatFormatter("&#00FF00-----------------------------------------------"));
				return true;
				}
				catch(Exception e){
					StringWriter sw = new StringWriter();
			    	PrintWriter pw = new PrintWriter(sw);
			    	e.printStackTrace(pw);
			    	String stackTrace = sw.toString();
					player.sendMessage(Messaging.chatFormatter("&#FF0000" + stackTrace));
					return true;
				}
			}
			
			Team_SMP team = Team_SMP.getPlayersTeam(player);
			player.sendMessage(Messaging.chatFormatter("&#00FF00----------------List of Faction Invites----------------"));
			player.sendMessage(Messaging.chatFormatter("&#FF0000Trying to get invites"));
			for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
				player.sendMessage(Messaging.chatFormatter("&#FF0000Trying to get invites part 2"));
				String expertInvites = new YamlPlayerWrapper(offlinePlayer).getSmpInvitesField();
				player.sendMessage(Messaging.chatFormatter("&#FF0000got the invites"));
				if(expertInvites == null || expertInvites.equals("")) continue;
				player.sendMessage(Messaging.chatFormatter("&#FF0000they arent null"));
				for(String invites : expertInvites.split(" ")){
					Team_SMP teamInv = Team_SMP.getTeamFromID(UUID.fromString(invites));
					if(team.equals(teamInv)) {
						player.sendMessage(Messaging.chatFormatter("&#00FF00" + offlinePlayer.getName()));
					}
				}
			}
			player.sendMessage(Messaging.chatFormatter("&#00FF00-----------------------------------------------------"));
			

			return true;
		}
		else if(args.get(0).equalsIgnoreCase("rank")) //Rank Settings
		{
			if(!requireTeam(player)) return true;
			Team_SMP team = Team_SMP.getPlayersTeam(player);
			if(args.size() == 1) 
			{
				sender.sendMessage(Messaging.chatFormatter("&#ccccccPlease type a sub command [add/remove/modify/list]"));
				return true;
			}
			if(args.get(1).equalsIgnoreCase("add")) 
			{
				if(!requirePermission(player, TeamPermissions_SMP.MODIFY_RANKS)) return true;
				
				if(args.size() == 2) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name your rank!"));
					return true;
				}
				String name =  Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				if(TeamRank_SMP.getRankFromName(name, team) != null) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000Your faction already has a rank named " + name));
					return true;
				}
				TeamRank_SMP teamRank = new TeamRank_SMP(team, name);
				teamRank.setPermissions(team.getDefaultRank().getPermissions());
				team.addRank(teamRank);
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Rank &#00cc00" + name + " &#00FF00was successfully created!"));
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("remove")) 
			{
				if(!requirePermission(player, TeamPermissions_SMP.MODIFY_RANKS)) return true;
				
				if(args.size() == 2) 
				{
					sender.sendMessage(Messaging.chatFormatter("&#CC0000You need to name the rank!"));
					return true;
				}
				String name =  Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				TeamRank_SMP rank = TeamRank_SMP.getRankFromName(name, team);
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
				sender.sendMessage(Messaging.chatFormatter("&#00FF00Rank &#00cc00" + rank.getName() + " &#00FF00was successfully removed!"));
				team.removeRank(rank);
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("list")) 
			{
				if(!requirePermission(player, TeamPermissions_SMP.READ_PERMISSIONS)) return true;
				
				if(args.size() == 2) 
				{
					for(TeamRank_SMP rank : team.getRanks()) rank.display(player);
					return true;
				}
				String name = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				TeamRank_SMP rank = TeamRank_SMP.getRankFromName(name, team);
				if(rank == null) sender.sendMessage(Messaging.chatFormatter("&#CC0000Could not find the rank named " + name));
				else rank.display(player);
				return true;
			}
			else if(args.get(1).equalsIgnoreCase("default")) 
			{
				if(args.size() == 2) 
				{
					if(!requirePermission(player, TeamPermissions_SMP.READ_PERMISSIONS)) return true;
					
					for(TeamRank_SMP rank : team.getRanks()) 
					{
						if(rank.isDefault()) 
						{
							sender.sendMessage(Messaging.chatFormatter("&#FFFF00The default rank is " + rank.getName() + "!"));
							break;
						}
					}
					return true;
				}
				if(!requirePermission(player, TeamPermissions_SMP.OWNER)) return true;
				
				String name = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' ');
				TeamRank_SMP rank = TeamRank_SMP.getRankFromName(name, team);
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
				TeamPermissions_SMP permission = TeamPermissions_SMP.valueOf(permissionString);
				String rankName = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' '),' ');
				TeamRank_SMP rank = TeamRank_SMP.getRankFromName(rankName, team);
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
				TeamPermissions_SMP permission = TeamPermissions_SMP.valueOf(permissionString);
				String rankName = Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.removeBeforeCharacter(Common_Shared.convertArgs(args, " "), ' '), ' '),' ');
				TeamRank_SMP rank = TeamRank_SMP.getRankFromName(rankName, team);
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
			if(Common_Command.requirePermission("igsq.team",sender) && sender instanceof Player && YamlWrapper.isSMP()) 
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
		Team_SMP team = null;
		if(sender instanceof Player) 
		{
			player = (Player) sender;
			team = Team_SMP.getPlayersTeam(player);
		}
		List<String> options = new ArrayList<>();
		if(args.length == 1) 
		{
			List<String> types = Arrays.asList("found","pledge","leave","invite","kick","banish","disband","defect","transferowner","claim","unclaim","rank","adminunclaim","adminclaim");
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
				List<TeamRank_SMP> ranks = team.getRanks();
				List<String> types = new ArrayList<>();
				for(TeamRank_SMP rank : ranks) types.add(rank.getName());
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
				for(TeamPermissions_SMP rank : TeamPermissions_SMP.values()) types.add(rank.toString());
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
				List<TeamRank_SMP> ranks = team.getRanks();
				List<String> types = new ArrayList<>();
				for(TeamRank_SMP rank : ranks) types.add(rank.getName());
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
	private boolean requireTeam(Player player) 
	{
		boolean inTeam = Team_SMP.isInATeam(player);
		if(!inTeam) player.sendMessage(Messaging.chatFormatter("&#FF0000You are not in a faction! This command is for factions only!"));
		return inTeam;
	}
	private boolean requireNoTeam(Player player) 
	{
		boolean inTeam = Team_SMP.isInATeam(player);
		if(inTeam) player.sendMessage(Messaging.chatFormatter("&#FF0000You are already in a faction! This command is for the factionless only!"));
		return !inTeam;
	}
	private boolean requirePermission(Player player,TeamPermissions_SMP permission) 
	{
		if(!requireTeam(player)) return false;
		Team_SMP team = Team_SMP.getPlayersTeam(player);
		boolean hasPermission = team.hasPermission(player, permission);
		if(!hasPermission) player.sendMessage(Messaging.chatFormatter("&#FF0000You are missing permission &#FFb900" + permission.toString() + " &#FF0000to run this command!"));
		return hasPermission;
	}
}
