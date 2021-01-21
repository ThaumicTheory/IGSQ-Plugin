package me.murrobby.igsq.bungee;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Communication 
{
    public static void sendSound(ProxiedPlayer player,String sound,float volume,float pitch) 
    {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		DataOutputStream out = new DataOutputStream(stream);
    		try
    		{
    			out.writeUTF(Common_Shared.removeNull(sound));
    			out.writeFloat(volume);
    			out.writeFloat(pitch);
    			player.getServer().getInfo().sendData("igsq:sound", stream.toByteArray());
    		}
    		catch (IOException e)
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
    public static void sendConfigUpdate(String path,String fileName,String data,ProxiedPlayer player) 
    {
    			ByteArrayOutputStream stream = new ByteArrayOutputStream();
        		DataOutputStream out = new DataOutputStream(stream);
        		try
        		{
        			out.writeInt(0);//Strings id
        			out.writeUTF(Common_Shared.removeNull(fileName));
        			out.writeUTF(Common_Shared.removeNull(path));
        			out.writeUTF(Common_Shared.removeNull(data));
        			player.getServer().getInfo().sendData("igsq:yml", stream.toByteArray());
        		}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    	}
    public static void sendConfigUpdate(String path,String fileName,String data) 
    {
    	ArrayList<String> servers = new ArrayList<>();
    	for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) 
    	{
    		Boolean serverChecked = false;
    		for (String serversDone : servers) 
    		{
    			if(player.getServer() != null && serversDone.equals(player.getServer().getInfo().getName())) 
    			{
    				serverChecked = true;
    				break;
    			}
    		}
    		if((!serverChecked) && player.getServer() != null) 
    		{
    			servers.add(player.getServer().getInfo().getName());
    			ByteArrayOutputStream stream = new ByteArrayOutputStream();
        		DataOutputStream out = new DataOutputStream(stream);
        		try
        		{
        			out.writeInt(0);//Strings id
        			out.writeUTF(Common_Shared.removeNull(fileName));
        			out.writeUTF(Common_Shared.removeNull(path));
        			out.writeUTF(Common_Shared.removeNull(data));
        			player.getServer().getInfo().sendData("igsq:yml", stream.toByteArray());
        		}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    		}
    	}
    }
    public static void sendConfigUpdate(String path,String fileName,int data) 
    {
    	ArrayList<String> servers = new ArrayList<>();
    	for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) 
    	{
    		Boolean serverChecked = false;
    		for (String serversDone : servers) 
    		{
    			if(player.getServer() != null && serversDone.equals(player.getServer().getInfo().getName())) 
    			{
    				serverChecked = true;
    				break;
    			}
    		}
    		if((!serverChecked) && player.getServer() != null) 
    		{
				servers.add(player.getServer().getInfo().getName());
    			ByteArrayOutputStream stream = new ByteArrayOutputStream();
        		DataOutputStream out = new DataOutputStream(stream);
        		try
        		{
        			out.writeInt(1);//Ints id
        			out.writeUTF(Common_Shared.removeNull(fileName));
        			out.writeUTF(Common_Shared.removeNull(path));
        			out.writeInt(data);
        			player.getServer().getInfo().sendData("igsq:yml", stream.toByteArray());
        		}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    		}
    	}
    }
    public static void sendConfigUpdate(String path,String fileName,boolean data) 
    {
    	ArrayList<String> servers = new ArrayList<>();
    	for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) 
    	{
    		Boolean serverChecked = false;
    		for (String serversDone : servers) 
    		{
    			if(player.getServer() != null && serversDone.equals(player.getServer().getInfo().getName())) 
    			{
    				serverChecked = true;
    				break;
    			}
    		}
    		if((!serverChecked) && player.getServer() != null) 
    		{
    			servers.add(player.getServer().getInfo().getName());
    			ByteArrayOutputStream stream = new ByteArrayOutputStream();
        		DataOutputStream out = new DataOutputStream(stream);
        		try
        		{
        			out.writeInt(2);//Booleans id
        			out.writeUTF(Common_Shared.removeNull(fileName));
        			out.writeUTF(Common_Shared.removeNull(path));
        			out.writeBoolean(data);
        			player.getServer().getInfo().sendData("igsq:yml", stream.toByteArray());
        		}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
    		}
    	}
    }
}
