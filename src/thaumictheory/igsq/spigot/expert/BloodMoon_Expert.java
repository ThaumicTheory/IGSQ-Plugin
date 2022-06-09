package thaumictheory.igsq.spigot.expert;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Messaging;
import thaumictheory.igsq.spigot.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.YamlWorldWrapper;
import thaumictheory.igsq.spigot.YamlWrapper;

public class BloodMoon_Expert {
	Random random = new Random();
	
	final int taskID;
	
	private int bloodMoonTask = -1;
	
	public BloodMoon_Expert(int taskID) 
	{
		this.taskID = taskID;
		bloodMoonQuery();
	}
	private void bloodMoonQuery() 
	{
		bloodMoonTask = Common.spigot.scheduler.scheduleSyncRepeatingTask(Common.spigot, new Runnable()
    	{

			@Override
			public void run() 
			{
				for(World selectedWorld : Common.spigot.getServer().getWorlds()) 
				{
					bloodMoonEnabler(selectedWorld);
					YamlWorldWrapper yaml = new YamlWorldWrapper(selectedWorld);
					if(yaml.isExpertBloodMoon()) 
					{
						bloodMoonVisuals(selectedWorld);
					}
				}
				luckEffects();
				if(Main_Expert.taskID != taskID || !YamlWrapper.isExpert())
				{
					Common.spigot.scheduler.cancelTask(bloodMoonTask);
					System.out.println("Task: \"Blood Moon Expert\" Expired Closing Task To Save Resources.");
				}
			} 		
    	}, 0, 20);
	}
	private void bloodMoonEnabler(World world) 
	{
		YamlWorldWrapper yaml = new YamlWorldWrapper(world);
		long worldTimeSecs = world.getTime()/20;
		if(worldTimeSecs == 600) //NightBegins
		{
			bloodMoonToggler(world,true);
		}
		else if(worldTimeSecs > 1150 || worldTimeSecs < 600) //Day
		{
			if(yaml.isExpertBloodMoon()) 
			{
				bloodMoonToggler(world,false);
			}
		}
	}
	private void bloodMoonVisuals(World world) 
	{
		for(Player selectedPlayer : Common.spigot.getServer().getOnlinePlayers()) 
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
	public void bloodMoonToggler(World world,Boolean enabled) 
	{
		YamlWorldWrapper yaml = new YamlWorldWrapper(world);
		if(!enabled) 
		{
			yaml.setExpertBloodMoon(false);
			world.setMonsterSpawnLimit(-1);
			world.setTicksPerMonsterSpawns(-1);
		}
		else 
		{
			if(random.nextInt(9) == 1 && world.getAllowMonsters()) 
			{
				yaml.setExpertBloodMoon(true);
				if(world.getEnvironment() == Environment.NORMAL) 
				{
					Bukkit.broadcastMessage(Messaging.chatFormatter("&#32FF82The Blood Moon is rising..."));
				}
				else if(world.getEnvironment() == Environment.NETHER) 
				{
					Bukkit.broadcastMessage(Messaging.chatFormatter("&#FF6464Screams echo from deep bellow..."));
				}
				else if(world.getEnvironment() == Environment.THE_END) 
				{
					Bukkit.broadcastMessage(Messaging.chatFormatter("&#FFFFD0Some otherworldly place calls your name..."));
				}
				else 
				{
					Bukkit.broadcastMessage(Messaging.chatFormatter("&#00FF00I wouldn't enter \""+ world.getName() + "\" tonight..."));
				}
				world.setMonsterSpawnLimit(world.getMonsterSpawnLimit()*3);
				world.setTicksPerMonsterSpawns(10);
			}
		}
	}
	private void luckEffects() 
	{
		for(Player selectedPlayer : Common.spigot.getServer().getOnlinePlayers()) 
		{
			YamlPlayerWrapper yaml = new YamlPlayerWrapper(selectedPlayer);
			YamlWorldWrapper worldYaml = new YamlWorldWrapper(selectedPlayer.getWorld());
			if(!selectedPlayer.hasPotionEffect(PotionEffectType.UNLUCK)) 
			{
				long timeSinceDamageSeconds = (selectedPlayer.getTicksLived() - yaml.getLastDamage())/20;
				int bloodMoonBonus = worldYaml.isExpertBloodMoon() ? 1 : 0;

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
