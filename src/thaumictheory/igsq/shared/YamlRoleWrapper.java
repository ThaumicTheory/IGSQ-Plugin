package thaumictheory.igsq.shared;

public class YamlRoleWrapper 
{
	private int roleID;
	public YamlRoleWrapper(int roleID) 
	{
		this.roleID = roleID;
	}
	public String getRoleNode() 
	{
		return getRoleNode(roleID);
	}
	public void setRoleNode(String node) 
	{
		setRoleNode(roleID,node);
	}
	
	public String getChatPrefix() 
	{
		return getChatPrefix(roleID);
	}
	public void setChatPrefix(String prefix) 
	{
		setChatPrefix(roleID,prefix);
	}
	
	public String getChatSuffix() 
	{
		return getChatSuffix(roleID);
	}
	public void setChatSuffix(String suffix) 
	{
		setChatSuffix(roleID,suffix);
	}
	
	
	public String getTagPrefix() 
	{
		return getTagPrefix(roleID);
	}
	public void setTagPrefix(String prefix) 
	{
		setTagPrefix(roleID,prefix);
	}
	
	public String getTagSuffix() 
	{
		return getTagSuffix(roleID);
	}
	public void setTagSuffix(String suffix) 
	{
		setTagSuffix(roleID,suffix);
	}
	
	public RoleType getRoleType() 
	{
		return getRoleType(roleID);
	}
	public void setRoleType(RoleType type) 
	{
		setRoleType(roleID,type);
	}
	
	public void applyDefault() 
	{
		applyDefault(roleID);
	}
	
	public static String getRoleNode(int roleID) 
	{
		return (String) IGSQ.getYaml().getField(roleID + ".permissionnode", "role.yaml");
	}
	public static void setRoleNode(int roleID,String node) 
	{
		IGSQ.getYaml().setField(roleID + ".permissionnode", "role.yaml",node);
	}
	
	public static String getChatPrefix(int roleID) 
	{
		return (String) IGSQ.getYaml().getField(roleID + ".chat.prefix", "role.yaml");
	}
	public static void setChatPrefix(int roleID,String prefix) 
	{
		IGSQ.getYaml().setField(roleID + ".chat.prefix", "role.yaml",prefix);
	}
	
	public static String getChatSuffix(int roleID) 
	{
		return (String) IGSQ.getYaml().getField(roleID + ".chat.suffix", "role.yaml");
	}
	public static void setChatSuffix(int roleID,String suffix) 
	{
		IGSQ.getYaml().setField(roleID + ".chat.suffix", "role.yaml",suffix);
	}
	
	public static String getTagPrefix(int roleID) 
	{
		return (String) IGSQ.getYaml().getField(roleID + ".tag.prefix", "role.yaml");
	}
	public static void setTagPrefix(int roleID,String prefix) 
	{
		IGSQ.getYaml().setField(roleID + ".tag.prefix", "role.yaml",prefix);
	}
	
	public static String getTagSuffix(int roleID) 
	{
		return (String) IGSQ.getYaml().getField(roleID + ".tag.suffix", "role.yaml");
	}
	public static void setTagSuffix(int roleID,String suffix) 
	{
		IGSQ.getYaml().setField(roleID + ".tag.suffix", "role.yaml",suffix);
	}
	
	public static RoleType getRoleType(int roleID) 
	{
		String value = (String) IGSQ.getYaml().getField(roleID + ".type", "role.yaml");
		if(value == null || value.equals(RoleType.PRIMARY.toString())) return RoleType.PRIMARY;
		else if(value.equals(RoleType.SECONDARY.toString())) return RoleType.SECONDARY;
		else return RoleType.EITHER;
	}
	public static void setRoleType(int roleID,RoleType type) 
	{
		IGSQ.getYaml().setField(roleID + ".type", "role.yaml",type.toString().toUpperCase());
	}
	
    public static void applyDefault(int roleID)
    {
    	if(roleID == 0) IGSQ.getYaml().defaultField(roleID + ".permissionnode", "role.yaml","group.default");
    	else IGSQ.getYaml().defaultField(roleID + ".permissionnode",  "role.yaml","group.role" + roleID);
    	IGSQ.getYaml().defaultField(roleID + ".type","role.yaml","PRIMARY");
    	IGSQ.getYaml().defaultField(roleID + ".chat.prefix", "role.yaml","");
    	IGSQ.getYaml().defaultField(roleID + ".chat.suffix", "role.yaml","");
    	IGSQ.getYaml().defaultField(roleID + ".tag.prefix", "role.yaml","");
    	IGSQ.getYaml().defaultField(roleID + ".tag.suffix", "role.yaml","");
    }	
}
