package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.entity.Player;

public class GenericSeeker_BlockHunt extends GenericPlayer_BlockHunt
{

	public GenericSeeker_BlockHunt(Player player) 
	{
		super(player);
	}
	public GenericSeeker_BlockHunt(GenericPlayer_BlockHunt player) 
	{
		super(player.getPlayer());
		setHotbar(player.getHotbar());
		setDead(player.isDead());
		setOutOfBoundsTime(player.getOutOfBoundsTime());
	}
}
