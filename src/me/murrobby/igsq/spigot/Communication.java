package me.murrobby.igsq.spigot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;

import me.murrobby.igsq.shared.Common_Shared;

import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

public class Communication 
{

	public static void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		if(channel.equals("igsq:yml")) 
		{
	        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			try
			{
		        int dataType = in.readInt();
		        String fileName = in.readUTF();
				String path = in.readUTF();
				switch(dataType) 
				{
					case 0: //String
			        	String dataString = in.readUTF();
			        	Yaml.updateField(path, fileName, dataString);
						break;
					case 1: //Int
			        	int dataInt = in.readInt();
			        	Yaml.updateField(path, fileName, dataInt);
						break;
					case 2: //Boolean
			        	boolean dataBool = in.readBoolean();
			        	Yaml.updateField(path, fileName, dataBool);
						break;
					default:
						break;
				}
				
		        /*
				if(data.equalsIgnoreCase("false") || data.equalsIgnoreCase("true")) Yaml.updateField(path, fileName, Boolean.valueOf(data));
				else if(Integer.getInteger(data) != null) Yaml.updateField(path, fileName, Integer.getInteger(data));
				else Yaml.updateField(path, fileName, data);
				*/
			}
			catch (IOException e)
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
	public static void setTag(Player player,String tag) 
	{
		try 
		{
			Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
			int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
			
			PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
			packet.getPlayerInfoAction().write(0, PlayerInfoAction.ADD_PLAYER);
			List<PlayerInfoData> data = new ArrayList<PlayerInfoData>();
			WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
			profile.withName(tag);
			NativeGameMode gameMode =  NativeGameMode.fromBukkit(player.getGameMode());
			WrappedChatComponent name = WrappedChatComponent.fromText(tag);
			data.add(new PlayerInfoData(profile, ping, gameMode, name));
			packet.getPlayerInfoDataLists().write(0, data);
			for(Player selectedPlayer : Bukkit.getOnlinePlayers())
			{
				 ProtocolLibrary.getProtocolManager().sendServerPacket(selectedPlayer, packet);
			}
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
		
	}
	public static void requestYaml(String path,String fileName,int dataType) 
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(stream);
		try
		{
			out.writeInt(dataType);
			out.writeUTF(Common_Shared.removeNull(fileName));
			out.writeUTF(Common_Shared.removeNull(path));
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
		try 
		{
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
		}
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		
	}
}