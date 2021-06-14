package thaumictheory.igsq.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import thaumictheory.igsq.shared.Common_Shared;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;

public class Block_Command {
	private CommandSender sender;
	public Boolean result;
	private List<String> args = new ArrayList<>();
	private Player player;
	private List<Player> players = new ArrayList<>();
	private String display = "Yourself";
	
	public Block_Command(CommandSender sender,List<String> args) 
	{
		this.sender = sender;
		this.args = args;
		result = BlockQuery();
	}
		
	
	private boolean Block() {
		player = (Player) sender;
		Location location = player.getLocation();
		List<String> playerArgs = new ArrayList<>();
		Material material;
		players.add(player);
		try 
		{
			material = Material.valueOf(args.get(0).toUpperCase());
			playerArgs = Common_Shared.getBetween(args, 2, args.size());
			if(args.size() >=3) 
			{
				if(args.get(2).equalsIgnoreCase("@all")) 
				{
					display = "Everyone";
					for(Player selectedPlayer : Common.spigot.getServer().getOnlinePlayers()) players.add(selectedPlayer);
				}
				else 
				{
					players.clear();
					display = "";
					for (String selectedPlayer : playerArgs) 
					{ 
						players.add(Bukkit.getPlayer(selectedPlayer));
						display += players.get(players.size()-1).getName() + " ";
					}
				}
			}
		}
		catch(Exception exception) 
		{
			sender.sendMessage(Messaging.chatFormatter("&#CD0000This Block, or a Player cound not be found!"));
			exception.printStackTrace();
			return false;
		}
		Block block = location.getBlock();
		if(args.size() == 1 || args.get(1).equalsIgnoreCase("fake")) 
		{
			for (Player selectedPlayer : players) 
			{ 
				selectedPlayer.sendBlockChange(location, material.createBlockData());
			}
			sender.sendMessage(Messaging.chatFormatter("&#58FFFFGave &#C8C8C8FAKE &#00FFC7"+ args.get(0).toLowerCase() +" &#58FFFFto " + display));
			return true;
		}
		else if(args.get(1).equalsIgnoreCase("real"))
		{
			block.setType(Material.valueOf(args.get(0).toUpperCase()));
			sender.sendMessage(Messaging.chatFormatter("&#58FFFFGave &#00FFC7"+ args.get(0).toLowerCase() +" &#58FFFFto " + display));
			return true;
		}
		else if(args.get(1).equalsIgnoreCase("trap")) 
		{
			block.setBlockData(Bukkit.createBlockData("minecraft:tnt[unstable=true]"));
			Bukkit.getScheduler().runTaskLater(Common.spigot, () -> {
				for (Player selectedPlayer : players) 
				{ 
					selectedPlayer.sendBlockChange(location, material.createBlockData());
				}
				sender.sendMessage(Messaging.chatFormatter("&#58FFFFGave &#FFD700TRAP &#00FFC7"+ args.get(0).toLowerCase() +" &#58FFFFto " + display));
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