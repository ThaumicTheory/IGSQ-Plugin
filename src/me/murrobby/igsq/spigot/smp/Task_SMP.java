package me.murrobby.igsq.spigot.smp;

import org.bukkit.Bukkit;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class Task_SMP {
	
	final int taskID;
	
	private int uiTask = -1;
	
	public Task_SMP(int taskID) 
	{
		this.taskID = taskID;
		uiQuery();
	}
	private void uiQuery() 
	{
		uiTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				ui();
				if(Main_SMP.taskID != taskID || !YamlWrapper.isSMP() || Bukkit.getOnlinePlayers().size() == 0)
				{
					Common.spigot.scheduler.cancelTask(uiTask);
					System.out.println("Task: \"Task SMP\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 1);
	}
	private void ui()
	{
		for(Player_SMP player : Player_SMP.getPlayers()) 
		{
			if(player.isUIAccessable()) 
			{
				player.getUI().display();
			}
			player.getAspect().run();
		}
	}
}
