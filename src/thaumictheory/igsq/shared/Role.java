package thaumictheory.igsq.shared;

import java.util.UUID;

public class Role{
	private final int roleID;
	private final YamlRoleWrapper yaml;
	public Role(int roleID) 
	{
		this.roleID = roleID;
		this.yaml = new YamlRoleWrapper(roleID);
	}
	
	public int getRoleID() 
	{
		return roleID;
	}
	public YamlRoleWrapper getYaml() 
	{
		return yaml;
	}
	public static Role getHighestRole(UUID uuid, RoleType type) 
	{
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(uuid);
		char[] roleBinary = Integer.toBinaryString(yaml.getRoles()).toCharArray();
		for(int i = 0; i < roleBinary.length;i++) 
		{
			if(roleBinary[i]== '0') continue;
			int roleID = roleBinary.length - i;
			
			if(YamlRoleWrapper.getRoleType(roleID).equals(type) || type.equals(RoleType.EITHER)) return new Role(roleID);
		}
		return new Role(0);
	}
	
	public static String getHighestChatPrefix(UUID uuid,RoleType type) {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(uuid);
		char[] roleBinary = Integer.toBinaryString(yaml.getRoles()).toCharArray();
		for(int i = 0; i < roleBinary.length;i++) 
		{
			if(roleBinary[i]== '0') continue;
			int roleID = roleBinary.length - i;
			
			if(YamlRoleWrapper.getRoleType(roleID).equals(type) || type.equals(RoleType.EITHER)) 
			{
				String chatPrefix = YamlRoleWrapper.getChatPrefix(roleID);
				if(chatPrefix.isEmpty()) continue;
				return chatPrefix;
			}
		}
		return YamlRoleWrapper.getChatPrefix(0);
	}

	public static String getHighestChatSuffix(UUID uuid,RoleType type) {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(uuid);
		char[] roleBinary = Integer.toBinaryString(yaml.getRoles()).toCharArray();
		for(int i = 0; i < roleBinary.length;i++) 
		{
			if(roleBinary[i]== '0') continue;
			int roleID = roleBinary.length - i;
			
			if(YamlRoleWrapper.getRoleType(roleID).equals(type) || type.equals(RoleType.EITHER)) 
			{
				String chatSuffix = YamlRoleWrapper.getChatSuffix(roleID);
				if(chatSuffix.isEmpty()) continue;
				return chatSuffix;
			}
		}
		return YamlRoleWrapper.getChatSuffix(0);
	}

	public static String getHighestTagPrefix(UUID uuid,RoleType type) {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(uuid);
		char[] roleBinary = Integer.toBinaryString(yaml.getRoles()).toCharArray();
		for(int i = 0; i < roleBinary.length;i++) 
		{
			if(roleBinary[i]== '0') continue;
			int roleID = roleBinary.length - i;
			
			if(YamlRoleWrapper.getRoleType(roleID).equals(type) || type.equals(RoleType.EITHER)) 
			{
				String tagPrefix = YamlRoleWrapper.getTagPrefix(roleID);
				if(tagPrefix.isEmpty()) continue;
				return tagPrefix;
			}
		}
		return YamlRoleWrapper.getTagPrefix(0);
	}

	public static String getHighestTagSuffix(UUID uuid,RoleType type) {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(uuid);
		char[] roleBinary = Integer.toBinaryString(yaml.getRoles()).toCharArray();
		for(int i = 0; i < roleBinary.length;i++) 
		{
			if(roleBinary[i]== '0') continue;
			int roleID = roleBinary.length - i;
			
			if(YamlRoleWrapper.getRoleType(roleID).equals(type) || type.equals(RoleType.EITHER)) 
			{
				String tagSuffix = YamlRoleWrapper.getTagSuffix(roleID);
				if(tagSuffix.isEmpty()) continue;
				return tagSuffix;
			}
		}
		return YamlRoleWrapper.getTagSuffix(0);
	}
	
}
