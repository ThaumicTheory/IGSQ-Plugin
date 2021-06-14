package thaumictheory.igsq.shared;

public enum SubRanks 
{
	NONE(0,"","",""),
	SUPPORTER(1,"group.supporter","&#ff3358 [&#ff899fS&#ff3358]","&d [&cS&d]"),
	NITROBOOST(2,"group.nitroboost","&#ff1af0 [&#ff61f4N&#ff1af0]","&5 [&dN&5]"),
	BIRTHDAY(3,"group.birthday","&#ff8080 [&#ffcbcbB&#ff8080]","&d [&fB&d]"),
	FOUNDER(4,"group.founder","&#80ffe8 [&#bdfff3F&#80ffe8]","&3 [&bF&3]"),
	DEVELOPER(5,"group.developer","&#68c867 [&#67fd67D&#68c867]","&2 [&aD&2]");

	private String permission;
	private String tag;
	private String name;
	private int position;

	SubRanks(int position,String permission,String name, String tag) 
	{
		this.position = position;
		this.permission = permission;
		this.name = name;
		this.tag = tag;
		
	}

	public int getPosition()
	{
		return position;
	}
	
	public String getPermission()
	{
		return permission;
	}
	public String getName()
	{
		return name;
	}

	public String getTag()
	{
		return tag;
	}
	public static SubRanks getRank(String permission)
	{
		for(SubRanks rank : values()) 
		{
			if(rank.getPermission().equals(permission)) return rank;
		}
		return null;
	}
	public static SubRanks getRank(int position)
	{
		for(SubRanks rank : values()) 
		{
			if(rank.getPosition() == position) return rank;
		}
		return null;
	}
}
