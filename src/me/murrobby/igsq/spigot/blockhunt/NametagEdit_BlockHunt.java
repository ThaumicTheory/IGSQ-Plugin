package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.nametagedit.plugin.NametagEdit;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;

public class NametagEdit_BlockHunt
{	
	int nteTask = -1;
	final int taskID;
	
	public NametagEdit_BlockHunt(int taskID) 
	{
		this.taskID = taskID;
		NametagUpdaterQuery();
	}
	private void NametagUpdaterQuery() 
	{
		nteTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				NametagUpdater();
				if(Main_BlockHunt.taskID != taskID || (!Common_BlockHunt.blockhuntCheck()) || Game_BlockHunt.getGameInstances().length == 0) 
				{
					Common.spigot.scheduler.cancelTask(nteTask);
					System.out.println("Task: \"NametagEdit BlockHunt\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 100);
	}
	private void NametagUpdater()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			Game_BlockHunt playersGame =  Game_BlockHunt.getPlayersGame(player);
			if(Common.isCurrentTagController("blockhunt", player) && playersGame != null) 
			{
				if(playersGame.isHider(player)) NametagEdit.getApi().setNametag(player,Messaging.chatFormatter("&3[&bH&3] &b"),"");
				else if(playersGame.isSeeker(player)) NametagEdit.getApi().setNametag(player,Messaging.chatFormatter("&4[&cS&4] &c"),"");
				else NametagEdit.getApi().setNametag(player,Messaging.chatFormatter("&8[&7Sp&8] &7"),"");
			}
		}
		
	}
}
