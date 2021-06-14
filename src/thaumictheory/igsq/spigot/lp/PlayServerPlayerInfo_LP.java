package thaumictheory.igsq.spigot.lp;

import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import thaumictheory.igsq.spigot.Common;

public class PlayServerPlayerInfo_LP implements Listener
{
	public PlayServerPlayerInfo_LP()
	{
		ProtocolLibrary.getProtocolManager().addPacketListener
		(
			new PacketAdapter(Common.spigot, ListenerPriority.NORMAL,PacketType.Play.Server.PLAYER_INFO) 
			{
				@Override
			    public void onPacketSending(PacketEvent event) 
			    {
			        if (!event.isCancelled() && Common.isCurrentNameController("main", event.getPlayer()))
			        {
			        	if(event.getPacketType() == PacketType.Play.Server.PLAYER_INFO)
			        	{
			        		event.setPacket(Common_LP.tagEvent(event.getPlayer()));
			        	}
			        }
			    }
			  }
		);
	}
	
}
