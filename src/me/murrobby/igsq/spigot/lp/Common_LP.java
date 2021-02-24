package me.murrobby.igsq.spigot.lp;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.events.PacketContainer;

import me.murrobby.igsq.shared.Ranks;
import me.murrobby.igsq.shared.SubRanks;
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
	
	public static final Pattern COLOR_PATTERN = Pattern.compile("/(&[0-9a-f])/i");
	
	private static CachedMetaData metaData;
	private static QueryOptions queryOptions;
	
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
    public static Ranks getRank(Player player)
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	getUserData(user);
    	if(Ranks.getRank("group." + metaData.getPrimaryGroup()) == null) return Ranks.getRank(1);
    	return Ranks.getRank("group." + metaData.getPrimaryGroup());
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
				Ranks rank = getRank(player);
				SubRanks subRank = getSubRank(player);
				Communication.setTag(player, rank.getTag(),rank.getColor() ,name,subRank.getTag());
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
			Ranks rank = getRank(player);
			SubRanks subRank = getSubRank(player);
			return Communication.setTagAsPacket(player, rank.getTag(),rank.getColor() ,name,subRank.getTag());
		}
		return null;
    }
    private static SubRanks getSubRank(Player player) 
	{
    	int highestRank = 0;
		for(SubRanks rank : SubRanks.values()) if(player.hasPermission(rank.getPermission()) && highestRank < rank.getPosition()) highestRank = rank.getPosition();
		return SubRanks.getRank(highestRank);
	}
}
