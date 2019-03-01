package net.balsoftware.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionHelper {
	private static Connection connection;
    
	public static Connection getConnection() throws SQLException
	{
		if (connection != null && ! connection.isClosed())
		{
			return connection;
		} else
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
            	ResourceBundle bundle = ResourceBundle.getBundle("net/balsoftware/util/db");
            	// TODO - replace with Environment variables
                String url = bundle.getString("url");
                String user = bundle.getString("user");
                String password = bundle.getString("password");
//	            String url = "jdbc:mysql://localhost:3306/rrule";
//	            String user = "root";
//	            String password = "skywalker";
				connection =  DriverManager.getConnection(url, user, password);
	//			return DriverManager.getConnection(instance.url);
			} catch (SQLException e) {
				throw e;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return connection;
		}
	}

	public static void close(Connection c) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
