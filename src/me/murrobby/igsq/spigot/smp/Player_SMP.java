package me.murrobby.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.YamlPlayerWrapper;

public class Player_SMP {
	private OfflinePlayer player;
	public OfflinePlayer getOfflinePlayer()
	{
		return player;
	}
	public List<UUID> getRawSmpInvites() 
	{
		List<UUID> rawInvites = new ArrayList<>();
		for(String invite : new YamlPlayerWrapper(player).getSmpInvitesField().split(" ")) if (!invite.equals("")) rawInvites.add(UUID.fromString(invite));
		return rawInvites;
	}
	
	public List<Team_SMP> getSmpInvites() 
	{
		List<UUID> rawInvites = getRawSmpInvites();
		List<Team_SMP> invites = new ArrayList<>();
		for(UUID invite : rawInvites) invites.add(Team_SMP.getTeamFromID(invite));
		return invites;
	}
	
	private void setSmpInvites(List<Team_SMP> teams) 
	{
		YamlPlayerWrapper playerYaml = new YamlPlayerWrapper(player);
		if(teams.size() == 0) 
		{
			playerYaml.setSmpInvitesField("");
			return;
		}
		String teamsstring = teams.get(0).toString();
		for(int i = 1; i < teams.size();i++) teamsstring += " " + teams.get(i).toString();
		playerYaml.setSmpInvitesField(teamsstring);
	}
	public void addSmpInvite(Team_SMP team) 
	{
		List<Team_SMP> invites = getSmpInvites();
		invites.add(team);
		setSmpInvites(invites);
	}
	public void removeSmpInvite(Team_SMP team) 
	{
		List<Team_SMP> invites = getSmpInvites();
		invites.remove(team);
		setSmpInvites(invites);
	}
}
