package me.murrobby.igsq.spigot.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;

public class GameStartEvent extends Event implements Cancellable{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private final int MAP_ID;
	public GameStartEvent() 
	{
		MAP_ID = Common_BlockHunt.mapID;
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
	
	
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
