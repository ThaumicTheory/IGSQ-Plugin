package thaumictheory.igsq.spigot.main;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import thaumictheory.igsq.shared.Role;
import thaumictheory.igsq.shared.RoleType;
import thaumictheory.igsq.shared.YamlPlayerWrapper;
import thaumictheory.igsq.spigot.Common;
import thaumictheory.igsq.spigot.Communication;

public class PlayServerPlayerInfo_Main implements Listener
{
	public PlayServerPlayerInfo_Main()
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
			        		event.setPacket(tagEvent(event.getPlayer()));
			        	}
			        }
			    }
			  }
		);
	}
    private PacketContainer tagEvent(Player player) 
    {
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
		if(Common.isCurrentNameController("main", player)) 
		{
			String name = player.getName();
			if(yaml.isLinked()) name = yaml.getNickname();
			return Communication.setTagAsPacket(player, Role.getHighestTagPrefix(player.getUniqueId(), RoleType.EITHER),name,Role.getHighestTagSuffix(player.getUniqueId(), RoleType.EITHER));
		}
		return null;
    }
}
