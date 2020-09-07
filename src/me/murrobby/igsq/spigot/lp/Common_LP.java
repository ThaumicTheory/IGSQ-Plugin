package me.murrobby.igsq.spigot.lp;

import org.bukkit.entity.Player;

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
	public Common_LP() 
	{
	}
	private static void GetUserData(User user) 
	{
	    queryOptions = contextManager.getQueryOptions(user).orElse(contextManager.getStaticQueryOptions());
		metaData = user.getCachedData().getMetaData(queryOptions);
	}
	public static String GetPrefix(Player player) 
	{
		User user = luckPerms.getUserManager().getUser(player.getUniqueId());
		GetUserData(user);
		return metaData.getPrefix();
	}
	public static String GetSuffix(Player player) 
	{
		User user = luckPerms.getUserManager().getUser(player.getUniqueId());
		GetUserData(user);
		return metaData.getSuffix();
	}
    /**
     * gets the name of the Players highest rank.
     * @apiNote Shows the Primary Group the user is in.
     * @see net.luckperms.api.cacheddata.CachedMetaData#getPrimaryGroup()
     * @return <b>String</b>
     */
    public static String GetRank(Player player)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	GetUserData(user);
    	return metaData.getPrimaryGroup();
    }
}
