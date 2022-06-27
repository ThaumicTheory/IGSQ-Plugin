package thaumictheory.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.Messaging;

public class TeamRank_SMP 
{
	private final UUID UID;
	private final YamlTeamRankWrapper_SMP yaml;
	private static List<TeamRank_SMP> ranks = new ArrayList<>();
	public TeamRank_SMP(Team_SMP owner,String rankName) 
	{
		UUID uid = null;
		do 
		{
			uid = UUID.randomUUID();
		}
		while(isRank(uid));
		this.UID = uid;
		yaml = new YamlTeamRankWrapper_SMP(uid);
		yaml.applyDefault();
		setName(rankName);
		setOwner(owner);
		ranks.add(this);
		
	}
	public TeamRank_SMP(UUID uid) 
	{
		this.UID = uid;
		yaml = new YamlTeamRankWrapper_SMP(uid);
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
	public Team_SMP getOwner() 
	{
		return Team_SMP.getTeamFromID(UUID.fromString(yaml.getOwner()));
	}
	public void setOwner(Team_SMP faction) 
	{
		yaml.setOwner(faction.getUID());
	}
	
	public boolean isDefault() 
	{
		return yaml.getDefault();
	}
	public void setDefault()
	{
		for(TeamRank_SMP rank : ranks) 
		{
			if(rank.equals(this)) yaml.setDefault(true);
			else rank.yaml.setDefault(false);
		}
	}
	
	public boolean isGivable() 
	{
		return yaml.getGivable();
	}
	public void setGivable(boolean givable)
	{
		yaml.setGivable(givable);
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
		if(newMember == null) return;
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
	
	public List<TeamPermissions_SMP> getPermissions() 
	{
		List<TeamPermissions_SMP> permissions = new ArrayList<>();
		for(String permissionString : yaml.getPermissions().split(" ")) if(!permissionString.equals("")) permissions.add(TeamPermissions_SMP.valueOf(permissionString));
		return permissions;
	}
	public void setPermissions(List<TeamPermissions_SMP> permissions) 
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
	public void removePermission(TeamPermissions_SMP permission) 
	{
		List<TeamPermissions_SMP> permissions = getPermissions();
		permissions.remove(permission);
		setPermissions(permissions);
	}
	public void addPermission(TeamPermissions_SMP permission) 
	{
		List<TeamPermissions_SMP> permissions = getPermissions();
		permissions.add(permission);
		setPermissions(permissions);
	}
	
	public boolean hasPermission(TeamPermissions_SMP permission) 
	{
		for(TeamPermissions_SMP perm : getPermissions()) if(perm.equals(permission)) return true;
		return false;
	}
	
	
	
	
	
	
	
	
	public static TeamRank_SMP getRankFromName(String name,Team_SMP team) 
	{
		for(TeamRank_SMP rank : ranks) 
		{
			if(rank.getName().equalsIgnoreCase(name) && team.getUID().equals(rank.getOwner().getUID())) return rank;
		}
		return null;
	}
	public static TeamRank_SMP getRankFromID(UUID uid) 
	{
		for(TeamRank_SMP rank : ranks) 
		{
			if(rank.getUID().equals(uid)) return rank;
		}
		return null;
	}
	public static boolean isRank(UUID uid) 
	{
		return getRankFromID(uid) != null;
	}
	public static TeamRank_SMP getPlayersRank(OfflinePlayer player) 
	{
		for(TeamRank_SMP rank : ranks) for(UUID member : rank.getRawMembers()) if(member.equals(player.getUniqueId())) return rank;
		return null;
	}
	public boolean canModify(OfflinePlayer player) 
	{
		TeamRank_SMP modifier = getPlayersRank(player);
		TeamRank_SMP modified = this;
		if(modifier.equals(modified)) return false; //you cant change your own role
		if(modifier.hasPermission(TeamPermissions_SMP.OWNER)) return true;
		int score = 0;
		List<TeamPermissions_SMP> modifierPermissions = modifier.getPermissions();
		List<TeamPermissions_SMP> modifiedPermissions = modified.getPermissions();
		for(TeamPermissions_SMP perm : modifierPermissions) 
		{
			score += (modifiedPermissions.remove(perm) ? 0 : 1);
		}
		if(modifiedPermissions.size() == 0 && score > 0) return true;
		return false;
	}
	public boolean canDelete(OfflinePlayer player) 
	{
		if(!canModify(player) || isDefault() || !isGivable()) return false;	
		return true;
	}
	public void display(Player player) 
	{
		String message = "&l&#00FFFF"+ getName() + "\n&r&#00FF00Permissions\n";
		int number = 0;
		for(TeamPermissions_SMP permission : getPermissions()) 
		{
			if(permission.isImportant()) message += "&l";
			
			message += permission.toString() +"&r&#00FF00";
			if(++number != getPermissions().size()) message += ", ";
		}
		
		message += "\n&#FFFF00Is " + (isDefault() ? "" : "&#cc0000not ") + "&#FFFF00Default\n&#a900FFMembers\n";
		
		number = 0;
		for(OfflinePlayer selectedPlayer : getMembers()) 
		{
			if(player.getUniqueId().equals(selectedPlayer.getUniqueId())) message += "&l";
			message += new YamlPlayerWrapper(selectedPlayer.getUniqueId()).getNickname() +"&r&#a900FF";
			if(++number != getPermissions().size()) message += ", ";
		}
		player.sendMessage(Messaging.chatFormatter(message));
	}
	public void delete() 
	{
		ranks.remove(this);
		yaml.delete();
	}
}
