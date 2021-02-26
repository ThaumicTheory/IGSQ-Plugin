package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.Location;
import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Dictionaries;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayServerBlockChange_BlockHunt implements Listener
{
	public PlayServerBlockChange_BlockHunt()
	{
		Common.protocol.addPacketListener
		(
			new PacketAdapter(Common.spigot, ListenerPriority.NORMAL,PacketType.Play.Server.BLOCK_CHANGE) 
			{
				@Override
			    public void onPacketSending(PacketEvent event) 
			    {
			        if (!event.isCancelled() && YamlWrapper.isBlockHunt())
			        {
			        	for(Game_BlockHunt gameInstance : Game_BlockHunt.getGameInstances()) 
			        	{
				        	PacketContainer packet = event.getPacket();
				        	if(event.getPacketType() == PacketType.Play.Server.BLOCK_CHANGE)
				        	{
				        		BlockPosition position = packet.getBlockPositionModifier().read(0);
				        		Location location = event.getPlayer().getLocation();
				        		location.setX(position.getX());
				        		location.setY(position.getY());
				        		location.setZ(position.getZ());
				        		
				        		Hider_BlockHunt hider = gameInstance.getHiderCloaked(location);
					        	if(hider != null && gameInstance.isPlayer(event.getPlayer()) && !event.getPlayer().getUniqueId().equals(hider.getPlayer().getUniqueId()))
					        	{
					        		PacketContainer fakeBlock = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
					        		fakeBlock.getBlockPositionModifier().write(0, position);
					        		fakeBlock.getIntegers().write(0, Dictionaries.getNetworkIdFromMaterial(hider.getGeneric().getBlock()) + hider.getGeneric().getBlockMeta()-1);
					        		//fakeBlock.getBlockData().write(0, WrappedBlockData.createData(Material.valueOf(Yaml.getFieldString(hider.getUniqueId().toString()+".blockhunt.block", "internal")))); 
					        		event.setPacket(fakeBlock);
				        		}
				        	}
			        	}
			        }
			    }
			  }
		);
	}
	
}
