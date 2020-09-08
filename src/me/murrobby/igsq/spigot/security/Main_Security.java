package me.murrobby.igsq.spigot.security;

import me.murrobby.igsq.spigot.Main_Spigot;

public class Main_Security 
{
	private static Main_Spigot plugin;
	public static int taskID = 0;
	public Main_Security(Main_Spigot plugin)
	{
		Main_Security.plugin = plugin;
		//Events run forever and cannot be turned off
		new PlayerCommandPreprocessEvent_Security(plugin);
		new ServerCommandEvent_Security(plugin);
		
		new PlayerDropItemEvent_Security(plugin);
		new PlayerInteractEvent_Security(plugin);
		new PlayerBedEnterEvent_Security(plugin);
		new PlayerInteractEntityEvent_Security(plugin);
		new PlayerItemDamageEvent_Security(plugin);
		new PlayerEditBookEvent_Security(plugin);
		new EntityPickupItemEvent_Security(plugin);
		new EntityDamageEvent_Security(plugin);
		new InventoryClickEvent_Security(plugin);
		new FoodLevelChangeEvent_Security(plugin);
		new EntityTargetEvent_Security(plugin);
		new EntityDamageByEntityEvent_Security(plugin);
		new PlayerMoveEvent_Security(plugin);
		new EntityAirChangeEvent_Security(plugin);
		Start_Security();
	}
	public static void Start_Security() //Tasks will need to be closed if security is turned off therefor they will need to be rerun for enabling security
	{
		taskID++;
		new AccountProtection_Security(plugin,taskID);
	}
}
