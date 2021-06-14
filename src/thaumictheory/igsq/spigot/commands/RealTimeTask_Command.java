package thaumictheory.igsq.spigot.commands;

import java.util.Calendar;
import org.bukkit.Bukkit;
import org.bukkit.World;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.YamlWorldWrapper;

public class RealTimeTask_Command
{	
	
	int realTimeTask = -1;
	final int taskID;
	
	public RealTimeTask_Command(int taskID) 
	{
		this.taskID = taskID;
		realTimeQuery();
	}
	private void realTimeQuery() 
	{
		realTimeTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				Boolean expireTask = true;
				for(World world : Bukkit.getServer().getWorlds()) 
				{
					YamlWorldWrapper yaml = new YamlWorldWrapper(world);
					if(yaml.isRealtime()) expireTask = false;
				}
				realTime();
				if(Main_Command.taskID != taskID || expireTask) 
				{
					Common.spigot.scheduler.cancelTask(realTimeTask);
					System.out.println("Task: \"RealTime Command\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 100);
	}
	private void realTime() 
	{
		Calendar calendar = Calendar.getInstance();
		int seconds = calendar.get(Calendar.SECOND);
		int minutes = calendar.get(Calendar.MINUTE);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		long totalSeconds = (long) ((double)seconds + ((double)minutes*60) + ((double)hours*3600));
		long mctime = (long)((double)totalSeconds/72*20);
		for(World world : Bukkit.getServer().getWorlds()) 
		{
			YamlWorldWrapper yaml = new YamlWorldWrapper(world);
			if(yaml.isRealtime()) world.setTime((mctime)-5000);
		}
	}
}
