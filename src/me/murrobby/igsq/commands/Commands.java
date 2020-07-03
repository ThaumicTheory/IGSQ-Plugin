package me.murrobby.igsq.commands;

import java.util.Calendar;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.murrobby.igsq.Common;
import me.murrobby.igsq.Main;

public class Commands implements CommandExecutor{
	private Main plugin;
	private Player player;
	private CommandSender sender;
	private String[] args = new String[0];
	
	private Player[] players = null;
	private Material material = Material.valueOf("STONE");
	private String display = "Yourself";
	
	private int realtimeTask = -1;
	private int realtimeSpeed = 1;
	
	public Commands(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("igsq").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender,Command command,String label,String[] args) 
	{
		this.sender = sender;
		if(args.length == 0) 
		{
			sender.sendMessage(Common.ChatColour("&cPlease Specify a command!"));
			return false;
		}
		this.args = Common.Depend(args, 0);
    	switch(args[0].toLowerCase()) 
    	{
  	  		case "version":
  	  			return VersionQuery();
  	  		case "nightvision":
  	  			return NightVisionQuery();
  	  		case "nv":
  	  			return NightVisionQuery();
  	  		case "block":
  	  			return BlockQuery();
  	  		case "entity":
  	  			return EntityQuery();
  	  		case "realtime":
  	  			return RealTimeQuery();
  	  		case "notification":
  	  			return NotificationQuery();
  	  		case "ping":
  	  			return NotificationQuery();
  	  		case "expert":
  	  			return ExpertDifficultyQuery();
  	  		default:
  	  			Help();
  	  			return false;
    	}
	}
	private boolean RequirePermission(String permission) 
	{
		if(IsPlayer() && player.hasPermission(permission))
		{	
			return true;
		}
		else if(!IsPlayer())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private boolean IsPlayer() 
	{
		if(sender instanceof Player) 
		{
			player = (Player)sender;
			return true;
		}
		else 
		{
			return false;
		}
	}
	private Boolean Version() 
	{
		String version = "RELEASE1";
		String forBuild = "Spigot 1.16.1";
		if(args.length == 0) 
		{
			sender.sendMessage(Common.ChatColour("&bIGSQ Version " + version + " for " + forBuild + "!"));
			return true;
		}
		if(args[0].equalsIgnoreCase("version")) 
		{
			sender.sendMessage(Common.ChatColour("&bIGSQ Version " + version + "!"));
			return true;
		}
		else if(args[0].equalsIgnoreCase("build"))
		{
			sender.sendMessage(Common.ChatColour("&bIGSQ for " + forBuild + "!"));
			return true;
		}
		return false;
		
	}
	private Boolean VersionQuery() 
	{
			if(RequirePermission("igsq.version")) 
			{
				if(Version()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common.ChatColour("&1version [build/version]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
	private boolean RealTimeQuery()
	{
		
		if(RequirePermission("igsq.realtime")) 
		{
			if(RealTime()) 
			{
				return true;
			}
			else 
			{
				sender.sendMessage(Common.ChatColour("&cSomething Went Wrong When Executing this Command!"));
				return false;
			}
		}
		else 
		{
			sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to not having the required permission"));
  			return false;
		}
	}
	private boolean RealTime() {
		World world = player.getWorld();
		realtimeSpeed = 1;
		if(args.length > 1) 
		{
			Common.ChatColour("&cToo many arguments were given");
			return false;
		}
		else if(args.length == 1) 
		{
			try 
			{
				realtimeSpeed = Integer.parseInt(args[0]);
			}
			catch(Exception exception) 
			{
				Common.ChatColour("&cSomething went wrong when making the speed multiplier");
				return false;
			}
		}
		if(args.length != 0) 
		{
			plugin.scheduler.cancelTask(realtimeTask);
			realtimeTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
	    	{

				@Override
				public void run() 
				{
					Calendar calendar = Calendar.getInstance();
					int seconds = calendar.get(Calendar.SECOND);
					int minutes = calendar.get(Calendar.MINUTE);
					int hours = calendar.get(Calendar.HOUR_OF_DAY);
					//long unix = (long) calendar.getTimeInMillis();
					long totalSeconds = (long) ((double)seconds + ((double)minutes*60) + ((double)hours*3600));
					//long totalSeconds = unix;
					long mctime = (long)((double)totalSeconds/72*20);
					world.setTime((mctime*realtimeSpeed)-5000);
				} 		
	    	}, 0, 20);
			Common.internalData.set("Modules.realtime", true);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
			world.setGameRule(GameRule.RANDOM_TICK_SPEED,realtimeSpeed);
			sender.sendMessage(Common.ChatColour("&bRealtime mode Turned On!"));
			return true;
		}
		else if(Common.getFieldBool("Modules.realtime", "internal").equals(false)) 
		{
			realtimeTask = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
	    	{

				@Override
				public void run() 
				{
					Calendar calendar = Calendar.getInstance();
					int seconds = calendar.get(Calendar.SECOND);
					int minutes = calendar.get(Calendar.MINUTE);
					int hours = calendar.get(Calendar.HOUR_OF_DAY);
					//long unix = (long) calendar.getTimeInMillis();
					long totalSeconds = (long) ((double)seconds + ((double)minutes*60) + ((double)hours*3600));
					//long totalSeconds = unix;
					long mctime = (long)((double)totalSeconds/72*20);
					world.setTime((mctime*realtimeSpeed)-5000);
				} 		
	    	}, 0, 20);
			Common.internalData.set("Modules.realtime", true);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
			world.setGameRule(GameRule.RANDOM_TICK_SPEED,realtimeSpeed);
			sender.sendMessage(Common.ChatColour("&bRealtime mode Turned On!"));
			return true;
		}
		else
		{
			Common.internalData.set("Modules.realtime", false);
			world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,true);
			world.setGameRule(GameRule.RANDOM_TICK_SPEED,3);
			plugin.scheduler.cancelTask(realtimeTask);
			sender.sendMessage(Common.ChatColour("&3Realtime mode Turned Off!"));
			return true;
		}
		
	}
	private boolean NightVisionQuery() 
	{
			if(IsPlayer() && RequirePermission("igsq.nightvision")) 
			{
				if(NightVision()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common.ChatColour("&cSomething Went Wrong When Executing this Command!"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
	private boolean NightVision() {
		String display;
		if(args.length == 0) 
		{
			display = "Yourself";
			player = (Player)sender;
		}
		else 
		{
			try
			{
				player = Bukkit.getPlayer(args[0]);
				display = player.getName();
			}
			catch(Exception exception) 
			{
				sender.sendMessage(Common.ChatColour("&cPlayer Could not be found!"));
				return false;
			}
		}
		if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) 
		{
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			sender.sendMessage(Common.ChatColour("&3Removed nightvision from " + display + "!"));
		}
		else 
		{
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000000,255,true));
			sender.sendMessage(Common.ChatColour("&bGave nightvision too " + display + "!"));
		}
		return true;
	}
	private boolean BlockQuery() 
	{
			if(RequirePermission("igsq.block") && IsPlayer()) 
			{
				if(Block()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Common.ChatColour("&9block [block_ID] [*fake*/real/trap] {@all/@others/username/*\"you\"*} {\"another user\"} ..."));
					return false;
				}
			}
			else
			{
				sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
	private boolean Block() {
		Location location = player.getLocation();
		players = new Player[1];
		players[0] = player;
		display = "Yourself";
		String[] playerArgs = new String[0];
		try 
		{
			material = Material.valueOf(args[0].toUpperCase());
			playerArgs = Common.GetBetween(args, 2, -1);
			if(args.length >=3) 
			{
				if(args[2].equalsIgnoreCase("@all")) 
				{
					display = "Everyone";
					for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
					{
							players = Common.Append(players,selectedPlayer);
					}
				}
				else if(args[2].equalsIgnoreCase("@others")) 
				{
					display = "Everyone Else";
					for(Player selectedPlayer : plugin.getServer().getOnlinePlayers()) 
					{
						if(!(selectedPlayer.equals(player))) 
						{
							players = Common.Append(players,selectedPlayer);
						}
					}
				}
				else 
				{
					players = new Player[0];
					display = "";
					for (String selectedPlayer : playerArgs) 
					{ 
						players = Common.Append(players,Bukkit.getPlayer(selectedPlayer));
						display += players[players.length-1].getName() + " ";
					}
				}
			}
		}
		catch(Exception exception) 
		{
			sender.sendMessage(Common.ChatColour("&cThis Block, or a Player cound not be found!"));
			return false;
		}
		Block block = location.getBlock();
		if(args.length == 1 || args[1].equalsIgnoreCase("fake")) 
		{
			for (Player selectedPlayer : players) 
			{ 
				selectedPlayer.sendBlockChange(location, material.createBlockData());
			}
			sender.sendMessage(Common.ChatColour("&bGave &4FAKE &a"+ args[0].toLowerCase() +" &bto " + display));
			return true;
		}
		else if(args[1].equalsIgnoreCase("real"))
		{
			block.setType(Material.valueOf(args[0].toUpperCase()));
			sender.sendMessage(Common.ChatColour("&bGave &a"+ args[0].toLowerCase() +" &bto " + display));
			return true;
		}
		else if(args[1].equalsIgnoreCase("trap")) 
		{
			block.setBlockData(Bukkit.createBlockData("minecraft:tnt[unstable=true]"));
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				for (Player selectedPlayer : players) 
				{ 
					selectedPlayer.sendBlockChange(location, material.createBlockData());
				}
				sender.sendMessage(Common.ChatColour("&bGave &eTRAP &a"+ args[0].toLowerCase() +" &bto " + display));
			}, 2);
			return true;
		}
		else 
		{
			return false;
		}
	}
	private Boolean Help() 
	{
		sender.sendMessage(Common.ChatColour("&c+&e----&6IGSQ HELP&e----&c+"));
		sender.sendMessage(Common.ChatColour("&eVersion &0- &6Returns current IGSQ plugin version."));
		sender.sendMessage(Common.ChatColour("&eBlock &0- &6Allows you to create blocks bellow you."));
		sender.sendMessage(Common.ChatColour("&eNightvision &0- &6Gives a player night vision."));
		sender.sendMessage(Common.ChatColour("&eEntity &0- &6Allows you to create entities bellow you."));
		sender.sendMessage(Common.ChatColour("&c+&e----&6COMMAND KEY HELP&e----&c+"));
		sender.sendMessage(Common.ChatColour("&e* &0- &6Default"));
		sender.sendMessage(Common.ChatColour("&e\" &0- &6Abreviation"));
		sender.sendMessage(Common.ChatColour("&e[ &0- &6Required"));
		sender.sendMessage(Common.ChatColour("&e{ &0- &6Optional"));
		sender.sendMessage(Common.ChatColour("&e... &0- &6Follows previous block"));
		return true;
	}
	private boolean EntityQuery() 
    {
        if(IsPlayer() && RequirePermission("igsq.entity"))
        {
            if(Entity()) 
            {
                return true;
            }
            else 
            {
                sender.sendMessage(Common.ChatColour("&9entity [entity_ID]"));
                return false;
            }
        }
        else 
        {
            sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
            return false;
        }
}

    private boolean Entity() {
        EntityType entitytype;
        @SuppressWarnings("unused")
        LivingEntity entity;
        try
        {
            entitytype = EntityType.valueOf(args[0].toUpperCase());
        }
        catch(Exception exception)
        {
            sender.sendMessage(Common.ChatColour("&cThis Entity could not be found!"));
            return false;
        }
        if(args.length == 1 || args[1].equalsIgnoreCase("fake")) 
		{
        	return true;
		}
        else if(args[1].equalsIgnoreCase("real")) 
        {
            entity = (LivingEntity) plugin.getServer().getPlayer(player.getUniqueId()).getWorld().spawnEntity(player.getLocation(), entitytype);
            sender.sendMessage(Common.ChatColour("&bGave &a"+ args[0].toLowerCase() +" &bto " + display));
            return true;
        }
        else 
        {
        	
        	return false;
        }

    }
	private boolean NotificationQuery() 
    {
        if(IsPlayer() && RequirePermission("igsq.notification"))
        {
            if(Notification()) 
            {
                return true;
            }
            else 
            {
                sender.sendMessage(Common.ChatColour("&9notification [enabled/sound]"));
                return false;
            }
        }
        else 
        {
            sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
              return false;
        }
}

    private boolean Notification() {
        if(args.length != 2) 
        {
        	 return false;
        }
        else if(args[0].equalsIgnoreCase("sound"))
        {
            try
            {
            	Sound sound = Sound.valueOf(args[1].toUpperCase());
            	Common.playerData.set(player.getUniqueId().toString() + ".notification.sound",sound.toString());
            	Common.playerData.save(Common.playerDataFile);
            	player.sendMessage(Common.ChatColour("&bSound set to &a" + sound.toString() + "&b!"));
            }
            catch(Exception exception)
            {
                sender.sendMessage(Common.ChatColour("&cThis Sound could not be found!"));
                return false;
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("pitch"))
        {
            try
            {
            	Float pitch;
            	if(Float.parseFloat(args[1]) > 2f) 
            	{
            		pitch = 2f;
            	}
            	else if(Float.parseFloat(args[1]) < 0f) 
            	{
            		pitch = 0f;
            	}
            	else 
            	{
            		pitch = Float.parseFloat(args[1]);
            	}
            	
            	Common.playerData.set(player.getUniqueId().toString() + ".notification.pitch",pitch);
            	Common.playerData.save(Common.playerDataFile);
            	player.sendMessage(Common.ChatColour("&bPitch set to &a" + pitch.toString() +"&b!"));
            }
            catch(Exception exception)
            {
                sender.sendMessage(Common.ChatColour("&cThis Pitch is not valid!"));
                return false;
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("enabled"))
        {
            try
            {
            	boolean enabled = Boolean.parseBoolean(args[1]);
            	Common.playerData.set(player.getUniqueId().toString() + ".notification.enabled",enabled);
            	Common.playerData.save(Common.playerDataFile);
            	if(enabled) 
            	{
            		player.sendMessage(Common.ChatColour("&bNotifications &aEnabled&b!"));
            	}
            	else 
            	{
            		player.sendMessage(Common.ChatColour("&bNotifications &cDisabled&b!"));
            	}
            }
            catch(Exception exception)
            {
                sender.sendMessage(Common.ChatColour("&cThis Boolean is not valid!"));
                return false;
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("allowself"))
        {
            try
            {
            	boolean allowSelf = Boolean.parseBoolean(args[1]);
            	Common.playerData.set(player.getUniqueId().toString() + ".notification.allowself",allowSelf);
            	Common.playerData.save(Common.playerDataFile);
            	if(allowSelf) 
            	{
            		player.sendMessage(Common.ChatColour("&bSelf notify is &aAllowed&b!"));
            	}
            	else 
            	{
            		player.sendMessage(Common.ChatColour("&bSelf notify is &cNot Allowed&b!"));
            	}
            }
            catch(Exception exception)
            {
                sender.sendMessage(Common.ChatColour("&cThis Boolean is not valid!"));
                return false;
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("allowconsole"))
        {
            try
            {
            	boolean allowConsole = Boolean.parseBoolean(args[1]);
            	Common.playerData.set(player.getUniqueId().toString() + ".notification.allowconsole",allowConsole);
            	Common.playerData.save(Common.playerDataFile);
            	if(allowConsole) 
            	{
            		player.sendMessage(Common.ChatColour("&bServer notify is &aAllowed&b!"));
            	}
            	else 
            	{
            		player.sendMessage(Common.ChatColour("&bServer notify is &cNot Allowed&b!"));
            	}
            }
            catch(Exception exception)
            {
                sender.sendMessage(Common.ChatColour("&cThis Boolean is not valid!"));
                return false;
            }
            return true;
        }
        else 
        {
        	return false;
        }


    }
	private boolean ExpertDifficultyQuery() 
    {
        if(RequirePermission("igsq.difficulty"))
        {
            if(ExpertDifficulty()) 
            {
                return true;
            }
            else 
            {
                sender.sendMessage(Common.ChatColour("&9expert [true/false]"));
                return false;
            }
        }
        else 
        {
            sender.sendMessage(Common.ChatColour("&cYou cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
            return false;
        }
}

    private boolean ExpertDifficulty()
    {
    	 try
         {
         	boolean enabled = Boolean.parseBoolean(args[0]);
        	plugin.getConfig().set("GAMEPLAY.expert", enabled);
         	plugin.saveConfig();
         	if(enabled) 
         	{
         		player.sendMessage(Common.ChatColour("&bExpert Mode &aEnabled&b!"));
         	}
         	else 
         	{
         		player.sendMessage(Common.ChatColour("&bExpert Mode &cDisabled&b!"));
         	}
         }
         catch(Exception exception)
         {
             sender.sendMessage(Common.ChatColour("&cThis Boolean is not valid!"));
             return false;
         }
         return true;
    }
}
