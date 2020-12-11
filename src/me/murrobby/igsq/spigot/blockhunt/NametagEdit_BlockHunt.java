package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

//import com.nametagedit.plugin.NametagEdit;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Communication;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.YamlPlayerWrapper;
import me.murrobby.igsq.spigot.YamlWrapper;

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
				if(Main_BlockHunt.taskID != taskID || (!YamlWrapper.isBlockHunt()) || Game_BlockHunt.getGameInstances().length == 0) 
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
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(player);
			if(Common.isCurrentNameController("blockhunt", player) && playersGame != null) 
			{
				String name = player.getName();
				if(yaml.isLinked()) name = yaml.getNickname();
				if(playersGame.isStage(Stage.IN_LOBBY)) Communication.setTag(player,Messaging.chatFormatter("&e[&6I&e] &6"+name));
				else if(playersGame.isHider(player)) Communication.setTag(player, Messaging.chatFormatter("&3[&bH&3] &b"+name));
				else if(playersGame.isSeeker(player)) Communication.setTag(player, Messaging.chatFormatter("&4[&cS&4] &c"+name));
				else Communication.setTag(player, Messaging.chatFormatter("&8[&7Sp&8] &7"+name));
				
				/*
				if(playersGame.isHider(player)) NametagEdit.getApi().setNametag(player,Messaging.chatFormatter("&3[&bH&3] &b"),"");
				else if(playersGame.isSeeker(player)) NametagEdit.getApi().setNametag(player,Messaging.chatFormatter("&4[&cS&4] &c"),"");
				else NametagEdit.getApi().setNametag(player,Messaging.chatFormatter("&8[&7Sp&8] &7"),"");
				*/
			}
		}
		
	}
}
