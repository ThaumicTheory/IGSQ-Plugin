package me.murrobby.igsq.bungee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;


public class Database 
{
    static String url;
    static String user;
    static String password;
	public Database()
	{
		url = YamlWrapper.getMySQLLocation();
		user = YamlWrapper.getMySQLUsername();
		password = YamlWrapper.getMySQLPassword();
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
            Common.bungee.getProxy().getScheduler().schedule(Common.bungee, new Runnable()
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
        catch (Exception exception) 
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
        catch (Exception exception) 
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
        catch (Exception exception) 
        {
        	System.out.println("Database Scalar:" + exception.toString());
        	return -1;
        } 
    }
	public static Boolean testDatabase() 
	{
        try 
        {
        	Connection connection = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connection.createStatement();
            commandAdapter.executeUpdate("CREATE TABLE IF NOT EXISTS testDatabase(number int PRIMARY KEY AUTO_INCREMENT,test VARCHAR(36));");
            commandAdapter.executeUpdate("DROP TABLE testDatabase;");
            connection.close();
            return true;
        }
        catch (Exception exception) 
        {
        	return false;
        }
    }
}
