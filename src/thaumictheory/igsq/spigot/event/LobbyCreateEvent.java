package thaumictheory.igsq.spigot.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import thaumictheory.igsq.spigot.blockhunt.Game_BlockHunt;

public class LobbyCreateEvent extends Event implements Cancellable{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
	private Game_BlockHunt gameInstance;
	public LobbyCreateEvent(Game_BlockHunt gameInstance) 
	{
		this.gameInstance = gameInstance;
	}
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
		
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
