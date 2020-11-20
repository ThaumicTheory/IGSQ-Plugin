package me.murrobby.igsq.spigot.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.murrobby.igsq.spigot.blockhunt.Game_BlockHunt;
import me.murrobby.igsq.spigot.blockhunt.Player_BlockHunt;

public class PlayerJoinLobbyEvent extends Event implements Cancellable{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private Player_BlockHunt player;
	private Game_BlockHunt gameInstance;
    public PlayerJoinLobbyEvent(Game_BlockHunt gameInstance,Player player) 
    {
    	this.gameInstance = gameInstance;
    	gameInstance.addPlayer(player);
    	this.player = gameInstance.getPlayer(player);
    }
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
		
	}
	
	public Player_BlockHunt getPlayer() 
	{
		return player;
	}
	
	public Game_BlockHunt getGame() 
	{
		return gameInstance;
	}

	
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
