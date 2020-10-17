package me.murrobby.igsq.spigot;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Communication 
{

	public static void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		if(channel.equals("igsq:yml")) 
		{
	        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			try
			{
				String fileName = in.readUTF();
				String path = in.readUTF();
				String data = in.readUTF();
				if(data.equalsIgnoreCase("false") || data.equalsIgnoreCase("true")) Yaml.updateField(path, fileName, Boolean.valueOf(data));
				else if(Integer.getInteger(data) != null) Yaml.updateField(path, fileName, Integer.getInteger(data));
				else Yaml.updateField(path, fileName, data);
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
}
