package thaumictheory.igsq.spigot.lp;



import thaumictheory.igsq.spigot.Common;

public class CustomTag_LP
{	
	int nteTask = -1;
	final int taskID;
	
	public CustomTag_LP(int taskID) 
	{
		this.taskID = taskID;
		customTagQuery();
	}
	private void customTagQuery() 
	{
		nteTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				customTagUpdater();
				if(Main_LP.taskID != taskID) 
				{
					Common.spigot.scheduler.cancelTask(nteTask);
					System.out.println("Task: \"NametagEdit LuckPerms\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 100);
	}
	private void customTagUpdater()
	{
		Common_LP.tag();
		
	}
}
