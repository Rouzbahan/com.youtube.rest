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
			query = conn
					.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, "
							+ "PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC from PC_PARTS where UPPER(PC_PARTS_MAKER) = ? ");

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

	public JSONArray queryReturnBrandItemNumber(String brand, int item_number)
			throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			conn = MySQLPcPartsConnection();
			query = conn
					.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, "
							+ "PC_PARTS_AVAIL, PC_PARTS_DESC from PC_PARTS "
							+ "where UPPER(PC_PARTS_MAKER) = ? and PC_PARTS_CODE = ?");

			/*
			 * protect against sql injection when you have more than one ?, it
			 * will go in chronological order.
			 */
			query.setString(1, brand.toUpperCase()); // first ?
			query.setInt(2, item_number); // second ?
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

	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, String PC_PARTS_CODE,
			String PC_PARTS_MAKER, String PC_PARTS_AVAIL, String PC_PARTS_DESC)
			throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation
			 * here before starting to insert data into the database.
			 * 
			 * Important: The primary key on PC_PARTS table will auto increment.
			 * That means the PC_PARTS_PK column does not need to be apart of
			 * the SQL insert query below.
			 */
			conn = MySQLPcPartsConnection();
			query = conn
					.prepareStatement("insert into PC_PARTS (PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC) "
							+ "VALUES ( ?, ?, ?, ?, ? ) ");

			query.setString(1, PC_PARTS_TITLE);
			query.setString(2, PC_PARTS_CODE);
			query.setString(3, PC_PARTS_MAKER);

			// PC_PARTS_AVAIL is a number column, so we need to convert the
			// String into a integer
			int avilInt = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(4, avilInt);

			query.setString(5, PC_PARTS_DESC);
			query.executeUpdate(); // note the new command for insert statement

		} catch (Exception e) {
			e.printStackTrace();
			return 500; // if a error occurs, return a 500
		} finally {
			if (conn != null)
				conn.close();
		}

		return 200;
	}

	public int updatePC_PARTS(int pk, int avail) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation
			 * here before updating data.
			 */

			conn = MySQLPcPartsConnection();
			query = conn.prepareStatement("update PC_PARTS "
					+ "set PC_PARTS_AVAIL = ? " + "where PC_PARTS_PK = ? ");

			query.setInt(1, avail);
			query.setInt(2, pk);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		} finally {
			if (conn != null)
				conn.close();
		}

		return 200;
	}

	public int deletePC_PARTS(int pk) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation
			 * here before deleting data.
			 */

			conn = MySQLPcPartsConnection();
			query = conn.prepareStatement("delete from PC_PARTS "
					+ "where PC_PARTS_PK = ? ");

			query.setInt(1, pk);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		} finally {
			if (conn != null)
				conn.close();
		}

		return 200;
	}

}
