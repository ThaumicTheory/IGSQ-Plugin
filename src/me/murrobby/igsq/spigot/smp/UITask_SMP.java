package me.murrobby.igsq.spigot.smp;

import org.bukkit.Bukkit;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class UITask_SMP {
	
	final int taskID;
	
	private int uiTask = -1;
	
	public UITask_SMP(int taskID) 
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
					System.out.println("Task: \"UI SMP\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 5);
	}
	private void ui()
	{
		for(UI_SMP ui : UI_SMP.getUIs()) ui.display();
	}
}
