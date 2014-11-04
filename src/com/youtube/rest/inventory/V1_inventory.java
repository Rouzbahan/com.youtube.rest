package com.youtube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.RESTfulMySQL;
import com.youtube.util.ToJSON;


@Path("/v1/inventory")
public class V1_inventory {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts() throws Exception {
		String string = "";
		PreparedStatement query = null;
		Connection connection = null;
		Response rb = null;
		
		try {
			connection = RESTfulMySQL.MySQLConn().getConnection();
			query = connection.prepareStatement("select * from pc_parts");
			ResultSet resultSet = query.executeQuery();
			
			ToJSON convertor = new ToJSON();
			JSONArray jsonArray = new JSONArray();
			
			jsonArray = convertor.toJSONArray(resultSet);
			
			query.close();
			
			string = jsonArray.toString();
			rb = Response.ok(string).build();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			
			
			if (connection != null)
				connection.close();
		}
				
		return rb;
	}
	
}
