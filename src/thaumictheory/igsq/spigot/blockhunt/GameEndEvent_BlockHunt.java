package thaumictheory.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class GameEndEvent_BlockHunt implements Listener
{
	
	public GameEndEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void GameEnd_BlockHunt(thaumictheory.igsq.spigot.event.GameEndEvent event) 
	{
		if(!event.isCancelled()) 
		{
			if(event.getReason().equals(EndReason.FORCED) || event.getReason().equals(EndReason.NOT_SPECIFIED)) 
			{
				for(Player_BlockHunt player : event.getGame().getPlayers()) 
				{
					player.delete();
				}
				event.getGame().delete();
			}
			else 
			{
				event.getGame().setStage(Stage.GAME_END);
				event.getGame().setTimer(0);
				for(Player_BlockHunt player : event.getGame().getPlayers()) 
				{
					player.outOfGame();
					if(player.isHider()) 
					{
						if((event.getReason().equals(EndReason.TIME_UP) || event.getReason().equals(EndReason.SEEKERS_DEAD)))
						{
							player.getSoundSystem().playWin();
							player.setWinner(true);
						}
						else 
						{
							player.getSoundSystem().playLoss();
							player.setWinner(false);
						}
					}
					else if(player.isSeeker())
					{
						if((event.getReason().equals(EndReason.HIDERS_DEAD)))
						{
							player.getSoundSystem().playWin();
							player.setWinner(true);
						}
						else 
						{
							player.getSoundSystem().playLoss();
							player.setWinner(false);
						}
					}
				}
				
				
				
				Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
		    	{

					@Override
					public void run() 
					{
						event.getGame().leaveItem();
						event.getGame().nextGameItem();
					}
		    	},360);
		    	
			}
		}
		else 
		{
			if(event.getReason().equals(EndReason.TIME_UP)) event.getGame().setTimer(YamlWrapper.getBlockHuntGameTime());
		}
	}
}
