package thaumictheory.igsq.spigot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;

import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlPlayerWrapper;

import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

public class Communication 
{
	private static Hashtable<UUID, String> nameHashTable = new Hashtable<UUID, String>();
	private static Hashtable<UUID, String> prefixHashTable = new Hashtable<UUID, String>();
	private static Hashtable<UUID, String> suffixHashTable = new Hashtable<UUID, String>();
	private static ArrayList<String> teams = new ArrayList<String>();

	public static void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		if(channel.equals("igsq:yaml")) 
		{
			try
			{
		        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(message));
				String node = in.readUTF();
		        String path = in.readUTF();
		        Object data = in.readObject();
	        	IGSQ.getYaml().setField(node, path, data);
			}
			catch (Exception e)
			{
				Messaging.sendException(e,"Plugin Messaging Channel For Configuration Failed.","REDSTONE_LAMP", player);
			}
		}
		else if(channel.equals("igsq:sound")) 
		{
	        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			try
			{
				String sound = in.readUTF();
				float volume = in.readFloat();
				float pitch = in.readFloat();
				player.playSound(player.getLocation(), Sound.valueOf(sound.toUpperCase()), volume, pitch);
			}
			catch (IOException e)
			{
				Messaging.sendException(e,"Plugin Messaging Channel For Sound Failed.","GLOWSTONE", player);
			}
		}
	}
	public static void setTag(Player player,String prefix,String tag,String suffix) 
	{
		PacketContainer packet = setTagAsPacket(player,prefix,tag,suffix);
		for(Player selectedPlayer : Bukkit.getOnlinePlayers())
		{
			ProtocolLibrary.getProtocolManager().sendServerPacket(selectedPlayer, packet);
		}
	}
	public static void deletePlayer(Player player) 
	{
		PacketContainer fakeTeam = new PacketContainer(PacketType.Play.Server.SCOREBOARD_TEAM);
		fakeTeam.getStrings().write(0, player.getUniqueId().toString().substring(0, 16));
		fakeTeam.getIntegers().write(0,1);
		for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
		{
			ProtocolLibrary.getProtocolManager().sendServerPacket(selectedPlayer, fakeTeam);
		}
		teams.remove(player.getUniqueId().toString());
		prefixHashTable.remove(player.getUniqueId());
		suffixHashTable.remove(player.getUniqueId());
		nameHashTable.remove(player.getUniqueId());
	}
	public static void setDefaultTagData(Player player) 
	{
		prefixHashTable.put(player.getUniqueId(), "");
		suffixHashTable.put(player.getUniqueId(), "");
		refreshTagUsername(player);
		
		sendTeamsToPlayer(player);
		createNewTeamForPlayer(player);
	}
	private static void sendTeamsToPlayer(Player player) 
	{
		for(String team : teams) 
		{
			Player selectedPlayer = Bukkit.getPlayer(UUID.fromString(team));
			if(selectedPlayer != null) 
			{
				PacketContainer fakeTeam = new PacketContainer(PacketType.Play.Server.SCOREBOARD_TEAM);
				fakeTeam.getStrings().write(0, team.substring(0, 16));
				fakeTeam.getIntegers().write( 0, 0 );
				//fakeTeam.getIntegers().write(1, 0);
				//fakeTeam.getIntegers().write( 1, 1 );
				List<String> playerList = new ArrayList<String>();
				playerList.add(nameHashTable.get(selectedPlayer.getUniqueId()));
				fakeTeam.getSpecificModifier(Collection.class).write(0, playerList);
				ProtocolLibrary.getProtocolManager().sendServerPacket(player, fakeTeam);
			}
		}
	}
	private static void createNewTeamForPlayer(Player player) 
	{
		try
		{
			PacketContainer fakeTeam = new PacketContainer(PacketType.Play.Server.SCOREBOARD_TEAM);
			fakeTeam.getStrings().write(0, player.getUniqueId().toString().substring(0, 16));
			fakeTeam.getIntegers().write( 0, 0);
			//fakeTeam.getIntegers().write(1, 0);
			//fakeTeam.getIntegers().write( 1, 1 );
			List<String> playerList = new ArrayList<String>();
			playerList.add(nameHashTable.get(player.getUniqueId()));
			fakeTeam.getSpecificModifier(Collection.class).write(0, playerList);
			teams.add(player.getUniqueId().toString());
			for(Player selectedPlayer : Bukkit.getOnlinePlayers())
			{
				 ProtocolLibrary.getProtocolManager().sendServerPacket(selectedPlayer, fakeTeam);
			}
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}
	public static void setDefaultTagDataReload(Player player) 
	{
		prefixHashTable.put(player.getUniqueId(), "");
		suffixHashTable.put(player.getUniqueId(), "");
		refreshTagUsername(player);
	}
	public static void setDefaultTagData(UUID player) 
	{
		setDefaultTagData(Bukkit.getPlayer(player));
	}
	private static void refreshTagUsername(Player player) 
	{
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
		String name = player.getName();
		if(yaml.isLinked()) name = convertToSafe(yaml.getNickname());
		nameHashTable.put(player.getUniqueId(), name);
	}
	public static String convertToSafe(String tag) 
	{
		tag = tag.replaceAll("[^A-Za-z-0-9 _]", "");
		while(tag.startsWith(" ")) 
		{
			tag = tag.substring(1, tag.length());
		}
		while(tag.endsWith(" ")) 
		{
			tag = tag.substring(0, tag.length()-1);
		}
		if(tag.length() > 16) tag = tag.substring(0, 16);
		return tag;
	}
	public static PacketContainer setTagAsPacket(Player player,String prefix,String tag,String suffix) 
	{
		tag = convertToSafe(tag);
		ChatColor nameColor = Messaging.getLastLegacyColourByText(tag);
		nameHashTable.put(player.getUniqueId(), tag);
		prefixHashTable.put(player.getUniqueId(), Messaging.chatFormatter(prefix));
		suffixHashTable.put(player.getUniqueId(), Messaging.chatFormatter(suffix));
		List<PlayerInfoData> data = new ArrayList<PlayerInfoData>();
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
		try 
		{
			packet.getPlayerInfoAction().write(0, PlayerInfoAction.ADD_PLAYER);
			for(Player selectedPlayer : Bukkit.getOnlinePlayers()) 
			{
				WrappedGameProfile profile = WrappedGameProfile.fromPlayer(selectedPlayer);
				WrappedGameProfile newProfile = profile.withName(nameHashTable.get(selectedPlayer.getUniqueId()));
				NativeGameMode gameMode =  NativeGameMode.fromBukkit(selectedPlayer.getGameMode());
				data.add(new PlayerInfoData(newProfile, selectedPlayer.getPing(), gameMode, WrappedChatComponent.fromText(Messaging.chatFormatter(prefixHashTable.get(selectedPlayer.getUniqueId()) + nameHashTable.get(selectedPlayer.getUniqueId()) + suffixHashTable.get(selectedPlayer.getUniqueId())))));
			}
			packet.getPlayerInfoDataLists().write(0, data);
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
		
		
		try
		{
			PacketContainer fakeTeam = new PacketContainer(PacketType.Play.Server.SCOREBOARD_TEAM);
			fakeTeam.getStrings().write(0, player.getUniqueId().toString().substring(0, 16));
			fakeTeam.getIntegers().write( 0, 2);
			
			fakeTeam.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, nameColor);
			fakeTeam.getChatComponents().write( 0, WrappedChatComponent.fromText(player.getName()));
			fakeTeam.getChatComponents().write( 1,  WrappedChatComponent.fromText(Messaging.chatFormatter(prefix)));
			fakeTeam.getChatComponents().write( 2,  WrappedChatComponent.fromText(Messaging.chatFormatter(suffix + " &8" + player.getName())));
			for(Player selectedPlayer : Bukkit.getOnlinePlayers())
			{
				 ProtocolLibrary.getProtocolManager().sendServerPacket(selectedPlayer, fakeTeam);
			}
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
		
		
		return packet;
		
	}
	public static void requestYaml(String path,String fileName) 
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(stream);
		try
		{
			out.writeUTF(IGSQ.removeNull(fileName));
			out.writeUTF(IGSQ.removeNull(path));
			Common.spigot.getServer().sendPluginMessage(Common.spigot, "igsq:ymlreq", stream.toByteArray());
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
		
	}
	public static void stopSound(Player player,SoundCategory type) 
	{
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.STOP_SOUND);
		//packet.getBytes().write(0, (byte) 0x1);
		packet.getSoundCategories().write(0, EnumWrappers.SoundCategory.valueOf(type.toString()));
		ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
		
	}
}
