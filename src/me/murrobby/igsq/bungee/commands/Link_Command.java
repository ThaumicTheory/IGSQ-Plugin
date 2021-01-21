package me.murrobby.igsq.bungee.commands;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.murrobby.igsq.bungee.Messaging;
import me.murrobby.igsq.bungee.Database;
import me.murrobby.igsq.shared.Common_Shared;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class Link_Command extends Command implements TabExecutor
{
	public Link_Command() 
	{
		super("link");
	}

	@Override
	public void execute(CommandSender sender, String[] args) 
	{
		ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));
		if(sender instanceof ProxiedPlayer) 
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if(args.length >= 1 && args[0].equalsIgnoreCase("add")) 
			{
				String input = Common_Shared.convertArgs(arguments," ");
				if(Database.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND current_status = 'linked';") >= 1) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000Your Account Is Already Linked!"))); //Checks If Mc Account Is Linked First (linked accounts cannot attempt link)
				else if(input.equalsIgnoreCase("")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FFFF00Use /link add [discord_username]"))); //If input was empty
				else //Valid input
				{
					String[] packagedData = GetDataForLink(input); //Get id & username for discord account
					if(packagedData.length != 0) AddLink(packagedData[0],packagedData[1],player); //All Data Successful Attempt To Add Link
					else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000No discord account found with " + input + ". (Are you in the discord server?)"))); //Neither Valid Username Or ID Was Inputed
				}
			}
			else if(args.length >= 1 && args[0].equalsIgnoreCase("remove")) 
			{
				String input = Common_Shared.convertArgs(arguments," ");
				if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"'") == 0) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000You Have No Accounts To Remove!")));
				else if(input.equalsIgnoreCase("")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FFFF00Use /link remove [discord_username]"))); //If input was empty
				else //Valid input
				{
					String[] packagedData = GetDataForLink(input); //Get id & username for discord account
					if(packagedData.length != 0) RemoveLink(packagedData[0],packagedData[1],player); //All Data Successful Attempt To Remove Link
					else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000No discord account found with " + input + ". (use /link to see your current links)"))); //Neither Valid Username Or ID Was Inputed
				}
			}
			else
			{
				player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFCurrent Active Link / Link Requests:")));
				if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
				{
					ResultSet linked_accounts = Database.QueryCommand("SELECT * FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"';");
					try 
					{
						while(linked_accounts.next()) 
						{
							ResultSet discordUsername_RS = Database.QueryCommand("SELECT username FROM discord_accounts WHERE id = '"+ linked_accounts.getString(3) +"'");
							discordUsername_RS.next();
							String discordUsername = discordUsername_RS.getString(1);
							String current_status = linked_accounts.getString(4);
							if(current_status.equalsIgnoreCase("linked")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF00" + discordUsername)));
							else if(current_status.equalsIgnoreCase("mwait")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FFFF00" + discordUsername + " Waiting For Your Acceptance!")));
							else if(current_status.equalsIgnoreCase("dwait")) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#C8C8C8" + discordUsername + " Waiting For Their Acceptance...")));
							else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000" + discordUsername + " Unknown Issue.")));
						}
					}
					catch (SQLException e)
					{
						e.printStackTrace();
						player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#FF0000Something Went Wrong When Trying To Read Your Links.")));
					}
				}
				else player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000No Links Found.")));
				player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFUse &#FFFF00/link add [discord_username/id] &#00FFFFto create a link or confirm it.")));
				player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFUse &#FFFF00/link remove [discord_username/id] &#00FFFFto remove an existing link, deny or close a request.")));
				player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FFFFType &#FFFF00/link &#00FFFFTo See This Summary Again.")));
			}
		}
		else sender.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000Sorry, but only Players can link accounts!")));
	}
	private void AddLink(String id, String username, ProxiedPlayer player) 
	{
		if(Database.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE id = '"+ id +"' AND current_status = 'linked';") >= 1) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#CD0000Discord Account " + username + " Is Already Linked!"))); //Checks If Discord Account Is Linked First (linked accounts cannot attempt link)
		else if (Database.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"' AND current_status = 'dwait';") >= 1) player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#C8C8C8Already Waiting For This Discord Account To Respond.."))); //Check if link is already waiting for the discord account
		else if (Database.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"' AND current_status = 'mwait';") >= 1) //Check if link is already waiting for the mc account and should confirm link instead
		{
			Database.UpdateCommand("UPDATE linked_accounts SET current_status = 'linked' WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"';"); //Update Database To Show that Account Link has Been Confirmed
			Database.UpdateCommand("DELETE FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND NOT id = '"+ id +"';");//Deletes All Other Links Connected To The Mc Account (linked accounts cannot attempt link)
			Database.UpdateCommand("DELETE FROM linked_accounts WHERE id = '"+ id +"' AND NOT uuid = '"+ player.getUniqueId().toString() +"';");//Deletes All Other Links Connected To The Discord Account (linked accounts cannot attempt link)
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF00Account Link With " + username +" Confirmed!")));
		}
		else //When No Link Is Present Create Request For Discord
		{
			Database.UpdateCommand("INSERT INTO linked_accounts VALUES(null,'"+ player.getUniqueId().toString() +"','"+ id +"','dwait');"); //Create Record In Database To Show Discord Link Request
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00E936Account Link Now Waiting For "+ username +"'s Discord to confirm! &#FFFF00Type .mclink")));
		}
	}
	private String[] GetDataForLink(String input) 
	{
		try 
		{
			if(Database.ScalarCommand("SELECT COUNT(*) FROM discord_accounts WHERE id = '"+ input +"'") >= 1) //Get username From ID if valid ID inputed
			{
				ResultSet discordUsername_RS = Database.QueryCommand("SELECT username FROM discord_accounts WHERE id = '"+ input +"'");
				discordUsername_RS.next();
				return new String[] {input,discordUsername_RS.getString(1)};
			}
			else if(Database.ScalarCommand("SELECT COUNT(*) FROM discord_accounts WHERE username = '"+ input +"'") >= 1) //Get ID From Username if valid username inputed
			{
				ResultSet discordID_RS = Database.QueryCommand("SELECT id FROM discord_accounts WHERE username = '"+ input +"'");
				discordID_RS.next();
				return new String[] {discordID_RS.getString(1),input};
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return new String[0];
	}
	private void RemoveLink(String id, String username, ProxiedPlayer player) 
	{
		if(Database.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"';") == 1) //Check If Link Is Present On This Mc Account
		{
			Database.UpdateCommand("DELETE FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"';"); //Update Database to remove link
			player.sendMessage(TextComponent.fromLegacyText(Messaging.chatFormatter("&#00FF00Deleted Link with "+ username)));
		}
	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) 
	{
		ProxiedPlayer player = null;
		if(sender instanceof ProxiedPlayer) 
		{
			player = (ProxiedPlayer) sender;
		}
		List<String> options = new ArrayList<String>();
		if(args.length == 1) 
		{
			String[] types = {"add","remove","help"};
			for (String commands : types) if(commands.contains(args[0].toLowerCase())) options.add(commands);
		}
		else if(args.length == 2) 
		{
			if(args[0].equalsIgnoreCase("add")) 
			{
				if(Database.ScalarCommand("SELECT Count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND current_status = 'linked';") == 0) 
				{
					ResultSet linkableAccounts = Database.QueryCommand("SELECT id,username FROM discord_accounts WHERE id NOT IN(SELECT id FROM linked_accounts WHERE current_status = 'linked') AND NOT id IN(SELECT id FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() +"' AND current_status = 'dwait');");
					try
					{
						while (linkableAccounts.next())
						{
							if(linkableAccounts.getString(1).contains(args[1])) options.add(linkableAccounts.getString(1));
							if(linkableAccounts.getString(2).contains(args[1])) options.add(linkableAccounts.getString(2));
						}
					}
					catch(Exception exception) 
					{
						exception.printStackTrace();
					}
				}
			}
			else if(args[0].equalsIgnoreCase("remove")) 
			{
				ResultSet linkableAccounts = Database.QueryCommand("SELECT id,username FROM discord_accounts WHERE id IN(SELECT id FROM linked_accounts WHERE uuid = '" + player.getUniqueId().toString() +"');");
				try
				{
					while (linkableAccounts.next())
					{
						if(linkableAccounts.getString(1).contains(args[1])) options.add(linkableAccounts.getString(1));
						if(linkableAccounts.getString(2).contains(args[1])) options.add(linkableAccounts.getString(2));
					}
				}
				catch(Exception exception) 
				{
					exception.printStackTrace();
				}
			}
		}
		return options;
	}
}
