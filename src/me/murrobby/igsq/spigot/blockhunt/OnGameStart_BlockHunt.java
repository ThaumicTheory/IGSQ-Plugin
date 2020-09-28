package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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
	}
	private static void resetGame() 
	{
		Common_BlockHunt.hiders = new Player[]{};
		Common_BlockHunt.seekers = new Player[]{};
		Common_BlockHunt.players = new Player[]{};
		Common_BlockHunt.playerCount = 0;
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
		for(int i = 0; i < Common_BlockHunt.getTraitorCount() ;i++) //seeker allocation
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
			setupGear(player);
			
		}
	}
	private static void setupGear(Player player) 
	{
		List<String> bootsLore = new ArrayList<String>();
		List<String> leggingsLore = new ArrayList<String>();
		List<String> chestplateLore = new ArrayList<String>();
		List<String> helmetLore = new ArrayList<String>();
		
    	ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
    	ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
    	ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
    	ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
    	
		LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
		LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
		LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
		LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
		
		if(Common_BlockHunt.isHider(player)) 
		{
			bootsLore.add(Common_Spigot.chatFormatter("&#6666FFConfuses them when they are to far"));
			leggingsLore.add(Common_Spigot.chatFormatter("&#6666FFAllows us to survive in their realm"));
			chestplateLore.add(Common_Spigot.chatFormatter("&#6666FFCounters their lock after 10 seconds."));
			helmetLore.add(Common_Spigot.chatFormatter("&#6666FFAllows us to see thems."));
			
			bootsMeta.setColor(Color.fromRGB(127, 127, 255));
			leggingsMeta.setColor(Color.fromRGB(127, 127, 255));
			chestplateMeta.setColor(Color.fromRGB(127, 127, 255));
			helmetMeta.setColor(Color.fromRGB(127, 127, 255));
			
			bootsMeta.setDisplayName(Common_Spigot.chatFormatter("&#0000FFThemarite Corrupters"));
			leggingsMeta.setDisplayName(Common_Spigot.chatFormatter("&#0000FFThemarite Shields"));
			chestplateMeta.setDisplayName(Common_Spigot.chatFormatter("&#0000FFF6Themarite Counter-Lockdowns"));
			helmetMeta.setDisplayName(Common_Spigot.chatFormatter("&#0000FFThemarite Sight"));
		}
		else if(Common_BlockHunt.isSeeker(player)) 
		{
			bootsLore.add(Common_Spigot.chatFormatter("&#FF0000Corrupts the ones when i get too close."));
			leggingsLore.add(Common_Spigot.chatFormatter("&#FF0000Block the ones offences."));
			chestplateLore.add(Common_Spigot.chatFormatter("&#FF0000The ones wont shift dimensions."));
			helmetLore.add(Common_Spigot.chatFormatter("&#FF0000Allows I to see the ones true form."));
			
			bootsMeta.setColor(Color.fromRGB(255, 25, 25));
			leggingsMeta.setColor(Color.fromRGB(255, 25, 25));
			chestplateMeta.setColor(Color.fromRGB(255, 25, 25));
			helmetMeta.setColor(Color.fromRGB(255, 25, 25));
			
			bootsMeta.setDisplayName(Common_Spigot.chatFormatter("&#FF0000Oneite Corrupters"));
			leggingsMeta.setDisplayName(Common_Spigot.chatFormatter("&#FF0000Oneite Shields"));
			chestplateMeta.setDisplayName(Common_Spigot.chatFormatter("&#FF0000Oneite Lockdowns"));
			helmetMeta.setDisplayName(Common_Spigot.chatFormatter("&#FF0000Oneite Sight"));
			
			ItemStack sword = new ItemStack(Material.GOLDEN_SWORD, 1);
			ItemMeta swordMeta = sword.getItemMeta();
			List<String> swordLore = new ArrayList<String>();
			
			swordLore.add(Common_Spigot.chatFormatter("&#FF0000Allows I to damage the ones."));
			swordMeta.setLore(swordLore);
			
			swordMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
			swordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("generic.attackSpeed", 3, Operation.ADD_NUMBER));
			swordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 5, Operation.ADD_NUMBER));
			
			swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			swordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			
			swordMeta.setUnbreakable(true);
			
			swordMeta.setDisplayName(Common_Spigot.chatFormatter("&#FF0000Oneite Ones Destroyer"));
			
			sword.setItemMeta(swordMeta);
			player.getInventory().setItem(0,sword);
			
		}
		bootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		bootsMeta.addItemFlags(ItemFlag.HIDE_DYE);
		bootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		leggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		leggingsMeta.addItemFlags(ItemFlag.HIDE_DYE);
		leggingsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		chestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		chestplateMeta.addItemFlags(ItemFlag.HIDE_DYE);
		chestplateMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		helmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		helmetMeta.addItemFlags(ItemFlag.HIDE_DYE);
		helmetMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
	
		bootsMeta.setLore(bootsLore);
		leggingsMeta.setLore(leggingsLore);
		chestplateMeta.setLore(chestplateLore);
		helmetMeta.setLore(helmetLore);
		
		bootsMeta.setUnbreakable(true);
		leggingsMeta.setUnbreakable(true);
		chestplateMeta.setUnbreakable(true);
		helmetMeta.setUnbreakable(true);
		
		
		boots.setItemMeta(bootsMeta);
		leggings.setItemMeta(leggingsMeta);
		chestplate.setItemMeta(chestplateMeta);
		helmet.setItemMeta(helmetMeta);
		
		
		player.getInventory().setArmorContents(new ItemStack[]{boots,leggings,chestplate,helmet});
	}
}
