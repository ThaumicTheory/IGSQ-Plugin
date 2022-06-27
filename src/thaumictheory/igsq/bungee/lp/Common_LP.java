package thaumictheory.igsq.bungee.lp;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import thaumictheory.igsq.bungee.Communication;
import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlRoleWrapper;

public class Common_LP {

	private final static LuckPerms luckPerms = LuckPermsProvider.get();
	/**
     * sets the Players roles
     * @apiNote Sets the Groups the user is in to the ranks specified.
     * @see net.luckperms.api.node.Node
     */
    public static void updateRoles(ProxiedPlayer player,int ranks) 
    {
    	User user = luckPerms.getUserManager().getUser(player.getUniqueId());
    	char[] roleBinary = Integer.toBinaryString(ranks).toCharArray();
    	user.data().add(Node.builder(YamlRoleWrapper.getRoleNode(0)).build()); //default role
    	for(int i = roleBinary.length-1; 0 <= i;i--) 
    	{
    		int roleID = roleBinary.length - i;
        	if(roleBinary[i] == '1') user.data().add(Node.builder(YamlRoleWrapper.getRoleNode(roleID)).build());
        	else user.data().remove(Node.builder(YamlRoleWrapper.getRoleNode(roleID)).build());
    	}
    	luckPerms.getUserManager().saveUser(user);
    	luckPerms.getMessagingService().get().pushUserUpdate(user);
    }
    public static void updateYaml(ProxiedPlayer player) 
    {
    	int i = 0;
    	while(IGSQ.getYaml().getField(String.valueOf(i), "role.yaml") != null) 
    	{
    		Communication.sendTargetedConfigUpdate(i + ".permissionnode", "role.yaml", (String) IGSQ.getYaml().getField(i + ".permissionnode","role.yaml"), player);
    		Communication.sendTargetedConfigUpdate(i + ".chat.prefix", "role.yaml", (String) IGSQ.getYaml().getField(i + ".chat.prefix","role.yaml"), player);
    		Communication.sendTargetedConfigUpdate(i + ".chat.suffix", "role.yaml", (String) IGSQ.getYaml().getField(i + ".chat.suffix","role.yaml"), player);
    		Communication.sendTargetedConfigUpdate(i + ".tag.prefix", "role.yaml", (String) IGSQ.getYaml().getField(i + ".tag.prefix","role.yaml"), player);
    		Communication.sendTargetedConfigUpdate(i + ".tag.suffix", "role.yaml", (String) IGSQ.getYaml().getField(i + ".tag.suffix","role.yaml"), player);
    		i++;
    	}
    }
}
