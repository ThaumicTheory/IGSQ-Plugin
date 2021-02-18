package me.murrobby.igsq.spigot.expert;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.murrobby.igsq.spigot.YamlWrapper;
import me.murrobby.igsq.spigot.expert.protection.BlockDamageEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.BlockPlaceEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.EntityDamageByEntityEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.EntityPickupItemEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.PlayerArmorStandManipulateEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.PlayerDropItemEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.PlayerHarvestBlockEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.PlayerInteractEntityEvent_Protection;
import me.murrobby.igsq.spigot.expert.protection.PlayerInteractEvent_Protection;

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
		new EntityExplodeEvent_Expert();
		new PlayerJoinEvent_Expert();
		new PlayerQuitEvent_Expert();
		
		
		new BlockDamageEvent_Protection();
		new EntityDamageByEntityEvent_Protection();
		new EntityPickupItemEvent_Protection();
		new PlayerArmorStandManipulateEvent_Protection();
		new PlayerDropItemEvent_Protection();
		new PlayerHarvestBlockEvent_Protection();
		new PlayerInteractEntityEvent_Protection();
		new PlayerInteractEvent_Protection();
		new BlockPlaceEvent_Protection();
		//Tasks
		startExpert();
	}
	public static void startExpert() //Tasks will close if expert is turned off therefor they will need to be rerun for enabling expert
	{
		if(YamlWrapper.isExpert())
		{
			refreshExpert();
			Team_Expert.longBuild();
			Chunk_Expert.longBuild();
			for(Player player : Bukkit.getOnlinePlayers()) new UI_Expert(player);
			
		}
	}
	public static void refreshExpert() //Tasks will close if expert is turned off therefor they will need to be rerun for enabling expert
	{
		if(YamlWrapper.isExpert())
		{
			taskID++;
			new BloodMoon_Expert(taskID);
			new EnderDragon_Expert(taskID);
			new UITask_Expert(taskID);
		}
	}
}
