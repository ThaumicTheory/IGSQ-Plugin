package thaumictheory.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlWrapper;

public class LobbyCreateEvent_BlockHunt implements Listener
{
	public LobbyCreateEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void LobbyCreate_BlockHunt(thaumictheory.igsq.spigot.event.LobbyCreateEvent event) 
	{
		if(!event.isCancelled()) 
		{
			event.getGame().setStage(Stage.IN_LOBBY);
			event.getGame().setTimer(YamlWrapper.getBlockHuntLobbyTime());
			if(Game_BlockHunt.getGameInstanceCount() == 1) Main_BlockHunt.startBlockHunt();
		}
		else 
		{
			event.getGame().delete();
		}
	}
	
}
