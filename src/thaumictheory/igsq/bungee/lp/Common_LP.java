package thaumictheory.igsq.bungee.lp;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.query.QueryOptions;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import thaumictheory.igsq.shared.Ranks;
import thaumictheory.igsq.shared.SubRanks;

public class Common_LP {

	private final static LuckPerms luckPerms = LuckPermsProvider.get();
	private final static ContextManager contextManager = luckPerms.getContextManager();
	
	private static CachedMetaData metaData;
	private static QueryOptions queryOptions;
	public Common_LP() 
	{
	}
	private static void getUserData(User user) 
	{
	    queryOptions = contextManager.getQueryOptions(user).orElse(contextManager.getStaticQueryOptions());
		metaData = user.getCachedData().getMetaData(queryOptions);
	}
	public static String getPrefix(ProxiedPlayer player) 
	{
		User user = luckPerms.getUserManager().getUser(player.getUniqueId());
		getUserData(user);
		return metaData.getPrefix();
	}
	public static String getSuffix(ProxiedPlayer player) 
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
    public static Ranks getRank(ProxiedPlayer player)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	getUserData(user);
    	if(Ranks.getRank("group." + metaData.getPrimaryGroup()) == null) return Ranks.getRank(1);
    	return Ranks.getRank("group." + metaData.getPrimaryGroup());
    }
    /**
     * sets the Players highest rank.
     * @apiNote Sets the Primary Group the user is in to the rank specified.
     * @see net.luckperms.api.node.Node
     */
    public static void setRank(ProxiedPlayer player,Ranks newRank,Ranks oldRank)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	user.data().add(Node.builder(newRank.getPermission()).build());
    	if(!oldRank.equals(Ranks.NONE)) user.data().remove(Node.builder(oldRank.getPermission()).build());
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    	
    	
    }
    /**
     * adds the rank to the designated Player
     * @apiNote Sets the Primary Group the user is in to the rank specified.
     * @see net.luckperms.api.node.Node
     */
    public static void giveRank(ProxiedPlayer player,SubRanks rank)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	user.data().add(Node.builder(rank.getPermission()).build());
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    }
    
    /**
     * removes the rank to the designated Player
     * @apiNote Sets the Primary Group the user is in to the rank specified.
     * @see net.luckperms.api.node.Node
     */
    public static void removeRank(ProxiedPlayer player,SubRanks rank)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	user.data().remove(Node.builder(rank.getPermission()).build());
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    }
    /**
     * removes the rank to the designated Player
     * @apiNote Sets the Primary Group the user is in to the rank specified.
     * @see net.luckperms.api.node.Node
     */
    public static void removeRank(ProxiedPlayer player,Ranks rank)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	user.data().remove(Node.builder(rank.getPermission()).build());
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    }
}
