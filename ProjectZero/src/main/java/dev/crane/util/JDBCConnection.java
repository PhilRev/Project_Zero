package dev.crane.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
	
	private static Connection conn = null;
	
	public static Connection getConnected() {
		
		try {
			
			if (conn == null) {
				/*Make a new connection
				 * First!! Oracle "Hotfix" to ensure that ojdbc drives 
				 * would correctly load at the beginning of the app
				 * starting.
				 */
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				/* To establish a connection we need 3 credentials
				 * url (endpoint), username, password.
				 */
				
				String endpoint = "crane2101etl.cldl1zxirpuj.us-west-1.rds.amazonaws.com";
				String url = "jdbc:oracle:thin:@" + endpoint + ":1521:ORCL";
				String username = "phil";
				String password = "password";
				
				// Establish our connection
				conn = DriverManager.getConnection(url, username, password);
				
				return conn;
				
			} else {
				//Return existing connection
				return conn;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		
		return null;
	}

}
