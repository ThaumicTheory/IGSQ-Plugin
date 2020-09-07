package me.murrobby.igsq.bungee.lp;

import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Rank_LP
{	
	Main_Bungee plugin;
	Random random = new Random();
	ScheduledTask rankTask;
	final int taskID;
	
	public Rank_LP(Main_Bungee plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		RankQuery();
	}
	private void RankQuery() 
	{
		rankTask = plugin.getProxy().getScheduler().schedule(plugin, new Runnable() 
    	{

			@Override
			public void run() 
			{
				Rank();
				if(Main_LP.taskID != taskID) 
				{
					rankTask.cancel();
					System.out.println("Task: \"Rank LuckPerms\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 7, 5, TimeUnit.SECONDS);
	}
	private void Rank() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			String originalRank = Common_LP.GetRank(player);
<<<<<<< Updated upstream
			String serverRank = Common_Bungee.GetFieldString(player.getUniqueId() + ".discord.role", "playerData");
			if(serverRank != null && (!serverRank.equalsIgnoreCase("")) && (!originalRank.equalsIgnoreCase(serverRank))) Common_LP.SetRank(player, serverRank,originalRank);	
=======
			String serverRank = Common_Bungee.getFieldString(player.getUniqueId() + ".discord.role", "player");
			if(serverRank != null && (!serverRank.equalsIgnoreCase("")) && (!originalRank.equalsIgnoreCase(serverRank))) Common_LP.SetRank(player, serverRank,originalRank);
			CheckSecondary(player,"developer", Boolean.valueOf(Common_Bungee.getFieldString(player.getUniqueId() + ".discord.developer", "player")));
			CheckSecondary(player,"founder", Boolean.valueOf(Common_Bungee.getFieldString(player.getUniqueId() + ".discord.founder", "player")));
			CheckSecondary(player,"supporter", Boolean.valueOf(Common_Bungee.getFieldString(player.getUniqueId() + ".discord.supporter", "player")));
			CheckSecondary(player,"nitroboost", Boolean.valueOf(Common_Bungee.getFieldString(player.getUniqueId() + ".discord.nitroboost", "player")));
			CheckSecondary(player,"birthday", Boolean.valueOf(Common_Bungee.getFieldString(player.getUniqueId() + ".discord.birthday", "player")));
>>>>>>> Stashed changes
		}
	}
	private void CheckSecondary(ProxiedPlayer player,String secondaryRole,Boolean hasRole) 
	{
		if(player.hasPermission("group." + secondaryRole) && (!hasRole)) Common_LP.RemoveRank(player, secondaryRole);
		else if((!player.hasPermission("group." + secondaryRole)) && hasRole) Common_LP.GiveRank(player, secondaryRole);
	}
}
