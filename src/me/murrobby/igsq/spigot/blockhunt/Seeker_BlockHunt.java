package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import me.murrobby.igsq.spigot.Messaging;

public class Seeker_BlockHunt extends Player_BlockHunt
{
	
	private GenericSeeker_BlockHunt generic;
	public Seeker_BlockHunt(Player player, Game_BlockHunt game) 
	{
		super(player, game);
		this.generic = new GenericSeeker_BlockHunt(player);
		setup();
	}
	public Seeker_BlockHunt(Player_BlockHunt player) 
	{
		super(player);
		this.generic = new GenericSeeker_BlockHunt(player);
		setup();
	}
	private void goToShould() 
	{
		if(getGame().isStage(Stage.PRE_SEEKER)) 
		{
			getPlayer().teleport(getGame().getMap().getSeekerWaitLocation());
			getPlayer().setGameMode(GameMode.ADVENTURE);
		}
		else if(getGame().isStage(Stage.IN_GAME)) 
		{
			getPlayer().teleport(getGame().getMap().getSeekerSpawnLocation());
			getPlayer().setGameMode(GameMode.SURVIVAL);
		}
	}
	private void setup() 
	{
		getPlayer().getInventory().clear();
		Common_BlockHunt.seekersTeam.removeEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
		Common_BlockHunt.hidersTeam.removeEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
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
		

		getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000000000,0, true,false));
		bootsLore.add(Messaging.chatFormatter("&#990000Corrupts the ones when I get too close."));
		leggingsLore.add(Messaging.chatFormatter("&#990000Block the ones offences."));
		chestplateLore.add(Messaging.chatFormatter("&#990000The ones wont shift dimensions."));
		helmetLore.add(Messaging.chatFormatter("&#990000Allows I to see the ones true form."));
		
		bootsMeta.setColor(Color.fromRGB(255, 25, 25));
		leggingsMeta.setColor(Color.fromRGB(255, 25, 25));
		chestplateMeta.setColor(Color.fromRGB(255, 25, 25));
		helmetMeta.setColor(Color.fromRGB(255, 25, 25));
		
		bootsMeta.setDisplayName(Messaging.chatFormatter("&#FF0000Oneite Corrupters"));
		leggingsMeta.setDisplayName(Messaging.chatFormatter("&#FF0000Oneite Shields"));
		chestplateMeta.setDisplayName(Messaging.chatFormatter("&#FF0000Oneite Lockdowns"));
		helmetMeta.setDisplayName(Messaging.chatFormatter("&#FF0000Oneite Sight"));
		
		ItemStack sword = new ItemStack(Material.GOLDEN_SWORD, 1);
		ItemMeta swordMeta = sword.getItemMeta();
		List<String> swordLore = new ArrayList<String>();
		
		swordLore.add(Messaging.chatFormatter("&#990000Allows I to damage the ones."));
		swordMeta.setLore(swordLore);
		
		swordMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
		
		swordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("generic.attackSpeed", 3, Operation.ADD_NUMBER));
		swordMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 5, Operation.ADD_NUMBER));
		
		swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		swordMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		swordMeta.setUnbreakable(true);
		
		swordMeta.setDisplayName(Messaging.chatFormatter("&#FF0000Oneite Ones Destroyer"));
		
		sword.setItemMeta(swordMeta);
		getPlayer().getInventory().setItem(0,sword);
		
		Common_BlockHunt.seekersTeam.addEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
			
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
		
		
		getPlayer().getInventory().setArmorContents(new ItemStack[]{boots,leggings,chestplate,helmet});
		
		goToShould();
	}
	public Hider_BlockHunt raycastForCloak(int range) 
	{
        BlockIterator iter = new BlockIterator(getPlayer(), range);
        Block lastBlock = iter.next();
        int rangeNumber = 0;
        while (iter.hasNext()) 
        {
        	rangeNumber++;
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) //Server will only see hider blocks as air.
            {
            	Hider_BlockHunt hiderHiding = getGame().getHiderCloaked(lastBlock.getLocation());
            	if(hiderHiding != null) 
            	{
            		return hiderHiding;
            	}
            }
            else if(rangeNumber > 3) break;
        }
        return null;
    }
	//Getter
	public GenericSeeker_BlockHunt getGeneric() 
	{
		return generic;
	}

}
