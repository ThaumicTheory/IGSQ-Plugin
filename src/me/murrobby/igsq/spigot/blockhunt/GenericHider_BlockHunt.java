package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import me.murrobby.igsq.spigot.Dictionaries;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.Yaml;

public class GenericHider_BlockHunt extends GenericPlayer_BlockHunt
{
	private Material blockType;
	private int blockMeta = 1;
	private int maxMeta = 1;
	
	private Location cloakLocation;
	private int cloakCooldown;
	private int blockPickCooldown;
	private int seeSelfTime;

	public GenericHider_BlockHunt(Player player) 
	{
		super(player);
	}
	public GenericHider_BlockHunt(GenericPlayer_BlockHunt player) 
	{
		super(player.getPlayer());
		setHotbar(player.getHotbar());
		setDead(player.isDead());
		setOutOfBoundsTime(player.getOutOfBoundsTime());
	}
	//Items
    public void updateBlockPickerItem()
    {
		ItemStack item;
		ItemMeta itemMeta;
		List<String> itemLore = new ArrayList<String>();
		int itemCount = getItemCount(getBlockPickerCooldown());
		if(isCloaked()) 
		{
			item = new ItemStack(Material.FIREWORK_STAR, 1);
			itemMeta = item.getItemMeta();
			itemLore.add(Messaging.chatFormatter("&#66ccffPower draw too high."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shifter &#ccccccDISABLED"));
		}
		else if(itemCount != 0) 
		{
			item = new ItemStack(Material.FIRE_CHARGE, itemCount);
			itemMeta = item.getItemMeta();
			itemLore.add(Messaging.chatFormatter("&#FFFF00Overheated!"));
			itemLore.add(Messaging.chatFormatter("&#ff6600"+itemCount +" &#0099ffsecond/s until heat sink."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shifter &#ff6600"+ itemCount));
		}
		else
		{
			item = new ItemStack(Material.ENDER_PEARL, 1);
			itemMeta = item.getItemMeta();
			itemLore.add(Messaging.chatFormatter("&#0099ffAllows us to shift forms."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shifter &#FFFF00READY"));
			itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		itemMeta.setLore(itemLore);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		getPlayer().getInventory().setItem(0, item);
    }
    
    public void updateBlockMetaPickerItem()
    {
    	if(blockType == null) return;
		ItemStack item;
		ItemMeta itemMeta;
		List<String> itemLore = new ArrayList<String>();
		if(isCloaked()) 
		{
			item = new ItemStack(Material.FIREWORK_STAR, 1);
			itemMeta = item.getItemMeta();
			itemLore.add(Messaging.chatFormatter("&#66ccffPower draw too high."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Meta &#ccccccDISABLED"));
		}
		else if(maxMeta == 1) 
		{
			item = new ItemStack(Material.SLIME_BALL, 1);
			itemMeta = item.getItemMeta();
			itemLore.add(Messaging.chatFormatter("&#66ccffNo seperate meta's could be found."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Meta &#ff0000FAILED"));
			itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		else
		{
			item = new ItemStack(Material.ENDER_EYE, 1);
			itemMeta = item.getItemMeta();
			itemLore.add(Messaging.chatFormatter("&#0099ffAllows us to shift forms."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Meta &#FFFF00" + blockMeta + " / " + maxMeta));
			itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		itemMeta.setLore(itemLore);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		getPlayer().getInventory().setItem(7, item);
    }
    public void updateCloakItem()
    {
    	if(blockType == null) return;
    	int itemCount = getItemCount(cloakCooldown);
		ItemStack item;
		ItemMeta itemMeta;
		List<String> itemLore = new ArrayList<String>();
		
		if(isCloaked()) 
		{
			item = new ItemStack(blockType, 1);
			itemMeta = item.getItemMeta();
			
			itemLore.add(Messaging.chatFormatter("&#FFFF00" +blockType.toString()));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Cloak &#00ff00ACTIVE"));
			itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		else if(itemCount != 0) 
		{
			item = new ItemStack(blockType, itemCount);
			itemMeta = item.getItemMeta();
			
			itemLore.add(Messaging.chatFormatter("&#FFFF00Overheated!"));
			itemLore.add(Messaging.chatFormatter("&#ff6600"+itemCount +" &#0099ffsecond/s until heat sink."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Cloak &#ff6600"+ itemCount));
		}
		else 
		{
			item = new ItemStack(blockType, 1);
			itemMeta = item.getItemMeta();
			itemLore.add(Messaging.chatFormatter("&#0099ffThe block us has selected."));
			itemMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Cloak &#FFFF00" + blockType.toString()));
			itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		itemMeta.setLore(itemLore);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);	
		getPlayer().getInventory().setItem(8, item);
    }
    
    public int getItemCount(int cooldown) 
    {
    	return cooldown/20;
    }
	private void updateMaxMeta() 
	{
		maxMeta = Dictionaries.getMetaCountFromMaterial(blockType);
		if(blockMeta > maxMeta) blockMeta = maxMeta;
		
	}
    //Cloaking
    public void changeCloak(Material material) 
    {
    	if(!isCloaked() && getBlockPickerCooldown() == 0) 
    	{
    		setBlock(material);
        	updateCloakItem();
        	setBlockPickerCooldown(Yaml.getFieldInt("blockpickcooldown", "blockhunt"));
        	updateBlockMetaPickerItem();
    	}
    	else setBlockPickerCooldown(Yaml.getFieldInt("blockpickcooldown", "blockhunt")/Yaml.getFieldInt("failcooldown", "blockhunt"));
    }
    public PacketContainer dynamicCloakPacket(int entityID,boolean isNotExact) 
    {
    	PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
		packet.getIntegers().write(0,entityID);
		packet.getUUIDs().write(0, UUID.randomUUID());
		packet.getEntityTypeModifier().write(0, EntityType.FALLING_BLOCK);
		if(isNotExact) 
		{
			packet.getDoubles().write(0, getPlayer().getLocation().getBlockX()+ 0.5);
			packet.getDoubles().write(1, (double) getPlayer().getLocation().getBlockY());
			packet.getDoubles().write(2,getPlayer().getLocation().getBlockZ()+ 0.5);
			
		}
		else 
		{
			packet.getDoubles().write(0, getPlayer().getLocation().getX());
			packet.getDoubles().write(1, getPlayer().getLocation().getY());
			packet.getDoubles().write(2, getPlayer().getLocation().getZ());
		}
		packet.getIntegers().write(4,0); //Angle Pitch
		packet.getIntegers().write(5,0); //Angle Yaw
		packet.getIntegers().write(6, Dictionaries.getNetworkIdFromMaterial(blockType)+ blockMeta-1);
		return packet;
    }
	//Getters
	public Material getBlock() 
    {
		return blockType;
    }
	public int getBlockMeta() 
    {
		return blockMeta;
    }
	
	public int getBlockPickerCooldown() 
	{
    	return blockPickCooldown;
	}
	public int getCloakCooldown() 
	{
    	return cloakCooldown;
	}
	public Location getCloakLocation() 
	{
    	return cloakLocation;
	}
	public int getSeeSelfTime() 
	{
    	return seeSelfTime;
	}
	//Setters
	public void setBlock(Material material) 
    {
		this.blockType = material;
		updateBlockMetaPickerItem();
		updateCloakItem();
		updateBlockPickerItem();
		updateMaxMeta();
    }
	public void setCloakLocation(Location cloakLocation) 
	{
		if(cloakLocation == null) this.cloakLocation = null;
		else this.cloakLocation = cloakLocation.getBlock().getLocation();
	}
	public void setCloakLocation() 
	{
		setCloakLocation(getPlayer().getLocation());
	}
	public void setBlockMeta(int metaID) 
    {
		if(metaID > maxMeta) metaID = 1;
    	else if(metaID < 1) metaID = maxMeta;
        if(!isCloaked()) 
        {
        	blockMeta = metaID;
        	updateBlockMetaPickerItem();
        	setSeeSelfTime(100);
    	}
		blockMeta = metaID;
    }
	public void setBlockPickerCooldown(int cooldown) 
	{
    	blockPickCooldown = cooldown;
    	if(cooldown == 0) updateBlockPickerItem();
	}
	public void setCloakCooldown(int cooldown) 
	{
    	cloakCooldown = cooldown;
    	if(cooldown == 0) updateCloakItem();
	}
	public void setSeeSelfTime(int time) 
	{
    	seeSelfTime = time;
	}
	//Issers
	public Boolean isCloaked() 
    {
    	return cloakLocation != null;
    }
	
    public Boolean isCloakValid() 
    {
    	if(!isCloaked()) 
    	{
        	Location targetLocation = getPlayer().getLocation().getBlock().getLocation();
        	Location checkingLocation = targetLocation;
        	
        	if(!checkingLocation.getBlock().isEmpty()) return false; //Deny if block replacing is not air
        	checkingLocation.setY(targetLocation.getY()-1);
        	if(checkingLocation.getBlock().isPassable() || checkingLocation.getBlock().isLiquid()) return false; //Deny if block bellow is not solid
        	checkingLocation.setY(targetLocation.getY()+1);
        	if(checkingLocation.getBlock().isPassable()) return true; //Allow only if headroom is not solid
    	}
    	return false;
    }
	
	
}
