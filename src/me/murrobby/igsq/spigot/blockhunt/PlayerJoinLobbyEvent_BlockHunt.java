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
			Common_BlockHunt.addPlayer(event.getPlayer());
			
			event.getPlayer().setGameMode(GameMode.ADVENTURE);
			Common_BlockHunt.showPlayer(event.getPlayer());
			event.getPlayer().setAllowFlight(false);
			event.getPlayer().setAbsorptionAmount(0);
			event.getPlayer().setArrowsInBody(0);
			event.getPlayer().setCanPickupItems(false);
			event.getPlayer().setExp(0);
			event.getPlayer().setLevel(0);
			event.getPlayer().setFlying(false);
			event.getPlayer().setFireTicks(0);
			event.getPlayer().setFoodLevel(20);
			event.getPlayer().setGliding(false);
			event.getPlayer().setGlowing(false);
			event.getPlayer().setGravity(true);
			event.getPlayer().setHealthScale(20);
			event.getPlayer().setHealth(20);
			event.getPlayer().setSaturation(0);
			event.getPlayer().setWalkSpeed(0.2f);
			event.getPlayer().setSprinting(false);
			event.getPlayer().getInventory().clear();
			event.getPlayer().getInventory().setHeldItemSlot(0);
			for (PotionEffect effect : event.getPlayer().getActivePotionEffects()) event.getPlayer().removePotionEffect(effect.getType());
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1000000000,0, true,false));
			
			event.getPlayer().teleport(Common_BlockHunt.lobbyLocation);
			
		}
	}
	
	
	
	
}
