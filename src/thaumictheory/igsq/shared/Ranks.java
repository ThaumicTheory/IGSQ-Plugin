package thaumictheory.igsq.shared;

import org.bukkit.ChatColor;

public enum Ranks 
{
	NONE(0,"","","",ChatColor.WHITE),
	DEFAULT(1,"group.default","&#006e99[&#00b7ffSquirrel&#006e99] &#00b7ff","&3[&bS&3] &b",ChatColor.AQUA),
	RISING(2,"group.rising","&#004599[&#0074ffRising&#004599] &#0074ff","&3[&9R&3] &9",ChatColor.BLUE),
	FLYING(3,"group.flying","&#b30000[&#ff2525Flying&#b30000] &#ff2525","&3[&6F&3] &6",ChatColor.RED),
	SOARING(4,"group.soaring","&#408000[&#77ec00Soaring&#408000] &#77ec00","&3[&bS&3] &b",ChatColor.GREEN),
	EPIC(5,"group.epic","&#001a11[&#005a3aEpic I&#001a11] &#005a3a","&3[&aEp&11&3] &a",ChatColor.DARK_GREEN),
	EPIC2(6,"group.epic2","&#003320[&#00a368Epic II&#003320] &#00a368","&3[&aEp&92&3] &a",ChatColor.DARK_GREEN),
	EPIC3(7,"group.epic3","&#008055[&#00f19fEpic III&#008055] &#00f19f","&3[&aEp&b3&3] &a",ChatColor.DARK_GREEN),
	ELITE(8,"group.elite","&#49357e[&#7e65beElite I&#49357e] &#7e65be","&3[&dEl&11&3] &d",ChatColor.LIGHT_PURPLE),
	ELITE2(9,"group.elite2","&#5e2bd4[&#a183e6Elite II&#5e2bd4] &#a183e6","&3[&dEl&92&3] &d",ChatColor.LIGHT_PURPLE),
	ELITE3(10,"group.elite3","&#884dff[&#cbb1ffElite III&#884dff] &#cbb1ff","&3[&dEl&b3&3] &d",ChatColor.LIGHT_PURPLE),
	CELESTIAL(11,"group.celestial","&#00cc9c[&#5cffdaCelestial&#00cc9c] &#5cffda","&b[&fC&b] &b",ChatColor.WHITE),
	
	MOD(12,"group.mod","&#a25710[&#eb913aHelpful&#a25710] &#eb913a","&e[&6H&e] &6",ChatColor.GOLD),
	MOD2(13,"group.mod2","&#997d00[&#ffd100Staff&#997d00] &#ffd100","&6[&eS&6] &e",ChatColor.YELLOW),
	MOD3(14,"group.mod3","&#999600[&#fffa00Senior Staff&#999600] &#fffa00","&6[&dS&eS&6] &e",ChatColor.YELLOW),
	COUNCIL(15,"group.council","&#99003d[&#ff0065Council&#99003d] &#ff0065","&5[&dC&5] &d",ChatColor.LIGHT_PURPLE);
	
	private String permission;
	private String tag;
	private String name;
	private int position;
	private ChatColor color;

	Ranks(int position,String permission,String name, String tag,ChatColor color) 
	{
		this.position = position;
		this.permission = permission;
		this.name = name;
		this.tag = tag;
		this.color = color;
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
	public ChatColor getColor() 
	{
		return color;
	}
	public static Ranks getRank(String permission)
	{
		for(Ranks rank : values()) 
		{
			if(rank.getPermission().equals(permission)) return rank;
		}
		return null;
	}
	public static Ranks getRank(int position)
	{
		for(Ranks rank : values()) 
		{
			if(rank.getPosition() == position) return rank;
		}
		return null;
	}
}
