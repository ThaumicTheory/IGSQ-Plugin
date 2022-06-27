package thaumictheory.igsq.bungee.lp;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.security.Main_Security;
import thaumictheory.igsq.shared.YamlPlayerWrapper;
import java.util.concurrent.TimeUnit;

public class RoleSync_LP
{	
	ScheduledTask roleSyncTask;
	final int taskID;
	
	public RoleSync_LP(int taskID) 
	{
		this.taskID = taskID;
		DiscordLinkQuery();
	}
	//backup task that forces a role sync every 5mins
	private void DiscordLinkQuery() 
	{
		roleSyncTask = Common.bungee.getProxy().getScheduler().schedule(Common.bungee, new Runnable() 
    	{

			@Override
			public void run() 
			{
				roleSync();
				if(Main_Security.taskID != taskID) 
				{
					roleSyncTask.cancel();
					System.out.println("Task: \"Role Sync LP\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 5, 300, TimeUnit.SECONDS);
	}
	private void roleSync() 
	{
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
		{
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
			Common_LP.updateRoles(player, yaml.getRoles());
			Common_LP.updateYaml(player);
		}
	}
}
