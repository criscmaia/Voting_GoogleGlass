package edu.mdx.server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.mysql.fabric.xmlrpc.base.Data;

public class RegisterStudentServerResource extends ServerResource {

	@Get("text")
	public String registerStudent(){

		Connection connection = null;
		Statement statement = null;
		String studentId = getQuery().getValues("studentId");
		
		if (studentId.matches("M00\\d{6}")){
			try {
				connection = Database.getConnection();
				statement = connection.createStatement();

				int result = statement.executeUpdate("insert into student (STUDENT_ID, TIME_STAMP) values ('"+studentId+"', '" + new Timestamp(System.currentTimeMillis()) + "' ) ON DUPLICATE KEY UPDATE TIME_STAMP=VALUES(TIME_STAMP)");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				Database.closeConnection(statement, connection);
			}
			return "success";
		}
		return "badId";
	}
}
