package edu.mdx.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class UpdateBodyLanguageResource extends ServerResource {

	Connection connection = null;
	Statement statement = null;
	
	@Get
	public String updatePace(){
		String studentId = getQuery().getValues("studentId");
		int bodyLanguageValue = Integer.parseInt(getQuery().getValues("value"));
		
		
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO FEEDBACK (STUDENT_ID, BODY_LANGUAGE) values ('"+studentId+"', '" + bodyLanguageValue + "' ) ON DUPLICATE KEY UPDATE BODY_LANGUAGE=VALUES(BODY_LANGUAGE)");
			Database.log(connection, studentId, Metric.BODY_LANGUAGE, bodyLanguageValue);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			Database.closeConnection(statement, connection);
		}
		
		
		return "success";
	}
}
