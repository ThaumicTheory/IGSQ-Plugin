package thaumictheory.igsq.bungee.lp;

public class Main_LP 
{
	public static int taskID = 0;
	public Main_LP()
	{
		//Events run forever and cannot be turned off
		startLuckPerms();
	}
	public static void startLuckPerms() //Tasks will close if the permission system is turned off therefor they will need to be rerun for enabling the permission system
	{
		taskID++;
		new Account_LP(taskID);
		new RoleSync_LP(taskID);
	}
}
