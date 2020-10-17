package me.murrobby.igsq.bungee.lp;

public class Main_LP 
{
	public static int taskID = 0;
	public Main_LP()
	{
		//Events run forever and cannot be turned off
		Start_LuckPerms();
	}
	public static void Start_LuckPerms() //Tasks will close if the permission system is turned off therefor they will need to be rerun for enabling the permission system
	{
		taskID++;
		new Rank_LP(taskID);
	}
}
