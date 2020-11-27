package me.murrobby.igsq.bungee.main;

import me.murrobby.igsq.bungee.Common;
import me.murrobby.igsq.bungee.Communication;
import me.murrobby.igsq.bungee.Messaging;
import me.murrobby.igsq.bungee.Yaml;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent_Bungee implements Listener
{
	public ChatEvent_Bungee()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
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
				System.out.println(Messaging.getFormattedMessageConsole("commandwatch",new String[] {"<player>",player.getName(),"<command>",event.getMessage(),"<server>",server}));
				if(!player.hasPermission("igsq.commandwatchbypass"))
				{
					for(ProxiedPlayer selectedPlayer : Common.bungee.getProxy().getPlayers())
					{
						if(selectedPlayer.hasPermission("igsq.commandwatch") && selectedPlayer != player)
						{
							selectedPlayer.sendMessage(Messaging.getFormattedMessage("commandwatch",new String[] {"<player>",player.getName(),"<command>",event.getMessage(),"<server>",server}));
						}
					}
				}
			}
			else
			{
				
				for(ProxiedPlayer selectedPlayer : Common.bungee.getProxy().getPlayers())
				{
					if(player.getUniqueId().equals(selectedPlayer.getUniqueId())) continue;
					Boolean sendmessage = false;
					String[] message = event.getMessage().split(" ");
					String discordUsername = Yaml.getFieldString(selectedPlayer.getUniqueId() + ".discord.username", "player");
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
							Communication.sendSound(selectedPlayer, "BLOCK_NOTE_BLOCK_PLING", 1, 1);
							if(!selectedPlayer.getServer().getInfo().getName().equals(player.getServer().getInfo().getName())) 
							{
								selectedPlayer.sendMessage(Messaging.getFormattedMessage("mention", new String[] {"<mentioner>",player.getName(),"<mentionerserver>",player.getServer().getInfo().getName().toUpperCase(),"<message>",event.getMessage()}));
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
