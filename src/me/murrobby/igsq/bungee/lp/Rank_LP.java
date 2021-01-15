package me.murrobby.igsq.bungee.lp;

import me.murrobby.igsq.bungee.YamlPlayerWrapper;
import me.murrobby.igsq.shared.Ranks;
import me.murrobby.igsq.shared.SubRanks;
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
		rankQuery();
	}
	private void rankQuery() 
	{
		rankTask = Common.bungee.getProxy().getScheduler().schedule(Common.bungee, new Runnable() 
    	{

			@Override
			public void run() 
			{
				rank();
				if(Main_LP.taskID != taskID) 
				{
					rankTask.cancel();
					System.out.println("Task: \"Rank LuckPerms\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 7, 1, TimeUnit.SECONDS);
	}
	private void rank() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			Ranks originalRank = Common_LP.getRank(player);
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
			Ranks serverRank = Ranks.getRank(yaml.getRole());
			if(serverRank == null || serverRank == Ranks.getRank(0)) Common_LP.setRank(player, Ranks.getRank(1),Ranks.getRank(0));
			else if((!originalRank.equals(serverRank))) Common_LP.setRank(player, serverRank,originalRank);
			checkSubRank(player,SubRanks.DEVELOPER, yaml.isDeveloper());
			checkSubRank(player,SubRanks.FOUNDER, yaml.isFounder());
			checkSubRank(player,SubRanks.SUPPORTER, yaml.isSupporter());
			checkSubRank(player,SubRanks.NITROBOOST, yaml.isBooster());
			checkSubRank(player,SubRanks.BIRTHDAY, yaml.isBirthday());
		}
	}
	private void checkSubRank(ProxiedPlayer player,SubRanks subRank,boolean hasRole) 
	{
		if(player.hasPermission(subRank.getPermission()) && (!hasRole)) Common_LP.removeRank(player, subRank);
		else if((!player.hasPermission(subRank.getPermission()) && hasRole)) Common_LP.giveRank(player, subRank);
	}
}
