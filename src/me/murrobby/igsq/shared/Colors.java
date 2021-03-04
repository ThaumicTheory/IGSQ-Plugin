package me.murrobby.igsq.shared;

import net.md_5.bungee.api.ChatColor;

public enum Colors 
{
	ERROR(ChatColor.of("#FF0000"),ChatColor.DARK_RED),
	SOFT_ERROR(ChatColor.of("#FF6161"),ChatColor.RED),
	DISABLED(ChatColor.of("#C8C8C8"),ChatColor.GRAY),
	ENABLED(ChatColor.of("#00FF00"),ChatColor.GREEN),
	INSTRUCTION(ChatColor.of("#FFFF00"),ChatColor.YELLOW),
	ATTENTION(ChatColor.of("#FFB900"),ChatColor.GOLD),
	NEUTRAL(ChatColor.of("#00FFFF"),ChatColor.AQUA),
	PURPLE(ChatColor.of("#a900FF"),ChatColor.DARK_PURPLE);

	private ChatColor colour;
	private ChatColor legacyColour;
	Colors(ChatColor colour, ChatColor legacyColour) 
	{
		this.colour = colour;
		this.legacyColour = legacyColour;
	}
	public ChatColor getColour() 
	{
		return colour;
	}
	public ChatColor getLegacyColour() 
	{
		return legacyColour;
	}
	@Override
	public String toString() 
	{
		return getColour().toString();
	}
	
}
