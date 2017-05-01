package edu.mdx.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class Test extends ServerResource {

	public static final BasicDataSource bds = new BasicDataSource();
	@Get
	public String test() {
//		Connection connection = null;
//		Statement statement = null;
//
//		try {
//			
//			connection = Database.getConnection();
//			statement = connection.createStatement();
//
//			ResultSet resultSet = statement.executeQuery("SELECT * FROM LOG");
//			if (resultSet.next()) {
//				System.out.println("-->" + resultSet.getString(1));
//			}
//			statement.close();
//			statement = null;
//			connection.close();
//			connection = null;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("hello");
//			e.printStackTrace();
//		} finally {
//			if(statement!=null){
//				try {
//					statement.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				statement = null;
//			}
//			if(connection!=null){
//				try {
//					connection.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				connection = null;
//			}
//
//		}
		return "Hello, world!";
	}
}
