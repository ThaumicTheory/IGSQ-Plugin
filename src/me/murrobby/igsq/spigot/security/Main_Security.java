package me.murrobby.igsq.spigot.security;

public class Main_Security 
{
	public static int taskID = 0;
	public Main_Security()
	{
		//Events run forever and cannot be turned off
		new PlayerCommandPreprocessEvent_Security();
		new ServerCommandEvent_Security();
		
		new PlayerDropItemEvent_Security();
		new PlayerInteractEvent_Security();
		new PlayerBedEnterEvent_Security();
		new PlayerInteractEntityEvent_Security();
		new PlayerItemDamageEvent_Security();
		new PlayerEditBookEvent_Security();
		new EntityPickupItemEvent_Security();
		new EntityDamageEvent_Security();
		new InventoryClickEvent_Security();
		new FoodLevelChangeEvent_Security();
		new EntityTargetEvent_Security();
		new EntityDamageByEntityEvent_Security();
		new PlayerMoveEvent_Security();
		new EntityAirChangeEvent_Security();
		Start_Security();
	}
	public static void Start_Security() //Tasks will need to be closed if security is turned off therefor they will need to be rerun for enabling security
	{
		taskID++;
		new AccountProtection_Security(taskID);
	}
}
