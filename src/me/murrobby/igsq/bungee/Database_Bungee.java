package me.murrobby.igsq.bungee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;


public class Database_Bungee 
{
    static String url;
    static String user;
    static String password;
    static Main_Bungee plugin;
	public Database_Bungee(Main_Bungee plugin)
	{
		Database_Bungee.plugin = plugin;
		url = Common_Bungee.getFieldString("MYSQL.database", "config.yml");
		user = Common_Bungee.getFieldString("MYSQL.username", "config.yml");
		password = Common_Bungee.getFieldString("MYSQL.password", "config.yml");
		UpdateCommand("CREATE TABLE IF NOT EXISTS linked_accounts(uuid VARCHAR(36) PRIMARY KEY,id VARCHAR(18),current_status VARCHAR(16));");
		UpdateCommand("CREATE TABLE IF NOT EXISTS discord_2fa(uuid VARCHAR(36) PRIMARY KEY,current_status VARCHAR(16));");
		UpdateCommand("CREATE TABLE IF NOT EXISTS mc_accounts(uuid VARCHAR(36) PRIMARY KEY,username VARCHAR(16));");
		UpdateCommand("CREATE TABLE IF NOT EXISTS discord_accounts(id VARCHAR(18) PRIMARY KEY,username VARCHAR(37));");
		UpdateCommand("CREATE TABLE IF NOT EXISTS player_command_communicator(command_number int PRIMARY KEY AUTO_INCREMENT,command VARCHAR(32),uuid VARCHAR(36),arg1 VARCHAR(32),arg2 VARCHAR(32),arg3 VARCHAR(32),arg4 VARCHAR(32),arg5 VARCHAR(32));");
	}
	public static ResultSet QueryCommand(String sql) 
	{
        try 
        {
        	Connection connection = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connection.createStatement();
            ResultSet resultTable = commandAdapter.executeQuery(sql);
            plugin.getProxy().getScheduler().schedule(plugin, new Runnable()
	    	{

				@Override
				public void run() 
				{
					try
					{
						connection.close();
					}
					catch (Exception exception)
					{
						System.out.println("Database Query Close: " + exception.toString());
					}
				} 		
	    	},3,TimeUnit.SECONDS);
			return resultTable;
        }
        catch (SQLException exception) 
        {
        	System.out.println("Database Query:" + exception.toString());
        	return null;
        } 
    }
	public static void UpdateCommand(String sql) 
	{
        try 
        {
        	Connection connection = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connection.createStatement();
            commandAdapter.executeUpdate(sql);
            connection.close();
        }
        catch (SQLException exception) 
        {
        	System.out.println("Database Update:" + exception.toString());
        }
    }
	public static int ScalarCommand(String sql) 
	{
        try 
        {
        	Connection connection = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connection.createStatement();
            ResultSet resultTable = commandAdapter.executeQuery(sql);
            resultTable.next();
            int result = resultTable.getInt(1);
            connection.close();
            return result;
        } 
        catch (SQLException exception) 
        {
        	System.out.println("Database Scalar:" + exception.toString());
        	return -1;
        } 
    }
}
