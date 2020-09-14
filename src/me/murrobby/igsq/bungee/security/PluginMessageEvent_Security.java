package me.murrobby.igsq.bungee.security;

import java.io.IOException;
import java.util.Arrays;
import me.murrobby.igsq.bungee.Main_Bungee;
import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageEvent_Security implements Listener
{
	public PluginMessageEvent_Security(Main_Bungee plugin)
	{
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void PluginMessage_Security(net.md_5.bungee.api.event.PluginMessageEvent event) 
	{
		if(!event.isCancelled()) 
		{
			System.out.println("Channel was: " + event.getTag());
			if(event.getTag().equalsIgnoreCase("REGISTER")) 
			{
		        try 
				{
					if(event.getData()[0] == 2) //Modlist
					{
						String[][] mods = getModList(event.getData());
						System.out.println(mods[0][0]);
					}
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private String[][] getModList(byte[] modList) throws IOException
    {
        String[] modNames = new String[0];
        String[] modVersions = new String[0];
        Boolean isModName = true;     
        for (int i=2; i < modList.length; isModName = !isModName)
        {
            int skipTo =  i+modList[i]+1;
        	String string = new String(Arrays.copyOfRange(modList,i+1,skipTo));
            if (isModName) modNames = Common_Shared.append(modNames,string);
            else modVersions = Common_Shared.append(modVersions,string);
            System.out.println(string);
            i = skipTo;
        }
        String[][] modData = new String[modNames.length][2];
        for(int i = 0;i < modNames.length;i++) 
        {
        	modData[i][0] = modNames[i];
        	modData[i][1] = modVersions[i];
        }
        return modData;
    }
}
