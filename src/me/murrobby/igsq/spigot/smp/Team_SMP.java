package me.murrobby.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;

public class Team_SMP 
{
	private static List<Team_SMP> teams = new ArrayList<>();
	private final UUID UID;
	private final YamlTeamWrapper_SMP yaml;
	
	public Team_SMP(String name,OfflinePlayer owner) 
	{
		UUID uid = null;
		do 
		{
			uid = UUID.randomUUID();
		}
		while(getTeamFromID(uid) != null);
		this.UID = uid;
		teams.add(this);
		this.yaml = new YamlTeamWrapper_SMP(this);
		yaml.applyDefault();
		if(owner != null) 
		{
			addMember(owner);
			TeamRank_SMP ownerRank = new TeamRank_SMP(this, "founder");
			ownerRank.setGivable(false);
			ownerRank.addPermission(TeamPermissions_SMP.OWNER);
			ownerRank.addMember(owner);
			addRank(ownerRank);
			yaml.setOwner(owner.getUniqueId().toString());
			TeamRank_SMP defaultRank = new TeamRank_SMP(this, "member");
			defaultRank.setDefault();
			defaultRank.addPermission(TeamPermissions_SMP.INVITE);
			defaultRank.addPermission(TeamPermissions_SMP.READ_PERMISSIONS);
			addRank(defaultRank);
		}
		yaml.setName(name);
		longStore();
	}
	public Team_SMP(UUID uid) 
	{
		this.UID = uid;
		teams.add(this);
		this.yaml = new YamlTeamWrapper_SMP(this);
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
		if(yaml.getOwner() == null || yaml.getOwner().equals("")) return null;
		return Bukkit.getOfflinePlayer(UUID.fromString(yaml.getOwner()));
	}
	
	public boolean isOwner(OfflinePlayer player) 
	{
		if(yaml.getOwner() == null || yaml.getOwner().equals("")) return false;
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
		if(newMember == null) return;
		if(isInATeam(newMember)) return;
		List<UUID> members = getRawMembers();
		members.add(newMember.getUniqueId());
		setMembers(members);
	}
	public void removeMember(OfflinePlayer removeMember) 
	{
		if(!isInTeam(removeMember)) return;
		List<UUID> members = getRawMembers();
		members.remove(removeMember.getUniqueId());
		setMembers(members);
	}

	public List<UUID> getRawBanMembers() 
	{
		List<UUID> rawPlayers = new ArrayList<>();
		for(String member : yaml.getBanned().split(" ")) if (!member.equals("")) rawPlayers.add(UUID.fromString(member));
		return rawPlayers;
	}
	
	public List<OfflinePlayer> getBanMembers() 
	{
		List<UUID> rawPlayers = getRawBanMembers();
		List<OfflinePlayer> players = new ArrayList<>();
		for(UUID member : rawPlayers) players.add(Bukkit.getOfflinePlayer(member));
		return players;
	}
	private void setBanMembers(List<UUID> newMembers) 
	{
		if(newMembers.size() == 0) 
		{
			yaml.setBanned("");
			return;
		}
		String membersString = newMembers.get(0).toString();
		for(int i = 1; i < newMembers.size();i++) membersString += " " + newMembers.get(i).toString();
		yaml.setBanned(membersString);
	}
	public void addBanMember(OfflinePlayer newMember) 
	{
		if(isInATeam(newMember)) return;
		List<UUID> members = getRawBanMembers();
		members.add(newMember.getUniqueId());
		setBanMembers(members);
	}
	public void removeBanMember(OfflinePlayer removeMember) 
	{
		if(!isInTeam(removeMember)) return;
		List<UUID> members = getRawBanMembers();
		members.remove(removeMember.getUniqueId());
		setBanMembers(members);
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
			for(TeamRank_SMP rank : getRanks()) rank.delete();
			Chunk_SMP.deleteChunk(this);
			yaml.delete();
			teams.remove(this);
			longStore();
			if (changer != null) changer.sendMessage(Messaging.chatFormatter("&#00FF00Faction disbanded successfully."));
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
		if(changer != null && !isOwner(changer)) //Can the changer change the leader
		{
			changer.sendMessage(Messaging.chatFormatter("&#FF0000You need to be the current leader to transfer leadership!"));
			return;
		}
		if(changer != null && player.getUniqueId().equals(changer.getUniqueId())) 
		{
			changer.sendMessage(Messaging.chatFormatter("&#cccccc" + player.getName() + " hurt itself in confusion."));
			return;
		}
		if(isOwner(player)) 
		{
			System.out.println("The code hurt itself in confusion.");
			return;
		}
		if(!isInATeam(player)) // is the new owner in the team
		{
			if(changer != null) changer.sendMessage(Messaging.chatFormatter("&#FF0000That person needs to be in the faction to transfer leadership!"));
			return;
		}
		TeamRank_SMP ownerRank = getPlayersRank(getOwner());
		ownerRank.removeMember(getOwner());
		getDefaultRank().addMember(getOwner());
		yaml.setOwner(player.getUniqueId().toString());
		ownerRank.addMember(player);
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
		Team_SMP team = getTeamFromName(name);
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
		if(changer == null) return false;
		Chunk selectedChunk = changer.getLocation().getChunk();
		if(Chunk_SMP.isChunkOwned(selectedChunk)) 
		{
			Chunk_SMP ownedChunk = Chunk_SMP.getChunkFromLocation(selectedChunk);
			if(ownedChunk.isOwnedBy(this)) changer.sendMessage(Messaging.chatFormatter("&#FFb900Chunk at" + ownedChunk.getLocation().get(0) + ", " + ownedChunk.getLocation().get(1) + " in " + ownedChunk.getWorld().getName() + " is already owned by your faction!"));
			else changer.sendMessage(Messaging.chatFormatter("&#FF0000Chunk at" + ownedChunk.getLocation().get(0) + ", " + ownedChunk.getLocation().get(1) + " in " + ownedChunk.getWorld().getName() + " is already owned by the faction &#CD0000" + ownedChunk.getOwner().getName()));
			return false;
		}
		if(!Chunk_SMP.isChunkClaimable(changer)) return false;
		Chunk_SMP chunk = new Chunk_SMP(selectedChunk);
		chunk.setOwner(this);
		if(changer != null) for(Player selectedPlayer : getOnlineMembers()) selectedPlayer.sendMessage(Messaging.chatFormatter("&#00FF00" + new YamlPlayerWrapper(changer).getNickname()+ " has claimed a new chunk at " + chunk.getLocation().get(0) + ", " + chunk.getLocation().get(1) + " in " + chunk.getWorld().getName() + "."));
		return true;
	}
	public boolean addChunk(Location location) 
	{
		if(location == null) return false;
		Chunk selectedChunk = location.getChunk();
		if(Chunk_SMP.isChunkOwned(selectedChunk)) return false;
		Chunk_SMP chunk = new Chunk_SMP(selectedChunk);
		chunk.setOwner(this);
		return true;
	}
	public boolean removeChunk(Player changer) 
	{
		Chunk selectedChunk = changer.getLocation().getChunk();
		if(Chunk_SMP.isChunkOwned(selectedChunk)) 
		{
			Chunk_SMP chunk = Chunk_SMP.getChunkFromLocation(selectedChunk);
			if(chunk.isOwnedBy(this)) 
			{
				if(changer == null || isOwner(changer)) 
				{
					if(changer != null) for(Player selectedPlayer : getOnlineMembers()) selectedPlayer.sendMessage(Messaging.chatFormatter("&#FF0000" + new YamlPlayerWrapper(changer).getNickname() + " has unclaimed a chunk at " + chunk.getLocation().get(0) + ", " + chunk.getLocation().get(1) + " in " + chunk.getWorld().getName() + "."));
					chunk.deleteChunk();
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
		if(player == null) return false;
		for(UUID offline : getRawMembers()) if(player.getUniqueId().equals(offline)) return true;
		return false;
	}
	public List<UUID> getRawRanks() 
	{
		List<UUID> ranks = new ArrayList<>();
		for(String rankString : yaml.getRanks().split(" ")) if (!rankString.equals("")) ranks.add(UUID.fromString(rankString));
		return ranks;
	}
	public List<TeamRank_SMP> getRanks() 
	{
		List<TeamRank_SMP> ranks = new ArrayList<>();
		for(UUID rankUID : getRawRanks()) ranks.add(TeamRank_SMP.getRankFromID(rankUID));
		return ranks;
	}
	private void setRanks(List<TeamRank_SMP> ranks) 
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
	public void removeRank(TeamRank_SMP rank) 
	{
		List<TeamRank_SMP> ranks = getRanks();
		ranks.remove(rank);
		rank.delete();
		setRanks(ranks);
	}
	public void addRank(TeamRank_SMP rank) 
	{
		List<TeamRank_SMP> ranks = getRanks();
		ranks.add(rank);
		setRanks(ranks);
	}
	public TeamRank_SMP getPlayersRank(OfflinePlayer player) 
	{
		if(!isInTeam(player)) return null;
		return TeamRank_SMP.getPlayersRank(player);
	}
	public boolean hasPermission(OfflinePlayer player,TeamPermissions_SMP permission) 
	{
		if(getOwner().getUniqueId().equals(player.getUniqueId())) return true; //Owners can always do anything
		return getPlayersRank(player).hasPermission(permission);
	}
	
	public TeamRank_SMP getDefaultRank() 
	{
		for(TeamRank_SMP rank : getRanks()) if(rank.isDefault()) return rank;
		return null;
	}

	public boolean isAlly(Team_SMP team)
	{
		if(team == null) return false;
		for(Team_SMP ally : getAllies()) if(ally.equals(team)) return true;
		return false;
	}
	public boolean isAllyPending(Team_SMP team)
	{
		if(team == null) return false;
		if(isAllyInPending(team) || isAllyOutPending(team)) return true;
		return false;
	}
	public boolean isAllyInPending(Team_SMP team)
	{
		if(team == null) return false;
		for(Team_SMP ally : getAlliesPending()) if(ally.equals(team)) return true;
		return false;	
	}
	public boolean isAllyOutPending(Team_SMP team)
	{
		for(Team_SMP ally : team.getAlliesPending()) if(ally.equals(team)) return true;
		return false;	
	}
	
	
	public List<UUID> getRawAllies() 
	{
		List<UUID> allies = new ArrayList<>();
		for(String alliesString : yaml.getAlly().split(" ")) if (!alliesString.equals("")) allies.add(UUID.fromString(alliesString));
		return allies;
	}
	
	public List<Team_SMP> getAllies() 
	{
		List<Team_SMP> allies = new ArrayList<>();
		for(UUID allyUID : getRawAllies()) allies.add(Team_SMP.getTeamFromID(allyUID));
		return allies;
	}
	private void setAlly(List<Team_SMP> allies) 
	{
		if(allies.size() == 0) 
		{
			yaml.setAlly("");
			return;
		}
		String alliesString = allies.get(0).getUID().toString();
		for(int i = 1; i < allies.size();i++) alliesString += " " + allies.get(i).getUID().toString();
		yaml.setAlly(alliesString);
	}
	public void removeAlly(Team_SMP ally) 
	{
		List<Team_SMP> allies = getAllies();
		allies.remove(ally);
		setAlly(allies);
		
		List<Team_SMP> alliesAllies = ally.getAllies();
		alliesAllies.remove(this);
		ally.setAlly(alliesAllies);
	}
	public void addAlly(Team_SMP ally) 
	{
		List<Team_SMP> allies = getAllies();
		allies.add(ally);
		setAlly(allies);
		
		List<Team_SMP> alliesAllies = ally.getAllies();
		alliesAllies.add(this);
		ally.setAlly(alliesAllies);
	}
	
	public List<UUID> getRawAlliesPending() 
	{
		List<UUID> allies = new ArrayList<>();
		for(String alliesString : yaml.getAllyPending().split(" ")) if (!alliesString.equals("")) allies.add(UUID.fromString(alliesString));
		return allies;
	}
	
	public List<Team_SMP> getAlliesPending() 
	{
		List<Team_SMP> allies = new ArrayList<>();
		for(UUID allyUID : getRawAlliesPending()) allies.add(Team_SMP.getTeamFromID(allyUID));
		return allies;
	}
	private void setAllyPending(List<Team_SMP> allies) 
	{
		if(allies.size() == 0) 
		{
			yaml.setAllyPending("");
			return;
		}
		String alliesString = allies.get(0).getUID().toString();
		for(int i = 1; i < allies.size();i++) alliesString += " " + allies.get(i).getUID().toString();
		yaml.setAllyPending(alliesString);
	}
	public void removeAllyPending(Team_SMP ally) 
	{
		List<Team_SMP> allies = getAlliesPending();
		allies.remove(ally);
		setAllyPending(allies);
		
		List<Team_SMP> alliesAllies = ally.getAlliesPending();
		alliesAllies.remove(this);
		ally.setAllyPending(alliesAllies);
	}
	public void addAllyPending(Team_SMP ally) 
	{
		List<Team_SMP> allies = getAlliesPending();
		allies.add(ally);
		setAllyPending(allies);
	}
	
	public boolean isEnemy(Team_SMP team)
	{
		for(Team_SMP enemy : getEnemies()) if(enemy.equals(team)) return true;
		return false;
	}
	public List<UUID> getRawEnemies() 
	{
		List<UUID> enemies = new ArrayList<>();
		for(String enemiesString : yaml.getEnemy().split(" ")) if (!enemiesString.equals("")) enemies.add(UUID.fromString(enemiesString));
		return enemies;
	}
	
	public List<Team_SMP> getEnemies() 
	{
		List<Team_SMP> enemies = new ArrayList<>();
		for(UUID allyUID : getRawEnemies()) enemies.add(Team_SMP.getTeamFromID(allyUID));
		return enemies;
	}
	private void setEnemy(List<Team_SMP> enemies) 
	{
		if(enemies.size() == 0) 
		{
			yaml.setEnemy("");
			return;
		}
		String enemiesString = enemies.get(0).getUID().toString();
		for(int i = 1; i < enemies.size();i++) enemiesString += " " + enemies.get(i).getUID().toString();
		yaml.setEnemy(enemiesString);
	}
	public void removeEnemy(Team_SMP enemy) 
	{
		List<Team_SMP> enemies = getEnemies();
		enemies.remove(enemy);
		setEnemy(enemies);
		
		List<Team_SMP> enemiesEnemies = enemy.getEnemies();
		enemiesEnemies.remove(this);
		enemy.setEnemy(enemiesEnemies);
	}
	public void addEnemy(Team_SMP enemy) 
	{
		List<Team_SMP> enemies = getEnemies();
		enemies.add(enemy);
		setEnemy(enemies);
		
		List<Team_SMP> enemiesEnemies = enemy.getEnemies();
		enemiesEnemies.add(this);
		enemy.setEnemy(enemiesEnemies);
	}
	public static List<UUID> getRawInvite(OfflinePlayer offlinePlayer) {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(offlinePlayer);
		List<UUID> rawinvites = new ArrayList<>();
		for(String invitesString : yaml.getSmpInvites().split(" ")) if (!invitesString.equals("")) rawinvites.add(UUID.fromString(invitesString));
		return rawinvites;
	}
	public static List<Team_SMP> getInvites(OfflinePlayer offlinePlayer) {
		List<Team_SMP> invites = new ArrayList<>();
		for(UUID allyUID : getRawInvite(offlinePlayer)) invites.add(Team_SMP.getTeamFromID(allyUID));
		return invites;
	}
	public void setInvite(List<Team_SMP> invites, OfflinePlayer offlinePlayer) {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(offlinePlayer);
		if(invites.size() == 0 || invites == null) 
		{
			yaml.setSmpInvites("");
			return;
		}
		if(invites.get(0) == null) return;
		String invitesString = invites.get(0).getUID().toString();
		for(int i = 1; i < invites.size();i++) invitesString += " " + invites.get(i).getUID().toString();
		yaml.setSmpInvites(invitesString);
	}
	public void removeInvite(Player player) {
		List<Team_SMP> invites = getInvites(player);
		invites.remove(this);
		setInvite(invites, player);
	}
	public void addInvite(OfflinePlayer offlinePlayer) {
		List<Team_SMP> invites = getInvites(offlinePlayer);
		invites.add(this);
		setInvite(invites, offlinePlayer);
	}
	
	public List<UUID> getLeavePending() {
		List<UUID> rawleavependings = new ArrayList<>();
		for(String leavependingsString : yaml.getLeavePending().split(" ")) if (!leavependingsString.equals("")) rawleavependings.add(UUID.fromString(leavependingsString));
		return rawleavependings;
	}

	public void setLeavePending(List<UUID> leavePending) {
		if(leavePending.size() == 0) 
		{
			yaml.setLeavePending("");
			return;
		}
		String leavePendingString = leavePending.get(0).toString();
		for(int i = 1; i < leavePending.size();i++) leavePendingString += " " + leavePending.get(i).toString();
		yaml.setLeavePending(leavePendingString);
	}
	public void removeLeavePending(Player player) {
		List<UUID> leavePending = getLeavePending();
		leavePending.remove(player.getUniqueId());
		setLeavePending(leavePending);
	}
	public void addLeavePending(Player player) {
		List<UUID> leavePending = getLeavePending();
		leavePending.add(player.getUniqueId());
		setLeavePending(leavePending);
	}
	
	public static Team_SMP getPlayersTeam(OfflinePlayer player) 
	{
		for(Team_SMP team : teams) if(team.isInTeam(player)) return team;
		return null;
	}
	public static boolean isInATeam(OfflinePlayer player) 
	{
		return getPlayersTeam(player) != null;
	}
	public static Team_SMP getTeamFromID(UUID uid) 
	{
		for(Team_SMP team : teams) if(team.getUID().equals(uid)) return team;
		return null;
	}
	public static Team_SMP getTeamFromName(String name) 
	{
		for(Team_SMP team : teams) if(team.getName().equalsIgnoreCase(name)) return team;
		return null;
	}
	public static void longStore()
	{
		List<String> names = new ArrayList<>();
		for(Team_SMP team : teams) names.add(team.getUID().toString());
		YamlTeamWrapper_SMP.setTeams(String.join(" ", names));
	}
	public static void longBuild()
	{
		String teams = YamlTeamWrapper_SMP.getTeams();
		if(teams == null || teams.equalsIgnoreCase("")) 
		{
			YamlTeamWrapper_SMP.setTeams("");
			return;
		}
		for(String teamString : teams.split(" ")) 
		{
			Team_SMP team = new Team_SMP(UUID.fromString(teamString));
			for(UUID rankUID : team.getRawRanks()) if(!TeamRank_SMP.isRank(rankUID)) new TeamRank_SMP(rankUID);
		}
	}
}