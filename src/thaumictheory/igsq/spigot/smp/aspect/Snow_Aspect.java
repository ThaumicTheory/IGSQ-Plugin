package thaumictheory.igsq.spigot.smp.aspect;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import thaumictheory.igsq.spigot.Climate;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Weather;
import thaumictheory.igsq.spigot.smp.Player_SMP;

public class Snow_Aspect extends Base_Aspect
{

	public Snow_Aspect(Player_SMP player) //Player Instance
	{
		generate(player);
	}
	public Snow_Aspect() //Internal constructor
	{
		generate();
	}
	@Override
	protected void generate() 
	{
		setID(Enum_Aspect.SNOW);
		setName("&#FFFFFFNix");
		addGoodPerkDescription("Filled with power from cold climates");
		addGoodPerkDescription("Immunity to being frozen");
		addBadPerkDescription("Vulnurable to worlds of extreme heat");
		addBadPerkDescription("Weaker in hot climates");
		setLogo(Material.ICE);
		
		addProtectiveEntity(EntityType.SNOWMAN);
		addProtectiveEntity(EntityType.POLAR_BEAR);
		//addPassiveEntity(EntityType.GOAT);
		addAgressiveEntity(EntityType.BLAZE);
		addAgressiveEntity(EntityType.STRIDER);
		setLore("Nix's origins reach as far back as the ice age. They thrive in cold climates and make them their primary home. They hate hot climates and are generally anxious of deserts and the nether. They are generally not very social, only enjoying company of other coldbloods. Their natural defence is second to none and they have a big loyalty for their home, wherever it be. They also dislike Furni, smokers, campfires and fires in general.");
		setSuggester("666DevilPL#0413");
		setMovementSpeed(.2f);
	}
	
	@Override
	public void aspectTick() 
	{
		Climate climate = Common.getClimate(player.getPlayer());
		
		if(!player.getPlayer().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) 
		{
			if(climate.equals(Climate.FREEZING)) 
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 259, 0, true,false,false));
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 39, 1, true,false,false));
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 39, 0, true,false,false));
				setMovementSpeed(.3f);
			}
			else if(climate.equals(Climate.FROSTY)) 
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 259, 0, true,false,false));
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 39, 0, true,false,false));
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 39, 0, true,false,false));
				setMovementSpeed(.25f);
			}
			else if(climate.equals(Climate.COLD)) 
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 39, 0, true,false,false));
				setMovementSpeed(.22f);
			}
			else if(climate.equals(Climate.NORMAL)) 
			{
				setMovementSpeed(.18f);
			}
			else if(climate.equals(Climate.HOT)) 
			{
				setMovementSpeed(.16f);
			}
			else if(climate.equals(Climate.HEATWAVE)) 
			{
				setMovementSpeed(.14f);
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 39, 0, true,false,false));
			}
			else if(climate.equals(Climate.HELL)) 
			{
				player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 39, 0, true,false,false));
				setMovementSpeed(.12f);
			}
		}
		else setMovementSpeed(.2f);
		/* for 1.17 frozen effect if no event does this better
		if(player.getPlayer().hasPotionEffect(PotionEffectType.FROZEN)) 
		{
			player.getPlayer().removePotionEffect(PotionEffectType.FROZEN);
		}
		*/
		Weather weather = Common.getWeatherEstimated(player.getPlayer());
		
		if(weather.equals(Weather.SNOW) || weather.equals(Weather.SNOWSTORM)) player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 39, 0, true,false,false));
	}
	@Override
	public void aspectSecond() 
	{
		Climate climate = Common.getClimate(player.getPlayer());
		
		if(!player.getPlayer().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) 
		{
			if(climate.equals(Climate.HELL)  && player.getPlayer().getFireTicks() < 21) player.getPlayer().setFireTicks(21);
		}
	}
}
