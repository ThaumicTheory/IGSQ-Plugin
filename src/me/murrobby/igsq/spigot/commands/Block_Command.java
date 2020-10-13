package me.murrobby.igsq.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murrobby.igsq.shared.Common_Shared;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;

public class Block_Command {
	private CommandSender sender;
	public Boolean result;
	private String[] args;
	private Player player;
	private Player[] players = null;
	private String display = "Yourself";
	
	public Block_Command(CommandSender sender,String[] args) 
	{
		this.sender = sender;
		this.args = args;
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
			playerArgs = Common_Shared.getBetween(args, 2, -1);
			if(args.length >=3) 
			{
				if(args[2].equalsIgnoreCase("@all")) 
				{
					display = "Everyone";
					for(Player selectedPlayer : Common.spigot.getServer().getOnlinePlayers()) 
					{
						players = Common.append(players,selectedPlayer);
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
						players = Common.append(players,Bukkit.getPlayer(selectedPlayer));
						display += players[players.length-1].getName() + " ";
					}
				}
			}
		}
		catch(Exception exception) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#CD0000This Block, or a Player cound not be found!"));
			return false;
		}
		Block block = location.getBlock();
		if(args.length == 1 || args[1].equalsIgnoreCase("fake")) 
		{
			for (Player selectedPlayer : players) 
			{ 
				selectedPlayer.sendBlockChange(location, material.createBlockData());
			}
			sender.sendMessage(Messaging.chatFormatter("&#58FFFFGave &#C8C8C8FAKE &#00FFC7"+ args[0].toLowerCase() +" &#58FFFFto " + display));
			return true;
		}
		else if(args[1].equalsIgnoreCase("real"))
		{
			block.setType(Material.valueOf(args[0].toUpperCase()));
			sender.sendMessage(Messaging.chatFormatter("&#58FFFFGave &#00FFC7"+ args[0].toLowerCase() +" &#58FFFFto " + display));
			return true;
		}
		else if(args[1].equalsIgnoreCase("trap")) 
		{
			block.setBlockData(Bukkit.createBlockData("minecraft:tnt[unstable=true]"));
			Bukkit.getScheduler().runTaskLater(Common.spigot, () -> {
				for (Player selectedPlayer : players) 
				{ 
					selectedPlayer.sendBlockChange(location, material.createBlockData());
				}
				sender.sendMessage(Messaging.chatFormatter("&#58FFFFGave &#FFD700TRAP &#00FFC7"+ args[0].toLowerCase() +" &#58FFFFto " + display));
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
		if(Common_Command.requirePermission("igsq.block",sender) && sender instanceof Player) 
		{
			if(Block()) 
			{
				return true;
			}
			else 
			{
				sender.sendMessage(Messaging.chatFormatter("&#FFFF00block [block_ID] [*fake*/real/trap] {@all/username/*\"you\"*} {\"another user\"} ..."));
				return false;
			}
		}
		else
		{
			sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
  			return false;
		}
	}
}