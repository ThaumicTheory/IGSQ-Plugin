package me.murrobby.igsq.spigot.lp;

import java.util.Dictionary;
import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.nametagedit.plugin.NametagEdit;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class NametagEdit_LP
{	
	Main_Spigot plugin;
	int nteTask = -1;
	final int taskID;
	
	public NametagEdit_LP(Main_Spigot plugin,int taskID) 
	{
		this.plugin = plugin;
		this.taskID = taskID;
		TwoFactorAuthenticationQuery();
	}
	private void TwoFactorAuthenticationQuery() 
	{
		nteTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
    	{

			@Override
			public void run() 
			{
				NametagUpdater();
				if(Main_LP.taskID != taskID) 
				{
					plugin.scheduler.cancelTask(nteTask);
					System.out.println("Task: \"NametagEdit LuckPerms\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 100);
	}
	private void NametagUpdater() 
	{
		Dictionary<String, String> nametagPrefixes = new Hashtable<String, String>();
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
		nametagPrefixes.put("mod", "&6[&5T&eS&6] &e");
		nametagPrefixes.put("mod2", "&6[&bJ&eS&6] &e");
		nametagPrefixes.put("mod3", "&6[&dS&eS&6] &e");
		nametagPrefixes.put("council", "&5[&dC&5] &d");
<<<<<<< Updated upstream
=======
		
		Dictionary<String, String> nametagSuffixes = new Hashtable<String, String>();
		nametagSuffixes.put("developer", "&2 [&aD&2]");
		nametagSuffixes.put("founder", "&3 [&bF&3]");
		nametagSuffixes.put("birthday", "&d [&fB&d]");
		nametagSuffixes.put("nitroboost", "&5 [&dN&5]");
		nametagSuffixes.put("supporter", "&d [&cS&d]");
		nametagSuffixes.put("none", "");
>>>>>>> Stashed changes
		for (Player player : Bukkit.getOnlinePlayers())
		{
			Common_LP.GetRank(player);
			NametagEdit.getApi().setNametag(player,Common_Spigot.ChatFormatter(nametagPrefixes.get(Common_LP.GetRank(player))),"");
		}
	}
}
