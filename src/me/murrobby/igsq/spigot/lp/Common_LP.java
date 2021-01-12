package me.murrobby.igsq.spigot.lp;

import java.util.Dictionary;
import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.comphenix.protocol.events.PacketContainer;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Communication;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public class Common_LP {

	private final static LuckPerms luckPerms = LuckPermsProvider.get();
	private final static ContextManager contextManager = luckPerms.getContextManager();
	
	private static CachedMetaData metaData;
	private static QueryOptions queryOptions;
	static Dictionary<String, String> nametagPrefixes = new Hashtable<String, String>();
	static Dictionary<String, String> nametagSuffixes = new Hashtable<String, String>();
	static Dictionary<String, ChatColor> nametagColor = new Hashtable<String, ChatColor>();
	public Common_LP() 
	{
	}
	private static void getUserData(User user) 
	{
	    queryOptions = contextManager.getQueryOptions(user).orElse(contextManager.getStaticQueryOptions());
		metaData = user.getCachedData().getMetaData(queryOptions);
	}
	public static String getPrefix(Player player) 
	{
		User user = luckPerms.getUserManager().getUser(player.getUniqueId());
		getUserData(user);
		return metaData.getPrefix();
	}
	public static String getSuffix(Player player) 
	{
		User user = luckPerms.getUserManager().getUser(player.getUniqueId());
		getUserData(user);
		return metaData.getSuffix();
	}
    /**
     * gets the name of the Players highest rank.
     * @apiNote Shows the Primary Group the user is in.
     * @see net.luckperms.api.cacheddata.CachedMetaData#getPrimaryGroup()
     * @return <b>String</b>
     */
    public static String getRank(Player player)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	getUserData(user);
    	return metaData.getPrimaryGroup();
    }
    public static void tag() 
    {
		for (Player player : Bukkit.getOnlinePlayers())
		{
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
			if(Common.isCurrentNameController("main", player)) 
			{
				String name = player.getName();
				if(yaml.isLinked()) name = yaml.getNickname();
				Communication.setTag(player, nametagPrefixes.get(Common_LP.getRank(player)),nametagColor.get(Common_LP.getRank(player)),name,nametagSuffixes.get(getHighestSubRank(player)));
				//NametagEdit.getApi().setNametag(player,Messaging.chatFormatter(nametagPrefixes.get(Common_LP.getRank(player))),nametagSuffixes.get(getHighestSubRank(player)));
			}
		}
    }
    public static PacketContainer tagEvent(Player player) 
    {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
		if(Common.isCurrentNameController("main", player)) 
		{
			String name = player.getName();
			if(yaml.isLinked()) name = yaml.getNickname();
			return Communication.setTagAsPacket(player, nametagPrefixes.get(Common_LP.getRank(player)),nametagColor.get(Common_LP.getRank(player)),name,nametagSuffixes.get(getHighestSubRank(player)));
			//NametagEdit.getApi().setNametag(player,Messaging.chatFormatter(nametagPrefixes.get(Common_LP.getRank(player))),nametagSuffixes.get(getHighestSubRank(player)));
		}
		return null;
    }
    private static String getHighestSubRank(Player player) 
	{
		if(player.hasPermission("group.developer")) return "developer";
		else if(player.hasPermission("group.founder")) return "founder";
		else if(player.hasPermission("group.birthday")) return "birthday";
		else if(player.hasPermission("group.nitroboost")) return "nitroboost";
		else if(player.hasPermission("group.supporter")) return "supporter";
		else return "none";
	}
}
