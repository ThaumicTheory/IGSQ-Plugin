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
			updateCommand("CREATE TABLE IF NOT EXISTS `discord_accounts`(`id` BIGINT UNSIGNED NOT NULL,`username` VARCHAR(37) UNIQUE NOT NULL CHECK (`username` LIKE '__%#____')  ,`nickname` VARCHAR(32) DEFAULT NULL,`roles` INT UNSIGNED NOT NULL DEFAULT 0,PRIMARY KEY(`id`));");
			//note that the minecraft username field is not checked and extended for safety because of issues on mojangs end (duplicate and illegal usernames) usernames should be 3-16 characters, unique and a-z,A-Z,0-9,_ but are not
			updateCommand("CREATE TABLE IF NOT EXISTS `mc_accounts`(`uuid` VARCHAR(36) NOT NULL,`username` VARCHAR(32) NOT NULL,PRIMARY KEY(`uuid`));");
			
			updateCommand("CREATE TABLE IF NOT EXISTS `linked_accounts`(`uuid` VARCHAR(36) NOT NULL,`id` BIGINT UNSIGNED NOT NULL,`current_status` VARCHAR(16),PRIMARY KEY(`uuid`,`id`), FOREIGN KEY(`uuid`) REFERENCES `mc_accounts`(`uuid`) ON DELETE CASCADE ON UPDATE RESTRICT,FOREIGN KEY(`id`) REFERENCES `discord_accounts`(`id`) ON DELETE CASCADE ON UPDATE RESTRICT);");
			updateCommand("CREATE TABLE IF NOT EXISTS `discord_2fa`(`uuid` VARCHAR(36) NOT NULL,`id` BIGINT UNSIGNED NOT NULL,`current_status` VARCHAR(16),`code` VARCHAR(6) DEFAULT NULL,`ip` VARCHAR(15) DEFAULT NULL,PRIMARY KEY(`uuid`,`id`), FOREIGN KEY(`uuid`) REFERENCES `mc_accounts`(`uuid`) ON DELETE CASCADE ON UPDATE RESTRICT,FOREIGN KEY(`id`) REFERENCES `discord_accounts`(`id`) ON DELETE CASCADE ON UPDATE RESTRICT);");
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
