package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
import me.murrobby.igsq.spigot.Messaging;

public class Common_BlockHunt 
{
	public static int numberPerSeeker = 3;
	
	public static ProtocolManager protocol = ProtocolLibrary.getProtocolManager();
	public static Material[] interactWhitelist = 
		{
			Material.ACACIA_BUTTON,
			Material.ACACIA_DOOR,
			Material.ACACIA_FENCE_GATE,
			Material.ACACIA_TRAPDOOR,
			
			Material.OAK_BUTTON,
			Material.OAK_DOOR,
			Material.OAK_FENCE_GATE,
			Material.OAK_TRAPDOOR,
			
			Material.BIRCH_BUTTON,
			Material.BIRCH_DOOR,
			Material.BIRCH_FENCE_GATE,
			Material.BIRCH_TRAPDOOR,
			
			Material.SPRUCE_TRAPDOOR,
			Material.SPRUCE_BUTTON,
			Material.SPRUCE_DOOR,
			Material.SPRUCE_FENCE_GATE,
			
			Material.DARK_OAK_TRAPDOOR,
			Material.DARK_OAK_BUTTON,
			Material.DARK_OAK_DOOR,
			Material.DARK_OAK_FENCE_GATE,
			
			Material.JUNGLE_TRAPDOOR,
			Material.JUNGLE_BUTTON,
			Material.JUNGLE_DOOR,
			Material.JUNGLE_FENCE_GATE,
			
			Material.JUNGLE_TRAPDOOR,
			Material.JUNGLE_BUTTON,
			Material.JUNGLE_DOOR,
			Material.JUNGLE_FENCE_GATE,
			
			Material.CRIMSON_TRAPDOOR,
			Material.CRIMSON_BUTTON,
			Material.CRIMSON_DOOR,
			Material.CRIMSON_FENCE_GATE,
			
			Material.WARPED_TRAPDOOR,
			Material.WARPED_BUTTON,
			Material.WARPED_DOOR,
			Material.WARPED_FENCE_GATE,
			
			Material.STONE_BUTTON,
			Material.LEVER
			
		};
	
    public static ScoreboardManager manager;
    public static Scoreboard board;
    
    public static Team hidersTeam;
    public static Team seekersTeam;

    public static Boolean blockhuntCheck() 
    {
    	return Yaml.getFieldBool("GAMEPLAY.blockhunt", "config");
    }
    public static Boolean validateCloak(Player player) 
    {
    	if(!Yaml.getFieldBool(player.getUniqueId().toString() + ".blockhunt.cloak", "internal")) 
    	{
        	Location targetLocation = player.getLocation().getBlock().getLocation();
        	Location checkingLocation = targetLocation;
        	
        	if(!checkingLocation.getBlock().isEmpty()) return false; //Deny if block replacing is not air
        	checkingLocation.setY(targetLocation.getY()-1);
        	if(checkingLocation.getBlock().isPassable() || checkingLocation.getBlock().isLiquid()) return false; //Deny if block bellow is not solid
        	checkingLocation.setY(targetLocation.getY()+1);
        	if(checkingLocation.getBlock().isPassable()) return true; //Allow only if headroom is not solid
    	}
    	return false;
    }
	public static int getSeekerCount(Player[] players) 
	{
		if(players.length <= 2) return 1;
		else return (int)(players.length/numberPerSeeker);
	}
	public static int getSeekerCount(int players) 
	{
		if(players <= 2) return 1;
		else return (int)(players/numberPerSeeker);
	}
    public static Boolean isInteractWhitelisted(Material material) 
    {
    	for (Material allowedMaterial : interactWhitelist) 
    	{
    		if(allowedMaterial.equals(material)) return true;
    	}
    	return false;
    }
    public static int getBlockPickerCooldown(Player player) 
    {
    	return Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.blockpickcooldown", "internal");
    }
    
    public static void updateBlockPickerItem(Player player,Boolean isBeingUsed)
    {
		ItemStack eye;
		ItemMeta eyeMeta;
		List<String> eyeLore = new ArrayList<String>();
		int cooldown = Common_BlockHunt.getBlockPickerCooldown(player)/20;
		if(isCloaked(player)) 
		{
			eye = new ItemStack(Material.FIREWORK_STAR, 1);
			eyeMeta = eye.getItemMeta();
			eyeLore.add(Messaging.chatFormatter("&#66ccffPower draw too high."));
			eyeMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shifter &#ccccccDISABLED"));
		}
		else if(cooldown != 0) 
		{
			eye = new ItemStack(Material.FIRE_CHARGE, cooldown);
			eyeMeta = eye.getItemMeta();
			eyeLore.add(Messaging.chatFormatter("&#FFFF00Overheated!"));
			eyeLore.add(Messaging.chatFormatter("&#ff6600"+cooldown +" &#0099ffsecond/s until heat sink."));
			eyeMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shifter &#ff6600"+ cooldown));
		}
		else if(isBeingUsed) 
		{
			eye = new ItemStack(Material.ENDER_EYE, 1);
			eyeMeta = eye.getItemMeta();
			eyeLore.add(Messaging.chatFormatter("&#0099FFAnalysing surroundings for us."));
			eyeMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shifter &#00ff00LOOKING"));
			eyeMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		else
		{
			eye = new ItemStack(Material.ENDER_PEARL, 1);
			eyeMeta = eye.getItemMeta();
			eyeLore.add(Messaging.chatFormatter("&#0099ffAllows us to shift forms."));
			eyeMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Shifter &#FFFF00READY"));
			eyeMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		
		eyeMeta.setLore(eyeLore);
		
		
		eyeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		eye.setItemMeta(eyeMeta);
		
		player.getInventory().setItem(0, eye);
    }
    public static void setBlockPickerCooldown(Player player,int cooldown) 
    {
    	int currentCooldown = Common_BlockHunt.getBlockPickerCooldown(player);
    	Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.blockpickcooldown", "internal", cooldown);
    	if(currentCooldown == 0) updateBlockPickerItem(player, true); //Force an update to block object use
    }
    public static Boolean isCloaked(Player player) 
    {
    	return Yaml.getFieldBool(player.getUniqueId().toString() + ".blockhunt.cloak", "internal");
    }
    public static int getCloakCooldown(Player player) 
    {
    	return Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.cloakcooldown", "internal");
    }
    public static void setCloakCooldown(Player player,int cooldown) 
    {
    	int currentCooldown = getCloakCooldown(player);
    	Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.cloakcooldown", "internal", cooldown);
    	if(currentCooldown == 0) updateCloakItem(player); //Force an update to block object use
    }
    
    public static void updateCloakItem(Player player)
    {
    	int cooldown = getCloakCooldown(player)/20;
    	String matString = Yaml.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal");
    	Material material = Material.BARRIER;
    	if(matString != null && (!matString.equalsIgnoreCase("")) && Material.valueOf(matString) != null) material = Material.valueOf(matString);
		ItemStack block;
		ItemMeta blockMeta;
		List<String> blockLore = new ArrayList<String>();
		
		if(isCloaked(player)) 
		{
			block = new ItemStack(material, 1);
			blockMeta = block.getItemMeta();
			
			blockLore.add(Messaging.chatFormatter("&#FFFF00" +material.toString()));
			blockMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Cloak &#00ff00ACTIVE"));
			blockMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		else if(cooldown != 0) 
		{
			block = new ItemStack(material, cooldown);
			blockMeta = block.getItemMeta();
			
			blockLore.add(Messaging.chatFormatter("&#FFFF00Overheated!"));
			blockLore.add(Messaging.chatFormatter("&#ff6600"+cooldown +" &#0099ffsecond/s until heat sink."));
			blockMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Cloak &#ff6600"+ cooldown));
		}
		else 
		{
			block = new ItemStack(material, 1);
			blockMeta = block.getItemMeta();
			
			blockLore.add(Messaging.chatFormatter("&#0099ffThe block us has selected."));
			blockMeta.setDisplayName(Messaging.chatFormatter("&#66ccffThemarite Cloak &#FFFF00" + material.toString()));
			blockMeta.addEnchant(Enchantment.DEPTH_STRIDER, 0, true);
		}
		blockMeta.setLore(blockLore);
		
		blockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		block.setItemMeta(blockMeta);
		
		player.getInventory().setItem(8, block);
    }
    public static void hiderChangeDisguise(Player player,Material material) 
    {
    	if(!isCloaked(player) && getBlockPickerCooldown(player) == 0) 
    	{
        	Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.block", "internal", material.toString());
        	updateCloakItem(player);
        	setBlockPickerCooldown(player, Yaml.getFieldInt("blockpickcooldown", "blockhunt"));
    	}
    	else setBlockPickerCooldown(player, Yaml.getFieldInt("blockpickcooldown", "blockhunt")/Yaml.getFieldInt("failcooldown", "blockhunt"));
    }
	public static void showPlayer(Player player) 
	{
		for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
		{
			if(selectedPlayer != player) 
			{
				selectedPlayer.showPlayer(Common.spigot, player);
			}
		}
	}
	public static int inventoryAssistTick(Player player) 
	{
		int slot = Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.hotbar", "internal");
		int direction = 0;
		if(slot != player.getInventory().getHeldItemSlot())
		{
			if(slot == 8 && player.getInventory().getHeldItemSlot() == 0) 
			{
				direction = 1;
			}
			else if(slot == 0 && player.getInventory().getHeldItemSlot() == 8) 
			{
				direction = -1;
			}
			else if(slot > player.getInventory().getHeldItemSlot()) 
			{
				direction = -1;
			}
			else if(slot < player.getInventory().getHeldItemSlot()) 
			{
				direction = 1;
			}
			if(direction != 0) 
			{
				slot+=direction;
				int attempts = 0;
				while(true) 
				{
					if(slot> 8) 
					{
						slot = 0;
					}
					else if(slot < 0) 
					{
						slot = 8;
					}
					
					
					if(player.getInventory().getItem(slot) != null) 
					{
						Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.hotbar", "internal", slot);
						return slot;
					}
					else
					{
						attempts++;
						slot+=direction;
						
						
						if(attempts > 8) //Nothing in inventory 
						{
							Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.hotbar", "internal", player.getInventory().getHeldItemSlot());
							return -1;
						}
					}
				}
			}
		}
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.hotbar", "internal", player.getInventory().getHeldItemSlot());
		return -1; //Inventory has not Moved
	}
	public static void setupTeams() 
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
	}
  
}
