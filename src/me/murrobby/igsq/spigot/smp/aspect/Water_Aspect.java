package me.murrobby.igsq.spigot.smp.aspect;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.smp.Player_SMP;

public class Water_Aspect extends Base_Aspect
{

	public Water_Aspect(Player_SMP player) //Player Instance
	{
		generate(player);
	}
	public Water_Aspect() //Internal constructor
	{
		generate();
	}
	@Override
	protected void generate() 
	{
		setID(Enum_Aspect.WATER);
		setName("&#00FFFFAqueno");
		//setColour(ChatColor.of("#00FFFF"));
		addGoodPerkDescription("Superior control over water");
		addGoodPerkDescription("Protect travelers from the depths of the sea bed");
		addBadPerkDescription("Vulnurable to worlds absent of water");
		addBadPerkDescription("Weaker on land");
		addBadPerkDescription("Impailing hurts");
		setLogo(Material.TROPICAL_FISH_BUCKET);
		addPassiveEntity(EntityType.SQUID);
		//addPassiveEntity(EntityType.GLOW_SQUID);
		addProtectiveEntity(EntityType.GUARDIAN);
		addProtectiveEntity(EntityType.ELDER_GUARDIAN);
		addNeutralEntity(EntityType.DROWNED);
		addPassiveEntity(EntityType.DOLPHIN);
		//addNeutralEntity(EntityType.AXOLOTYL);
		addAgressiveEntity(EntityType.BLAZE);
		
		setMovementSpeed(0.15f);
	}
	
	@Override
	public void aspectTick() 
	{
		if(player.getPlayer().isInWater()) 
		{
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 19, 0, true,false,false));
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 219, 0, true,false,false));
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 19, 3, true,false,false));
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 19, 0, true,false,false));
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 19, 0, true,false,false));
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 19, 0, true,false,false));
			player.getPlayer().setAllowFlight(true);
			setMovementSpeed(.2f);
			player.getPlayer().setFlySpeed(.05f);
		}
		else 
		{
			if(player.getPlayer().getGameMode().equals(GameMode.ADVENTURE)||player.getPlayer().getGameMode().equals(GameMode.SURVIVAL))player.getPlayer().setAllowFlight(false);
			player.getPlayer().setFlySpeed(0.2f);
			if(player.isInEnvironment(Environment.NETHER))
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 19, 1, true,false,false));
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 19, 0, true,false,false));
				setMovementSpeed(0.1f);
			}
			else
			{
				setMovementSpeed(0.15f);
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 19, 0, true,false,false));
			}
		}
		if(player.getPlayer().getGameMode().equals(GameMode.CREATIVE)||player.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) player.getPlayer().setAllowFlight(true);
	}
	@Override
	public void aspectSecond() 
	{
		if(player.isInEnvironment(Environment.NETHER) && player.getPlayer().getFireTicks() < 21) player.getPlayer().setFireTicks(21);
	}
}
