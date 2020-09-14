package me.murrobby.igsq.bungee.main;

import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.bungee.Main_Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent_Bungee implements Listener
{
	private Main_Bungee plugin;
	public ChatEvent_Bungee(Main_Bungee plugin)
	{
		this.plugin = plugin;
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
	}
	
	@EventHandler
	public void Chat_Bungee(net.md_5.bungee.api.event.ChatEvent event) 
	{
		//Runs on bungee whenever a chat message is sent
		if(event.getSender() instanceof ProxiedPlayer) 
		{
			ProxiedPlayer player = (ProxiedPlayer) event.getSender();
			if(event.isCommand()) 
			{
				String server = null;
				if(event.isProxyCommand()) 
				{
					server = "PROXY";
				}
				else 
				{
					server = player.getServer().getInfo().getName().toUpperCase();
				}
				System.out.println(Common_Bungee.getFormattedMessageConsole("commandwatch",new String[] {"<player>",player.getName(),"<command>",event.getMessage(),"<server>",server}));
				if(!player.hasPermission("igsq.commandwatchbypass"))
				{
					for(ProxiedPlayer selectedPlayer : plugin.getProxy().getPlayers())
					{
						if(selectedPlayer.hasPermission("igsq.commandwatch") && selectedPlayer != player)
						{
							selectedPlayer.sendMessage(Common_Bungee.getFormattedMessage("commandwatch",new String[] {"<player>",player.getName(),"<command>",event.getMessage(),"<server>",server}));
						}
					}
				}
			}
			else
			{
				
				for(ProxiedPlayer selectedPlayer : plugin.getProxy().getPlayers())
				{
					if(player == selectedPlayer) continue;
					Boolean sendmessage = false;
					String[] message = event.getMessage().split(" ");
					String discordUsername = Common_Bungee.getFieldString(selectedPlayer.getUniqueId() + ".discord.username", "player");
					for(String string : message) 
					{

						if(string.equalsIgnoreCase("@" + selectedPlayer.getName()))
						{
							sendmessage = true;
						}
					}
					if(sendmessage || ((discordUsername != null && (!discordUsername.equalsIgnoreCase("")) && event.getMessage().contains("@" + discordUsername)))) 
					{
						try
						{
							Common_Bungee.sendSound(selectedPlayer, "BLOCK_NOTE_BLOCK_PLING", 1, 1);
							if(!selectedPlayer.getServer().getInfo().getName().equals(player.getServer().getInfo().getName())) 
							{
								selectedPlayer.sendMessage(Common_Bungee.getFormattedMessage("mention", new String[] {"<mentioner>",player.getName(),"<mentionerserver>",player.getServer().getInfo().getName().toUpperCase(),"<message>",event.getMessage()}));
							}
						}
						catch(Exception exception) 
						{
							exception.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}
}
