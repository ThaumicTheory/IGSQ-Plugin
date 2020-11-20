package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.murrobby.igsq.spigot.Common;

public class PlayerJoinLobbyEvent_BlockHunt implements Listener
{
	public PlayerJoinLobbyEvent_BlockHunt()
	{
		Bukkit.getPluginManager().registerEvents(this, Common.spigot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void PlayerJoinLobby_BlockHunt(me.murrobby.igsq.spigot.event.PlayerJoinLobbyEvent event) 
	{
		if(!event.isCancelled()) 
		{
			event.getGame().addPlayer(event.getPlayer());
			
			event.getPlayer().getPlayer().setGameMode(GameMode.ADVENTURE);
			event.getGame().showPlayer(event.getPlayer().getPlayer());
			event.getPlayer().getPlayer().setAllowFlight(false);
			event.getPlayer().getPlayer().setAbsorptionAmount(0);
			event.getPlayer().getPlayer().setArrowsInBody(0);
			event.getPlayer().getPlayer().setCanPickupItems(false);
			event.getPlayer().getPlayer().setExp(0);
			event.getPlayer().getPlayer().setLevel(0);
			event.getPlayer().getPlayer().setFlying(false);
			event.getPlayer().getPlayer().setFireTicks(0);
			event.getPlayer().getPlayer().setFoodLevel(20);
			event.getPlayer().getPlayer().setGliding(false);
			event.getPlayer().getPlayer().setGlowing(false);
			event.getPlayer().getPlayer().setGravity(true);
			event.getPlayer().getPlayer().setHealthScale(20);
			event.getPlayer().getPlayer().setHealth(20);
			event.getPlayer().getPlayer().setSaturation(0);
			event.getPlayer().getPlayer().setWalkSpeed(0.2f);
			event.getPlayer().getPlayer().setSprinting(false);
			event.getPlayer().getPlayer().getInventory().clear();
			event.getPlayer().getPlayer().getInventory().setHeldItemSlot(0);
			for (PotionEffect effect : event.getPlayer().getPlayer().getActivePotionEffects()) event.getPlayer().getPlayer().removePotionEffect(effect.getType());
			event.getPlayer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000000000,0, true,false));
			
			event.getPlayer().getPlayer().teleport(event.getGame().getMap().getLobbyLocation());
			
		}
	}
	
	
	
	
}
