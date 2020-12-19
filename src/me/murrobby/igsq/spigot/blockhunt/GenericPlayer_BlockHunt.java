package me.murrobby.igsq.spigot.blockhunt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.Common;
public class GenericPlayer_BlockHunt
{
	private Player player;
	private int hotbar = 0;
	private boolean dead;
	private int outOfBoundsTime;
	
	public GenericPlayer_BlockHunt(Player player) 
	{
		this.player = player;
	}
	public void showPlayer() 
	{
		for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
		{
			if(selectedPlayer != player) 
			{
				selectedPlayer.showPlayer(Common.spigot, player);
			}
		}
	}
	public void hidePlayer() 
	{
		for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
		{
			if(selectedPlayer != player) 
			{
				selectedPlayer.hidePlayer(Common.spigot, player);
			}
		}
	}
	//Getters
	public int getHotbar() 
	{
		return hotbar;
	}
	public int getOutOfBoundsTime() 
	{
		return outOfBoundsTime;
	}
	public Player getPlayer()
	{
		return player;
	}
	//Setters
	public void setHotbar(int hotbar) 
	{
		this.hotbar = hotbar;
	}
    public void setDead(boolean dead) 
    {
    	this.dead = dead;
    }
	public void setOutOfBoundsTime(int time) 
	{
		this.outOfBoundsTime = time;
	}
	//issers
    public Boolean isDead() 
    {
    	return dead;
    }
}
