package com.youtube.rest.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.youtube.dao.*;

@Path("/v1/status") // or @Path("/v1/status/*")
public class V1_status {
	
	private static final String version = "00.02.00";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String returnTitle() {
		return "<p>Java Web Service</p>";
	}
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public static String returnVersion() {
		return "<p>Version: " + version + "</p>";
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception {
		String string = "", tempString = "";
		PreparedStatement query = null;
		Connection connection = null;
		
		try {
			connection = RESTfulMySQL.MySQLConn().getConnection();
			query = connection.prepareStatement("SELECT * FROM user_details");
			ResultSet resultSet = query.executeQuery();
			
			while (resultSet.next()) {
				tempString += resultSet.getString("USER_NAME");
				
				if (!resultSet.isLast())
					tempString += " & ";
			}
			
			query.close();
			
			string = "<h2>Database Status - Connectivity Test!!!</h2>" + "<p>Database Info returns: " + tempString + "</p>";
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			if (connection != null)
				connection.close();
		}
		
		
		return string;
	}

}
