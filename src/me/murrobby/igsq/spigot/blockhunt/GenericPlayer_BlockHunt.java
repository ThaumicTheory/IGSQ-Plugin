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
	
	public int inventoryAssist() 
	{
		int slot = getHotbar();
		int direction = 0;
		if(slot != getPlayer().getInventory().getHeldItemSlot())
		{
			if(slot == 8 && getPlayer().getInventory().getHeldItemSlot() == 0) direction = 1;
			else if(slot == 0 && getPlayer().getInventory().getHeldItemSlot() == 8) direction = -1;
			else if(slot > getPlayer().getInventory().getHeldItemSlot()) direction = -1;
			else if(slot < getPlayer().getInventory().getHeldItemSlot()) direction = 1;
			if(direction != 0) 
			{
				slot+=direction;
				int attempts = 0;
				while(true) 
				{
					if(slot> 8) slot = 0;
					else if(slot < 0) slot = 8;
					if(getPlayer().getInventory().getItem(slot) != null) 
					{
						setHotbar(slot);
						return slot;
					}
					else
					{
						attempts++;
						slot+=direction;
						if(attempts > 8) //Nothing in inventory 
						{
							setHotbar(player.getInventory().getHeldItemSlot());
							return -1;
						}
					}
				}
			}
		}
		setHotbar(player.getInventory().getHeldItemSlot());
		return -1; //Inventory has not Moved
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
