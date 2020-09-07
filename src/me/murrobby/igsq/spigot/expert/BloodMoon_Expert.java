package me.murrobby.igsq.spigot.expert;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common_Spigot;
import me.murrobby.igsq.spigot.Main_Spigot;

public class BloodMoon_Expert {
	Main_Spigot plugin;
	Random random = new Random();
	
	final int taskID;
	
	private int bloodMoonTask = -1;
	
	public BloodMoon_Expert(Main_Spigot plugin,int taskID) 
	{
		this.taskID = taskID;
		this.plugin = plugin;
		BloodMoonQuery();
	}
	private void BloodMoonQuery() 
	{
		bloodMoonTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
    	{

			@Override
			public void run() 
			{
				for(World selectedWorld : plugin.getServer().getWorlds()) 
				{
					BloodMoonEnabler(selectedWorld);
					if(Common_Spigot.getFieldBool(selectedWorld.getUID() + ".event.bloodmoon", "internal")) 
					{
						BloodMoonVisuals(selectedWorld);
					}
				}
				LuckEffects();
				if(Main_Expert.taskID != taskID || !Common_Expert.ExpertCheck()) 
				{
					plugin.scheduler.cancelTask(bloodMoonTask);
					System.out.println("Task: \"Blood Moon Expert\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 20);
	}
	private void BloodMoonEnabler(World world) 
	{
		
	long worldTimeSecs = world.getTime()/20;
		if(worldTimeSecs == 600) //NightBegins
		{
			BloodMoonToggler(world,true);
		}
		else if(worldTimeSecs > 1150 || worldTimeSecs < 600) //Day
		{
			if(Common_Spigot.getFieldBool(world.getUID() + ".event.bloodmoon", "internal")) 
			{
				BloodMoonToggler(world,false);
			}
		}
	}
	private void BloodMoonVisuals(World world) 
	{
		for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
		{
			if(selectedPlayer.getWorld() == world) 
			{
				Location location = selectedPlayer.getLocation();
				if(world.getEnvironment() == Environment.NORMAL) 
				{
					if(75 > location.getBlockY()+30) 
					{
						location.setY(75);
					}
					else 
					{
						location.setY(location.getBlockY()+30);
					}
					selectedPlayer.spawnParticle(Particle.DRIP_LAVA,location, 200,20,10,20,.25);
				}
				else if(world.getEnvironment() == Environment.NETHER) 
				{
					selectedPlayer.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,location, 200,20,10,20,.25);
				}
				else if(world.getEnvironment() == Environment.THE_END)
				{
					selectedPlayer.spawnParticle(Particle.END_ROD,location, 200,20,10,20,.25);
				}
				else 
				{
					selectedPlayer.spawnParticle(Particle.PORTAL,location, 200,20,10,20,.25);
				}
			}
		}
	}
	public void BloodMoonToggler(World world,Boolean enabled) 
	{
		if(!enabled) 
		{
			Common_Spigot.updateField(world.getUID() + ".event.bloodmoon","internal",false);
			world.setMonsterSpawnLimit(-1);
			world.setTicksPerMonsterSpawns(-1);
		}
		else 
		{
			if(random.nextInt(9) == 1 && world.getAllowMonsters()) 
			{
				Common_Spigot.updateField(world.getUID() + ".event.bloodmoon","internal",true);
				if(world.getEnvironment() == Environment.NORMAL) 
				{
					Bukkit.broadcastMessage(Common_Spigot.chatFormatter("&#32FF82The Blood Moon is rising..."));
				}
				else if(world.getEnvironment() == Environment.NETHER) 
				{
					Bukkit.broadcastMessage(Common_Spigot.chatFormatter("&#FF6464Screams echo from deep bellow..."));
				}
				else if(world.getEnvironment() == Environment.THE_END) 
				{
					Bukkit.broadcastMessage(Common_Spigot.chatFormatter("&#FFFFD0Some otherworldly place calls your name..."));
				}
				else 
				{
					Bukkit.broadcastMessage(Common_Spigot.chatFormatter("&#00FF00I wouldn't enter \""+ world.getName() + "\" tonight..."));
				}
				world.setMonsterSpawnLimit(world.getMonsterSpawnLimit()*3);
				world.setTicksPerMonsterSpawns(10);
			}
		}
	}
	private void LuckEffects() 
	{
		for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
		{
			if(Common_Expert.ExpertCheck()) 
			{
				if(!selectedPlayer.hasPotionEffect(PotionEffectType.UNLUCK)) 
				{
					long timeSinceDamageSeconds = (selectedPlayer.getTicksLived() - Common_Spigot.getFieldInt(selectedPlayer.getUniqueId() + ".damage.last","internal"))/20;
					int bloodMoonBonus = Common_Spigot.getFieldBool(selectedPlayer.getWorld().getUID() + ".event.bloodmoon", "internal") ? 1 : 0;

					if(timeSinceDamageSeconds >= 1800) 
					{
						selectedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,39,2 + bloodMoonBonus ,true));
					}
					else if(timeSinceDamageSeconds >= 900) 
					{
						selectedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,39,1 + bloodMoonBonus,true));
					}
					else if(timeSinceDamageSeconds >= 300) 
					{
						selectedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,39,bloodMoonBonus,true));
					}
					else if(bloodMoonBonus == 1) 
					{
						selectedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,39,0,true));
					}
				}
			}
		}
	}
}
