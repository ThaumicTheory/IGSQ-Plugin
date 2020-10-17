package me.murrobby.igsq.bungee.lp;

import me.murrobby.igsq.bungee.Yaml;
import me.murrobby.igsq.bungee.Common;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

public class Rank_LP
{	
	ScheduledTask rankTask;
	final int taskID;
	
	public Rank_LP(int taskID) 
	{
		this.taskID = taskID;
		RankQuery();
	}
	private void RankQuery() 
	{
		rankTask = Common.bungee.getProxy().getScheduler().schedule(Common.bungee, new Runnable() 
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
			String serverRank = Yaml.getFieldString(player.getUniqueId() + ".discord.role", "player");
			if(serverRank != null && (!serverRank.equalsIgnoreCase("")) && (!originalRank.equalsIgnoreCase(serverRank))) Common_LP.SetRank(player, serverRank,originalRank);
			CheckSecondary(player,"developer", Boolean.valueOf(Yaml.getFieldString(player.getUniqueId() + ".discord.developer", "player")));
			CheckSecondary(player,"founder", Boolean.valueOf(Yaml.getFieldString(player.getUniqueId() + ".discord.founder", "player")));
			CheckSecondary(player,"supporter", Boolean.valueOf(Yaml.getFieldString(player.getUniqueId() + ".discord.supporter", "player")));
			CheckSecondary(player,"nitroboost", Boolean.valueOf(Yaml.getFieldString(player.getUniqueId() + ".discord.nitroboost", "player")));
			CheckSecondary(player,"birthday", Boolean.valueOf(Yaml.getFieldString(player.getUniqueId() + ".discord.birthday", "player")));
		}
	}
	private void CheckSecondary(ProxiedPlayer player,String secondaryRole,Boolean hasRole) 
	{
		if(player.hasPermission("group." + secondaryRole) && (!hasRole)) Common_LP.RemoveRank(player, secondaryRole);
		else if((!player.hasPermission("group." + secondaryRole)) && hasRole) Common_LP.GiveRank(player, secondaryRole);
	}
}
