package me.murrobby.igsq.bungee.lp;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
	public static String GetPrefix(ProxiedPlayer player) 
	{
		User user = luckPerms.getUserManager().getUser(player.getUniqueId());
		GetUserData(user);
		return metaData.getPrefix();
	}
    /**
     * gets the name of the Players highest rank.
     * @apiNote Shows the Primary Group the user is in.
     * @see net.luckperms.api.cacheddata.CachedMetaData#getPrimaryGroup()
     * @return <b>String</b>
     */
    public static String GetRank(ProxiedPlayer player)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	GetUserData(user);
    	return metaData.getPrimaryGroup();
    }
}
