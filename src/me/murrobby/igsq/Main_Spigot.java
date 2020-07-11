package me.murrobby.igsq;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import me.murrobby.igsq.commands.Executor_Command;
import me.murrobby.igsq.expert.Main_Expert;
import me.murrobby.igsq.main.AsyncPlayerChatEvent_Main;
import me.murrobby.igsq.main.BlockBreakEvent_Main;
import me.murrobby.igsq.main.InventoryClickEvent_Main;
import me.murrobby.igsq.main.PlayerCommandPreprocessEvent_Main;
import me.murrobby.igsq.main.PlayerJoinEvent_Main;
import java.util.Random;

public class Main_Spigot extends JavaPlugin{
	public BukkitScheduler scheduler = getServer().getScheduler();
	Random random = new Random();
	@Override
	public void onEnable()
	{
		Common.plugin = this;
		Common.loadConfiguration();
		Common.createPlayerData();
		Common.createInternalData();
		new Database(this);
		
		new Executor_Command(this);
		
		
		new PlayerJoinEvent_Main(this);
		new BlockBreakEvent_Main(this);
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
		
		
		
		
		new Main_Expert(this);
	}

	public void onLoad()
	{
	}
	
	public void onDisable()
	{
		this.getServer().getScheduler().cancelTasks(this);
		this.getServer().getPluginManager().disablePlugin(this);
	}
	
}
