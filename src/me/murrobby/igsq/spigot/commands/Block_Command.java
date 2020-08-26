package me.murrobby.igsq.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class Block_Command {
	private Main_Spigot plugin;
	private Main_Command commands;
	private CommandSender sender;
	public Boolean result;
	private String[] args;
	private Player player;
	private Player[] players = null;
	private String display = "Yourself";
	
	public Block_Command(Main_Spigot plugin,Main_Command commands,CommandSender sender,String[] args) 
	{
		this.commands = commands;
		this.sender = sender;
		this.args = args;
		this.plugin = plugin;
		result = BlockQuery();
	}
		
	
	private boolean Block() {
		player = (Player) sender;
		Location location = player.getLocation();
		String[] playerArgs = new String[0];
		Material material;
		players = new Player[1];
		players[0] = player;
		try 
		{
			material = Material.valueOf(args[0].toUpperCase());
			playerArgs = Common_Spigot.GetBetween(args, 2, -1);
			if(args.length >=3) 
			{
				if(args[2].equalsIgnoreCase("@all")) 
				{
					display = "Everyone";
					for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
					{
						players = Common_Spigot.Append(players,selectedPlayer);
					}
				}
//				depracated code
//				else if(args[2].equalsIgnoreCase("@others")) 
//				{
//					display = "Everyone Else";
//					for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
//					{
//						if(!(selectedPlayer.equals(player))) 
//						{
//							players = Common_Spigot.Append(players,selectedPlayer);
//						}
//					}
//				}
				else 
				{
					players = new Player[0];
					display = "";
					for (String selectedPlayer : playerArgs) 
					{ 
						players = Common_Spigot.Append(players,Bukkit.getPlayer(selectedPlayer));
						display += players[players.length-1].getName() + " ";
					}
				}
			}
		}
		catch(Exception exception) 
		{
			sender.sendMessage(Common_Spigot.ChatFormatter("&#CD0000This Block, or a Player cound not be found!"));
			return false;
		}
		Block block = location.getBlock();
		if(args.length == 1 || args[1].equalsIgnoreCase("fake")) 
		{
			for (Player selectedPlayer : players) 
			{ 
				selectedPlayer.sendBlockChange(location, material.createBlockData());
			}
			sender.sendMessage(Common_Spigot.ChatFormatter("&#58FFFFGave &#C8C8C8FAKE &#00FFC7"+ args[0].toLowerCase() +" &#58FFFFto " + display));
			return true;
		}
		else if(args[1].equalsIgnoreCase("real"))
		{
			block.setType(Material.valueOf(args[0].toUpperCase()));
			sender.sendMessage(Common_Spigot.ChatFormatter("&#58FFFFGave &#00FFC7"+ args[0].toLowerCase() +" &#58FFFFto " + display));
			return true;
		}
		else if(args[1].equalsIgnoreCase("trap")) 
		{
			block.setBlockData(Bukkit.createBlockData("minecraft:tnt[unstable=true]"));
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				for (Player selectedPlayer : players) 
				{ 
					selectedPlayer.sendBlockChange(location, material.createBlockData());
				}
				sender.sendMessage(Common_Spigot.ChatFormatter("&#58FFFFGave &#FFD700TRAP &#00FFC7"+ args[0].toLowerCase() +" &#58FFFFto " + display));
			}, 2);
			return true;
		}
		else 
		{
			return false;
		}
	}

	private boolean BlockQuery() 
	{
		if(commands.RequirePermission("igsq.block") && commands.IsPlayer()) 
		{
			if(Block()) 
			{
				return true;
			}
			else 
			{
				sender.sendMessage(Common_Spigot.ChatFormatter("&#FFFF00block [block_ID] [*fake*/real/trap] {@all/username/*\"you\"*} {\"another user\"} ..."));
				return false;
			}
		}
		else
		{
			sender.sendMessage(Common_Spigot.ChatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
  			return false;
		}
	}
}