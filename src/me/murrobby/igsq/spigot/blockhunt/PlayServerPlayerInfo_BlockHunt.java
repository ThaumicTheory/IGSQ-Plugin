package me.murrobby.igsq.spigot.blockhunt;

import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.YamlWrapper;

public class PlayServerPlayerInfo_BlockHunt implements Listener
{
	public PlayServerPlayerInfo_BlockHunt()
	{
		Common.protocol.addPacketListener
		(
			new PacketAdapter(Common.spigot, ListenerPriority.NORMAL,PacketType.Play.Server.PLAYER_INFO) 
			{
				@Override
			    public void onPacketSending(PacketEvent event) 
			    {
			        if (!event.isCancelled() && YamlWrapper.isBlockHunt() && Common.isCurrentNameController("blockhunt", event.getPlayer()) && Game_BlockHunt.getPlayersGame(event.getPlayer()) != null)
			        {
			        	if(event.getPacketType() == PacketType.Play.Server.PLAYER_INFO)
			        	{
			        		event.setPacket(Common_BlockHunt.tagEvent(event.getPlayer()));
			        	}
			        }
			    }
			  }
		);
	}
	
}
