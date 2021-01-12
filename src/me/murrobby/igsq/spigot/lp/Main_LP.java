package me.murrobby.igsq.spigot.lp;

import org.bukkit.ChatColor;

import me.murrobby.igsq.spigot.Spigot;

public class Main_LP 
{
	public static int taskID = 0;
	public Main_LP(Spigot plugin)
	{
		//Events run forever and cannot be turned off
		new AsyncPlayerChatEvent_LP();
		new PlayServerPlayerInfo_LP();
		Start_LuckPerms();
	}
	public static void Start_LuckPerms() //Tasks will close if LP is turned off therefor they will need to be rerun for enabling lp
	{
		Common_LP.nametagPrefixes.put("default", "&3[&bS&3] &b");
		Common_LP.nametagPrefixes.put("rising", "&3[&9R&3] &9");
		Common_LP.nametagPrefixes.put("flying", "&3[&6F&3] &6");
		Common_LP.nametagPrefixes.put("soaring", "&a[&2S&a] &2");
		Common_LP.nametagPrefixes.put("epic", "&3[&aEp&11&3] &a");
		Common_LP.nametagPrefixes.put("epic2", "&3[&aEp&92&3] &a");
		Common_LP.nametagPrefixes.put("epic3", "&3[&aEp&b3&3] &a");
		Common_LP.nametagPrefixes.put("elite", "&3[&dEl&11&3] &d");
		Common_LP.nametagPrefixes.put("elite2", "&3[&dEl&92&3] &d");
		Common_LP.nametagPrefixes.put("elite3", "&3[&dEl&b3&3] &d");
		Common_LP.nametagPrefixes.put("mod", "&e[&6H&e] &6");
		Common_LP.nametagPrefixes.put("mod2", "&6[&eS&6] &e");
		Common_LP.nametagPrefixes.put("mod3", "&6[&dS&eS&6] &e");
		Common_LP.nametagPrefixes.put("council", "&5[&dC&5] &d");
		
		Common_LP.nametagColor.put("default", ChatColor.AQUA);
		Common_LP.nametagColor.put("rising", ChatColor.BLUE);
		Common_LP.nametagColor.put("flying", ChatColor.GOLD);
		Common_LP.nametagColor.put("soaring", ChatColor.DARK_GREEN);
		Common_LP.nametagColor.put("epic", ChatColor.GREEN);
		Common_LP.nametagColor.put("epic2", ChatColor.GREEN);
		Common_LP.nametagColor.put("epic3", ChatColor.GREEN);
		Common_LP.nametagColor.put("elite", ChatColor.LIGHT_PURPLE);
		Common_LP.nametagColor.put("elite2", ChatColor.LIGHT_PURPLE);
		Common_LP.nametagColor.put("elite3", ChatColor.LIGHT_PURPLE);
		Common_LP.nametagColor.put("mod", ChatColor.GOLD);
		Common_LP.nametagColor.put("mod2", ChatColor.YELLOW);
		Common_LP.nametagColor.put("mod3", ChatColor.YELLOW);
		Common_LP.nametagColor.put("council", ChatColor.DARK_PURPLE);
		
		Common_LP.nametagSuffixes.put("developer", "&2 [&aD&2]");
		Common_LP.nametagSuffixes.put("founder", "&3 [&bF&3]");
		Common_LP.nametagSuffixes.put("birthday", "&d [&fB&d]");
		Common_LP.nametagSuffixes.put("nitroboost", "&5 [&dN&5]");
		Common_LP.nametagSuffixes.put("supporter", "&d [&cS&d]");
		Common_LP.nametagSuffixes.put("none", "");
		taskID++;
		new CustomTag_LP(taskID);
	}
}
