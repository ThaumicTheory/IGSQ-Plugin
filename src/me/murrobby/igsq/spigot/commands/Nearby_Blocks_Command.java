package me.murrobby.igsq.spigot.commands;


import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;
/**
 * This Class is no longer required please use alternatives.
 *
 * @deprecated use {@link Common_Spigot#GetHighestBlock()} instead.  
 */
@Deprecated
public class Nearby_Blocks_Command {
	private Main_Command commands;
	private CommandSender sender;
	public Boolean result;
	private Player player;	
	private boolean block;
	public Nearby_Blocks_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		result = NearbyQuery();
	}
	
	public boolean NearbyBlocks() {		
		if(block = ((Block) player.getServer().getPlayer(player.getUniqueId()).getWorld()).getRelative(BlockFace.UP) != null)
		{
			System.out.println(block);
			return true;
		}
		else 
		{
			return false;
		}
	}

	

	private boolean NearbyQuery() 
	{
		if(commands.RequirePermission("igsq.nearby") && commands.IsPlayer()) 
		{
			if(NearbyBlocks()) 
			{
				return true;
			}
			else 
			{
				sender.sendMessage(Common_Spigot.ChatColour("Wrong Syntax"));
				return false;
			}
		}
		else
		{
			sender.sendMessage(Common_Spigot.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
  			return false;
		}
	}
}