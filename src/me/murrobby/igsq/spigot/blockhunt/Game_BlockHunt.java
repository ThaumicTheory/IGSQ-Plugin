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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.Yaml;
import me.murrobby.igsq.spigot.event.BeginSeekEvent;
import me.murrobby.igsq.spigot.event.GameEndEvent;
import me.murrobby.igsq.spigot.event.GameStartEvent;
import me.murrobby.igsq.spigot.event.LobbyCreateEvent;
import me.murrobby.igsq.spigot.event.PlayerJoinLobbyEvent;

public class Game_BlockHunt 
{
	private Player[] seekers = {};
	private Player[] hiders = {};
	
	private Player[] players = {};
	private Stage stage = Stage.NO_GAME;
	private Map_BlockHunt map;
    private int timer;
    private String name;
    
    private static Game_BlockHunt[] games = {};
    private static Random random = new Random();
	
	public Game_BlockHunt(String name) 
	{
		map = new Map_BlockHunt();
		this.name = name;
		timer = Yaml.getFieldInt("lobbytime", "blockhunt");
		games = append(games, this);
		createLobby();
	}
	
	public Game_BlockHunt() 
	{
		map = new Map_BlockHunt();
		this.name = String.valueOf(System.currentTimeMillis());
		timer = Yaml.getFieldInt("lobbytime", "blockhunt");
		games = append(games, this);
		createLobby();
	}
	
	
	
	public void start()
	{
		if(stage.equals(Stage.IN_LOBBY)) 
		{
			GameStartEvent event = new GameStartEvent(this);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	public void end(EndReason reason) 
	{
		if(!stage.equals(Stage.NO_GAME)) 
		{
			GameEndEvent event = new GameEndEvent(this,reason);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	public void createLobby()
	{
		if(stage.equals(Stage.NO_GAME)) 
		{
			LobbyCreateEvent event = new LobbyCreateEvent(this);
			Bukkit.getPluginManager().callEvent(event);
		}
		
	}
	public void joinLobby(Player player)
	{
		if(stage.equals(Stage.NO_GAME)) createLobby();
		if(stage.equals(Stage.IN_LOBBY)) 
		{
			PlayerJoinLobbyEvent event = new PlayerJoinLobbyEvent(this,player);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	public void startSeek()
	{
		if(stage.equals(Stage.PRE_SEEKER)) 
		{
			BeginSeekEvent event = new BeginSeekEvent(this);
			Bukkit.getPluginManager().callEvent(event);
		}
	}
	
    public Boolean isSeeker(Player player) 
    {
    	for(Player selectedPlayer :seekers) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public Boolean isDead(Player player) 
    {
    	return Yaml.getFieldBool(player.getUniqueId().toString() + ".blockhunt.dead", "internal");
    }
    public Boolean isHider(Player player) 
    {
    	for(Player selectedPlayer :hiders) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public Boolean isPlayer(Player player) 
    {
    	for(Player selectedPlayer :players) 
    	{
    		if(selectedPlayer.getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    
    public Map_BlockHunt getMap()
    {
    	return map;
    }
    
    public Player getHiderCloaked(Location location) 
	{
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
    	for(Player hider : hiders) 
    	{
    		if(Common_BlockHunt.isCloaked(hider)) 
    		{
                if(hider.getLocation().getBlockX() == x && hider.getLocation().getBlockY() == y && hider.getLocation().getBlockZ() == z) 
                {
                	return hider;
                }
    		}
    	}
		return null;
	}
	
    public void addCloak(Player player) 
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
		Common_BlockHunt.updateBlockPickerItem(player);
		Common_BlockHunt.updateBlockMetaPickerItem(player);
    }
	public Player raycastForCloak(Player seeker, int range) {
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
	public void killPlayer(Player player) 
    {
		Common_BlockHunt.setCloakCooldown(player, 0);
		Common_BlockHunt.setBlockPickerCooldown(player, 0);
		removeCloak(player);
    	player.setHealthScale(20);
    	player.setHealth(20);
    	player.setGameMode(GameMode.ADVENTURE);
    	player.setAllowFlight(true);
    	player.setHealth(player.getHealthScale());
    	player.getInventory().clear();
    	player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000000000,0, true,false));
    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000000000,4, true,false));
    	Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.dead", "internal", true);
    	Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.barrier", "internal", 0);
    }
	public void cleanup(Player player) 
	{
		removeCloak(player);
		showPlayer(player);
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
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.dead", "internal", false);
	}
	
	public void setupPlayers(Player player,boolean forced) 
	{
		if(!forced) setupPlayers(player);
		else 
		{
			setupPlayers(player);
			if(isSeeker(player)) 
			{
				if(stage.equals(Stage.PRE_SEEKER)) player.teleport(getMap().getSeekerWaitLocation());
				else
				{
					player.setGameMode(GameMode.SURVIVAL); //Compensate for a role force which misses the BeginSeekEvent
					player.teleport(getMap().getSeekerSpawnLocation());
				}
			}
			else if(isHider(player)) 
			{
				player.teleport(getMap().getHiderSpawnLocation());
			}
		}
	}
	public void setupPlayers(Player player) 
	{
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.dead", "internal", false);
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
		
		if(isHider(player)) 
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
			Common_BlockHunt.setCloakCooldown(player, 100);
			Common_BlockHunt.setBlockPickerCooldown(player, 60);
			Common_BlockHunt.updateBlockMetaPickerItem(player);
			
			Common_BlockHunt.hidersTeam.addEntry(player.getName()); //Will cause issues when with duplicate accounts
			
		}
		else if(isSeeker(player)) 
		{
			player.setGameMode(GameMode.ADVENTURE);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1000000000,0, true,false));
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
			
			Common_BlockHunt.seekersTeam.addEntry(player.getName()); //Will cause issues when with duplicate accounts
			
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
	
	public void defaultDisguise(Player player) 
	{
		Yaml.updateField(player.getUniqueId().toString()+".blockhunt.blockmeta", "internal",1);
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.block", "internal", getMap().getBlocks()[random.nextInt(getMap().getBlocks().length)].toString());
	}
	public void hidePlayer(Player player) 
	{
		for(Player selectedPlayer : players) 
		{
			if(selectedPlayer != player) 
			{
				selectedPlayer.hidePlayer(Common.spigot, player);
			}
		}
	}
	public void showPlayer(Player player) 
	{
		for(Player selectedPlayer : players) 
		{
			if(selectedPlayer != player) 
			{
				selectedPlayer.showPlayer(Common.spigot, player);
			}
		}
	}
    public Boolean isBlockPlayable(Material material) 
    {
    	for (Material allowedMaterial : map.getBlocks()) 
    	{
    		if(allowedMaterial.equals(material)) return true;
    	}
    	return false;
    }
    
    public Boolean isPlayerVisible(Player player,double range)
    {
    	if(stage.equals(Stage.PRE_SEEKER)) return false; //no one shall be visible while seekers have not spawned
    	if(isDead(player)) return false;
    	if(isSeeker(player)) return true;
    	if(Common_BlockHunt.isCloaked(player)) return false;
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
    public Boolean isPlayerSilent(Player player)
    {
    	if(stage.equals(Stage.PRE_SEEKER)) return true; //no one shall be heard when the seekers have not spawned
    	if(isDead(player)) return true;
    	if(Common_BlockHunt.isCloaked(player)) return true;
    	if(player.isSneaking()) return true; //silent if sneaking
    	if(isHider(player) && isPlayerVisible(player, Yaml.getFieldInt("visibilityrange", "blockhunt"))) return false; //if player is revealed then the player is not silent
    	if(player.isSwimming()) return false; //we dont want to encourage people to swim
    	if(player.isSprinting()) return false; //no-one can sprint silently
    	if(isSeeker(player)) return true; //seekers are allowed to walk silently
    	return false; //hiders are not allowed to walk silently
    }
    public void removeCloak(Player player) 
    {
    	if(Yaml.getFieldBool(player.getUniqueId().toString() + ".blockhunt.cloak", "internal")) 
    	{
        	Location location = player.getLocation();
        	location.setX(Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.x", "internal"));
        	location.setY(Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.y", "internal"));
        	location.setZ(Yaml.getFieldInt(player.getUniqueId().toString() + ".blockhunt.location.z", "internal"));
    		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.cloak", "internal", false);
        	for(Player selectedPlayer : players) 	
        	{
        		selectedPlayer.sendBlockChange(location, Bukkit.createBlockData(Material.AIR));
        	}
    	}
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.x", "internal", 0);
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.y", "internal", 0);
		Yaml.updateField(player.getUniqueId().toString() + ".blockhunt.location.z", "internal", 0);
		Common_BlockHunt.updateCloakItem(player);
		Common_BlockHunt.updateBlockPickerItem(player);
		Common_BlockHunt.updateBlockMetaPickerItem(player);
    }
    //Timers
    public void decrementTimer() 
    {
    	timer--;
    }
    
    public int getTimer() 
    {
    	return timer;
    }
    
    public void setTimer(int timer) 
    {
    	this.timer = timer;
    }
    //Stage
    public Stage getStage() 
    {
    	return stage;
    }
    public void setStage(Stage stage) 
    {
    	this.stage = stage;
    }
    public boolean isStage(Stage stage) 
    {
    	return this.stage.equals(stage);
    }
    //Name
    public String getName() 
    {
    	return name;
    }
    //Seekers
    public Player[] getSeekers() 
    {
    	return seekers;
    }
    public void setSeekers(Player[] seekers) 
    {
    	this.seekers = seekers;
    }
    public void addSeeker(Player player) 
    {
    	removeHider(player);
    	addPlayer(player);
    	if(!isSeeker(player)) setSeekers(Common.append(getSeekers(),player));
    }
    public void removeSeeker(Player player) 
    {
    	if(isSeeker(player)) setSeekers(Common.depend(getSeekers(),player));
    }
	public int getSeekerCount() 
	{
		return seekers.length;
	}
    //Hiders
    public Player[] getHiders() 
    {
    	return hiders;
    }
    public void setHiders(Player[] hiders) 
    {
    	this.hiders = hiders;
    }
    public void addHider(Player player) 
    {
    	removeSeeker(player);
    	addPlayer(player);
    	if(!isHider(player)) setHiders(Common.append(getHiders(),player));
    }
    public void removeHider(Player player) 
    {
    	if(isHider(player)) setHiders(Common.depend(getHiders(),player));
    }
	public int getHiderCount() 
	{
		return hiders.length;
	}
    //Players
    public Player[] getPlayers() 
    {
    	return players;
    }
    public void setPlayers(Player[] players) 
    {
    	this.players = players;
    }
    public void addPlayer(Player player) 
    {
    	if(!isPlayer(player)) setPlayers(Common.append(getPlayers(),player));
    }
    public void removePlayer(Player player) 
    {
    	if(isPlayer(player)) setPlayers(Common.depend(getPlayers(),player));
    	removeHider(player);
    	removeSeeker(player);
    }
	public int getPlayerCount() 
	{
		return players.length;
	}
	
	public void delete() 
	{
		for(Player player : players) 
		{
			cleanup(player);
			player.teleport(Map_BlockHunt.getHubLocation());
		}
		games = depend(games,this);
	}
	
	
    public static Game_BlockHunt getPlayersGame(Player player)
    {
		for(Game_BlockHunt game : games) 
		{
			if(game.isPlayer(player)) return game;
		}
		return null;
    }
    public static Game_BlockHunt[] getGameInstances() 
    {
    	return games;
    }
    
	private static Game_BlockHunt[] append(Game_BlockHunt[] array, Game_BlockHunt value) 
	{
		Game_BlockHunt[] arrayAppended = new Game_BlockHunt[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
	}
	private static Game_BlockHunt[] depend(Game_BlockHunt[] array, Game_BlockHunt value)
    {
		Game_BlockHunt[] arrayDepended = new Game_BlockHunt[array.length-1];
        int hitRemove = 0;
        
        for (int i = 0;i < array.length;i++)
        {
            if(!value.equals(array[i]))
            {
                arrayDepended[i-hitRemove] = array[i];
            }
            else
            {
                hitRemove++;
            }
        }
        return arrayDepended;
    }
    public static Game_BlockHunt getInstanceByName(String name) 
    {
    	for(Game_BlockHunt game : games) 
    	{
    		if(game.getName().equals(name)) return game;
    	}
		return null;
    	
    }
    public static void removePlayerFromGames(Player player) 
    {
    	for(Game_BlockHunt game : games) game.removePlayer(player);
    	
    }
}
