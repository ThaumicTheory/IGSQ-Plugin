package me.murrobby.igsq.spigot.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import me.murrobby.igsq.spigot.Common;
import me.murrobby.igsq.spigot.Messaging;

public class Test_Command {

	private CommandSender sender;
	private String[] args = new String[0];
	public Boolean result;
	public Test_Command(CommandSender sender,String[] args) 
	{
		
		this.sender = sender;
		this.args = args;
		result = testQuery();
	}
	private Boolean test() 
	{
		int id = 0;
		if(sender instanceof Player && args.length >= 1) 
		{
			Player player = (Player) sender;
			if(args[0].equalsIgnoreCase("generate")) 
			{
				Location location = player.getLocation();
				location.setY(70);
				double x = 1.5;
				double z = 1.5;
				location.setX(x);
				location.setZ(z);
				int airCount = 0;
				Material lastBlock = null;
				String message = "";
				while(airCount <= 5) 
				{
					if(location.getBlock().getType() != lastBlock && (id == 0 || !location.getBlock().getType().isAir())) 
					{
						lastBlock = location.getBlock().getType();
						message += "blockDictionary.put(Material." + location.getBlock().getType()+ ", "+ id+ ");";
					}
					
					
					/*
					int entityID = (int) (Math.random() * Integer.MAX_VALUE);
					PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
					packet.getIntegers().write(0,entityID);
					packet.getUUIDs().write(0, UUID.randomUUID());
					packet.getEntityTypeModifier().write(0, EntityType.FALLING_BLOCK);
					packet.getDoubles().write(0, x);
					packet.getDoubles().write(1, (double) 71);
					packet.getDoubles().write(2, z);
					packet.getIntegers().write(4,0); //Angle Pitch
					packet.getIntegers().write(5,0); //Angle Yaw
					packet.getIntegers().write(6, id);
					try 
					{
						ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
					}
					catch (InvocationTargetException e) 
					{
						e.printStackTrace();
					}
					
					
					Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
			    	{

						@Override
						public void run() 
						{
							PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
							packet.getIntegerArrays().write(0, new int[]{entityID});
							try 
							{
								ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
							} 
							catch (InvocationTargetException e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
			    	},200);
			    	*/
					
					
					z+=2;
					if(z > 261.5) //Ignore last row because it is duplicated
					{
						x+=2;
						z=1.5;
					} 
					if(location.getBlock().getType().isAir()) airCount++;
					else airCount = 0;
					
					
					id++;
					location.setX(x);
					location.setZ(z);
				}
				System.out.println(message);
				return true;
			}
			else 
			{
				try 
				{
					id = Integer.parseInt(args[0]);
					player.sendMessage(Messaging.chatFormatter("&#CCCCCCSpawning block with id &#00FF00"+ id + "."));
				}
				catch(Exception exception) 
				{
					player.sendMessage(Messaging.chatFormatter("&#FF0000Failed to parse ID!"));
				}
				int entityID = (int) (Math.random() * Integer.MAX_VALUE);
				PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
				packet.getIntegers().write(0,entityID);
				packet.getUUIDs().write(0, UUID.randomUUID());
				packet.getEntityTypeModifier().write(0, EntityType.FALLING_BLOCK);
				packet.getDoubles().write(0, player.getLocation().getX()+2);
				packet.getDoubles().write(1, player.getLocation().getY()+1);
				packet.getDoubles().write(2, player.getLocation().getZ());
				packet.getIntegers().write(4,0); //Angle Pitch
				packet.getIntegers().write(5,0); //Angle Yaw
				packet.getIntegers().write(6, id);
				try {
					ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
				}
				catch (InvocationTargetException e) 
				{
					
					e.printStackTrace();
				}
				Common.spigot.scheduler.scheduleSyncDelayedTask(Common.spigot, new Runnable()
		    	{

					@Override
					public void run() 
					{
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
						packet.getIntegerArrays().write(0, new int[]{entityID});
						try {
							ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		    	},100);
				return true;
			}
		}
		return false;
	}
	private Boolean testQuery() 
	{
			if(Common_Command.requirePermission("igsq.test",sender)) 
			{
				if(test()) 
				{
					return true;
				}
				else 
				{
					sender.sendMessage(Messaging.chatFormatter("&#FFFF00test [redacted]"));
					return false;
				}
			}
			else 
			{
				sender.sendMessage(Messaging.chatFormatter("&#CD0000You cannot Execute this command!\nThis may be due to being the wrong type or not having the required permission"));
	  			return false;
			}
	}
}
