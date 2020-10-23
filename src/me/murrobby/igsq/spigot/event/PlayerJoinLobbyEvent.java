package me.murrobby.igsq.spigot.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinLobbyEvent extends Event implements Cancellable{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private Player player;
    public PlayerJoinLobbyEvent(Player player) 
    {
    	this.player = player;
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
	
	public Player getPlayer() 
	{
		return player;
	}

}
