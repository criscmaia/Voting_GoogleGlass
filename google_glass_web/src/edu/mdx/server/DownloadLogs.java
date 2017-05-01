package edu.mdx.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.restlet.data.Disposition;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class DownloadLogs extends ServerResource {

	private Connection connection = null;
	private Statement statement = null;
	private ResultSet logsResult = null;
	@Get("csv")
	public Representation downloadLogs() {

		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			logsResult = statement.executeQuery("select * FROM LOG");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			Database.closeConnection(statement, connection);
		}


		StringBuilder csvOut = new StringBuilder();
		csvOut.append("studentId, timestamp, pace, volume, bodyLanguage, interest, clarity");
		csvOut.append("\n");

		try {
			while (logsResult.next()) {

				csvOut.append(logsResult.getString("STUDENT_ID"));
				csvOut.append(",");
				csvOut.append(logsResult.getString("TIME_STAMP"));
				csvOut.append(",");
				csvOut.append(logsResult.getInt("PACE"));
				csvOut.append(",");
				csvOut.append(logsResult.getInt("VOLUME"));
				csvOut.append(",");
				csvOut.append(logsResult.getInt("BODY_LANGUAGE"));
				csvOut.append(",");
				csvOut.append(logsResult.getInt("INTEREST"));
				csvOut.append(",");
				csvOut.append(logsResult.getInt("CLARITY"));
				csvOut.append("\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Disposition attachment = new Disposition(Disposition.TYPE_ATTACHMENT);
		attachment.setFilename("feedback_logs.csv");
		StringRepresentation representation = new StringRepresentation(csvOut.toString());
		representation.setDisposition(attachment);
		representation.setMediaType(MediaType.APPLICATION_ALL);

		return representation;

	}

}
