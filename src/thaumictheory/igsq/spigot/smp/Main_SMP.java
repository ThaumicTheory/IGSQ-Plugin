package thaumictheory.igsq.spigot.smp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import thaumictheory.igsq.spigot.smp.aspect.EntityAirChangeEvent_Aspect;
import thaumictheory.igsq.spigot.smp.aspect.EntityDamageByEntityEvent_Aspect;
import thaumictheory.igsq.spigot.smp.aspect.EntityTargetEvent_Aspect;
import thaumictheory.igsq.spigot.smp.aspect.ProjectileHitEvent_Aspect;
import thaumictheory.igsq.spigot.smp.protection.BlockDamageEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.BlockPistonExtendEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.BlockPistonRetractEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.BlockPlaceEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.EntityDamageByEntityEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.EntityPickupItemEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.PlayerArmorStandManipulateEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.PlayerDropItemEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.PlayerHarvestBlockEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.PlayerInteractEntityEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.PlayerInteractEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.PotionSplashEvent_Protection;
import thaumictheory.igsq.spigot.smp.protection.SpongeAbsorbEvent_Protection;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class Main_SMP 
{
	public static int taskID = 0;
	public Main_SMP()
	{
		//Events run forever and cannot be turned off they must induvidualy have checks for expert mode
		new BlockExplodeEvent_SMP();
		new EntityExplodeEvent_SMP();
		new PlayerJoinEvent_SMP();
		new PlayerQuitEvent_SMP();
		new InventoryClickEvent_SMP();
		new PlayerMoveEvent_SMP();
		new PlayerToggleFlightEvent_SMP();
		
		
		new BlockDamageEvent_Protection();
		new EntityDamageByEntityEvent_Protection();
		new EntityPickupItemEvent_Protection();
		new PlayerArmorStandManipulateEvent_Protection();
		new PlayerDropItemEvent_Protection();
		new PlayerHarvestBlockEvent_Protection();
		new PlayerInteractEntityEvent_Protection();
		new PlayerInteractEvent_Protection();
		new BlockPlaceEvent_Protection();
		new BlockPistonExtendEvent_Protection();
		new BlockPistonRetractEvent_Protection();
		new SpongeAbsorbEvent_Protection();
		new PotionSplashEvent_Protection();
		
		new EntityAirChangeEvent_Aspect();
		new EntityDamageByEntityEvent_Aspect();
		new EntityTargetEvent_Aspect();
		new ProjectileHitEvent_Aspect();
		//new PlayServerGameStateChange_Aspect(); //Re-enable after protocollib fixes this event
		//Tasks
		if(YamlWrapper.isSMP())startSMP();
	}
	public static void startSMP() //Tasks will close if SMP is turned off therefor they will need to be rerun for enabling SMP
	{
		if(YamlWrapper.isSMP())
		{
			refreshSMP();
			Team_SMP.longBuild();
			Chunk_SMP.longBuild();
			for(Player player : Bukkit.getOnlinePlayers()) 
			{
				new UI_SMP(player);
			}
			
		}
	}
	public static void refreshSMP() //Tasks will close if SMP is turned off therefor they will need to be rerun for enabling SMP
	{
		if(YamlWrapper.isSMP())
		{
			taskID++;
			new Task_SMP(taskID);
		}
	}
}
