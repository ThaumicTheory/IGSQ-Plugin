package thaumictheory.igsq.bungee;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import thaumictheory.igsq.shared.IGSQ;

public class Communication 
{
    public static void sendSound(ProxiedPlayer player,String sound,float volume,float pitch) 
    {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		DataOutputStream out = new DataOutputStream(stream);
    		try
    		{
    			out.writeUTF(IGSQ.removeNull(sound));
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
    public static void sendTargetedConfigUpdate(String node,String path,Object data,ProxiedPlayer player) 
    {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				try
				{
		    		ObjectOutputStream out = new ObjectOutputStream(stream);
					out.writeUTF(IGSQ.removeNull(node));
					out.writeUTF(IGSQ.removeNull(path));
					out.writeObject(data);
					player.getServer().getInfo().sendData("igsq:yaml", stream.toByteArray());
				}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
	}
    public static void sendConfigUpdate(String path,String fileName,Object data) 
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
    			sendTargetedConfigUpdate(path, fileName, data, player);
    		}
    	}
    }
}
