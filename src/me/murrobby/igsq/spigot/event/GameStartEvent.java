package me.murrobby.igsq.spigot.event;

import java.util.Random;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.murrobby.igsq.spigot.blockhunt.Common_BlockHunt;

public class GameStartEvent extends Event implements Cancellable{

	private static Random random = new Random();
	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private final int MAP_ID;
	public GameStartEvent(int mapID) 
	{
		MAP_ID = mapID;
	}
	public GameStartEvent() 
	{
		MAP_ID = random.nextInt(Common_BlockHunt.getMapCount())+1;
	}
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
		
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	public int getMap() 
	{
		return MAP_ID;
	}

}
