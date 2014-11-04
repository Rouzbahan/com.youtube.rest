package com.youtube.dao;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;

public class RESTfulMySQL {
	
	private static DataSource dataSourceMySQL = null;
	private static Context context= null;
	
	public static DataSource MySQLConn() throws Exception {
		
		if (dataSourceMySQL != null)
			return dataSourceMySQL;
		
		try {
			if (context == null)
				context = new InitialContext();
			
			dataSourceMySQL = (DataSource) context.lookup("RESTfulMySQL");
			//dataSourceMySQL = (DataSource) context.lookup("RESTful_pc_parts");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataSourceMySQL;
	}
	
	protected static Connection MySQLPcPartsConnection() {
		Connection conn = null;
		
		try {
			conn = MySQLConn().getConnection();
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
