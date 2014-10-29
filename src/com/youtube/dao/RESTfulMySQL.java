package com.youtube.dao;

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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataSourceMySQL;
	}
	
}
