package thaumictheory.igsq.spigot.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Common_Command 
{
	public static boolean requirePermission(String permission,CommandSender sender) 
	{
		if(sender instanceof Player) 
		{
			Player player = (Player) sender;
			if(player.hasPermission(permission)) return true;
		}
		return false;
	}
}
