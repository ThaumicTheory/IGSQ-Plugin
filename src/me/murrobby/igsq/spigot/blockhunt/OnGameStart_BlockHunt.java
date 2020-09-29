package me.murrobby.igsq.spigot.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import me.murrobby.igsq.spigot.Common_Spigot;

public class OnGameStart_BlockHunt
{
	private static Random random = new Random();
	public OnGameStart_BlockHunt()
	{
		start();
	}
	public static void start() 
	{
		resetGame();
		getPlayers();
		shufflePlayers(100);
		allocatePlayers();
		setupPlayers();
		Common_BlockHunt.stage = 1;
		Main_BlockHunt.Start_BlockHunt();
	}
	private static void resetGame() 
	{
		Common_BlockHunt.hiders = new Player[]{};
		Common_BlockHunt.seekers = new Player[]{};
		Common_BlockHunt.players = new Player[]{};
		Common_BlockHunt.playerCount = 0;
		Common_BlockHunt.stage = 0;
	}
	private static void getPlayers() 
	{
		for(Player player : Bukkit.getOnlinePlayers()) 
		{
			Common_BlockHunt.players = Common_Spigot.append(Common_BlockHunt.players, player);
			Common_BlockHunt.playerCount++;
		}
	}
	private static void shufflePlayers(int shuffles) 
	{
		for(int i = 0; i < shuffles;i++) 
		{
			int randomNumber = random.nextInt(Common_BlockHunt.playerCount);
			Player player = Common_BlockHunt.players[randomNumber];
			Common_BlockHunt.players = Common_Spigot.depend(Common_BlockHunt.players, randomNumber);
			Common_BlockHunt.players = Common_Spigot.append(Common_BlockHunt.players, player);
			
		}
	}
	private static void allocatePlayers() 
	{
		Player[] allocation = Common_BlockHunt.players;
		for(int i = 0; i < Common_BlockHunt.getSeekerCount() ;i++) //seeker allocation
		{
			int randomNumber = random.nextInt(Common_BlockHunt.playerCount);
			Common_BlockHunt.seekers = Common_Spigot.append(Common_BlockHunt.seekers,allocation[randomNumber]);
			allocation = Common_Spigot.depend(allocation, randomNumber);
		}
		for(Player player : allocation) //hider allocation
		{
			Common_BlockHunt.hiders = Common_Spigot.append(Common_BlockHunt.hiders, player);
		}
	}
	private static void setupPlayers() 
	{
		for(Player player : Common_BlockHunt.players)
		{
			//int id = Common_BlockHunt.getUniqueId(player);
			player.setGameMode(GameMode.ADVENTURE);
			player.setAllowFlight(false);
			player.setAbsorptionAmount(0);
			player.setArrowsInBody(0);
			player.setCanPickupItems(false);
			player.setExp(0);
			player.setLevel(0);;
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
			Common_BlockHunt.setupGear(player);
			
		}
	}
}
