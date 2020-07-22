package com.guest.jdbc;

import java.sql.*;

public class JdbcUtil {
	
	public static void close(AutoCloseable... resource)
	{
		try {
			
			for(AutoCloseable res : resource) {
				res.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public static void rollback(Connection conn) {
		if(conn != null) {
			try {
				conn.rollback();				
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	

}
