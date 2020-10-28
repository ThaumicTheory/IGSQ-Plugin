package me.murrobby.igsq.spigot.event;

import java.util.Random;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;

public class LobbyCreateEvent extends Event implements Cancellable{

	private static Random random = new Random();
	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private int mapID;
	public LobbyCreateEvent(int mapID) 
	{
		this.mapID = mapID;
	}
	public LobbyCreateEvent() 
	{
		 mapID = random.nextInt(Common_BlockHunt.getMapCount())+1;
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
		return mapID;
	}
	public void setMap(int mapID) {
		this.mapID = mapID;
	}
	
	
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
