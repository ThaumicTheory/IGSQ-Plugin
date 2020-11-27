package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import me.murrobby.igsq.spigot.Messaging;

public class Hider_BlockHunt extends Player_BlockHunt
{
	private GenericHider_BlockHunt generic;
	public Hider_BlockHunt(Player player,Game_BlockHunt game) 
	{
		super(player,game);
		this.generic = new GenericHider_BlockHunt(player);
		setup();
	}

	public Hider_BlockHunt(Player_BlockHunt player) 
	{
		super(player);
		this.generic = new GenericHider_BlockHunt(player);
		setup();
	}
	
	private void goToShould() 
	{
		if(getGame().isStage(Stage.PRE_SEEKER) || getGame().isStage(Stage.IN_GAME)) getPlayer().teleport(getGame().getMap().getHiderSpawnLocation());
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
		
		getPlayer().setGameMode(GameMode.ADVENTURE);
		bootsLore.add(Messaging.chatFormatter("&#0099ffConfuses them when they are to far"));
		leggingsLore.add(Messaging.chatFormatter("&#0099ffAllows us to survive in their realm"));
		chestplateLore.add(Messaging.chatFormatter("&#0099ffCounters their lock after 10 seconds."));
		helmetLore.add(Messaging.chatFormatter("&#0099ffAllows us to see thems."));
		
		bootsMeta.setColor(Color.fromRGB(102, 204, 255));
		leggingsMeta.setColor(Color.fromRGB(102, 204, 255));
		chestplateMeta.setColor(Color.fromRGB(102, 204, 255));
		helmetMeta.setColor(Color.fromRGB(102, 204, 255));
		
		bootsMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Corrupters"));
		leggingsMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shields"));
		chestplateMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Counter-Lockdowns"));
		helmetMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Sight"));
		
		hidePlayer();
		defaultDisguise();
		Common_BlockHunt.hidersTeam.addEntry(getPlayer().getName()); //Will cause issues when with duplicate accounts
			
		
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
		
		getGeneric().updateBlockMetaPickerItem();
		getGeneric().updateCloakItem();
		getGeneric().updateBlockPickerItem();
		goToShould();
	}
	
	//Cloaking
    public void setCloak(boolean cloaked) 
    {
    	if(cloaked && !getGeneric().isCloaked()) 
    	{
        	getGeneric().setCloakLocation();
        	for(Player_BlockHunt selectedPlayer : getGame().getPlayers()) 	
        	{
        		if(!selectedPlayer.getPlayer().getUniqueId().equals(getPlayer().getUniqueId())) selectedPlayer.getPlayer().sendBlockChange(getGeneric().getCloakLocation(), Bukkit.createBlockData(getGeneric().getBlock()));
        	}
    	}
    	else if(getGeneric().isCloaked())
    	{
        	for(Player_BlockHunt selectedPlayer : getGame().getPlayers()) 	
        	{
        		selectedPlayer.getPlayer().sendBlockChange(getGeneric().getCloakLocation(), Bukkit.createBlockData(Material.AIR));
        	}
        	getGeneric().setCloakLocation(null);
    	}
		getGeneric().updateCloakItem();
		getGeneric().updateBlockPickerItem();
		getGeneric().updateBlockMetaPickerItem();
    }
	public void defaultDisguise() 
	{
		getGeneric().setBlock(getGame().getMap().getBlocks()[getGame().getRandom().nextInt(getGame().getMap().getBlocks().length)]);
		getGeneric().setBlockMeta(1);
	}
    @Override
	public void outOfGame() 
    {
		setCloak(false);
    	super.outOfGame();
    }
    
    @Override
	public void cleanup() 
	{
		setCloak(false);
		super.cleanup();
	}
    @Override
    public Boolean isPlayerVisible(double range)
    {
    	if(getGeneric().isCloaked()) return false;
    	return super.isPlayerVisible(range);
    }
    @Override
    public Boolean isPlayerSilent()
    {
    	if(getGeneric().isCloaked()) return true;
    	return super.isPlayerSilent();
    }

	public GenericHider_BlockHunt getGeneric() 
	{
		return generic;
	}
}
