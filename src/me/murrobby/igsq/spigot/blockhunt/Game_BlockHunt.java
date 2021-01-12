package me.murrobby.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.event.BeginSeekEvent;
import me.murrobby.igsq.spigot.event.GameEndEvent;
import me.murrobby.igsq.spigot.event.GameStartEvent;
import me.murrobby.igsq.spigot.event.LobbyCreateEvent;
import me.murrobby.igsq.spigot.event.PlayerJoinLobbyEvent;

public class Game_BlockHunt 
{
	private Seeker_BlockHunt[] seekers = {};
	private Hider_BlockHunt[] hiders = {};
	private Player_BlockHunt[] players = {};
	
	private Stage stage = Stage.NO_GAME;
	private Map_BlockHunt map;
    private int timer;
    private String name;
    private boolean testMode;
    private GuiSystem_BlockHunt gui;
    
    private static Game_BlockHunt[] games = {};
    private Random random = new Random();
	
	public Game_BlockHunt(String name) 
	{
		map = new Map_BlockHunt();
		this.name = name;
		gui = new GuiSystem_BlockHunt(this);
		timer = YamlWrapper.getBlockHuntLobbyTime();
		games = Common_BlockHunt.append(games, this);
		createLobby();
	}
	
	public Game_BlockHunt() 
	{
		map = new Map_BlockHunt();
		this.name = String.valueOf(System.currentTimeMillis());
		gui = new GuiSystem_BlockHunt(this);
		timer = YamlWrapper.getBlockHuntLobbyTime();
		games = Common_BlockHunt.append(games, this);
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
    	for(Seeker_BlockHunt selectedPlayer :seekers) 
    	{
    		if(selectedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public Boolean isHider(Player player) 
    {
    	for(Hider_BlockHunt selectedPlayer :hiders) 
    	{
    		if(selectedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    public Boolean isPlayer(Player player) 
    {
    	for(Player_BlockHunt selectedPlayer : getPlayers()) 
    	{
    		if(selectedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return true;
    	}
    	return false;
    }
    
    public Map_BlockHunt getMap()
    {
    	return map;
    }
    
    public Hider_BlockHunt getHiderCloaked(Location location) 
	{
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
    	for(Hider_BlockHunt hider : getHiders()) 
    	{
    		if(hider.getGeneric().isCloaked()) 
    		{
                if(hider.getPlayer().getLocation().getBlockX() == x && hider.getPlayer().getLocation().getBlockY() == y && hider.getPlayer().getLocation().getBlockZ() == z) 
                {
                	return hider;
                }
    		}
    	}
		return null;
	}
    
    public Boolean isBlockPlayable(Material material) 
    {
    	for (Material allowedMaterial : map.getBlocks()) 
    	{
    		if(allowedMaterial.equals(material)) return true;
    	}
    	return false;
    }
    
	public void hidePlayer(Player player) 
	{
		for(Player_BlockHunt selectedPlayer : getPlayers()) 
		{
			if(!selectedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId()))  
			{
				selectedPlayer.getPlayer().hidePlayer(Common.spigot, player);
			}
		}
	}
	public void showPlayer(Player player) 
	{
		for(Player_BlockHunt selectedPlayer : getPlayers()) 
		{
			if(!selectedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) 
			{
				selectedPlayer.getPlayer().showPlayer(Common.spigot, player);
			}
		}
	}
	//Test Mode
    public boolean isTestMode() 
    {
    	return testMode;
    }
    
    public void setTestMode(boolean testMode) 
    {
    	this.testMode = testMode;
    }
    //Timers
    public void decrementTimer() 
    {
    	timer--;
    }
    public void incrementTimer() 
    {
    	timer++;
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
    public Seeker_BlockHunt[] getSeekers() 
    {
    	return seekers;
    }
    public Seeker_BlockHunt[] getAliveSeekers() 
    {
    	Seeker_BlockHunt[] aliveSeekers = {};
    	for(Seeker_BlockHunt player : getSeekers()) 
    	{
    		if(!player.isDead()) aliveSeekers = Common_BlockHunt.append(aliveSeekers, player);
    	}
    	return aliveSeekers;
    }
    public int getAliveSeekerCount() 
    {
    	return getAliveSeekers().length;
    }
    public void setSeekers(Seeker_BlockHunt[] seekers) 
    {
    	this.seekers = seekers;
    }
    public void addSeeker(Player player) 
    {
    	removePlayer(player);
    	removeHider(player);
    	if(!isSeeker(player)) setSeekers(Common_BlockHunt.append(getSeekers(),new Seeker_BlockHunt(player,this)));
    }
    public void addSeeker(Player_BlockHunt player) 
    {
    	removePlayer(player);
    	removeHider(player);
    	if(!player.isSeeker()) setSeekers(Common_BlockHunt.append(getSeekers(),new Seeker_BlockHunt(player)));
    }
    public void addSeeker(Seeker_BlockHunt player) 
    {
    	removePlayer(player);
    	removeHider(player);
    	if(!player.isSeeker()) setSeekers(Common_BlockHunt.append(getSeekers(),player));
    }
    public void addPlayer(Player player) 
    {
    	removeSeeker(player);
    	removeHider(player);
    	if(!isPlayer(player)) setPlayers(Common_BlockHunt.append(getPlayers(),new Player_BlockHunt(player,this)));
    }
    public void addPlayer(Player_BlockHunt player) 
    {
    	removeSeeker(player);
    	removeHider(player);
    	if(!player.isPlayer()) setPlayers(Common_BlockHunt.append(getPlayers(),player));
    }
    public void removeSeeker(Player player) 
    {
    	Seeker_BlockHunt seeker = getSeeker(player);
    	if(seeker != null) 
    	{
    		setSeekers(Common_BlockHunt.depend(getSeekers(),seeker));
    	}
    }
    public void removeSeeker(Player_BlockHunt player) 
    {
    	Seeker_BlockHunt seeker = getSeeker(player.getPlayer());
    	if(seeker != null) 
    	{
    		setSeekers(Common_BlockHunt.depend(getSeekers(),seeker));
    	}
    }
	public int getSeekerCount() 
	{
		return seekers.length;
	}
    //Hiders
    public Hider_BlockHunt[] getHiders() 
    {
    	return hiders;
    }
    public Hider_BlockHunt[] getAliveHiders() 
    {
    	Hider_BlockHunt[] aliveHiders = {};
    	for(Hider_BlockHunt player : getHiders()) 
    	{
    		if(!player.isDead()) aliveHiders = Common_BlockHunt.append(aliveHiders, player);
    	}
    	return aliveHiders;
    }
    public int getAliveHiderCount() 
    {
    	return getAliveHiders().length;
    }
    public void setHiders(Hider_BlockHunt[] hiders) 
    {
    	this.hiders = hiders;
    }
    public void setPlayers(Player_BlockHunt[] players) 
    {
    	this.players = players;
    }
	public Seeker_BlockHunt getSeeker(Player player) 
	{
		for(Seeker_BlockHunt seeker : seekers) 
		{
			if(seeker.getPlayer().getUniqueId().equals(player.getUniqueId())) return seeker;
		}
		return null;
	}
    public void addHider(Player player) 
    {
    	removePlayer(player);
    	removeSeeker(player);
    	if(!isHider(player)) setHiders(Common_BlockHunt.append(getHiders(),new Hider_BlockHunt(player,this)));
    }
    public void addHider(Player_BlockHunt player) 
    {
    	removePlayer(player);
    	removeSeeker(player);
    	if(!player.isHider()) setHiders(Common_BlockHunt.append(getHiders(),new Hider_BlockHunt(player)));
    }
    public void addHider(Hider_BlockHunt player) 
    {
    	removePlayer(player);
    	removeSeeker(player);
    	if(!player.isHider()) setHiders(Common_BlockHunt.append(getHiders(),player));
    }
    public void removeHider(Player player) 
    {
    	Hider_BlockHunt hider = getHider(player);
    	if(hider != null) 
    	{
    		setHiders(Common_BlockHunt.depend(getHiders(),hider));
    	}
    }
    public void removeHider(Player_BlockHunt player) 
    {
    	Hider_BlockHunt hider = getHider(player.getPlayer());
    	if(hider != null) 
    	{
    		setHiders(Common_BlockHunt.depend(getHiders(),hider));
    	}
    }
	public Hider_BlockHunt getHider(Player player) 
	{
		for(Hider_BlockHunt hider : hiders) 
		{
			if(hider.getPlayer().getUniqueId().equals(player.getUniqueId())) return hider;
		}
		return null;
	}
	public int getHiderCount() 
	{
		return hiders.length;
	}
    //Players
    public Player_BlockHunt[] getPlayers() 
    {
    	Player_BlockHunt[] players = this.players;
    	for(Player_BlockHunt hider : getHiders()) players = Common_BlockHunt.append(players, hider);
    	for(Player_BlockHunt seeker : getSeekers()) players = Common_BlockHunt.append(players, seeker);
    	return players;
    }
    public Player_BlockHunt[] getAlivePlayers() 
    {
    	Player_BlockHunt[] alivePlayers = {};
    	for(Player_BlockHunt player : getPlayers()) 
    	{
    		if(!player.isDead()) alivePlayers = Common_BlockHunt.append(alivePlayers, player);
    	}
    	return alivePlayers;
    }
    public int getAlivePlayerCount() 
    {
    	return getAlivePlayers().length;
    }
    public void removePlayer(Player player) 
    {
    	Player_BlockHunt selectedPlayer = getPlayer(player);
    	if(selectedPlayer != null) 
    	{
    		setPlayers(Common_BlockHunt.depend(players,selectedPlayer));
    	}
    }
    public void removePlayer(Player_BlockHunt player) 
    {
    	Player_BlockHunt selectedPlayer = getPlayer(player.getPlayer());
    	if(selectedPlayer != null) 
    	{
    		setPlayers(Common_BlockHunt.depend(players,player));
    	}
    }
	public int getPlayerCount() 
	{
		return getPlayers().length;
	}
	public GuiSystem_BlockHunt getGuiSystem() 
	{
		return gui;
	}
	public Player_BlockHunt getPlayer(Player player) 
	{
		for(Player_BlockHunt selectedPlayer : getPlayers()) 
		{
			if(selectedPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return selectedPlayer;
		}
		return null;
	}
	public void delete() 
	{
		for(Player_BlockHunt selectedPlayer : getPlayers()) selectedPlayer.delete();
		games = Common_BlockHunt.depend(games,this);
	}
	public Random getRandom() 
	{
		return random;
	}
	
	public ItemStack getItem() 
	{
		ItemStack item = new ItemStack(Material.RED_CONCRETE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		if(isStage(Stage.IN_LOBBY)) 
		{
			item.setType(Material.LIME_CONCRETE);
			meta.setDisplayName(Messaging.chatFormatter("&#00FF00" + getName()));
			lore.add(Messaging.chatFormatter("&#00DD00JOINABLE"));
			lore.add(Messaging.chatFormatter("&#EEEEEE") + getMap().getName());
			lore.add(Messaging.chatFormatter("&#FF00FF") + getPlayerCount() + " Players");
			lore.add(Messaging.chatFormatter("&#FF0000Starts in ") + (getTimer()/20) + " Seconds");
		}
		else if(isStage(Stage.IN_GAME) || isStage(Stage.PRE_SEEKER)) 
		{
			item.setType(Material.GRAY_CONCRETE);
			meta.setDisplayName(Messaging.chatFormatter("&#cccccc" + getName()));
			lore.add(Messaging.chatFormatter("&#ccccccIN-GAME"));
			lore.add(Messaging.chatFormatter("&#EEEEEE") + getMap().getName());
			lore.add(Messaging.chatFormatter("&#66ccff" + getAliveHiderCount() +"/" + getHiderCount() +" &#FF0000" + getAliveSeekerCount() + "/" + getSeekerCount()));
			if(isStage(Stage.IN_GAME)) lore.add(Messaging.chatFormatter("&#FF0000Ends in ") + (getTimer()/20) + " Seconds");
			else lore.add(Messaging.chatFormatter("&#FF0000Ends in ") + ((getTimer()+ YamlWrapper.getBlockHuntGameTime())/20) + " Seconds");
		}
		else 
		{
			item.setType(Material.RED_CONCRETE);
			meta.setDisplayName(Messaging.chatFormatter("&#FF0000" + getName()));
			lore.add(Messaging.chatFormatter("&#DD0000UNKNOWN"));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public void leaveItem() 
	{
		ItemStack item = new ItemStack(Material.IRON_DOOR);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(Messaging.chatFormatter("&#FFFFFFLeave"));
		lore.add(Messaging.chatFormatter("&#ccccccLeave the Game"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		for(Player_BlockHunt player : getPlayers()) 
		{
			player.getPlayer().getInventory().setItem(8, item);
		}
	}
	public void nextGameItem() 
	{
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(Messaging.chatFormatter("&#00FFFFPlay Again"));
		lore.add(Messaging.chatFormatter("&#00ccccPlay Another Game"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		for(Player_BlockHunt player : getPlayers()) 
		{
			player.getPlayer().getInventory().setItem(0, item);
		}
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
    
    public static int getGameInstanceCount() 
    {
    	return getGameInstances().length;
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
    public static boolean instanceofGenericHider(Player player) 
    {
    	for(Game_BlockHunt game : games) 
    	{
    		if(game.isHider(player)) return true;
    	}
    	return false;
    }
	public void updatePlayerLayering() 
	{
		for(Player_BlockHunt selectedPlayer : getPlayers()) 
		{
			for(Player player : Bukkit.getOnlinePlayers()) 
			{
				if(isPlayer(player)) player.showPlayer(Common.spigot, selectedPlayer.getPlayer());
				else selectedPlayer.getPlayer().hidePlayer(Common.spigot, player);
			}
		}
	}

}
