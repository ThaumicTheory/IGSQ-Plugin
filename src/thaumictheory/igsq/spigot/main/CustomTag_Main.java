package thaumictheory.igsq.spigot.main;



import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import thaumictheory.igsq.shared.Role;
import thaumictheory.igsq.shared.RoleType;
import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Communication;

public class CustomTag_Main
{	
	int nteTask = -1;
	final int taskID;
	
	public CustomTag_Main(int taskID) 
	{
		this.taskID = taskID;
		customTagQuery();
	}
	private void customTagQuery() 
	{
		nteTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				customTagUpdater();
				if(Main_Main.taskID != taskID) 
				{
					Common.spigot.scheduler.cancelTask(nteTask);
					System.out.println("Task: \"NametagEdit LuckPerms\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 100);
	}
	private void customTagUpdater()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if(Common.isCurrentNameController("main", player)) 
			{
				YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
				String name = player.getName();
				if(yaml.isLinked()) name = yaml.getNickname();
				Communication.setTag(player, Role.getHighestTagPrefix(player.getUniqueId(), RoleType.EITHER),name,Role.getHighestTagSuffix(player.getUniqueId(), RoleType.EITHER));
			}
		}
		
	}
}
