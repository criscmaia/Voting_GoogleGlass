package edu.mdx.server;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {

	private static BasicDataSource dataSource = new BasicDataSource();
	
	static{
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/gg_database");
		dataSource.setUsername("emadashour");
		dataSource.setPassword("London123.");
	}
	
	private Database() {
		// disabled
	}
	
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	public static void closeConnection(Statement statement, Connection connection)
	{
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			statement = null;
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			connection = null;
		}
	}
	
	public static void log(Connection connection, String studentId, Metric metric, int value) throws SQLException {
		CallableStatement cStmt = connection.prepareCall("{call logFeedback(?, ?, ?, ?, ?, ?)}");
	    cStmt.setString(1, studentId);
	    cStmt.setInt(2, 0);
	    cStmt.setInt(3, 0);
	    cStmt.setInt(4, 0);
	    cStmt.setInt(5, 0);
	    cStmt.setInt(6, 0);
	    
	    switch (metric){
	    	case PACE:
	    		System.out.println("PACE");
	    		cStmt.setInt(2, value);
	    		break;
	    	case VOLUME:
	    		System.out.println("VOLUME");
	    		cStmt.setInt(3, value);
	    		break;
	    	case BODY_LANGUAGE:
	    		System.out.println("B LANGUAGE");
	    		cStmt.setInt(4, value);
	    		break;
	    	case CLARITY:
	    		System.out.println("CLARITY");
	    		cStmt.setInt(5, value);
	    		break;
	    	case INTEREST:
	    		System.out.println("INTEREST");
	    		cStmt.setInt(6, value);
	    		break;
	    }
	    
	    cStmt.execute();
	}
}
