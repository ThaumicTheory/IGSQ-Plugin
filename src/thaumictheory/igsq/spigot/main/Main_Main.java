package thaumictheory.igsq.spigot.main;

public class Main_Main 
{
	public static int taskID = 0;
	public Main_Main()
	{
		//Events run forever and cannot be turned off
		new PlayerJoinEvent_Main();
		new InventoryClickEvent_Main();
		new PlayerCommandPreprocessEvent_Main();
		new EntityDeathEvent_Main();
		new AsyncPlayerChatEvent_Main();
		new EntityDamageEvent_Main();
		new PlayerQuitEvent_Main();
		new LoggerHandler_Main();
		new CreatureSpawnEvent_Main();
		new PlayServerPlayerInfo_Main();
	}
	public static void start_Main() //Tasks will close if LP is turned off therefor they will need to be rerun for enabling lp
	{
		taskID++;
		new CustomTag_Main(taskID);
	}
}
