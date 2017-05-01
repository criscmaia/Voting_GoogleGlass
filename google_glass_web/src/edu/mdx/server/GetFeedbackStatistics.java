package edu.mdx.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GetFeedbackStatistics extends ServerResource {
	private static final Logger log = Logger.getLogger(GetFeedbackStatistics.class.getName());

	private final String[] metrics = { "PACE", "VOLUME", "BODY_LANGUAGE", "INTEREST", "CLARITY" };

	Connection connection = null;
	Statement statement = null;

	@Get("json")
	public String getLogs() {
		int leftSideCount = 0;
		int rightSideCount = 0;
		ObjectNode metricObjectNode = null;
		ObjectMapper metricMapper = new ObjectMapper();
		ArrayNode resultArray = metricMapper.createArrayNode();
		
		for (String metric : metrics) {
			leftSideCount = 0;
			rightSideCount = 0;
			try {
				connection = Database.getConnection();
				statement = connection.createStatement();
				ResultSet leftSideData = statement.executeQuery("select count(*) from FEEDBACK where " + metric + " = 1");
				leftSideCount = leftSideData.next() ? leftSideData.getInt(1) : 0;

				ResultSet rightSideData = statement.executeQuery("select count(*) from FEEDBACK where " + metric + " = -1");
				rightSideCount = rightSideData.next() ? rightSideData.getInt(1) : 0;

				metricObjectNode = metricMapper.createObjectNode();
				resultArray.add(createMetricJson(metricMapper, metric, leftSideCount, rightSideCount));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				Database.closeConnection(statement, connection);
			}
		}
		
		String result = "";
		try {
			result = metricMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultArray);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	private ObjectNode createMetricJson(ObjectMapper mapper, String metricName, int leftSideCount, int rightSideCount) {
		ObjectNode metricData = mapper.createObjectNode();
		int totalCount = leftSideCount + rightSideCount;
		metricData.put("metric", metricName);
		metricData.put("count", totalCount);
		if (totalCount != 0) {
			metricData.put("lhs",
					leftSideCount == 0 ? "0%" : Math.ceil((((int) leftSideCount * 100) / totalCount)) + "%");
			metricData.put("rhs",
					rightSideCount == 0 ? "0%" : Math.floor((((int) rightSideCount * 100) / totalCount)) + "%");
		} else {
			metricData.put("lhs", "0%");
			metricData.put("rhs", "0%");
		}

		return metricData;
	}

}
