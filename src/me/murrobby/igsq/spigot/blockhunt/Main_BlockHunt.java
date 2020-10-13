package me.murrobby.igsq.spigot.blockhunt;

public class Main_BlockHunt 
{
	public static int taskID = 0;
	public Main_BlockHunt()
	{
		//Events run forever and cannot be turned off
		new InventoryClickEvent_BlockHunt();
		new PlayerSwapHandItemsEvent_BlockHunt();
		new PlayerDropItemEvent_BlockHunt();
		new FoodLevelChangeEvent_BlockHunt();
		new ProjectileHitEvent_BlockHunt();
		new PlayerTeleportEvent_BlockHunt();
		new EntityChangeBlockEvent_BlockHunt();
		new EntitySpawnEvent_BlockHunt();
		new PlayerInteractEvent_BlockHunt();
		new PlayerQuitEvent_BlockHunt();
		
		new PlayServerBlockChange_BlockHunt();
	}
	public static void Start_BlockHunt() //Tasks will close if the game is turned off therefor they will need to be rerun for enabling the game
	{
		taskID++;
		new GameTick_BlockHunt(taskID);
		new NametagEdit_BlockHunt(taskID);
	}
}
