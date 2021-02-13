package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class UITask_Expert {
	
	final int taskID;
	
	private int uiTask = -1;
	
	public UITask_Expert(int taskID) 
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
				if(Main_Expert.taskID != taskID || !YamlWrapper.isExpert() || Bukkit.getOnlinePlayers().size() == 0)
				{
					Common.spigot.scheduler.cancelTask(uiTask);
					System.out.println("Task: \"UI Expert\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 5);
	}
	private void ui()
	{
		for(UI_Expert ui : UI_Expert.getUIs()) ui.display();
	}
}
