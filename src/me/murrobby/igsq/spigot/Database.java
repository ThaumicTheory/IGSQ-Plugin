package me.murrobby.igsq.spigot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.plugin.Plugin;

public class Database 
{
    static String url;
    static String user;
    static String password;
    static Plugin plugin;
	public Database(Plugin plugin)
	{
		Database.plugin = plugin;
		url = Yaml.getFieldString("MYSQL.database", "config");
		user = Yaml.getFieldString("MYSQL.username", "config");
		password = Yaml.getFieldString("MYSQL.password", "config");
		if(testDatabase()) 
		{
			UpdateCommand("CREATE TABLE IF NOT EXISTS linked_accounts(link_number int PRIMARY KEY AUTO_INCREMENT,uuid VARCHAR(36),id VARCHAR(18),current_status VARCHAR(16));");
			UpdateCommand("CREATE TABLE IF NOT EXISTS discord_2fa(uuid VARCHAR(36) PRIMARY KEY,current_status VARCHAR(16),code VARCHAR(6),ip VARCHAR(15));");
			UpdateCommand("CREATE TABLE IF NOT EXISTS mc_accounts(uuid VARCHAR(36) PRIMARY KEY,username VARCHAR(16));");
			UpdateCommand("CREATE TABLE IF NOT EXISTS discord_accounts(id VARCHAR(18) PRIMARY KEY,username VARCHAR(37),nickname VARCHAR(32),role VARCHAR(32),founder bit(1),birthday bit(1),nitroboost bit(1),supporter bit(1),developer bit(1));");
		}
		else System.err.println("A Database Error Has Occured On Startup.");
	}
	public static ResultSet QueryCommand(String sql) 
	{
        try 
        {
        	Connection connection = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connection.createStatement();
            ResultSet resultTable = commandAdapter.executeQuery(sql);
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable()
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
						Messaging.sendException(exception, "Failed to expire Database Query.", "BIRCH_PLANK", null);
					}
				} 		
	    	},60);
			return resultTable;
        }
        catch (Exception exception) 
        {
        	Messaging.sendException(exception, "Database Query: " + sql + " failed", "BIRCH_LOG", null);
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
        catch (Exception exception) 
        {
        	Messaging.sendException(exception, "Database Update: " + sql + " failed", "ACACIA_LOG", null);
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
        catch (Exception exception) 
        {
        	Messaging.sendException(exception, "Database Scalar Query: " + sql + " failed", "OAK_PLANK", null);
        	return -1;
        } 
    }
	public static Boolean testDatabase() 
	{
        try 
        {
        	Connection connection = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connection.createStatement();
            commandAdapter.executeUpdate("CREATE TABLE IF NOT EXISTS test_database(number int PRIMARY KEY AUTO_INCREMENT,test VARCHAR(36));");
            commandAdapter.executeUpdate("DROP TABLE test_database;");
            connection.close();
            return true;
        }
        catch (Exception exception) 
        {
        	return false;
        }
    }
}
