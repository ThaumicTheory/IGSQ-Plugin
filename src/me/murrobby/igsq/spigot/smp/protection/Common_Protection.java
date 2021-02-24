package me.murrobby.igsq.spigot.smp.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.smp.Chunk_SMP;
import me.murrobby.igsq.spigot.smp.Team_SMP;

public class Common_Protection 
{
	public static  ArrayList<PotionEffectType> badPotions = new ArrayList<>(Arrays.asList(PotionEffectType.BAD_OMEN,PotionEffectType.BLINDNESS,PotionEffectType.CONFUSION,PotionEffectType.HARM,PotionEffectType.POISON,PotionEffectType.WITHER,PotionEffectType.WEAKNESS,PotionEffectType.HUNGER,PotionEffectType.SLOW,PotionEffectType.SLOW_DIGGING,PotionEffectType.UNLUCK));
	public static boolean isProtected(Player player) 
	{
		return isProtected(player,player.getLocation());
	}
	
	public static boolean isProtected(Player player,Location location) 
	{
		Chunk_SMP chunk = Chunk_SMP.getChunkFromLocation(location);
		Team_SMP team = Team_SMP.getPlayersTeam(player);
		if(chunk == null) return false;
		if(team == null) return true;
		if(chunk.isOwnedBy(team)) return false;
		if(chunk.getOwner().isAlly(team)) return false;
		return true;
	}
	public static boolean isProtected(LivingEntity attackingEntity,Location location) 
	{
		if(attackingEntity instanceof Player) return isProtected((Player)attackingEntity, location);
		Chunk_SMP chunk = Chunk_SMP.getChunkFromLocation(location);
		if(chunk == null) return false;
		if(attackingEntity instanceof Tameable) 
		{
			//Tamables may attack on their own claims
			Player tameOwner = (Player) attackingEntity;
			Team_SMP tameOwnerTeam = Team_SMP.getPlayersTeam(tameOwner);
			Team_SMP claimOwner = chunk.getOwner();
			if(claimOwner.equals(tameOwnerTeam) || claimOwner.isAlly(tameOwnerTeam)) return false;
		}
		return true;
	}
	
	public static boolean isProtected(Location triggeringBlock,Location location) 
	{
		return isProtected(triggeringBlock,location,false);
	}
	
	public static boolean isProtected(Location triggeringBlock,Location location,boolean allowWildernessFromClaim) 
	{
		Chunk_SMP triggeringChunk = Chunk_SMP.getChunkFromLocation(triggeringBlock);
		Chunk_SMP chunk = Chunk_SMP.getChunkFromLocation(location);
		if(chunk == null && triggeringChunk == null) return false;
		if(chunk == null && triggeringChunk != null) return !allowWildernessFromClaim;
		if (chunk != null && triggeringChunk == null) return true; //if one of the 2 are wilderness
		
		Team_SMP triggeringTeam = triggeringChunk.getOwner();
		Team_SMP team = chunk.getOwner();
		if(triggeringTeam.equals(team)) return false;
		if(team.isAlly(triggeringTeam)) return false;
		return true;
	}
	
	public static boolean isProtected(Player player,Entity entity) 
	{
		if(entity instanceof Tameable) 
		{
			Tameable tameableEntity = (Tameable) entity;
			if(tameableEntity.getOwner() == null || !tameableEntity.getOwner().getUniqueId().equals(player.getUniqueId()))
			{
				return true;
			}
		}
		if(isProtected(player,entity.getLocation())) return true;
		if(entity instanceof Player) 
		{
			Player affectedPlayer = (Player) entity;
			Team_SMP affectedPlayersTeam = Team_SMP.getPlayersTeam(affectedPlayer);
			Team_SMP playersTeam = Team_SMP.getPlayersTeam(player);
			if(player != null && playersTeam != null && (playersTeam.equals(affectedPlayersTeam) || playersTeam.isAlly(affectedPlayersTeam))) 
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isProtected(LivingEntity attackingEntity,Entity entity) 
	{
		if(isProtected(attackingEntity, entity.getLocation())) return true;
		if(attackingEntity instanceof Tameable) 
		{
			AnimalTamer tameOwner = ((Tameable) attackingEntity).getOwner();
			if(entity instanceof Player && tameOwner instanceof Player)
			{
				Team_SMP tameOwnerTeam = Team_SMP.getPlayersTeam((Player) tameOwner);
				//faction member cannot be attacked by a tameable from their faction or ally
				Player affectedPlayer = (Player) entity;
				Team_SMP affectedPlayersTeam = Team_SMP.getPlayersTeam(affectedPlayer);
				if(attackingEntity != null && tameOwnerTeam != null && (tameOwnerTeam.equals(affectedPlayersTeam) || tameOwnerTeam.isAlly(affectedPlayersTeam))) 
				{
					return true;
				}
			}
			else if(entity instanceof Tameable && tameOwner instanceof Player) 
			{
				//faction members tames cannot be attacked by a tameable from their faction or ally
				Team_SMP tameOwnerTeam = Team_SMP.getPlayersTeam((Player) tameOwner);
				Player affectedPlayer = (Player) ((Tameable) entity).getOwner();

				Team_SMP affectedPlayersTeam = Team_SMP.getPlayersTeam(affectedPlayer);
				if(attackingEntity != null && tameOwnerTeam != null && (tameOwnerTeam.equals(affectedPlayersTeam) || tameOwnerTeam.isAlly(affectedPlayersTeam))) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isBadPotion(Collection<PotionEffect> effects) 
	{
		for(PotionEffect effect : effects) 
		{
			if(badPotions.contains(effect.getType())) return true;
		}
		return false;
	}
}
