package thaumictheory.igsq.spigot.blockhunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.comphenix.protocol.events.PacketContainer;

import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Communication;
import thaumictheory.igsq.spigot.Messaging;

public class Common_BlockHunt 
{
	public static int numberPerSeeker = 2;
	
	public static ScoreboardManager manager;
    public static Scoreboard board;
    
    public static Team hidersTeam;
    public static Team seekersTeam;

	public static int getSeekerCount(Player_BlockHunt[] players) 
	{
		if(players.length <= 2) return 1;
		else return (int)(players.length/numberPerSeeker);
	}
	public static int getSeekerCount(int players) 
	{
		if(players <= 2) return 1;
		else return (int)(players/numberPerSeeker);
	}
	/*
    public static Boolean isInteractWhitelisted(Material material) 
    {
    	for (Material allowedMaterial : interactWhitelist) 
    	{
    		if(allowedMaterial.equals(material)) return true;
    	}
    	return false;
    }
    */
	public void hidePlayer() 
	{
		for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
		{
			if(!selectedPlayer.getUniqueId().equals(selectedPlayer.getUniqueId()))  
			{
				selectedPlayer.hidePlayer(Common.spigot, selectedPlayer);
			}
		}
	}
	public void showPlayer() 
	{
		for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
		{
			if(!selectedPlayer.getUniqueId().equals(selectedPlayer.getUniqueId()))  
			{
				selectedPlayer.showPlayer(Common.spigot, selectedPlayer);
			}
		}
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
			Common_BlockHunt.hidersTeam = Common_BlockHunt.board.getTeam("hiderteambhigsq");
		}
		try
		{
			Common_BlockHunt.seekersTeam = Common_BlockHunt.board.registerNewTeam("seekerteambhigsq");
		}
		catch(Exception exception) 
		{
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
	    Common_BlockHunt.hidersTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
	    Common_BlockHunt.hidersTeam.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
	    Common_BlockHunt.hidersTeam.setOption(Option.DEATH_MESSAGE_VISIBILITY, OptionStatus.NEVER);
	    Common_BlockHunt.hidersTeam.setCanSeeFriendlyInvisibles(true);
	}
    public static Player_BlockHunt[] append(Player_BlockHunt[] array, Player_BlockHunt value)
    {
    	Player_BlockHunt[] arrayAppended = new Player_BlockHunt[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    public static Player_BlockHunt[] depend(Player_BlockHunt[] array, Player_BlockHunt value)
    {
		if(array.length == 0) return array;
		Player_BlockHunt[] arrayDepended = new Player_BlockHunt[array.length-1];
        int hitRemove = 0;
        
        for (int i = 0;i < array.length;i++)
        {
            if(!value.getPlayer().getUniqueId().equals(array[i].getPlayer().getUniqueId()))
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
    public static Hider_BlockHunt[] append(Hider_BlockHunt[] array, Hider_BlockHunt value)
    {
    	Hider_BlockHunt[] arrayAppended = new Hider_BlockHunt[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    public static Hider_BlockHunt[] depend(Hider_BlockHunt[] array, Hider_BlockHunt value)
    {
		if(array.length == 0) return array;
		Hider_BlockHunt[] arrayDepended = new Hider_BlockHunt[array.length-1];
        int hitRemove = 0;
        
        for (int i = 0;i < array.length;i++)
        {
            if(!value.getPlayer().getUniqueId().equals(array[i].getPlayer().getUniqueId()))
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
    public static Seeker_BlockHunt[] append(Seeker_BlockHunt[] array, Seeker_BlockHunt value)
    {
    	Seeker_BlockHunt[] arrayAppended = new Seeker_BlockHunt[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
    }
    public static Seeker_BlockHunt[] depend(Seeker_BlockHunt[] array, Seeker_BlockHunt value)
    {
		if(array.length == 0) return array;
		Seeker_BlockHunt[] arrayDepended = new Seeker_BlockHunt[array.length-1];
        int hitRemove = 0;
        
        for (int i = 0;i < array.length;i++)
        {
            if(!value.getPlayer().getUniqueId().equals(array[i].getPlayer().getUniqueId()))
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
	public static Game_BlockHunt[] append(Game_BlockHunt[] array, Game_BlockHunt value) 
	{
		Game_BlockHunt[] arrayAppended = new Game_BlockHunt[array.length+1];
    	for (int i = 0;i < array.length;i++)
    	{
    		arrayAppended[i] = array[i];
    	}
    	arrayAppended[array.length] = value;
    	return arrayAppended;
	}
	public static Game_BlockHunt[] depend(Game_BlockHunt[] array, Game_BlockHunt value)
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
	
	public static Player[] getPlayersInGui()
    {
		Player[] players = {};
		for(Player player : Bukkit.getOnlinePlayers()) 
		{
			if(isPlayerInGui(player)) players = Common.append(players, player);
		}
		return players;
    }
	public static boolean isPlayerInGui(Player player)
    {
		return player.getOpenInventory().getTitle().equals(Messaging.chatFormatter("&#00FFFFBlockHunt Games"));
    }
	public static Inventory updateGui() 
	{
		int slots = Game_BlockHunt.getGameInstanceCount()+1;
		int requiredSlots = 0;
		while(slots > requiredSlots && requiredSlots < 54) requiredSlots +=9;
		Inventory gui = Bukkit.createInventory(null, requiredSlots, Messaging.chatFormatter("&#00FFFFBlockHunt Games"));
		for(Game_BlockHunt game : Game_BlockHunt.getGameInstances()) 
		{
			gui.addItem(game.getItem());
		}
		ItemStack item = new ItemStack(Material.WHITE_CONCRETE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(Messaging.chatFormatter("&#FFFFFFCREATE A GAME"));
		lore.add(Messaging.chatFormatter("&#EEEEEECreate your own lobby"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		gui.addItem(item);
		for(Player selectedPlayer : getPlayersInGui()) selectedPlayer.openInventory(gui);
		return gui;
	}
	public static void updateGui(Player player) 
	{
		player.openInventory(updateGui());
	}
	
	public static void tag() 
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			Game_BlockHunt playersGame =  Game_BlockHunt.getPlayersGame(player);
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
			if(Common.isCurrentNameController("blockhunt", player) && playersGame != null) 
			{
				String name = player.getName();
				if(yaml.isLinked()) name = yaml.getNickname();
				if(playersGame.isStage(Stage.IN_LOBBY)) Communication.setTag(player,Messaging.chatFormatter("&e[&6I&e] &6"),name,"");
				else if(playersGame.isHider(player)) Communication.setTag(player, Messaging.chatFormatter("&3[&bH&3] &b"),name,"");
				else if(playersGame.isSeeker(player)) Communication.setTag(player, Messaging.chatFormatter("&4[&cS&4] &c"),name,"");
				else Communication.setTag(player, Messaging.chatFormatter("&8[&7Sp&8] &7"),name,"");
			}
		}
	}
	public static PacketContainer tagEvent(Player player) 
	{
		Game_BlockHunt playersGame =  Game_BlockHunt.getPlayersGame(player);
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
		if(Common.isCurrentNameController("blockhunt", player) && playersGame != null) 
		{
			String name = player.getName();
			if(yaml.isLinked()) name = yaml.getNickname();
			if(playersGame.isStage(Stage.IN_LOBBY)) return Communication.setTagAsPacket(player,Messaging.chatFormatter("&e[&6I&e] &6"),name,"");
			else if(playersGame.isHider(player)) return Communication.setTagAsPacket(player, Messaging.chatFormatter("&3[&bH&3] &b"),name,"");
			else if(playersGame.isSeeker(player)) return Communication.setTagAsPacket(player, Messaging.chatFormatter("&4[&cS&4] &c"),name,"");
			else return Communication.setTagAsPacket(player, Messaging.chatFormatter("&8[&7Sp&8] &7"),name,"");
		}
		return null;
	}
  
}
