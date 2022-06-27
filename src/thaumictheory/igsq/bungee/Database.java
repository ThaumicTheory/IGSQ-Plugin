package thaumictheory.igsq.bungee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import thaumictheory.igsq.bungee.yaml.YamlWrapper;


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
			updateCommand("CREATE TABLE IF NOT EXISTS linked_accounts(link_number int PRIMARY KEY AUTO_INCREMENT,uuid VARCHAR(36),id VARCHAR(18),current_status VARCHAR(16));");
			updateCommand("CREATE TABLE IF NOT EXISTS discord_2fa(uuid VARCHAR(36) PRIMARY KEY,current_status VARCHAR(16),code VARCHAR(6),ip VARCHAR(15));");
			updateCommand("CREATE TABLE IF NOT EXISTS mc_accounts(uuid VARCHAR(36) PRIMARY KEY,username VARCHAR(16));");
			updateCommand("CREATE TABLE IF NOT EXISTS discord_accounts(id VARCHAR(18) PRIMARY KEY,username VARCHAR(37),nickname VARCHAR(32),roles INT);");
		}
		else System.err.println("A Database Error Has Occured On Startup.");
	}
	public static ResultSet queryCommand(String sql) 
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
	public static void updateCommand(String sql) 
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
	public static int scalarCommand(String sql) 
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
	public static boolean testDatabase() 
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
