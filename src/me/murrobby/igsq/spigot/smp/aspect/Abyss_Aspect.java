package me.murrobby.igsq.spigot.smp.aspect;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.DetailedTime;
import me.murrobby.igsq.spigot.Messaging;
import me.murrobby.igsq.spigot.Time;
import me.murrobby.igsq.spigot.smp.Player_SMP;

public class Abyss_Aspect extends Base_Aspect
{

	public Abyss_Aspect(Player_SMP player) //Player Instance
	{
		generate(player);
	}
	public Abyss_Aspect() //Internal constructor
	{
		generate();
	}
	@Override
	protected void generate() 
	{
		setID(Enum_Aspect.ABYSS);
		setName("&#222222Abruptum (Talpa)");
		addGoodPerkDescription("Filled with power from cold climates");
		addGoodPerkDescription("Immunity to being frozen");
		addBadPerkDescription("Vulnurable to worlds of extreme heat");
		addBadPerkDescription("Weaker in hot climates");
		setLogo(Material.STONE);
		
		addProtectiveEntity(EntityType.BAT);
		//addNeutralEntity(EntityType.SOUNDYBOI);
		//addNeutralEntity(EntityType.AXOLOTYL);
		addProtectiveEntity(EntityType.PHANTOM);
		addPassiveEntity(EntityType.SILVERFISH);
		addPassiveEntity(EntityType.CAVE_SPIDER);
		addAgressiveEntity(EntityType.BEE);
		addAgressiveEntity(EntityType.ELDER_GUARDIAN);
		setLore("Once a town of miners, one day an unfortunate incident caused a collapse of their most prominent mine, trapping a large majority of miners underground, with very few resources. After waiting for help to arrive, with none forthcoming, they have resided underground for what has felt like centuries, learning and adapting to their new surroundings - their new home. Recently they managed to construct a way out, but are no longer fit for where they once resided. Now they are forced to stay underground, away from the colourful landscape they once knew, met with the monotone landscape of caves, only going out in desperate situations as they keep in their one goal in mind they set all those years ago - Survive. ");
		setSuggester("Newt#3735");
		setMovementSpeed(.2f);
	}
	
	@Override
	public void aspectTick() 
	{
		DetailedTime detailedTime = Common.getTimeDetailed(player.getPlayer().getWorld());
		Time time = Common.getTime(player.getPlayer().getWorld());
		
		if(detailedTime.equals(DetailedTime.RISE) || detailedTime.equals(DetailedTime.SET) && isNotProtected(10)) 
		{
			setMovementSpeed(0.2f);
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 39, 0, true,false,false));
		}
		else if(isProtected(0) || time.equals(Time.NIGHT)) 
		{
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 259, 0, true,false,false));
			if(isProtected(0)) 
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 39, 2, true,false,false));
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 39, 0, true,false,false));
				setMovementSpeed(0.3f);
			}
			else
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 39, 1, true,false,false));
				setMovementSpeed(0.25f);
			}
		}
		else if(time.equals(Time.DAY) && isNotProtected(5)) 
		{
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 39, 0, true,false,false));
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 39, 0, true,false,false));
			setMovementSpeed(0.15f);
		}
	}
	@Override
	public void aspectSecond() 
	{
		DetailedTime time = Common.getTimeDetailed(player.getPlayer().getWorld());
		
		if(time.equals(DetailedTime.MIDDAY) && isNotProtected(15) && player.getPlayer().getFireTicks() < 39) player.getPlayer().setFireTicks(39);
	}
	
	private boolean isProtected(int strength)
	{
		Block block = player.getPlayer().getLocation().getBlock();
		if(block.getLightFromSky() <= strength) return true;
		return false;
	}
	
	private boolean isNotProtected(int strength)
	{
		Block block = player.getPlayer().getLocation().getBlock();
		if(block.getLightFromSky() < strength) return false;
		return true;
	}
}
