package me.murrobby.igsq.main;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main_Spigot;

import java.util.Random;


@SuppressWarnings("unused")
public class PlayerCommandPreprocessEvent_Main implements Listener
{
	Random random = new Random();
	private Main_Spigot plugin;
	public PlayerCommandPreprocessEvent_Main(Main_Spigot plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void PlayerCommandPreprocess_Main(org.bukkit.event.player.PlayerCommandPreprocessEvent event) 
	{
		if(!event.getPlayer().hasPermission("igsq.commandwatchbypass"))
		{
			for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
			{
				if(selectedPlayer.hasPermission("igsq.commandwatch") && selectedPlayer != event.getPlayer()) 
				{
					selectedPlayer.sendMessage(Common.GetMessage("commandwatch1","<player>",event.getPlayer().getName(),"<command>",event.getMessage()));
				}
			}
			System.out.println(Common.GetMessage("commandwatch1","<player>",event.getPlayer().getName(),"<command>",event.getMessage()));
		}
	}
	
}
