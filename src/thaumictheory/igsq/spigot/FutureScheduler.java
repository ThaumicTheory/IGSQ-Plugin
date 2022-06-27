package thaumictheory.igsq.spigot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.spigot.smp.Team_SMP;

public class FutureScheduler 
{
	private LocalDateTime scheduledEventTime = LocalDateTime.MAX;
	private boolean update = false;
	private int futureTask = -1;

	
	public void runSchedule()
	{
		scheduledEventTime = LocalDateTime.MAX;
		
		runExpirableAlliesSchedule();
	}
	public void readSchedule()
	{
		update = false;
		scheduledEventTime = checkForCloserEvent(getExpirableAllies());
		
	}
	
	
	
	
	
	public void updateSchedule()
	{
		if(!update) return;
		update = false;
		if(futureTask != -1) Common.spigot.scheduler.cancelTask(futureTask);
		futureTask = Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				runSchedule();
				readSchedule();
				updateSchedule();
			} 		
    	},Duration.between(LocalDateTime.now(), scheduledEventTime).abs().get(ChronoUnit.MILLIS)/50);
	}

	public static void applyDefault() 
    {
		IGSQ.getYaml().defaultField("smp.ally", "future", "");
    }
	
	private String sort(String data) 
	{
		String[] sort = data.split(" ");
		for(int i = 0; i < sort.length-1; i++) 
		{
			for(int j = 0; j < sort.length-1; j++)
			{
				LocalDateTime time = IGSQ.getTime(sort[j].split(":")[0]);
				LocalDateTime time2 = IGSQ.getTime(sort[j+1].split(":")[0]);
				if (time.isAfter(time2)) 
                { 
					 String temp = sort[j]; 
	                 sort[j] = sort[j+1]; 
	                 sort[j+1] = temp; 
                }
			}
		}
		return String.join(" ", sort);
	}
	private LocalDateTime checkForCloserEvent(String data) 
	{
		if(data == null || data.isBlank()) return scheduledEventTime;
		LocalDateTime time = IGSQ.getTime(data.split(":")[0]);
		if(time.isAfter(scheduledEventTime) || time.isEqual(scheduledEventTime)) return scheduledEventTime;
		update = true;
		return time;
	}
	
	
	
	
	
	
	
	public void addExpirableAlly(Team_SMP ally1,Team_SMP ally2,String time) 
	{
		if(ally1 == null || ally2 == null) return;
		if(ally1.equals(ally2)) return;
		String data = IGSQ.getTimeFuture(time) + ":" + ally1.getUID().toString()+ ":" + ally2.getUID();
		if(getExpirableAllies() == null || getExpirableAllies().isBlank()) IGSQ.getYaml().setField("smp.ally", "future", data);
		else setExpirableAllies(getExpirableAllies() + " " + data);
		
	}
	private void setExpirableAllies(String data) 
	{
		IGSQ.getYaml().setField("smp.ally", "future.yaml", sort(data));
		readSchedule();
	}
	private String getExpirableAllies() 
	{
		return (String) IGSQ.getYaml().getField("smp.ally", "future.yaml");
	}
	
	private void runExpirableAlliesSchedule() 
	{
		for(String scheduleItem : getExpirableAllies().split(" ")) 
		{
			if(Long.valueOf(scheduleItem.split(":")[0]) <= System.currentTimeMillis()) 
			{
				Team_SMP ally1 = Team_SMP.getTeamFromID(UUID.fromString(scheduleItem.split(":")[1]));
				Team_SMP ally2 = Team_SMP.getTeamFromID(UUID.fromString(scheduleItem.split(":")[2]));
				ally1.removeAlly(ally2);
			}
			else return;
		}
	}
	
}
