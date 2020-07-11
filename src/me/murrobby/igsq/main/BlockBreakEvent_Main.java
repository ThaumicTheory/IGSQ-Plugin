package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main_Spigot;

public class BlockBreakEvent_Main implements Listener
{
	private String cancelMessage;
	public BlockBreakEvent_Main(Main_Spigot plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
		cancelMessage = "Sorry You cant break blocks Yet!";
	}
	
	@EventHandler
	public void BlockBreak_Main(org.bukkit.event.block.BlockBreakEvent event) 
	{
		@SuppressWarnings("unused")
		Block block = event.getBlock();
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		String player2FA = Common.getFieldString(uuid + ".2fa", "playerdata");
		if((!player2FA.equalsIgnoreCase("accepted")) && (!player2FA.equalsIgnoreCase("off"))) 
		{
			player.sendMessage(cancelMessage);
			event.setCancelled(true);
		}
	}
	
}
