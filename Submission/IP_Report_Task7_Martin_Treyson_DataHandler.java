package Project_Web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/*
	This class is for handing data for the JSP Task 7 part of the project
	Author: Treyson Martin
	This is for only use on CS4513 Database Project. All other uses PROHIBITED.
*/

public class DataHandler 
{
	private Connection conn;
	// Azure SQL connection credentials
	private String server = "mart3422-sql-server.database.windows.net";
	private String database = "cs-dsa-4513-sql-db";
	private String username = "mart3422";
	private String password = "Atticus666418!";
	// Resulting connection string
	final private String url =
			String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;"
					+ "password=%s;encrypt=true;trustServerCertificate=false;"
					+ "hostNameInCertificate=*.database.windows.net;"
					+ "loginTimeout=30;",
			server, database, username, password);				
	// Initialize and save the database connection
	private void getDBConnection() throws SQLException 
	{
		if (conn != null) 
		{
			System.out.println("HERE");
			return;
		}
		System.out.println("HERE2");
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			this.conn = DriverManager.getConnection(url);
			System.out.println("HERE3");
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	// Return the result of selecting everything from the movie_night table
	public ResultSet getAllCustomers() throws SQLException 
	{
		getDBConnection();
		final String sqlQuery = "SELECT * FROM Customers;";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		return stmt.executeQuery();
	}
	
	// Return the result of selecting everything from the movie_night table
	public ResultSet GetCustomers(String lowCat, String highCat)
			throws SQLException 
	{
		getDBConnection();
		final String sqlQuery = "SELECT * FROM Customers WHERE"
				+ " category between " + lowCat + " AND " + highCat 
				+ " ORDER by cust_name";
		
		System.out.println(sqlQuery);
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		return stmt.executeQuery();
	}
	
	// Inserts a record into the movie_night table with the given attribute values
	public boolean addCustomer(String name, String address, String category) 
			throws SQLException 
	{
		System.out.println("In addCustomer");
		getDBConnection(); // Prepare the database connection
		System.out.println("Made Connection");
		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Customers VALUES ('" + name + "','" 
				+ address + "', " + category + ")";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		System.out.println("Prepared Query");
		// Replace the '?' in the above statement with the given attribute values
		//stmt.setString(1, name);
		//stmt.setString(2, address);
		//stmt.setString(3, category);
		// Execute the query, if only one record is updated, then we indicate success by
		return stmt.executeUpdate() == 1;
	}
}
