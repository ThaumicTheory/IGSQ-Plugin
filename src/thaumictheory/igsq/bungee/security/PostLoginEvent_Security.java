package thaumictheory.igsq.bungee.security;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import thaumictheory.igsq.bungee.Common;
import thaumictheory.igsq.bungee.Database;
import thaumictheory.igsq.bungee.Messaging;
import thaumictheory.igsq.bungee.lp.Common_LP;
import thaumictheory.igsq.bungee.yaml.YamlWrapper;
import thaumictheory.igsq.shared.IGSQ;
import thaumictheory.igsq.shared.YamlPlayerWrapper;

public class PostLoginEvent_Security implements Listener
{
	public PostLoginEvent_Security()
	{
		ProxyServer.getInstance().getPluginManager().registerListener(Common.bungee, this);
	}
	
	@EventHandler
	public void PostLogin_Security(net.md_5.bungee.api.event.PostLoginEvent event) 
	{
		//Runs on bungee connect complete
		ProxiedPlayer player = event.getPlayer();
		YamlPlayerWrapper yaml = new YamlPlayerWrapper(player.getUniqueId());
		yaml.applyDefault();
		String playerUUID = player.getUniqueId().toString();
		int playerProtocol = player.getPendingConnection().getVersion();
		int recomendedProtocol = YamlWrapper.getRecommendedProtocol();
		//Integer recomendedProtocol = Integer.parseInt(Yaml.getFieldString("SUPPORT.protocol.recommended", "config"));
		if(playerProtocol == recomendedProtocol) 
		{
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF00Your Minecraft is running the recommended version! :)")));
		}
		else if( playerProtocol > recomendedProtocol) 
		{
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#ffb900Your Minecraft is running a newer version than the server! \nIssues may arise.")));
		}
		else 
		{
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&cYour Minecraft is running a older version than the server! \nIssues will arise! Most Servers Will Be Inaccessible!")));
		}
		
		if(Database.scalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ playerUUID + "' AND current_status = 'Linked';") == 1) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF00Your account is already Linked! :)")));
		else if(Database.scalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ playerUUID + "' AND current_status = 'mwait';") >= 1)
		{
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#ffb900Your account has a pending link/s. Do &#FFFF00/link &#ffb900to learn more.")));
			linkMessage(player);
		}
		else if(Database.scalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ playerUUID + "' AND current_status = 'dwait';") >= 1)
		{
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#C8C8C8Your account has a pending outbound link/s. Do &#FFFF00.mclink on discord.")));
			linkMessage(player);
			
		}
		else
		{
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000No Pending Account Links Found. &#FFFF00You can link your account to discord with /link add [discord_username/id]")));
			linkMessage(player);
		}
		
		
		String currentStatus = yaml.getStatus();
		String id = IGSQ.removeNull(yaml.getID());
		if((!id.equalsIgnoreCase(""))  && (currentStatus == null || currentStatus.equalsIgnoreCase(""))) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#a900ffYou can enable 2FA for increased account security! &#FFFF00 See /2fa for more details.")));
	}
	
	private void linkMessage(ProxiedPlayer player) 
	{
		player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00b7ffIf you want your rank portrayed on discord and not &#006e99[&#00b7ffSquirrel&#006e99] &#00b7ffyou will have to link your account.\n&#C0C0C0Linking also unlocks the ability to use our 2FA security feature.")));
	}
	
}
