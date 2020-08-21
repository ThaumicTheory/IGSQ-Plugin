package me.murrobby.igsq.bungee.commands;


import java.sql.ResultSet;
import java.sql.SQLException;

import me.murrobby.igsq.bungee.Common_Bungee;
import me.murrobby.igsq.bungee.Database_Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Link_Command extends Command
{
	public Link_Command() 
	{
		super("link");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) 
		{
			ProxiedPlayer player = (ProxiedPlayer) sender;
			if(args.length >= 1 && args[0].equalsIgnoreCase("add")) 
			{
				String input = ConvertArgs(args);
				if(Database_Bungee.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND current_status = 'linked';") >= 1) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cYour Account Is Already Linked!"))); //Checks If Mc Account Is Linked First (linked accounts cannot attempt link)
				else if(input.equalsIgnoreCase("")) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cUse /link add [discord_username]"))); //If input was empty
				else //Valid input
				{
					String[] packagedData = GetDataForLink(input); //Get id & username for discord account
					if(packagedData.length != 0) AddLink(packagedData[0],packagedData[1],player); //All Data Successful Attempt To Add Link
					else player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cNo discord account found with " + input + ". (Are you in the discord server?)"))); //Neither Valid Username Or ID Was Inputed
				}
			}
			else if(args.length >= 1 && args[0].equalsIgnoreCase("remove")) 
			{
				String input = ConvertArgs(args);
				if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"'") == 0) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&4You Have No Accounts To Remove!")));
				else if(input.equalsIgnoreCase("")) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cUse /link remove [discord_username]"))); //If input was empty
				else //Valid input
				{
					String[] packagedData = GetDataForLink(input); //Get id & username for discord account
					if(packagedData.length != 0) RemoveLink(packagedData[0],packagedData[1],player); //All Data Successful Attempt To Remove Link
					else player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cNo discord account found with " + input + ". (use /link to see your current links)"))); //Neither Valid Username Or ID Was Inputed
				}
			}
			else
			{
				player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&bCurrent Active Link / Link Requests:")));
				if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"';") >= 1) 
				{
					ResultSet linked_accounts = Database_Bungee.QueryCommand("SELECT * FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"';");
					try 
					{
						while(linked_accounts.next()) 
						{
							ResultSet discordUsername_RS = Database_Bungee.QueryCommand("SELECT username FROM discord_accounts WHERE id = '"+ linked_accounts.getString(3) +"'");
							discordUsername_RS.next();
							String discordUsername = discordUsername_RS.getString(1);
							String current_status = linked_accounts.getString(4);
							if(current_status.equalsIgnoreCase("linked")) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&2" + discordUsername)));
							else if(current_status.equalsIgnoreCase("mwait")) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&6" + discordUsername + " Waiting For Your Acceptance!")));
							else if(current_status.equalsIgnoreCase("dwait")) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&c" + discordUsername + " Waiting For Their Acceptance...")));
							else player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&4" + discordUsername + " Unknown Issue.")));
						}
					}
					catch (SQLException e)
					{
						e.printStackTrace();
						player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cSomething Went Wrong When Trying To Read Your Links.")));
					}
				}
				else player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&4No Links Found.")));
				player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&eUse &b/link add [discord_username/id] &eto create a link or confirm it.")));
				player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&eUse &6/link remove [discord_username/id] &eto remove an existing link, deny or close a request.")));
				player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&eType &a/link &eTo See This Summary Again.")));
			}
		}
		else sender.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cSorry, but only Players can link accounts!")));
	}
	
	private String ConvertArgs(String[] args) 
	{
		String input = "";
		String[] arrayConverted = Common_Bungee.Depend(args, 0);
		for (int i = 0;arrayConverted.length > i;i++) 
		{
			input += arrayConverted[i];
			if(arrayConverted.length > i+1) 
			{
				input += " ";
			}
		}
		return input;
	}
	private void AddLink(String id, String username, ProxiedPlayer player) 
	{
		if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM linked_accounts WHERE id = '"+ id +"' AND current_status = 'linked';") >= 1) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&cDiscord Account " + username + " Is Already Linked!"))); //Checks If Discord Account Is Linked First (linked accounts cannot attempt link)
		else if (Database_Bungee.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"' AND current_status = 'dwait';") >= 1) player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&7Already Waiting For This Discord Account To Respond.."))); //Check if link is already waiting for the discord account
		else if (Database_Bungee.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"' AND current_status = 'mwait';") >= 1) //Check if link is already waiting for the mc account and should confirm link instead
		{
			Database_Bungee.UpdateCommand("UPDATE linked_accounts SET current_status = 'linked' WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"';"); //Update Database To Show that Account Link has Been Confirmed
			Database_Bungee.UpdateCommand("DELETE FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND NOT id = '"+ id +"';");//Deletes All Other Links Connected To The Mc Account (linked accounts cannot attempt link)
			Database_Bungee.UpdateCommand("DELETE FROM linked_accounts WHERE id = '"+ id +"' AND NOT uuid = '"+ player.getUniqueId().toString() +"';");//Deletes All Other Links Connected To The Discord Account (linked accounts cannot attempt link)
			player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&aAccount Link Confirmed!")));
		}
		else //When No Link Is Present Create Request For Discord
		{
			Database_Bungee.UpdateCommand("INSERT INTO linked_accounts VALUES(null,'"+ player.getUniqueId().toString() +"','"+ id +"','dwait');"); //Create Record In Database To Show Discord Link Request
			player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&2Account Link Now Waiting For Discord!")));
		}
	}
	private String[] GetDataForLink(String input) 
	{
		try 
		{
			if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM discord_accounts WHERE id = '"+ input +"'") >= 1) //Get username From ID if valid ID inputed
			{
				ResultSet discordUsername_RS = Database_Bungee.QueryCommand("SELECT username FROM discord_accounts WHERE id = '"+ input +"'");
				discordUsername_RS.next();
				return new String[] {input,discordUsername_RS.getString(1)};
			}
			else if(Database_Bungee.ScalarCommand("SELECT COUNT(*) FROM discord_accounts WHERE username = '"+ input +"'") >= 1) //Get ID From Username if valid username inputed
			{
				ResultSet discordID_RS = Database_Bungee.QueryCommand("SELECT id FROM discord_accounts WHERE username = '"+ input +"'");
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
		if(Database_Bungee.ScalarCommand("SELECT count(*) FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"';") == 1) //Check If Link Is Present On This Mc Account
		{
			Database_Bungee.UpdateCommand("DELETE FROM linked_accounts WHERE uuid = '"+ player.getUniqueId().toString() +"' AND id = '"+ id +"';"); //Update Database to remove link
			player.sendMessage(new TextComponent(Common_Bungee.ChatColour("&aDeleted Link with "+ username)));
		}
	}
}
