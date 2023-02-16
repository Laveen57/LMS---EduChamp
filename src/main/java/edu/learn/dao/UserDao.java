package edu.learn.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.learn.model.*;
import edu.learn.util.*;

public class UserDao {

	private Connection con;

	public int insert(User user) {

		con = DBUtil.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO LMS (NAME, USERNAME, EMAIL, PASSWORD, DOMAIN) VALUES (?,?,?,?,?)");
			pst.setString(1,user.getName());
			pst.setString(2,user.getUsername());
			pst.setString(3,user.getEmail());
			pst.setString(4,user.getPassword());
			pst.setString(5,user.getDomain());
			int i = pst.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		return 0;
	}
	
	public String authenticateUser(User user) {
		con = DBUtil.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("select password from lms where USERNAME=?");
			pst.setString(1,user.getUsername());
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				if(user.getPassword().equals(rs.getString(1))) {
					return "success";
				}
			}
		
		} catch (SQLException e) {
			System.out.println(e);
		}
		return "error";
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			
			if (myRs != null) {
				
				myRs.close();
			}
			
			if (myStmt != null) {
				
				myStmt.close();
			}
			
			if (myConn != null) {
				
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}                     // and makes it available for someone else to use
		} catch (Exception exc) {
			
			System.out.println("A intervenit aceasta exceptie"); 
			System.out.println(exc);
		}
	}

	public void updateUser(User theUser) throws Exception {  
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			
			// get db connection
			con = DBUtil.getConnection();
			
			// create SQL update statement
			String sql = "UPDATE user "
						+ "SET user_name = ?, name = ?, password = ?, country = ?, town = ?, email = ?, age = ? "
						+ "WHERE id = ?";
			
			// prepare statement
			myStmt = con.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theUser.getUsername());
			myStmt.setString(2, theUser.getName());
			myStmt.setString(3, theUser.getPassword());
			myStmt.setString(6, theUser.getEmail());
			
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
		
	}
}
