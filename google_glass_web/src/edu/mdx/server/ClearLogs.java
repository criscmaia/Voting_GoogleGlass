package edu.mdx.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ClearLogs extends ServerResource {

	private Connection connection = null;
	private PreparedStatement statement = null;
	@Get
	public void restDatabase(){
		try {
			connection = Database.getConnection();
			statement = connection.prepareStatement("DELETE FROM LOG");
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			Database.closeConnection(statement, connection);
		}
	}
}
