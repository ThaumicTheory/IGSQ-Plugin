package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import me.murrobby.igsq.spigot.Common;

public class OnGameStart_BlockHunt
{
	private static Random random = new Random();
	public OnGameStart_BlockHunt()
	{
		start();
	}
	public static void start() 
	{
		Common_BlockHunt.cleanup();
		getPlayers();
		shufflePlayers(100);
		allocatePlayers();
		setupPlayers();
		Common_BlockHunt.stage = 1;
		Main_BlockHunt.Start_BlockHunt();
	}
	private static void getPlayers() 
	{
		for(Player player : Bukkit.getOnlinePlayers()) 
		{
			Common_BlockHunt.players = Common.append(Common_BlockHunt.players, player);
			Common_BlockHunt.playerCount++;
		}
	}
	private static void shufflePlayers(int shuffles) 
	{
		for(int i = 0; i < shuffles;i++) 
		{
			int randomNumber = random.nextInt(Common_BlockHunt.playerCount);
			Player player = Common_BlockHunt.players[randomNumber];
			Common_BlockHunt.players = Common.depend(Common_BlockHunt.players, randomNumber);
			Common_BlockHunt.players = Common.append(Common_BlockHunt.players, player);
			
		}
	}
	private static void allocatePlayers() 
	{
		Player[] allocation = Common_BlockHunt.players;
		for(int i = 0; i < Common_BlockHunt.getSeekerCount() ;i++) //seeker allocation
		{
			int randomNumber = random.nextInt(Common_BlockHunt.playerCount);
			Common_BlockHunt.seekers = Common.append(Common_BlockHunt.seekers,allocation[randomNumber]);
			allocation = Common.depend(allocation, randomNumber);
		}
		for(Player player : allocation) //hider allocation
		{
			Common_BlockHunt.hiders = Common.append(Common_BlockHunt.hiders, player);
		}
	}
	private static void setupPlayers() 
	{
		Common_BlockHunt.manager = Bukkit.getScoreboardManager();
		Common_BlockHunt.board = Common_BlockHunt.manager.getMainScoreboard();
		try
		{
			Common_BlockHunt.hidersTeam = Common_BlockHunt.board.registerNewTeam("hiderteambhigsq");
		}
		catch(Exception exception) 
		{
			System.out.println("Hider Team Already Exists. getting it!");
			Common_BlockHunt.hidersTeam = Common_BlockHunt.board.getTeam("hiderteambhigsq");
		}
		try
		{
			Common_BlockHunt.seekersTeam = Common_BlockHunt.board.registerNewTeam("seekerteambhigsq");
		}
		catch(Exception exception) 
		{
			System.out.println("Seeker Team Already Exists. getting it!");
			Common_BlockHunt.seekersTeam = Common_BlockHunt.board.getTeam("seekerteambhigsq");
		}
		
	    Common_BlockHunt.seekersTeam.setAllowFriendlyFire(false);
	    Common_BlockHunt.seekersTeam.setColor(ChatColor.RED);
	    Common_BlockHunt.seekersTeam.setDisplayName("Seekers");
	    Common_BlockHunt.seekersTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OWN_TEAM);
	    Common_BlockHunt.seekersTeam.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
	    Common_BlockHunt.seekersTeam.setOption(Option.DEATH_MESSAGE_VISIBILITY, OptionStatus.NEVER);
	    Common_BlockHunt.seekersTeam.setCanSeeFriendlyInvisibles(true);
	    
	    Common_BlockHunt.hidersTeam.setAllowFriendlyFire(false);
	    Common_BlockHunt.hidersTeam.setColor(ChatColor.AQUA);
	    Common_BlockHunt.hidersTeam.setDisplayName("Hiders");
	    Common_BlockHunt.hidersTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OWN_TEAM);
	    Common_BlockHunt.hidersTeam.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
	    Common_BlockHunt.hidersTeam.setOption(Option.DEATH_MESSAGE_VISIBILITY, OptionStatus.NEVER);
	    Common_BlockHunt.hidersTeam.setCanSeeFriendlyInvisibles(true);
	    
		for(Player player : Common_BlockHunt.players)
		{
			player.setAllowFlight(false);
			player.setAbsorptionAmount(0);
			player.setArrowsInBody(0);
			player.setCanPickupItems(false);
			player.setExp(0);
			player.setLevel(0);
			player.setFlying(false);
			player.setFireTicks(0);
			player.setFoodLevel(20);
			player.setGliding(false);
			player.setGlowing(false);
			player.setGravity(true);
			player.setHealthScale(20);
			player.setHealth(20);
			player.setSaturation(0);
			player.setWalkSpeed(0.2f);
			player.setSprinting(false);
			player.getInventory().clear();
			player.getInventory().setHeldItemSlot(0);
			for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
			Common_BlockHunt.setupPlayers(player);
			
		}
	}
}
