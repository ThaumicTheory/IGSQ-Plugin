package me.murrobby.igsq.spigot.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.murrobby.igsq.spigot.blockhunt.EndReason;

public class GameEndEvent extends Event implements Cancellable{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private EndReason endReason;
    
	public GameEndEvent(EndReason endReason) 
	{
		this.endReason = endReason;
	}
	
	public GameEndEvent() 
	{
		this.endReason = EndReason.NOT_SPECIFIED;
	}
    
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}
	public EndReason getReason() 
	{
		return endReason;
		
	}
	

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

}
