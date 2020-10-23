package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BlockIterator;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;
import me.murrobby.igsq.spigot.event.GameEndEvent;
import me.murrobby.igsq.spigot.event.GameStartEvent;
import me.murrobby.igsq.spigot.event.LobbyCreateEvent;
import me.murrobby.igsq.spigot.event.PlayerJoinLobbyEvent;
import me.murrobby.igsq.spigot.Messaging;

public class Common_BlockHunt 
{
	private static Random random = new Random();
	public static int numberPerSeeker = 3;
	
	public static ProtocolManager protocol = ProtocolLibrary.getProtocolManager();
	public static Player[] seekers = {};
	public static Player[] hiders = {};
	
	public static Player[] players = {};
	public static Material[] blocks = {Material.HAY_BLOCK,Material.CRAFTING_TABLE,Material.BOOKSHELF,Material.DRIED_KELP_BLOCK,Material.FURNACE,Material.PUMPKIN,Material.MELON,Material.OAK_WOOD,Material.NOTE_BLOCK};
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
			
		};
	public static int playerCount;
	public static Stage stage = Stage.NO_GAME;
	
    public static ScoreboardManager manager;
    public static Scoreboard board;
    
    public static Team hidersTeam;
    public static Team seekersTeam;

    public static Location hubLocation = Common.parseLocationFromString(Yaml.getFieldString("map.hub.location" , "blockhunt"));
    public static int mapID = 0;
    public static String mapName;
    public static Location lobbyLocation;
    public static Location hiderSpawnLocation;
    public static Location seekerSpawnLocation;
    public static Location seekerWaitLocation;
    public static int timer;
    
	public static void start()
	{
		if(stage.equals(Stage.IN_LOBBY)) 
		{
			GameStartEvent event = new GameStartEvent(mapID);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	public static void end(EndReason reason) 
	{
		if(stage.equals(Stage.IN_GAME)) 
		{
			GameEndEvent event = new GameEndEvent(reason);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	public static void createLobby()
	{
		if(stage.equals(Stage.NO_GAME)) 
		{
			LobbyCreateEvent event = new LobbyCreateEvent();
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	public static void joinLobby(Player player)
	{
		if(stage.equals(Stage.NO_GAME)) createLobby();
		if(stage.equals(Stage.IN_LOBBY)) 
		{
			PlayerJoinLobbyEvent event = new PlayerJoinLobbyEvent(player);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	public static void cleanup() 
	{
		hiders = new Player[]{};
		seekers = new Player[]{};
		players = new Player[]{};
		playerCount = 0;
		stage = Stage.NO_GAME;
		mapID = 0;
	}
	public static void cleanup(Player player) 
	{
		Common_BlockHunt.removeCloak(player);
		Common_BlockHunt.showPlayer(player);
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(false);
		player.setAbsorptionAmount(0);
		player.setArrowsInBody(0);
		player.setCanPickupItems(true);
		player.setExp(0);
		player.setLevel(0);
		player.setFlying(false);
		player.setFireTicks(0);
		player.setCollidable(true);
		player.setFoodLevel(20);
		player.setGliding(false);
		player.setGlowing(false);
		player.setGravity(true);
		player.setHealthScale(20);
		player.setHealth(20);
		player.setSaturation(0);
		player.setWalkSpeed(0.2f);
		player.setSprinting(false);
		Common_BlockHunt.seekersTeam.removeEntry(player.getName()); //Will cause issues when with duplicate accounts
		Common_BlockHunt.hidersTeam.removeEntry(player.getName()); //Will cause issues when with duplicate accounts
		player.getInventory().clear();
		for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.block", "internal", "");
		removePlayer(player);
	}
	public static int getSeekerCount() 
	{
		if(playerCount <= 2) return 1;
		else return (int)(playerCount/numberPerSeeker);
	}
    public static Boolean blockhuntCheck() 
    {
    	return Yaml.getFieldBool("GAMEPLAY.blockhunt", "config");
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
    public static Boolean isCloaked(Player player) 
    {
    	return Yaml.getFieldBool(player.getUniqueId().toString() + ".blockhunt.cloak", "internal");
    }
    public static void removeCloak(Player player) 
    {
    	if(Yaml.getFieldBool(player.getUniqueId().toString() + ".blockhunt.cloak", "internal")) 
    	{
        	Location location = player.getLocation();
        	location.setX(Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.x", "internal"));
        	location.setY(Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.y", "internal"));
        	location.setZ(Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.z", "internal"));
        	for(Player selectedPlayer : players) 	
        	{
        		selectedPlayer.sendBlockChange(location, Bukkit.createBlockData(Material.AIR));
        	}
    		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.cloak", "internal", false);
    	}
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.x", "internal", 0);
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.y", "internal", 0);
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.z", "internal", 0);
		Common_BlockHunt.updateCloakItem(player);
		Common_BlockHunt.updateBlockPickerItem(player, false);
    }
	public static Player raycastForCloak(Player seeker, int range) {
        BlockIterator iter = new BlockIterator(seeker, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) //Server will only see hider blocks as air.
            {
            	Player hiderHiding = getHiderCloaked(lastBlock.getLocation());
            	if(hiderHiding != null) 
            	{
            		return hiderHiding;
            	}
            }
        }
        return null;
    }
	public static Player getHiderCloaked(Location location) 
	{
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
    	for(Player hider : hiders) 
    	{
    		if(isCloaked(hider) && isHider(hider)) 
    		{
                if(hider.getLocation().getBlockX() == x && hider.getLocation().getBlockY() == y && hider.getLocation().getBlockZ() == z) 
                {
                	return hider;
                }
    		}
    	}
		return null;
	}
	
    public static void addCloak(Player player) 
    {
    	Location location = player.getLocation();
    	for(Player selectedPlayer : players) 	
    	{
    		if(!selectedPlayer.getUniqueId().equals(player.getUniqueId())) selectedPlayer.sendBlockChange(location, Bukkit.createBlockData(Material.valueOf(Yaml.getFieldString(player.getUniqueId().toString()+".blockhunt.block", "internal"))));
    	}
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.cloak", "internal", true);
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.x", "internal", location.getBlockX());
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.y", "internal", location.getBlockY());
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.z", "internal", location.getBlockZ());
		Common_BlockHunt.updateCloakItem(player);
		Common_BlockHunt.updateBlockPickerItem(player, false);
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
    public static void removePlayer(Player player) 
    {
    	if(isPlayer(player)) 
    	{
    		for(int i = 0;i < players.length;i++) 
    		{
    			if(player.getUniqueId().equals(players[i].getUniqueId())) 
    			{
    				 players = Common.depend(players,i);
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
					seekers =  Common.depend(seekers,i);
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
    				hiders =  Common.depend(hiders,i);
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
    public static Boolean isPlayerVisible(Player player,double range)
    {
    	if(isSeeker(player)) return true;
    	if(isCloaked(player)) return false;
    	if(player.isSneaking()) return true;
    	for (Entity entity : player.getNearbyEntities(range, range/2, range)) 
    	{
    		if(entity instanceof Player) 
    		{
    			Player selectedPlayer = (Player) entity;
    			if(isSeeker(selectedPlayer)) return true;
    		}
    	}
    	return false;
    }
    public static Boolean isPlayerSilent(Player player)
    {
    	if(isCloaked(player)) return true;
    	if(player.isSneaking()) return true; //silent if sneaking
    	if(isHider(player) && isPlayerVisible(player, Yaml.getFieldInt("visibilityrange", "blockhunt"))) return false; //if player is revealed then the player is not silent
    	if(player.isSwimming()) return false; //we dont want to encourage people to swim
    	if(player.isSprinting()) return false; //no-one can sprint silently
    	if(isSeeker(player)) return true; //seekers are allowed to walk silently
    	return false; //hiders are not allowed to walk silently
    }
    public static Boolean isInteractWhitelisted(Material material) 
    {
    	for (Material allowedMaterial : interactWhitelist) 
    	{
    		if(allowedMaterial.equals(material)) return true;
    	}
    	return false;
    }
    
    public static void addPlayer(Player player) 
    {
    	if(!isPlayer(player)) players = Common.append(players, player);
    }
    public static void addSeeker(Player player) 
    {
    	addPlayer(player);
    	if(!isSeeker(player)) seekers = Common.append(seekers, player);
    }
    public static void addHider(Player player) 
    {
    	addPlayer(player);
    	if(!isHider(player)) hiders = Common.append(hiders, player);
    }
    public static void updateBlockPickerItem(Player player,Boolean isBeingUsed)
    {
		ItemStack eye;
		ItemMeta eyeMeta;
		List<String> eyeLore = new ArrayList<String>();
		int cooldown = getBlockPickerCooldown(player)/20;
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
    public static int getBlockPickerCooldown(Player player) 
    {
    	return Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.blockpickcooldown", "internal");
    }
    public static void setBlockPickerCooldown(Player player,int cooldown) 
    {
    	int currentCooldown = getBlockPickerCooldown(player);
    	Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.blockpickcooldown", "internal", cooldown);
    	if(currentCooldown == 0) updateBlockPickerItem(player, true); //Force an update to block object use
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
	public static void setupPlayers(Player player) 
	{
		Common_BlockHunt.seekersTeam.removeEntry(player.getName()); //Will cause issues when with duplicate accounts
		Common_BlockHunt.hidersTeam.removeEntry(player.getName()); //Will cause issues when with duplicate accounts
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
			player.setGameMode(GameMode.ADVENTURE);
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
			
			removeCloak(player);
			
			hidePlayer(player);
			defaultDisguise(player);
			setCloakCooldown(player, 100);
			setBlockPickerCooldown(player, 60);
			
			hidersTeam.addEntry(player.getName()); //Will cause issues when with duplicate accounts
			
		}
		else if(Common_BlockHunt.isSeeker(player)) 
		{
			player.setGameMode(GameMode.SURVIVAL);
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
			player.getInventory().setItem(0,sword);
			
			seekersTeam.addEntry(player.getName()); //Will cause issues when with duplicate accounts
			
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
	
	public static void defaultDisguise(Player player) 
	{
		
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.block", "internal", Common_BlockHunt.blocks[random.nextInt(Common_BlockHunt.blocks.length)].toString());
	}
	public static void hidePlayer(Player player) 
	{
		for(Player selectedPlayer : players) 
		{
			if(selectedPlayer != player) 
			{
				selectedPlayer.hidePlayer(Common.spigot, player);
			}
		}
	}
	public static void showPlayer(Player player) 
	{
		for(Player selectedPlayer : players) 
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
	public static int getMapCount() 
	{
		String mapName = "";
		int incrementer = 1;
		int mapCount = -1;
		do 
		{
			mapCount++;
			mapName = Yaml.getFieldString("map."+ incrementer +".name", "blockhunt");
		}while (mapName != null && !mapName.equals(""));
		return mapCount;
	}
	public static void loadMap(int mapID) 
	{
		Common_BlockHunt.mapID = mapID;
		mapName = Yaml.getFieldString("map."+ mapID +".name" , "blockhunt");
		lobbyLocation = Common.parseLocationFromString(Yaml.getFieldString("map."+ mapID +".prelobby" , "blockhunt"));
		hiderSpawnLocation = Common.parseLocationFromString(Yaml.getFieldString("map."+ mapID +".hider" , "blockhunt"));
		seekerWaitLocation = Common.parseLocationFromString(Yaml.getFieldString("map."+ mapID +".preseeker" , "blockhunt"));
		seekerSpawnLocation = Common.parseLocationFromString(Yaml.getFieldString("map."+ mapID +".seeker" , "blockhunt"));
		blocks = Common.parseMaterialListFromString(Yaml.getFieldString("map."+ mapID +".blocks" , "blockhunt"));
	}
	public static void loadMap() 
	{
		loadMap(random.nextInt(getMapCount())+1);
	}
	public static Boolean isMapSelected() 
	{
		return (mapID > 0);
	}
  
}
