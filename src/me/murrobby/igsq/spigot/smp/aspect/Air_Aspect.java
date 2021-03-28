package me.murrobby.igsq.spigot.smp.aspect;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Weather;
import me.murrobby.igsq.spigot.smp.Player_SMP;

public class Air_Aspect extends Base_Aspect
{

	public Air_Aspect(Player_SMP player) //Player Instance
	{
		generate(player);
	}
	public Air_Aspect() //Internal constructor
	{
		generate();
	}
	@Override
	protected void generate() 
	{
		setID(Enum_Aspect.AIR);
		setName("&#FFFDD1Caeli");
		//setColour(ChatColor.of("#00FFFF"));
		addGoodPerkDescription("For the sky");
		addNeutralEntity(EntityType.PHANTOM);
		addProtectiveEntity(EntityType.BEE);
		addTwistedPerkDescription("Nothing good, nothing bad");
		addBadPerkDescription("Do not pick this if you can");
		setLogo(Material.FEATHER);
		setSuggester("JulGames#9066");
		setLore("Julian could not be arsed to write a lore.");
		setMovementSpeed(0.2f);
	}
	
	@Override
	public void aspectTick() 
	{
		Weather weather = Common.getWeatherEstimated(player.getPlayer());
		if(weather.equals(Weather.SNOWSTORM) || weather.equals(Weather.STORM) && heightRange(16)) 
		{
			setFlySpeed(0.08f);
			player.getPlayer().setAllowFlight(true);
			if(player.getWantFly()) player.getPlayer().setFlying(true);
		}
		else if(heightRange(8))
		{
			setFlySpeed(0.04f);
			player.getPlayer().setAllowFlight(true);
			if(player.getWantFly()) player.getPlayer().setFlying(true);
		}
		else 
		{
			player.getPlayer().setAllowFlight(false);
		}
		if(player.getPlayer().isSneaking()) 
		{
			if(heightRange(3)) 
			{
				for(Entity entity : player.getPlayer().getNearbyEntities(3, 10, 3)) 
				{
					if(entity instanceof LivingEntity) 
					{
						LivingEntity target = (LivingEntity) entity;
						if(target instanceof Player && heightRange(3, (Player) target)) continue;
						target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 19, 0, true,false,false));
					}
				}
			}
			else if(heightRange(128))
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 19, 254, true,false,false));
			}
		}
	}
	@Override
	public void aspectSecond() 
	{
		
	}
	
	private boolean heightRange(int blocks) 
	{
		return heightRange(blocks, player.getPlayer());
	}
	private boolean heightRange(int blocks,Player player) 
	{
		int highestBlock = player.getWorld().getHighestBlockYAt(player.getLocation());
		if(highestBlock <= player.getPlayer().getLocation().getBlockY() && highestBlock + blocks >= player.getPlayer().getLocation().getBlockY()) return true;
		return false;
	}
}
