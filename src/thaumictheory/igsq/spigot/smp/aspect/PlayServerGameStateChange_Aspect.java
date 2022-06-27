package thaumictheory.igsq.spigot.smp.aspect;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.smp.Player_SMP;
import thaumictheory.igsq.spigot.yaml.YamlWrapper;

public class PlayServerGameStateChange_Aspect implements Listener
{
	public PlayServerGameStateChange_Aspect()
	{
		Common.protocol.addPacketListener
		(
			new PacketAdapter(Common.spigot, ListenerPriority.NORMAL,PacketType.Play.Server.GAME_STATE_CHANGE) 
			{
				@Override
			    public void onPacketSending(PacketEvent event) 
			    {
			        if (!event.isCancelled() && YamlWrapper.isSMP())
			        {
			        	if(event.getPacketType() == PacketType.Play.Server.GAME_STATE_CHANGE)
			        	{
			        		PacketContainer packet = event.getPacket();
			        		if(packet.getIntegers().read(0) == 10) //elder guardian event
				        	{
				        		if(Player_SMP.getSMPPlayer(event.getPlayer()).getAspect().getID() == Enum_Aspect.WATER) 
				        		{
				        			event.setCancelled(true);
				        			event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
				        			event.getPlayer().spawnParticle(Particle.MOB_APPEARANCE, event.getPlayer().getLocation(), 5, .5, .5, .5);
				        			event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, 10, 1);
				        			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000, 0, false));
				        		}
				        	}
				        }
			        }
			    }
			  }
		);
	}
	
}
