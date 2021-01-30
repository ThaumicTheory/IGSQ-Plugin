package me.murrobby.igsq.spigot.expert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;

public class TeamRank_Expert 
{
	private final UUID UID;
	private final YamlTeamRankWrapper_Expert yaml;
	private static List<TeamRank_Expert> ranks = new ArrayList<>();
	public TeamRank_Expert(Team_Expert owner,String rankName) 
	{
		UUID uid = null;
		do 
		{
			uid = UUID.randomUUID();
		}
		while(isRank(uid));
		this.UID = uid;
		yaml = new YamlTeamRankWrapper_Expert(uid);
		yaml.setOwner(owner.getUID());
		ranks.add(this);
		
	}
	public TeamRank_Expert(UUID uid) 
	{
		this.UID = uid;
		yaml = new YamlTeamRankWrapper_Expert(uid);
		ranks.add(this);
	}
	public UUID getUID() 
	{
		return UID;
	}
	public String getName() 
	{
		return yaml.getName();
	}
	public void setName(String name) 
	{
		yaml.setName(name);
	}
	public Team_Expert getOwner() 
	{
		return Team_Expert.getTeamFromID(UUID.fromString(yaml.getOwner()));
	}
	public void setOwner(Team_Expert faction) 
	{
		yaml.setOwner(faction.getUID());
	}
	
	public boolean isDefault() 
	{
		return yaml.getDefault();
	}
	public void setDefault()
	{
		for(TeamRank_Expert rank : ranks) 
		{
			if(rank.equals(this)) yaml.setDefault(true);
			else yaml.setDefault(false);
		}
	}
	
	public List<UUID> getRawMembers() 
	{
		List<UUID> rawPlayers = new ArrayList<>();
		for(String member : yaml.getMembers().split(" ")) if (!member.equals("")) rawPlayers.add(UUID.fromString(member));
		return rawPlayers;
	}
	
	public List<OfflinePlayer> getMembers() 
	{
		List<UUID> rawPlayers = getRawMembers();
		List<OfflinePlayer> players = new ArrayList<>();
		for(UUID member : rawPlayers) players.add(Bukkit.getOfflinePlayer(member));
		return players;
	}
	public List<Player> getOnlineMembers() 
	{
		List<OfflinePlayer> offlinePlayers = getMembers();
		List<Player> players = new ArrayList<>();
		for(OfflinePlayer member : offlinePlayers) if(member.getPlayer() != null) players.add(member.getPlayer());
		return players;
	}
	private void setMembers(List<UUID> newMembers) 
	{
		if(newMembers.size() == 0) 
		{
			yaml.setMembers("");
			return;
		}
		String membersString = newMembers.get(0).toString();
		for(int i = 1; i < newMembers.size();i++) membersString += " " + newMembers.get(i).toString();
		yaml.setMembers(membersString);
	}
	public void addMember(OfflinePlayer newMember) 
	{
		if(!getOwner().isInTeam(newMember)) return;
		List<UUID> members = getRawMembers();
		members.add(newMember.getUniqueId());
		setMembers(members);
	}
	public void removeMember(OfflinePlayer newMember) 
	{
		if(!getOwner().isInTeam(newMember)) return;
		List<UUID> members = getRawMembers();
		members.remove(newMember.getUniqueId());
		setMembers(members);
	}
	
	public List<TeamPermissions_Expert> getPermissions() 
	{
		List<TeamPermissions_Expert> permissions = new ArrayList<>();
		for(String permissionString : yaml.getPermissions().split(" ")) if(!permissionString.equals("")) permissions.add(TeamPermissions_Expert.valueOf(permissionString));
		return permissions;
	}
	public void setPermissions(List<TeamPermissions_Expert> permissions) 
	{
		if(permissions.size() == 0) 
		{
			yaml.setPermissions("");
			return;
		}
		String permissionsString = permissions.get(0).toString();
		for(int i = 1; i < permissions.size();i++) permissionsString += " " + permissions.get(i).toString();
		yaml.setPermissions(permissionsString);
	}
	public void removePermission(TeamPermissions_Expert permission) 
	{
		List<TeamPermissions_Expert> permissions = getPermissions();
		permissions.remove(permission);
		setPermissions(permissions);
	}
	public void addPermission(TeamPermissions_Expert permission) 
	{
		List<TeamPermissions_Expert> permissions = getPermissions();
		permissions.add(permission);
		setPermissions(permissions);
	}
	
	public boolean hasPermission(TeamPermissions_Expert permission) 
	{
		for(TeamPermissions_Expert perm : getPermissions()) if(perm.equals(permission)) return true;
		return false;
	}
	
	
	
	
	
	
	
	
	public static TeamRank_Expert getRankFromName(String name,Team_Expert team) 
	{
		for(TeamRank_Expert rank : ranks) 
		{
			if(rank.getName().equalsIgnoreCase(name) && team.getUID().equals(rank.getOwner().getUID())) return rank;
		}
		return null;
	}
	public static TeamRank_Expert getRankFromID(UUID uid) 
	{
		for(TeamRank_Expert rank : ranks) 
		{
			if(rank.getUID().equals(uid)) return rank;
		}
		return null;
	}
	public static boolean isRank(UUID uid) 
	{
		return getRankFromID(uid) != null;
	}
	public static TeamRank_Expert getPlayersRank(OfflinePlayer player) 
	{
		for(TeamRank_Expert rank : ranks) for(UUID member : rank.getRawMembers()) if(member.equals(player.getUniqueId())) return rank;
		return null;
	}
	public void display(Player player) 
	{
		String message = "&l&#00FFFF"+ getName() + "\n&r&#00FF00Permissions\n";
		int number = 0;
		for(TeamPermissions_Expert permission : getPermissions()) 
		{
			if(permission.isImportant()) message += "&l";
			
			message += permission.toString() +"&r&#00FF00";
			if(++number != getPermissions().size()) message += ", ";
		}
		
		message += "\n&#FFFF00Is " + (isDefault() ? "" : "&#cc0000not ") + "&#FFFF00Default\n&##00FFFF";
		
		number = 0;
		for(OfflinePlayer selectedPlayer : getMembers()) 
		{
			if(player.getUniqueId().equals(selectedPlayer.getUniqueId())) message += "&l";
			message += selectedPlayer.getName() +"&r&#00FFFF";
			if(++number != getPermissions().size()) message += ", ";
		}
		player.sendMessage(Messaging.chatFormatter(message));
	}
}
