package thaumictheory.igsq.spigot.expert;

import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class Main_Expert 
{
	public static int taskID = 0;
	public Main_Expert()
	{
		//Events run forever and cannot be turned off they must induvidualy have checks for expert mode
		new CreatureSpawnEvent_Expert();
		new EntityAirChangeEvent_Expert();
		new EntityDamageByEntityEvent_Expert();
		new EntityDamageEvent_Expert();
		new EntityTargetEvent_Expert();
		new PlayerBedEnterEvent_Expert();
		new SlimeSplitEvent_Expert();
		new PlayerJoinEvent_Expert();
		
		
		//Tasks
		startExpert();
	}
	public static void startExpert() //Tasks will close if expert is turned off therefor they will need to be rerun for enabling expert
	{
		if(YamlWrapper.isExpert())
		{
			refreshExpert();
			
		}
	}
	public static void refreshExpert() //Tasks will close if expert is turned off therefor they will need to be rerun for enabling expert
	{
		if(YamlWrapper.isExpert())
		{
			taskID++;
			new BloodMoon_Expert(taskID);
			new EnderDragon_Expert(taskID);
		}
	}
}
