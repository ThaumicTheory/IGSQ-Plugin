package thaumictheory.igsq.spigot.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import thaumictheory.igsq.spigot.blockhunt.EndReason;
import thaumictheory.igsq.spigot.blockhunt.Game_BlockHunt;

public class GameEndEvent extends Event implements Cancellable{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private EndReason endReason;
	private Game_BlockHunt gameInstance;
    
	public GameEndEvent(Game_BlockHunt gameInstance, EndReason endReason) 
	{
		this.gameInstance = gameInstance;
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
