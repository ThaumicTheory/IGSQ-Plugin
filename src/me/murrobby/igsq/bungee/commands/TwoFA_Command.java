package me.murrobby.igsq.bungee.commands;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.bungee.Database_Bungee;
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
				if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
				{
					ResultSet current_StatusDB  = Database_Bungee.QueryCommand("SELECT current_status from discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
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
							Database_Bungee.UpdateCommand("UPDATE discord_2fa SET current_status = 'pending', ip = NULL WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#00FF002FA Security Enabled!")));
						}
						else
						{
							Database_Bungee.UpdateCommand("UPDATE discord_2fa SET current_status = null, ip = NULL WHERE uuid = '" +  player.getUniqueId().toString() +"';");
							player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#FF00002FA Security Disabled!")));
						}
					}
					else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#FF0000You cannot modify 2FA settings at this time. Are you logged in? Are you a staff member?")));
				}
				else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#FFFF00Use /2fa enabled [true/false]")));
			}
			else if(args.length >= 1 && args[0].equalsIgnoreCase("confirm")) 
			{
				if(args.length == 2) 
				{
					ResultSet discord_2fa = Database_Bungee.QueryCommand("SELECT current_status,code FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
					try 
					{
						if(discord_2fa.next()) 
						{
							current_status = discord_2fa.getString(1);
							String code = discord_2fa.getString(2);
							String[] socket = player.getPendingConnection().getSocketAddress().toString().split(":");
							socket[0] = Common_Bungee.RemoveBeforeCharacter(socket[0], '/');
							if(current_status != null && socket.length == 2 && current_status.equalsIgnoreCase("pending"))
							{
								if(code != null && args[1].equalsIgnoreCase(code)) 
								{
									Database_Bungee.UpdateCommand("UPDATE discord_2fa SET current_status = 'accepted', ip = '"+ socket[0] +"',code = null WHERE uuid = '" +  player.getUniqueId().toString() +"';");
									player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#00FF002FA Code matched! 2FA Security standing down!")));
								}
								else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#CD0000Code does not match try again!")));
							}
							else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#C8C8C8You dont need a 2FA code right now!")));

						}
					}
					catch (SQLException e)
					{
						e.printStackTrace();
						player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#FF0000Something Went Wrong When Trying To Read Your 2FA code.")));
					}
				} 
			}
			else
			{
				player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#00FFFFCurrent 2fa Status")));
				if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND current_status = 'linked';") >= 1) 
				{
					if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
					{
						ResultSet discord_2fa = Database_Bungee.QueryCommand("SELECT current_status,code FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
						try 
						{
							if(discord_2fa.next()) 
							{
								current_status = discord_2fa.getString(1);
								String code = discord_2fa.getString(2);
								if(current_status == null || current_status.equalsIgnoreCase("")) player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#CD00002fa Disabled.")));
								else if(current_status.equalsIgnoreCase("pending")) 
								{
									if(code == null || code.equalsIgnoreCase("")) player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#ffb900IGSQbot is unresponsive! We will let you in until a code is generated.")));
									else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#FFFF00Waiting For Discord Code! Your linked account should get a message from igsqbot.")));
								}
								else if(current_status.equalsIgnoreCase("accepted")) player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#00FF002FA Enabled and Accepted!")));
								else if(current_status.equalsIgnoreCase("expired")) player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#C8C8C82FA Enabled, On Standby!")));
								else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#CD0000 Unknown Issue.")));
							}
						}
						catch (SQLException e)
						{
							e.printStackTrace();
							player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#FF0000Something Went Wrong When Trying To Read Your 2fa status.")));
						}
					}
					else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#CD00002fa Disabled.")));
					if(player.hasPermission("igsq.require2fa")) player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#C8C8C8As a staff member your account will always be protected by 2FA!")));
					else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#00FFFFUse &#FFFF00/2fa enabled [true/false] &#00FFFFto enable or disable 2fa.")));
					player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#00FFFFUse &#FFFF00/2fa confirm [code_from_IGSQbot] &#00FFFFto confirm a pending discord 2fa request.")));
					player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#00FFFFType &#FFFF00/2fa &#00FFFFTo See This Summary Again.")));
				}
				else player.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#CD00002FA Disabled. You need to link your account to discord first before you can use 2FA.\nSee &#FFFF00/link &#CD0000for more details!")));
			}
		}
		else sender.sendMessage(TextComponent.fromLegacyText(Common_Bungee.ChatFormatter("&#CD0000Sorry, but only Players can go into 2FA settings!")));
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
			if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
			{
				ResultSet current_StatusDB  = Database_Bungee.QueryCommand("SELECT current_status from discord_2fa WHERE uuid = '"+ player.getUniqueId().toString() +"';");
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
			if(CanModify2FA(player,current_status)) types = new String[]{"enabled","help","confirm"};
			else types = new String[]{"help","confirm"};
			for (String commands : types) if(commands.contains(args[0].toLowerCase())) options.add(commands);
		}
		else if(args.length == 2) 
		{
			if(args[0].equalsIgnoreCase("enabled") && CanModify2FA(player,current_status)) 
			{
				String[] types = {"true","false"};
				for (String commands : types) if(commands.contains(args[0].toLowerCase())) options.add(commands);
			}

		}
		return options;
	}
	private boolean CanModify2FA(ProxiedPlayer player,String current_status) 
	{
		if (player.hasPermission("igsq.require2fa")) return false;
		if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND current_status = 'linked';") == 0) return false;
		if (current_status == null) return true;
		if (current_status.equalsIgnoreCase("")) return true;
		if (current_status.equalsIgnoreCase("accepted")) return true;
		return false;
	}
}
