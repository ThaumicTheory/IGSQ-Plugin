package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
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

import me.murrobby.igsq.spigot.Common_Spigot;

public class Common_BlockHunt 
{
	private static Random random = new Random();
	public static int numberPerSeeker = 3;
	
	public static Player[] seekers = {};
	public static Player[] hiders = {};
	
	public static Player[] players = {};
	public static Material[] blocks = {Material.HAY_BLOCK,Material.CRAFTING_TABLE,Material.BOOKSHELF,Material.DRIED_KELP_BLOCK,Material.FURNACE,Material.PUMPKIN,Material.MELON,Material.OAK_WOOD,Material.NOTE_BLOCK};
	public static int playerCount;
	public static int stage = -1;
	public static void start()
	{
		OnGameStart_BlockHunt.start();
	}
	public static int getSeekerCount() 
	{
		return (int)(playerCount/numberPerSeeker)+1;
	}
	
	public static int getUniqueId(Player player) 
	{
		for(int i = 0;i < playerCount;i++) 
		{
			if(player.getUniqueId().equals(players[i].getUniqueId())) 
			{
				return i;
			}
		}
		return -1;
	}
    public static Boolean blockhuntCheck() 
    {
    	return Common_Spigot.getFieldBool("GAMEPLAY.blockhunt", "config");
    }
    public static Boolean isSeeker(Player player) 
    {
    	for(Player selectedPlayer :seekers) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public static Boolean isHider(Player player) 
    {
    	for(Player selectedPlayer :hiders) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public static Boolean isPlayer(Player player) 
    {
    	for(Player selectedPlayer :players) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public static void removePlayer(Player player) 
    {
    	if(isPlayer(player)) 
    	{
    		for(int i = 0;i < players.length;i++) 
    		{
    			if(player.getUniqueId().equals(players[i].getUniqueId())) 
    			{
    				 players = Common_Spigot.depend(players,i);
    				 break;
    			}
    		}
    	}
    	removeSeeker(player);
    	removeHider(player);
    }
    public static void removeSeeker(Player player) 
    {
    	if(isSeeker(player)) 
    	{
    		for(int i = 0;i < seekers.length;i++) 
    		{
    			if(player.getUniqueId().equals(seekers[i].getUniqueId())) 
    			{
					seekers =  Common_Spigot.depend(seekers,i);
					break;
    			}
    		}
    	}
    }
    public static void removeHider(Player player) 
    {
    	if(isHider(player)) 
    	{
    		for(int i = 0;i < hiders.length;i++) 
    		{
    			if(player.getUniqueId().equals(hiders[i].getUniqueId())) 
    			{
    				hiders =  Common_Spigot.depend(hiders,i);
    				break;
    			}
    		}
    	}
    }
    public static Boolean isBlockPlayable(Material material) 
    {
    	for (Material allowedMaterial : blocks) 
    	{
    		if(allowedMaterial.equals(material)) return true;
    	}
    	return false;
    }
    
    public static void addPlayer(Player player) 
    {
    	if(!isPlayer(player)) players = Common_Spigot.append(players, player);
    }
    public static void addSeeker(Player player) 
    {
    	addPlayer(player);
    	if(!isSeeker(player)) seekers = Common_Spigot.append(seekers, player);
    }
    public static void addHider(Player player) 
    {
    	addPlayer(player);
    	if(!isHider(player)) hiders = Common_Spigot.append(hiders, player);
    }
    public static void giveEye(Player player,Boolean isEye)
    {
		ItemStack eye;
		ItemMeta eyeMeta;
		List<String> eyeLore = new ArrayList<String>();
		if(isEye) 
		{
			eye = new ItemStack(Material.ENDER_EYE, 1);
			eyeMeta = eye.getItemMeta();
			eyeLore.add(Common_Spigot.chatFormatter("&#0000FFAnalysing surroundings for us."));
			eyeMeta.setDisplayName(Common_Spigot.chatFormatter("&#0000FFThemarite Shifter (Looking)"));
		}
		else
		{
			eye = new ItemStack(Material.ENDER_PEARL, 1);
			eyeMeta = eye.getItemMeta();
			eyeLore.add(Common_Spigot.chatFormatter("&#0000FFAllows us to shift forms."));
			eyeMeta.setDisplayName(Common_Spigot.chatFormatter("&#0000FFThemarite Shifter"));
		}
		
		eyeMeta.setLore(eyeLore);
		
		eyeMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		
		eyeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		eye.setItemMeta(eyeMeta);
		
		player.getInventory().setItem(0, eye);
    }
    
    public static void hiderBlockVisuals(Player player)
    {
		ItemStack block = new ItemStack(Material.valueOf(Common_Spigot.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal")), 1);
		ItemMeta blockMeta = block.getItemMeta();
		List<String> blockLore = new ArrayList<String>();
		
		blockLore.add(Common_Spigot.chatFormatter("&#0000FFThe block us has selected."));
		blockMeta.setLore(blockLore);
		
		blockMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		blockMeta.setDisplayName(Common_Spigot.chatFormatter("&#0000FFThemarite Target"));
		
		blockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		block.setItemMeta(blockMeta);
		
		player.getInventory().setItem(8, block);
    }
	public static void setupGear(Player player) 
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
			
			hiderStartingBlock(player);
			Common_BlockHunt.giveEye(player,false);
			Common_BlockHunt.hiderBlockVisuals(player);
			
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
		bootsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		leggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		leggingsMeta.addItemFlags(ItemFlag.HIDE_DYE);
		leggingsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		leggingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		chestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		chestplateMeta.addItemFlags(ItemFlag.HIDE_DYE);
		chestplateMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		chestplateMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		helmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		helmetMeta.addItemFlags(ItemFlag.HIDE_DYE);
		helmetMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		helmetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	
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
	
	public static void hiderStartingBlock(Player player) 
	{
		
		Common_Spigot.updateField(player.getUniqueId().toString() + ".blockhunt.block", "internal", Common_BlockHunt.blocks[random.nextInt(Common_BlockHunt.blocks.length)].toString());
	}
  
}
