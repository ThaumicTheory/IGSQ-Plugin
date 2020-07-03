package me.murrobby.igsq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database 
{
    static String url = "jdbc:mysql://localhost:3306/igsq?useSSL=false";
    static String user = "igsq";
    static String password = "fgvGpSHwzxt7Q4PM";
	public Database(Main plugin)
	{
		UpdateCommand("CREATE TABLE IF NOT EXISTS linked_accounts(uuid VARCHAR(36) PRIMARY KEY,id VARCHAR(18),current_status VARCHAR(16));");
		UpdateCommand("CREATE TABLE IF NOT EXISTS discord_2fa(uuid VARCHAR(36) PRIMARY KEY,current_status VARCHAR(16));");
		UpdateCommand("CREATE TABLE IF NOT EXISTS mc_accounts(uuid VARCHAR(36) PRIMARY KEY,username VARCHAR(16));");
		UpdateCommand("CREATE TABLE IF NOT EXISTS discord_accounts(id VARCHAR(18) PRIMARY KEY,username VARCHAR(37));");
	}
	public static ResultSet QueryCommand(String sql) 
	{
        try 
        {
        	Connection connectionString = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connectionString.createStatement();
            ResultSet resultTable = commandAdapter.executeQuery(sql);
			return resultTable;
        } catch (SQLException exception) 
        {
        	System.out.println(exception.toString());
        	return null;
        } 
    }
	public static void UpdateCommand(String sql) 
	{
        try 
        {
        	Connection connectionString = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connectionString.createStatement();
            commandAdapter.executeUpdate(sql);
        } catch (SQLException exception) 
        {
        	System.out.println(exception.toString());
        } 
    }
	public static int ScalarCommand(String sql) 
	{
        try 
        {
        	Connection connectionString = DriverManager.getConnection(url, user, password);
            Statement commandAdapter = connectionString.createStatement();
            ResultSet resultTable = commandAdapter.executeQuery(sql);
            resultTable.next();
            return resultTable.getInt(1);
        } 
        catch (SQLException exception) 
        {
        	System.out.println(exception.toString());
        	return -1;
        } 
    }
}
