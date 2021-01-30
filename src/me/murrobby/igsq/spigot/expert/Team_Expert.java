package me.murrobby.igsq.spigot.expert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;

public class Team_Expert 
{
	private static List<Team_Expert> teams = new ArrayList<>();
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
		yaml.applyDefault();
		longStore();
	}
	public Team_Expert(UUID uid) 
	{
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
	
	public OfflinePlayer getOwner() 
	{
		return Bukkit.getOfflinePlayer(UUID.fromString(yaml.getOwner()));
	}
	
	public boolean isOwner(OfflinePlayer player) 
	{
		return UUID.fromString(yaml.getOwner()).equals(player.getUniqueId());
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
		List<UUID> members = getRawMembers();
		members.add(newMember.getUniqueId());
		setMembers(members);
	}
	public void removeMember(OfflinePlayer newMember) 
	{
		if(!isInTeam(newMember)) return;
		List<UUID> members = getRawMembers();
		members.remove(newMember.getUniqueId());
		setMembers(members);
	}
	public void deleteTeam() 
	{
		deleteTeam(null);
	}
	public void deleteTeam(Player changer) 
	{
		if(changer != null && !isOwner(changer)) 
		{
			changer.sendMessage(Messaging.chatFormatter("&#FF0000Only the owner can disband a faction"));
			return;
		}
		if(getRawMembers().size() == 1 || changer == null) 
		{
			if (changer != null) changer.sendMessage(Messaging.chatFormatter("&#00FF00Faction disbanded successfully."));
			yaml.delete(getUID().toString());
			teams.remove(this);
			longStore();
		}
		else 
		{
			changer.sendMessage(Messaging.chatFormatter("&#FF0000The team is not empty! Consider transfering leadership and leaving!"));
		}
	}
	public boolean setName(String name) 
	{
		return setName(name,null);
	}
	public void setOwner(OfflinePlayer player) 
	{
		setOwner(player,null);
	}
	
	public void setOwner(OfflinePlayer player,Player changer) 
	{
		if(changer != null && !isOwner(changer)) //Can the changer change the name
		{
			changer.sendMessage(Messaging.chatFormatter("&#FF0000You need to be the current leader to transfer leadership!"));
			return;
		}
		if(!isInATeam(player)) // is the new owner in the team
		{
			if(changer != null) changer.sendMessage(Messaging.chatFormatter("&#FF0000That person needs to be in the faction to transfer leadership!"));
			return;
		}
		yaml.setOwner(player.getUniqueId().toString());
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId().toString());
		if(changer != null) for(Player selectedPlayer : getOnlineMembers()) selectedPlayer.sendMessage(Messaging.chatFormatter("&#00cc00" + yaml.getNickname() + " &#00FF00is the captain now!"));
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
	public boolean addChunk(Player changer) 
	{
		Chunk selectedChunk = changer.getLocation().getChunk();
		if(Chunk_Expert.isChunkOwned(selectedChunk)) 
		{
			Chunk_Expert ownedChunk = Chunk_Expert.getChunkFromLocation(selectedChunk);
			if(ownedChunk.isOwnedBy(this)) changer.sendMessage(Messaging.chatFormatter("&#FFb900Chunk at" + ownedChunk.getLocation().get(0) + ", " + ownedChunk.getLocation().get(1) + " in " + ownedChunk.getWorld().getName() + " is already owned by your faction!"));
			else changer.sendMessage(Messaging.chatFormatter("&#FF0000Chunk at" + ownedChunk.getLocation().get(0) + ", " + ownedChunk.getLocation().get(1) + " in " + ownedChunk.getWorld().getName() + " is already owned by the faction &#CD0000" + ownedChunk.getOwner().getName()));
			return true;
		}
		if(changer == null || isOwner(changer)) 
		{
			Chunk_Expert chunk = new Chunk_Expert(selectedChunk);
			chunk.setOwner(this);
			if(changer != null) for(Player selectedPlayer : getOnlineMembers()) selectedPlayer.sendMessage(Messaging.chatFormatter("&#00FF00" + new YamlPlayerWrapper(changer).getNickname()+ " has claimed a new chunk at " + chunk.getLocation().get(0) + ", " + chunk.getLocation().get(1) + " in " + chunk.getWorld().getName() + "."));
		}
		else changer.sendMessage(Messaging.chatFormatter("&#FF0000You need to be the current leader to add a chunk!"));
		return true;
	}
	public boolean removeChunk(Player changer) 
	{
		Chunk selectedChunk = changer.getLocation().getChunk();
		if(Chunk_Expert.isChunkOwned(selectedChunk)) 
		{
			Chunk_Expert chunk = Chunk_Expert.getChunkFromLocation(selectedChunk);
			if(chunk.isOwnedBy(this)) 
			{
				if(changer == null || isOwner(changer)) 
				{
					chunk.deleteChunk(changer);
					if(changer != null) for(Player selectedPlayer : getOnlineMembers()) selectedPlayer.sendMessage(Messaging.chatFormatter("&#FF0000" + new YamlPlayerWrapper(changer).getNickname() + " has unclaimed a chunk at " + chunk.getLocation().get(0) + ", " + chunk.getLocation().get(1) + " in " + chunk.getWorld().getName() + "."));
				}
				else changer.sendMessage(Messaging.chatFormatter("&#FF0000You need to be the current leader to remove a chunk!"));
			}
			else changer.sendMessage(Messaging.chatFormatter("&#FF0000Chunk at" + chunk.getLocation().get(0) + ", " + chunk.getLocation().get(1) + " in " + chunk.getWorld().getName() + " is owned by the faction &#CD0000" + chunk.getOwner().getName()));
			return true;
		}
		changer.sendMessage(Messaging.chatFormatter("&#00cc00This chunk is wilderness!"));
		return true;
	}
	public boolean isInTeam(OfflinePlayer player) 
	{
		for(UUID offline : getRawMembers()) if(player.getUniqueId().equals(offline)) return true;
		return false;
	}
	public List<UUID> getRawRanks() 
	{
		List<UUID> ranks = new ArrayList<>();
		for(String rankString : yaml.getRanks().split(" ")) if (!rankString.equals("")) ranks.add(UUID.fromString(rankString));
		return ranks;
	}
	public List<TeamRank_Expert> getRanks() 
	{
		List<TeamRank_Expert> ranks = new ArrayList<>();
		for(UUID rankUID : getRawRanks()) ranks.add(TeamRank_Expert.getRankFromID(rankUID));
		return ranks;
	}
	private void setRanks(List<TeamRank_Expert> ranks) 
	{
		if(ranks.size() == 0) 
		{
			yaml.setRanks("");
			return;
		}
		String ranksString = ranks.get(0).getUID().toString();
		for(int i = 1; i < ranks.size();i++) ranksString += " " + ranks.get(i).getUID().toString();
		yaml.setRanks(ranksString);
	}
	public void removeRank(TeamRank_Expert rank) 
	{
		List<TeamRank_Expert> ranks = getRanks();
		ranks.remove(rank);
		setRanks(ranks);
	}
	public void addRank(TeamRank_Expert rank) 
	{
		List<TeamRank_Expert> ranks = getRanks();
		ranks.add(rank);
		setRanks(ranks);
	}
	public TeamRank_Expert getPlayersRank(OfflinePlayer player) 
	{
		if(!isInTeam(player)) return null;
		return TeamRank_Expert.getPlayersRank(player);
	}
	public boolean hasPermission(OfflinePlayer player,TeamPermissions_Expert permission) 
	{
		if(getOwner().getUniqueId().equals(player.getUniqueId())) return true; //Owners can always do anything
		return getPlayersRank(player).hasPermission(permission);
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
	public static void longStore()
	{
		List<String> names = new ArrayList<>();
		for(Team_Expert team : teams) names.add(team.getUID().toString());
		YamlTeamWrapper_Expert.setTeams(String.join(" ", names));
	}
	public static void longBuild()
	{
		String teams = YamlTeamWrapper_Expert.getTeams();
		if(teams == null || teams.equalsIgnoreCase("")) 
		{
			YamlTeamWrapper_Expert.setTeams("");
			return;
		}
		for(String teamString : teams.split(" ")) 
		{
			Team_Expert team = new Team_Expert(UUID.fromString(teamString));
			for(UUID rankUID : team.getRawRanks()) new TeamRank_Expert(rankUID);
		}
	}
}