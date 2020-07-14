package me.murrobby.igsq.spigot.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class BlockBreakEvent_Security implements Listener
{
	private String cancelMessage;
	public BlockBreakEvent_Security(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cancelMessage = "Sorry You cant break blocks Yet!";
	}
	
	@EventHandler
	public void BlockBreak_Main(org.bukkit.event.block.BlockBreakEvent event) 
	{
		if(!event.isCancelled()) 
		{
			Player player = event.getPlayer();
			String uuid = player.getUniqueId().toString();
			String player2FA = Common_Spigot.getFieldString(uuid + ".2fa", "playerdata");
			if(player2FA.equalsIgnoreCase("") || ((!player2FA.equalsIgnoreCase("accepted")) && (!player2FA.equalsIgnoreCase("off")))) 
			{
				player.sendMessage(cancelMessage);
				event.setCancelled(true);
			}
		}
	}
	
}
