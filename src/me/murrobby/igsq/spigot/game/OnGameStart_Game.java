package me.murrobby.igsq.spigot.game;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.murrobby.igsq.spigot.Common_Spigot;

public class OnGameStart_Game
{
	private static Random random = new Random();
	public OnGameStart_Game()
	{
		start();
	}
	public static void start() 
	{
		getPlayers();
		shufflePlayers(100);
		allocatePlayers();
		setupPlayers();
	}
	private static void getPlayers() 
	{
		for(Player player : Bukkit.getOnlinePlayers()) 
		{
			Common_Game.players = Common_Spigot.append(Common_Game.players, player);
			Common_Game.playerCount++;
		}
	}
	private static void shufflePlayers(int shuffles) 
	{
		for(int i = 0; i < shuffles;i++) 
		{
			int randomNumber = random.nextInt(Common_Game.playerCount);
			Player player = Common_Game.players[randomNumber];
			Common_Game.players = Common_Spigot.depend(Common_Game.players, randomNumber);
			Common_Game.players = Common_Spigot.append(Common_Game.players, player);
		}
	}
	private static void allocatePlayers() 
	{
		Player[] allocation = Common_Game.players;
		for(int i = 0; i < Common_Game.getTraitorCount() ;i++) //traitor allocation
		{
			int randomNumber = random.nextInt(Common_Game.playerCount);
			Common_Game.traitors = Common_Spigot.append(Common_Game.traitors,allocation[randomNumber]);
			allocation = Common_Spigot.depend(allocation, randomNumber);
		}
		for(Player player : allocation) //innocent allocation
		{
			Common_Game.innocents = Common_Spigot.append(Common_Game.innocents, player);
		}
	}
	private static void setupPlayers() 
	{
		for(Player player : Common_Game.players)
		{
			int id = Common_Game.getUniqueId(player);
			player.setGameMode(GameMode.ADVENTURE);
			player.setAllowFlight(false);
			player.setAbsorptionAmount(0);
			player.setArrowsInBody(0);
			player.setCanPickupItems(false);
			player.setExp(0);
			player.setFlying(false);
			player.setFireTicks(0);
			player.setFoodLevel(20);
			player.setGliding(false);
			player.setGlowing(false);
			player.setGravity(true);
			player.setHealthScale(20);
			player.setHealth(20);
			player.setMaximumAir(40);
			player.setSaturation(0);
			player.setWalkSpeed(1);
			player.setSprinting(false);
			
			ItemStack[] armors = new ItemStack[4];
			armors[0] = new ItemStack(Material.LEATHER_BOOTS, 1);
			armors[1] = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			armors[2] = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			armors[3] = new ItemStack(Material.LEATHER_HELMET, 1);
			for(ItemStack armor : armors) 
			{
				LeatherArmorMeta colour = (LeatherArmorMeta) armor.getItemMeta();
				colour.setColor(org.bukkit.Color.fromRGB(Common_Game.colours[id][0],Common_Game.colours[id][1], Common_Game.colours[id][2]));
			}
		}
	}
}
