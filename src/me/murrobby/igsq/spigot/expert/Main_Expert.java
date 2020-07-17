package me.murrobby.igsq.spigot.expert;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class Main_Expert 
{
	private static Main_Spigot plugin;
	public static int taskID = 0;
	public Main_Expert(Main_Spigot plugin)
	{
		Main_Expert.plugin = plugin;
		//Events run forever and cannot be turned off they must induvidualy have checks for expert mode
		new CreatureSpawnEvent_Expert(plugin);
		new EntityAirChangeEvent_Expert(plugin);
		new EntityDamageByEntityEvent_Expert(plugin);
		new EntityDamageEvent_Expert(plugin);
		new EntityTargetEvent_Expert(plugin);
		new PlayerBedEnterEvent_Expert(plugin);
		new SlimeSplitEvent_Expert(plugin);
		new ThunderChangeEvent_Expert(plugin);
		//Tasks
		Start_Expert();
	}
	public static void Start_Expert() //Tasks will close if expert is turned off therefor they will need to be rerun for enabling expert
	{
		if(Common_Spigot.ExpertCheck())
		{
			taskID++;
			new BloodMoon_Expert(plugin,taskID);
			new EnderDragon_Expert(plugin,taskID);
		}
	}
}
