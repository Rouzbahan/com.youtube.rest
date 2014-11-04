package com.youtube.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;

public class SchemaRESTfulMySQL extends RESTfulMySQL {

	public JSONArray queryReturnBrandParts(String brand) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = MySQLPcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, " +
					"PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC from PC_PARTS where UPPER(PC_PARTS_MAKER) = ? ");

			query.setString(1, brand.toUpperCase()); // protect against sql
														// injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); // close connection
			
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
			
		} catch (Exception e) {
			e.printStackTrace();
			return json;
			
		} finally {
			if (conn != null)
				conn.close();
		}

		return json;
	}

}