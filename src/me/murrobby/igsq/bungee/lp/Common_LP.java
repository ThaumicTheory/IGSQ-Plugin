package me.murrobby.igsq.bungee.lp;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
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
	public static String GetSuffix(ProxiedPlayer player) 
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
    public static String GetRank(ProxiedPlayer player)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	GetUserData(user);
    	return metaData.getPrimaryGroup();
    }
    /**
     * sets the Players highest rank.
     * @apiNote Sets the Primary Group the user is in to the rank specified.
     * @see net.luckperms.api.node.Node
     */
    public static void SetRank(ProxiedPlayer player,String newRank,String oldRank)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	user.data().add(Node.builder("group." + newRank).build());
    	user.data().remove(Node.builder("group." + oldRank).build());
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    	
    	
    }
    /**
     * adds the rank to the designated Player
     * @apiNote Sets the Primary Group the user is in to the rank specified.
     * @see net.luckperms.api.node.Node
     */
    public static void GiveRank(ProxiedPlayer player,String rank)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	user.data().add(Node.builder("group." + rank).build());
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    }
    
    /**
     * removes the rank to the designated Player
     * @apiNote Sets the Primary Group the user is in to the rank specified.
     * @see net.luckperms.api.node.Node
     */
    public static void RemoveRank(ProxiedPlayer player,String rank)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	user.data().remove(Node.builder("group." + rank).build());
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    }
}
