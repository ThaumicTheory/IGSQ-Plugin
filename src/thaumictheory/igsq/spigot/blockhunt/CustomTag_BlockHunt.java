package thaumictheory.igsq.spigot.blockhunt;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class CustomTag_BlockHunt
{	
	int nteTask = -1;
	final int taskID;
	
	public CustomTag_BlockHunt(int taskID) 
	{
		this.taskID = taskID;
		customTagUpdaterQuery();
	}
	private void customTagUpdaterQuery() 
	{
		nteTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				customTagUpdater();
				if(Main_BlockHunt.taskID != taskID || (!YamlWrapper.isBlockHunt()) || Game_BlockHunt.getGameInstances().length == 0) 
				{
					Common.spigot.scheduler.cancelTask(nteTask);
					System.out.println("Task: \"CustomTag BlockHunt\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 100);
	}
	private void customTagUpdater()
	{
		Common_BlockHunt.tag();
	}
}
