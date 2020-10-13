package me.murrobby.igsq.spigot.lp;

import java.util.Dictionary;
import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.nametagedit.plugin.NametagEdit;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;

public class NametagEdit_LP
{	
	int nteTask = -1;
	final int taskID;
	Dictionary<String, String> nametagPrefixes = new Hashtable<String, String>();
	Dictionary<String, String> nametagSuffixes = new Hashtable<String, String>();
	
	public NametagEdit_LP(int taskID) 
	{
		this.taskID = taskID;
		NametagUpdaterQuery();
		nametagPrefixes.put("default", "&3[&bS&3] &b");
		nametagPrefixes.put("rising", "&3[&9R&3] &9");
		nametagPrefixes.put("flying", "&3[&6F&3] &6");
		nametagPrefixes.put("soaring", "&a[&2S&a] &2");
		nametagPrefixes.put("epic", "&3[&aEp&11&3] &a");
		nametagPrefixes.put("epic2", "&3[&aEp&92&3] &a");
		nametagPrefixes.put("epic3", "&3[&aEp&b3&3] &a");
		nametagPrefixes.put("elite", "&3[&dEl&11&3] &d");
		nametagPrefixes.put("elite2", "&3[&dEl&92&3] &d");
		nametagPrefixes.put("elite3", "&3[&dEl&b3&3] &d");
		nametagPrefixes.put("mod", "&e[&6H&e] &6");
		nametagPrefixes.put("mod2", "&6[&eS&6] &e");
		nametagPrefixes.put("mod3", "&6[&dS&eS&6] &e");
		nametagPrefixes.put("council", "&5[&dC&5] &d");
		
		nametagSuffixes.put("developer", "&2 [&aD&2]");
		nametagSuffixes.put("founder", "&3 [&bF&3]");
		nametagSuffixes.put("birthday", "&d [&fB&d]");
		nametagSuffixes.put("nitroboost", "&5 [&dN&5]");
		nametagSuffixes.put("supporter", "&d [&cS&d]");
		nametagSuffixes.put("none", "");
	}
	private void NametagUpdaterQuery() 
	{
		nteTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				NametagUpdater();
				if(Main_LP.taskID != taskID) 
				{
					Common.spigot.scheduler.cancelTask(nteTask);
					System.out.println("Task: \"NametagEdit LuckPerms\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 100);
	}
	private void NametagUpdater()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if(Common.isCurrentTagController("main", player)) 
			{
				Common_LP.getRank(player);
				NametagEdit.getApi().setNametag(player,Messaging.chatFormatter(nametagPrefixes.get(Common_LP.getRank(player))),nametagSuffixes.get(getHighestSubRank(player)));
			}
		}
		
	}
	private String getHighestSubRank(Player player) 
	{
		if(player.hasPermission("group.developer")) return "developer";
		else if(player.hasPermission("group.founder")) return "founder";
		else if(player.hasPermission("group.birthday")) return "birthday";
		else if(player.hasPermission("group.nitroboost")) return "nitroboost";
		else if(player.hasPermission("group.supporter")) return "supporter";
		else return "none";
	}
}
