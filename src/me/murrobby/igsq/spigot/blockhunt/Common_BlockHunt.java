package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Yaml;

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
	    Common_BlockHunt.hidersTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OWN_TEAM);
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
  
}
