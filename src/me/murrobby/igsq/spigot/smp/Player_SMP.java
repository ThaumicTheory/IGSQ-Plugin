package me.murrobby.igsq.spigot.smp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.smp.aspect.Base_Aspect;
import me.murrobby.igsq.spigot.smp.aspect.Enum_Aspect;
import me.murrobby.igsq.spigot.smp.aspect.None_Aspect;
import me.murrobby.igsq.spigot.smp.aspect.Water_Aspect;

public class Player_SMP {
	private YamlPlayerWrapper yaml;
	private OfflinePlayer player;
	private UI_SMP userInterface;
	private Base_Aspect aspect;
	private static List<Player_SMP> players = new ArrayList<>();
	public Player_SMP(OfflinePlayer player)
	{
		this.player = player;
		yaml = new YamlPlayerWrapper(player);
		getUI();
		players.add(this);
	}
	public OfflinePlayer getOfflinePlayer()
	{
		return player;
	}
	public Player getPlayer()
	{
		return player.getPlayer();
	}
	public YamlPlayerWrapper getYaml()
	{
		return yaml;
	}
	public UI_SMP getUI()
	{
		if(userInterface == null && player.getPlayer() != null) userInterface = new UI_SMP(player.getPlayer());
		else if(player.getPlayer() == null && userInterface != null) 
		{
			userInterface.delete();
			userInterface = null;
		}
		return userInterface;
	}
	
	public Base_Aspect getAspect()
	{
		if(aspect == null) aspect = createAspect();
		return aspect;
	}
	public Base_Aspect createAspect()
	{
		if(getYaml().getSmpAspect() != null && getYaml().getSmpAspect().equalsIgnoreCase(Enum_Aspect.WATER.toString())) return new Water_Aspect(this);
		
		
		
		return new None_Aspect(this);
	}
	public void setAspect(Base_Aspect aspect)
	{
		this.aspect = aspect;
	}
	public void updateAspect()
	{
		aspect = null;
		createAspect();
	}
	public boolean isUIAccessable() //will be null for an offline player
	{
		return getUI() != null;
	}
	
	public int getMoney()
	{
		return getYaml().getSMPCurrency();
	}
	public void setMoney(int money)
	{
		getYaml().setSMPCurrency(money);
	}
	public void addMoney(int money)
	{
		getYaml().setSMPCurrency(getYaml().getSMPCurrency()+money);
	}
	public void removeMoney(int money)
	{
		getYaml().setSMPCurrency(getYaml().getSMPCurrency()-money);
	}
	
	
	
	
	public List<UUID> getRawSmpInvites() 
	{
		List<UUID> rawInvites = new ArrayList<>();
		for(String invite : getYaml().getSmpInvites().split(" ")) if (!invite.equals("")) rawInvites.add(UUID.fromString(invite));
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
		if(teams.size() == 0) 
		{
			getYaml().setSmpInvites("");
			return;
		}
		String teamsString = teams.get(0).toString();
		for(int i = 1; i < teams.size();i++) teamsString += " " + teams.get(i).toString();
		getYaml().setSmpInvites(teamsString);
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
	//Utility functions
	public boolean isInLiquid(Material material) 
	{
		Block blockLegs = player.getPlayer().getLocation().getBlock();
		Block blockHead = player.getPlayer().getLocation().add(0,1,0).getBlock();
		if(material.equals(Material.WATER)) 
		{
			if(blockLegs.getBlockData() instanceof Waterlogged) 
			{
				Waterlogged water = (Waterlogged) blockLegs.getBlockData();
				if(water.isWaterlogged()) return true;
			}
			if(blockHead.getBlockData() instanceof Waterlogged) 
			{
				Waterlogged water = (Waterlogged) blockHead.getBlockData();
				if(water.isWaterlogged()) return true;
			}
		}
		return blockLegs.getType() == material || blockHead.getType() == material;
	}
	public boolean isStandingOn(Material material) 
	{
		Block blockFeet = player.getPlayer().getLocation().add(0,-0.4,0).getBlock();
		return blockFeet.getType() == material;
	}
	public boolean isInEnvironment(Environment environment) 
	{
		return player.getPlayer().getWorld().getEnvironment() == environment;
	}
	
	
	public static List<Player_SMP> getPlayers() 
	{
		if(players.size() == 0) createPlayers();
		return players;
	}
	
	public static Player_SMP getSMPPlayer(OfflinePlayer offlinePlayer) 
	{
		for(Player_SMP player : getPlayers()) if(player.getOfflinePlayer().getUniqueId().equals(offlinePlayer.getUniqueId())) return player;
		return null;
	}
	public static void createPlayers() 
	{
		players = new ArrayList<>();
		for(OfflinePlayer player : Bukkit.getOfflinePlayers()) new Player_SMP(player);
	}
}
