package me.murrobby.igsq.bungee.commands;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.murrobby.igsq.bungee.Messaging;
import me.murrobby.igsq.bungee.security.Common_Security;
import me.murrobby.igsq.bungee.Database;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TwoFA_Command extends Command implements TabExecutor
{
	public TwoFA_Command() 
	{
		super("2FA");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) 
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			String current_status = null;
			try 
			{
				if(Database.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
				{
					ResultSet current_StatusDB  = Database.QueryCommand("SELECT current_status from discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
					current_StatusDB.next();
					current_status = current_StatusDB.getString(1);
					
				}
			}
			catch(Exception exception) 
			{
				exception.printStackTrace();
			}
			if(args.length >= 1 && args[0].equalsIgnoreCase("enabled")) 
			{
				if(args.length == 2) 
				{
					if(CanModify2FA(player,current_status)) 
					{
						if(args[1].equalsIgnoreCase("true")) 
						{
							Database.UpdateCommand("UPDATE discord_2fa SET current_status = 'pending', ip = NULL WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF002FA Security Enabled!")));
						}
						else
						{
							Database.UpdateCommand("UPDATE discord_2fa SET current_status = null, ip = NULL WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FF00002FA Security Disabled!")));
						}
					}
					else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FF0000You cannot modify 2FA settings at this time. Are you logged in? Are you a staff member?")));
				}
				else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FFFF00Use /2fa enabled [true/false]")));
			}
			else if(args.length >= 1 && args[0].equalsIgnoreCase("confirm")) 
			{
				if(args.length == 2) 
				{
					Common_Security.codeConfirm(player,args[1]);
				} 
			}
			else if(args.length >= 1 && args[0].equalsIgnoreCase("logout")) 
			{
				Database.UpdateCommand("UPDATE discord_2fa SET current_status = 'expired',ip = null,code = null WHERE uuid = '" +  player.getUniqueId().toString() +"';");
				player.disconnect(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF00Logout Successful. Goodbye!")));
			}
			else
			{
				player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFCurrent 2fa Status")));
				if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND current_status = 'linked';") >= 1) 
				{
					if(Database.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
					{
						ResultSet discord_2fa = Database.QueryCommand("SELECT current_status,code FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
						try 
						{
							if(discord_2fa.next()) 
							{
								current_status = discord_2fa.getString(1);
								String code = discord_2fa.getString(2);
								if(current_status == null || current_status.equalsIgnoreCase("")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD00002fa Disabled.")));
								else if(current_status.equalsIgnoreCase("pending")) 
								{
									if(code == null || code.equalsIgnoreCase("")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#ffb900IGSQbot is unresponsive! We will let you in until a code is generated.")));
									else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FFFF00Waiting For Discord Code! Your linked account should get a message from igsqbot.")));
								}
								else if(current_status.equalsIgnoreCase("accepted")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF002FA Enabled and Accepted!")));
								else if(current_status.equalsIgnoreCase("expired")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#C8C8C82FA Enabled, On Standby!")));
								else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000 Unknown Issue.")));
							}
						}
						catch (SQLException e)
						{
							e.printStackTrace();
							player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FF0000Something Went Wrong When Trying To Read Your 2fa status.")));
						}
					}
					else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD00002fa Disabled.")));
					if(player.hasPermission("igsq.require2fa")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#C8C8C8As a staff member your account will always be protected by 2FA!")));
					else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFUse &#FFFF00/2fa enabled [true/false] &#00FFFFto enable or disable 2fa.")));
					player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFUse &#FFFF00/2fa confirm [code_from_IGSQbot] &#00FFFFto confirm a pending discord 2fa request.")));
					player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFUse &#FFFF00/2fa logout &#00FFFFto logout completly and reset any \"rememberme\" options.")));
					player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFType &#FFFF00/2fa &#00FFFFTo See This Summary Again.")));
				}
				else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD00002FA Disabled. You need to link your account to discord first before you can use 2FA.\nSee &#FFFF00/link &#CD0000for more details!")));
			}
		}
		else sender.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000Sorry, but only Players can go into 2FA settings!")));
	}
	
	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) 
	{
		ProxiedPlayer player = null;
		String current_status = null;
		if(sender instanceof ProxiedPlayer) 
		{
			player = (ProxiedPlayer) sender;
		}
		try 
		{
			if(Database.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
			{
				ResultSet current_StatusDB  = Database.QueryCommand("SELECT current_status from discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
				current_StatusDB.next();
				current_status = current_StatusDB.getString(1);
				
			}
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
		List<String> options = new ArrayList<String>();
		if(args.length == 1) 
		{
			String[] types = null;
			if(CanModify2FA(player,current_status)) types = new String[]{"enabled","help","confirm","logout"};
			else types = new String[]{"help","confirm","logout"};
			for (String commands : types) if(commands.contains(args[0].toLowerCase())) options.add(commands);
		}
		else if(args.length == 2 && args[0].equalsIgnoreCase("enabled") && CanModify2FA(player,current_status))
		{
			String[] types = {"true","false"};
			for (String commands : types) if(commands.contains(args[1].toLowerCase())) options.add(commands);

		}
		return options;
	}
	private boolean CanModify2FA(ProxiedPlayer player,String current_status) 
	{
		if (player.hasPermission("igsq.require2fa")) return false;
		if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND current_status = 'linked';") == 0) return false;
		if (current_status == null) return true;
		if (current_status.equalsIgnoreCase("")) return true;
		if (current_status.equalsIgnoreCase("accepted")) return true;
		return false;
	}
}
