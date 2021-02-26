package me.murrobby.igsq.spigot.smp.aspect;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.smp.Player_SMP;

public class Water_Aspect extends Base_Aspect
{

	public Water_Aspect(Player_SMP player) 
	{
		setID(Enum_Aspect.WATER);
		this.player = player;
		setName("Aqueno");
		setColour(ChatColor.AQUA);
		addGoodPerkDescription("Superior control over water");
		addGoodPerkDescription("Protect travelers from the depths of the sea bed");
		addBadPerkDescription("Vulnurable to worlds absent of water");
		addBadPerkDescription("Weaker on land");
		addBadPerkDescription("Impailing hurts");
		setLogo(Material.TROPICAL_FISH_BUCKET);
		addPassiveEntity(EntityType.SQUID);
		//addPassiveEntity(EntityType.GLOW_SQUID);
		addPassiveEntity(EntityType.GUARDIAN);
		addPassiveEntity(EntityType.ELDER_GUARDIAN);
		addPassiveEntity(EntityType.DOLPHIN);
		//addPassiveEntity(EntityType.AXOLOTYL);
		addAgressiveEntity(EntityType.BLAZE);
	}
	@Override
	public void aspect() 
	{
		if(player.isInLiquid(Material.WATER)) 
		{
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 39, 255, true));
		}
		if(player.isInEnvironment(Environment.NETHER)) player.getPlayer().setFireTicks(21);
	}
}
