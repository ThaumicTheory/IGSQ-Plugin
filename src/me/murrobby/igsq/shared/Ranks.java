package me.murrobby.igsq.shared;

public enum Ranks 
{
	NONE(0,"","",""),
	DEFAULT(1,"group.default","&#006e99[&#00b7ffSquirrel&#006e99] &#00b7ff","&3[&bS&3] &b"),
	RISING(2,"group.rising","&#004599[&#0074ffRising&#004599] &#0074ff","&3[&9R&3] &9"),
	FLYING(3,"group.flying","&#b30000[&#ff2525Flying&#b30000] &#ff2525","&3[&6F&3] &6"),
	SOARING(4,"group.soaring","&#408000[&#77ec00Soaring&#408000] &#77ec00","&3[&bS&3] &b"),
	EPIC(5,"group.epic","&#001a11[&#005a3aEpic I&#001a11] &#005a3a","&3[&aEp&11&3] &a"),
	EPIC2(6,"group.epic2","&#003320[&#00a368Epic II&#003320] &#00a368","&3[&aEp&92&3] &a"),
	EPIC3(7,"group.epic3","&#008055[&#00f19fEpic III&#008055] &#00f19f","&3[&aEp&b3&3] &a"),
	ELITE(8,"group.elite","&#49357e[&#7e65beElite I&#49357e] &#7e65be","&3[&dEl&11&3] &d"),
	ELITE2(9,"group.elite2","&#5e2bd4[&#a183e6Elite II&#5e2bd4] &#a183e6","&3[&dEl&92&3] &d"),
	ELITE3(10,"group.elite3","&#884dff[&#cbb1ffElite III&#884dff] &#cbb1ff","&3[&dEl&b3&3] &d"),
	CELESTIAL(11,"group.celestial","&#00cc9c[&#5cffdaCelestial&#00cc9c] &#5cffda","&b[&fC&b] &b"),
	
	MOD(12,"group.mod","&#a25710[&#eb913aHelpful&#a25710] &#eb913a","&e[&6H&e] &6"),
	MOD2(13,"group.mod2","&#997d00[&#ffd100Staff&#997d00] &#ffd100","&6[&eS&6] &e"),
	MOD3(14,"group.mod3","&#999600[&#fffa00Senior Staff&#999600] &#fffa00","&6[&dS&eS&6] &e"),
	COUNCIL(15,"group.council","&#99003d[&#ff0065Council&#99003d] &#ff0065","&5[&dC&5] &d");
	
	private String permission;
	private String tag;
	private String name;
	private int position;

	Ranks(int position,String permission,String name, String tag) 
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
