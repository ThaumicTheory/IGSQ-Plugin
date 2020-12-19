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
		new PlayerInteractEvent_BlockHunt();
		new PlayerQuitEvent_BlockHunt();
		new EntityDamageEvent_BlockHunt();
		new EntityDamageByEntityEvent_BlockHunt();
		new PlayerMoveEvent_BlockHunt();
		new PlayerJoinEvent_BlockHunt();
		new PlayerInteractEntityEvent_BlockHunt();
		new BlockDamageEvent_BlockHunt();
		new PlayerHarvestBlockEvent_BlockHunt();
		new PlayerArmorStandManipulateEvent_BlockHunt();
		
		new PlayServerBlockChange_BlockHunt();
		
		new LobbyCreateEvent_BlockHunt();
		new PlayerJoinLobbyEvent_BlockHunt();
		new GameStartEvent_BlockHunt();
		new GameEndEvent_BlockHunt();
		new BeginSeekEvent_BlockHunt();
		
		startBlockHunt();
	}
	public static void startBlockHunt() //Tasks will close if the game is turned off therefor they will need to be rerun for enabling the game
	{
		taskID++;
		Common_BlockHunt.setupTeams();
		new GameTick_BlockHunt(taskID);
		new NametagEdit_BlockHunt(taskID);
	}
}
