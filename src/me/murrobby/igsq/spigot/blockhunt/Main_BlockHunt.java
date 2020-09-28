package me.murrobby.igsq.spigot.blockhunt;

import me.murrobby.igsq.spigot.Main_Spigot;

public class Main_BlockHunt 
{
	public static Main_Spigot plugin;
	public static int taskID = 0;
	public Main_BlockHunt(Main_Spigot plugin)
	{
		Main_BlockHunt.plugin = plugin;
		//Events run forever and cannot be turned off
		new InventoryClickEvent_BlockHunt(plugin);
		new PlayerSwapHandItemsEvent_BlockHunt(plugin);
		new PlayerDropItemEvent_BlockHunt(plugin);
		Start_BlockHunt();
	}
	public static void Start_BlockHunt() //Tasks will close if the game is turned off therefor they will need to be rerun for enabling the game
	{
		taskID++;
	}
}
