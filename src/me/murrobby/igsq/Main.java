package me.murrobby.igsq;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragon.Phase;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import me.murrobby.igsq.commands.Commands;
import me.murrobby.igsq.expert.BlockSpreadEvent_Expert;
import me.murrobby.igsq.expert.CreatureSpawnEvent_Expert;
import me.murrobby.igsq.expert.EntityAirChangeEvent_Expert;
import me.murrobby.igsq.expert.EntityDamageByEntityEvent_Expert;
import me.murrobby.igsq.expert.EntityDamageEvent_Expert;
import me.murrobby.igsq.expert.EntityTargetEvent_Expert;
import me.murrobby.igsq.expert.PlayerBedEnterEvent_Expert;
import me.murrobby.igsq.expert.SlimeSplitEvent_Expert;
import me.murrobby.igsq.listeners.BlockBreakEvent_Main;
import me.murrobby.igsq.listeners.BlockSpreadEvent;
import me.murrobby.igsq.listeners.AsyncPlayerChatEvent_Main;
import me.murrobby.igsq.listeners.CreatureSpawnEvent_Main;
import me.murrobby.igsq.listeners.EntityAirChangeEvent_Main;
import me.murrobby.igsq.listeners.EntityDamageByEntityEvent_Main;
import me.murrobby.igsq.listeners.EntityDamageEvent_Main;
import me.murrobby.igsq.listeners.EntityTargetEvent_Main;
import me.murrobby.igsq.listeners.InventoryClickEvent_Main;
import me.murrobby.igsq.listeners.PlayerJoinEvent_Main;
import me.murrobby.igsq.listeners.PlayerBedEnterEvent;
import me.murrobby.igsq.listeners.PlayerCommandPreprocessEvent_Main;
import me.murrobby.igsq.listeners.SlimeSplitEvent;

import java.sql.ResultSet;
import java.util.Random;

public class Main extends JavaPlugin{
	public BukkitScheduler scheduler = getServer().getScheduler();
	Random random = new Random();
	@Override
	public void onEnable()
	{
		Common.plugin = this;
		Common.loadConfiguration();
		Common.createPlayerData();
		Common.createInternalData();
    	scheduler.scheduleSyncRepeatingTask(this, new Runnable() 
    	{

			@Override
			public void run() {
				ResultSet mc_accounts = Database.QueryCommand("SELECT * FROM mc_accounts;");
				try {
					while(mc_accounts.next()) 
					{
						ResultSet discord_2fa = Database.QueryCommand("SELECT * FROM discord_2fa WHERE uuid = '"+ mc_accounts.getString(1) +"';");
						String uuid = mc_accounts.getString(1);
						String twoFAStatus = "off";
						if(discord_2fa.next()) 
						{
							twoFAStatus = discord_2fa.getString(2);
						}
						Common.playerData.set(uuid + ".2fa",twoFAStatus);
					}
					Common.playerData.save(Common.playerDataFile);
				} catch (Exception e)
				{
					System.out.println("DATABASE ERROR: " + e.toString());
				}				
				
			}
			
    		
    	}, 0, 200);
    	scheduler.scheduleSyncRepeatingTask(this, new Runnable() 
    	{

			@Override
			public void run() 
			{
				for(Player selectedPlayer : getServer().getOnlinePlayers()) 
				{
					if(Common.getFieldBool("GAMEPLAY.expert", "config")) 
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
				for(World selectedWorld : getServer().getWorlds()) 
				{
					long worldTimeSecs = selectedWorld.getTime()/20;
					if(Common.getFieldBool("GAMEPLAY.expert", "config")) 
					{	
						try 
						{
							if(selectedWorld.getEnvironment() == Environment.THE_END) 
							{
								EnderDragon enderDragon = selectedWorld.getEnderDragonBattle().getEnderDragon();
								if(enderDragon.getCustomName() == null) 
								{
									if(enderDragon.getPhase() == Phase.FLY_TO_PORTAL) 
									{
										for(Entity nearbyEntity : enderDragon.getNearbyEntities(50, 50, 50)) 
										{
											if(nearbyEntity.getType() == EntityType.PLAYER) 
											{
												if(random.nextInt(5)== 1) 
												{
													Player player = (Player) nearbyEntity;
													Phantom phantom = (Phantom) selectedWorld.spawnEntity(enderDragon.getLocation(), EntityType.PHANTOM);
													phantom.setCustomName("Expert Phantom Warrior");
													phantom.setTarget(player);
													phantom.setHealth(1);
												}
											}

										}
									}
								}
								else if(enderDragon.getCustomName().equalsIgnoreCase("True Expert Ender Dragon"))
								{
									if(enderDragon.getPhase() == Phase.LAND_ON_PORTAL || enderDragon.getPhase() == Phase.SEARCH_FOR_BREATH_ATTACK_TARGET) 
									for(Entity nearbyEntity : enderDragon.getNearbyEntities(20, 20, 20)) 
									{
										if(nearbyEntity.getType() == EntityType.PLAYER) 
										{
											Player player = (Player) nearbyEntity;

											if(enderDragon.getPhase() == Phase.LAND_ON_PORTAL) 
											{
												player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,79,4,true));
												player.spawnParticle(Particle.DRAGON_BREATH,player.getLocation(), 20,.5,.5,.5,.25);
											}
											else if(enderDragon.getPhase() == Phase.SEARCH_FOR_BREATH_ATTACK_TARGET && player.hasPotionEffect(PotionEffectType.LEVITATION)) 
											{
												player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,10,128,true));
												player.spawnParticle(Particle.END_ROD,player.getLocation(), 20,.5,.5,.5,.25);
												player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1,2);
											}

										}

									}
									if(enderDragon.getPhase() == Phase.FLY_TO_PORTAL) 
									{
										for(Entity nearbyEntity : enderDragon.getNearbyEntities(50, 50, 50)) 
										{
											if(nearbyEntity.getType() == EntityType.PLAYER) 
											{
												if(random.nextInt(2)== 1) 
												{
													Player player = (Player) nearbyEntity;
													Phantom phantom = (Phantom) selectedWorld.spawnEntity(enderDragon.getLocation(), EntityType.PHANTOM);
													phantom.setCustomName("Expert Phantom Warrior");
													phantom.setTarget(player);
													phantom.setHealth(3);
												}
											}

										}
									}
									else if(enderDragon.getPhase() == Phase.DYING) 
									{
										for(Entity nearbyEntity : enderDragon.getNearbyEntities(100, 100, 100)) 
										{
											if(nearbyEntity instanceof Phantom) 
											{
												Phantom phantom = (Phantom) nearbyEntity;
												if(phantom.getCustomName() != null && phantom.getCustomName().equalsIgnoreCase("Expert Phantom Warrior")) 
												{
													phantom.setHealth(0);
												}
											}
										}
									}
									for(Entity nearbyEntity : enderDragon.getNearbyEntities(8, 8, 8)) 
									{
										if(nearbyEntity.getType() == EntityType.PLAYER) 
										{

											Player player = (Player) nearbyEntity;
											player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,59,0,false));

										}

									}
									for(Entity nearbyEntity : enderDragon.getNearbyEntities(5, 5, 5)) 
									{
										if(nearbyEntity.getType() == EntityType.PLAYER) 
										{

											Player player = (Player) nearbyEntity;
											player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,59,4,false));

										}

									}
									if(enderDragon.getPhase() == Phase.STRAFING || enderDragon.getPhase() == Phase.CIRCLING) 
									{
										for(Entity nearbyEntity : enderDragon.getNearbyEntities(50, 50, 50)) 
										{
											if(nearbyEntity.getType() == EntityType.PLAYER) 
											{

												Player player = (Player) nearbyEntity;
												player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,59,0,false));
												player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,39,0,false));

											}

										}
										enderDragon.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,39,0,false));
									}
								}

							}
						}
						catch(Exception exception) 
						{
							
						}
						if(worldTimeSecs == 600) //NightBegins
						{
							BloodMoonToggler(selectedWorld,true);
						}
						else if(worldTimeSecs > 1150 || worldTimeSecs < 600) //Day
						{
							if(Common.getFieldBool(selectedWorld.getUID() + ".event.bloodmoon", "internal")) 
							{
								BloodMoonToggler(selectedWorld,false);
							}
						}
					}
					else
					{
						if(Common.getFieldBool(selectedWorld.getUID() + ".event.bloodmoon", "internal")) 
						{
							BloodMoonToggler(selectedWorld,false);
						}
					}
					for(Player selectedPlayer : getServer().getOnlinePlayers()) 
					{
						if(selectedPlayer.getWorld() == selectedWorld) 
						{
							if(Common.getFieldBool(selectedWorld.getUID() + ".event.bloodmoon", "internal")) 
							{
								Location location = selectedPlayer.getLocation();
								if(selectedWorld.getEnvironment() == Environment.NORMAL) 
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
								else if(selectedWorld.getEnvironment() == Environment.NETHER) 
								{
									selectedPlayer.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,location, 200,20,10,20,.25);
								}
								else if(selectedWorld.getEnvironment() == Environment.THE_END)
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
				}
			}
    	}, 0, 20);
		new Database(this);
		new PlayerJoinEvent_Main(this);
		new BlockBreakEvent_Main(this);
		new Commands(this);
		new AsyncPlayerChatEvent_Main(this);
		//new CreatureSpawnEvent_Main(this);
		//new EntityDamageEvent_Main(this);
		//new EntityAirChangeEvent_Main(this);
		//new BlockSpreadEvent(this);
		//new PlayerBedEnterEvent(this);
		//new EntityDamageByEntityEvent_Main(this);
		//new SlimeSplitEvent(this);
		new InventoryClickEvent_Main(this);
		//new EntityTargetEvent_Main(this);
		new PlayerCommandPreprocessEvent_Main(this);
		if(Common.getFieldBool("GAMEPLAY.expert", "config")) 
		{
			new BlockSpreadEvent_Expert(this);
			new CreatureSpawnEvent_Expert(this);
			new EntityAirChangeEvent_Expert(this);
			new EntityDamageByEntityEvent_Expert(this);
			new EntityDamageEvent_Expert(this);
			new EntityTargetEvent_Expert(this);
			new PlayerBedEnterEvent_Expert(this);
			new SlimeSplitEvent_Expert(this);
		}
	}

	public void onLoad()
	{
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
}
