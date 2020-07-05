package me.murrobby.igsq.expert;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

public class BloodMoon_Expert {
	Main plugin;
	Random random = new Random();
	
	final int taskID;
	
	private int bloodMoonTask = -1;
	
	public BloodMoon_Expert(Main plugin,int taskID) 
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
					if(Common.getFieldBool(selectedWorld.getUID() + ".event.bloodmoon", "internal")) 
					{
						BloodMoonVisuals(selectedWorld);
					}
				}
				LuckEffects();
				if(Main_Expert.taskID != taskID || !Common.ExpertCheck()) 
				{
					plugin.scheduler.cancelTask(bloodMoonTask);
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
			if(Common.getFieldBool(world.getUID() + ".event.bloodmoon", "internal")) 
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
			try 
			{
				Common.internalData.set(world.getUID() + ".event.bloodmoon",false);
				Common.internalData.save(Common.internalDataFile);
				world.setMonsterSpawnLimit(-1);
				world.setTicksPerMonsterSpawns(-1);
			}
			catch (Exception exception) 
			{
				exception.printStackTrace();
			}
		}
		else 
		{
			if(random.nextInt(8) == 1 && world.getAllowMonsters()) 
			{
				try 
				{
					Common.internalData.set(world.getUID() + ".event.bloodmoon",true);
					Common.internalData.save(Common.internalDataFile);
				}
				catch (Exception exception) 
				{
					exception.printStackTrace();
				}
				if(world.getEnvironment() == Environment.NORMAL) 
				{
					Bukkit.broadcastMessage(Common.ChatColour("&2The Blood Moon is rising..."));
				}
				else if(world.getEnvironment() == Environment.NETHER) 
				{
					Bukkit.broadcastMessage(Common.ChatColour("&2Screams echo from deep bellow..."));
				}
				else if(world.getEnvironment() == Environment.THE_END) 
				{
					Bukkit.broadcastMessage(Common.ChatColour("&2Some otherworldly place calls your name..."));
				}
				else 
				{
					Bukkit.broadcastMessage(Common.ChatColour("&2I wouldn't enter \""+ world.getName() + "\" tonight..."));
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
			if(Common.ExpertCheck()) 
			{
				if(!selectedPlayer.hasPotionEffect(PotionEffectType.UNLUCK)) 
				{
					long timeSinceDamageSeconds = (selectedPlayer.getTicksLived() - Common.getFieldInt(selectedPlayer.getUniqueId() + ".damage.last","internal"))/20;
					int bloodMoonBonus = Common.getFieldBool(selectedPlayer.getWorld().getUID() + ".event.bloodmoon", "internal") ? 1 : 0;

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
