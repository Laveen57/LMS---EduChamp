package edu.learn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private static Connection con;
	public static Connection getConnection() {
		try {
			if(con==null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","system","20f151");
			}
		}catch(ClassNotFoundException e) {
			System.out.println(e);
		}catch(SQLException e) {
			System.out.println(e);
		}
		return con;
	}
	
	public static void main(String[] args) {
	DBUtil bd=new DBUtil();
	System.out.println(bd.getConnection());
}
	
}
