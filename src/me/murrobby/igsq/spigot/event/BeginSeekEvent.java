package me.murrobby.igsq.spigot.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;

public class BeginSeekEvent extends Event implements Cancellable{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private final int MAP_ID;
    private final Player[] SEEKERS;
	public BeginSeekEvent() 
	{
		MAP_ID = Common_BlockHunt.mapID;
		SEEKERS = Common_BlockHunt.seekers;
	}
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
		
	}

	public int getMap() 
	{
		return MAP_ID;
	}
	
	public Player[] getSeekers() 
	{
		return SEEKERS;
	}
	
	
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
