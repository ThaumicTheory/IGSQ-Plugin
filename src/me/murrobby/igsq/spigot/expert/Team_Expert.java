package me.murrobby.igsq.spigot.expert;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;

public class Team_Expert 
{
	private static ArrayList<Team_Expert> teams = new ArrayList<Team_Expert>();
	private final UUID UID;
	private final YamlTeamWrapper_Expert yaml;
	
	public Team_Expert() 
	{
		UUID uid = null;
		do 
		{
			uid = UUID.randomUUID();
		}
		while(getTeamFromID(uid) != null);
		this.UID = uid;
		teams.add(this);
		this.yaml = new YamlTeamWrapper_Expert(this);
	}
	
	public UUID getUID() 
	{
		return UID;
	}
	
	public String getName() 
	{
		return yaml.getName();
	}
	
	public ArrayList<UUID> getRawMembers() 
	{
		ArrayList<UUID> rawPlayers = new ArrayList<>();
		for(String member : yaml.getMembers().split(" ")) rawPlayers.add(UUID.fromString(member));
		return rawPlayers;
	}
	
	public ArrayList<OfflinePlayer> getMembers() 
	{
		ArrayList<UUID> rawPlayers = getRawMembers();
		ArrayList<OfflinePlayer> players = new ArrayList<>();
		for(UUID member : rawPlayers) players.add(Bukkit.getOfflinePlayer(member));
		return players;
	}
	public ArrayList<Player> getOnlineMembers() 
	{
		ArrayList<OfflinePlayer> offlinePlayers = getMembers();
		ArrayList<Player> players = new ArrayList<>();
		for(OfflinePlayer member : offlinePlayers) if(member.getPlayer() != null) players.add(member.getPlayer());
		return players;
	}
	private void setMembers(ArrayList<UUID> newMembers) 
	{
		if(newMembers.size() == 0) 
		{
			deleteTeam();
			return;
		}
		String membersString = newMembers.get(0).toString();
		for(int i = 1; i < newMembers.size();i++) membersString += " " + newMembers.get(i).toString();
		yaml.setMembers(membersString);
	}
	public void addMember(OfflinePlayer newMember) 
	{
		if(isInATeam(newMember)) return;
		ArrayList<UUID> members = getRawMembers();
		members.add(newMember.getUniqueId());
		setMembers(members);
	}
	public void removeMember(OfflinePlayer newMember) 
	{
		if(!isInTeam(newMember)) return;
		ArrayList<UUID> members = getRawMembers();
		members.remove(newMember.getUniqueId());
		setMembers(members);
	}
	public void deleteTeam() 
	{
		yaml.delete(getName());
	}
	public boolean setName(String name) 
	{
		return setName(name,null);
	}
	public boolean setName(String name,Player changer) 
	{
		if(yaml.getName().equals(name)) //If input is the same as yaml its already this
		{
			if(changer != null) changer.sendMessage(Messaging.chatFormatter("&#ccccccThe team is already called " + name));
			return false;
		}
		 Team_Expert team = getTeamFromName(name);
		if(team != null && !team.equals(this)) //If a team has this name and this team does not (for Case Fixing)
		{
			if(changer != null) changer.sendMessage(Messaging.chatFormatter("&#FF0000A team called "+ team.getName() +" already exists!"));
			return false;
		}
		yaml.setName(name);
		for(Player player : getOnlineMembers()) player.sendMessage(Messaging.chatFormatter("&#00FF00Your team name was changed to &#00cc00"+ getName() +"&#00FF00!"));
		return true;
	}
	public boolean isInTeam(OfflinePlayer player) 
	{
		for(UUID offline : getRawMembers()) if(player.getUniqueId().equals(offline)) return true;
		return false;
	}
	public static Team_Expert getPlayersTeam(OfflinePlayer player) 
	{
		for(Team_Expert team : teams) if(team.isInTeam(player)) return team;
		return null;
	}
	public static boolean isInATeam(OfflinePlayer player) 
	{
		return getPlayersTeam(player) != null;
	}
	public static Team_Expert getTeamFromID(UUID uid) 
	{
		for(Team_Expert team : teams) if(team.getUID().equals(uid)) return team;
		return null;
	}
	public static Team_Expert getTeamFromName(String name) 
	{
		for(Team_Expert team : teams) if(team.getName().equalsIgnoreCase(name)) return team;
		return null;
	}
}
