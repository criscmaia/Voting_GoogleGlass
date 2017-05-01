package edu.mdx.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.mdx.common.FeedbackUpdate;

public class GlassViewer extends ServerResource {

	private final String[] metrics = { "PACE", "VOLUME", "BODY_LANGUAGE", "INTEREST", "CLARITY" };
	private static final int LS_PCNTG = 0;
	private static final int RS_PCNTG = 1;
	private static final int COUNT = 2;

	private Connection connection = null;
	private Statement statement = null;

	// private static volatile FeedbackUpdate feedbackUpdate = new
	// FeedbackUpdate(12, 13, 45);
	@Get("json")
	public String retrieve() {
		ObjectMapper mapper = new ObjectMapper();
		String metricsValuesResult = "";

		try {
			metricsValuesResult = mapper.writeValueAsString(fetchFeedbackUpdate());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return metricsValuesResult;

	}

	private FeedbackUpdate fetchFeedbackUpdate() {

		FeedbackUpdate feedbackUpdateObject = new FeedbackUpdate();
		int leftSideCount = 0;
		int rightSideCount = 0;
		int totalCount = 0;
		for (String metric : metrics) {
			leftSideCount = 0;
			rightSideCount = 0;
			totalCount = 0;
			try {
				connection = Database.getConnection();
				statement = connection.createStatement();
				ResultSet leftSideData = statement.executeQuery("select count(*) from FEEDBACK where " + metric + " = 1");
				leftSideCount = leftSideData.next() ? leftSideData.getInt(1) : 0;

				ResultSet rightSideData = statement.executeQuery("select count(*) from FEEDBACK where " + metric + " = -1");
				rightSideCount = rightSideData.next() ? rightSideData.getInt(1) : 0;
				totalCount = leftSideCount + rightSideCount;
				if (totalCount != 0) {

					leftSideCount = ((leftSideCount == 0) ? 0 : (int) Math.ceil((leftSideCount * 100 / totalCount)));
					rightSideCount = ((rightSideCount == 0) ? 0
							: (int) Math.floor((rightSideCount * 100 / totalCount)));
				} else {
					leftSideCount = 0;
					rightSideCount = 0;
				}
				int[] valuesArray = new int[3];
				valuesArray[LS_PCNTG] = leftSideCount;
				valuesArray[RS_PCNTG] = rightSideCount;
				valuesArray[COUNT] = totalCount;

				feedbackUpdateObject.setMetricValues(metric, valuesArray);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				Database.closeConnection(statement, connection);
			}

		}
		return feedbackUpdateObject;
	}

}
