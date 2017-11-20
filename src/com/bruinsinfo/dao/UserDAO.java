package com.bruinsinfo.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bruinsinfo.model.User;

public class UserDAO {
	private String USERNAME = "root";
	private String PASSWORD = "root";
	
	private static final String URL ="jdbc:mysql://localhost:3306/BruinsInfo";
    private static final String MYSQL_DRIVER ="com.mysql.jdbc.Driver";
    private static final String GET_USER_INFO = "select user_email, user_name, user_password, user_phone, user_address from User where user_email = ?;";
    private static final String INSERT_USER = "insert into User (user_email, user_name, user_password, user_phone, user_address) values (?, ?, ?, ?, ?);";
    
    public static void main(String args[]) throws SQLException {
    	UserDAO dao = new UserDAO();
    	//dao.updateUserInfo("caraxin", "0129", "123@123.com");
    }
    
    static {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found.");
        }
    }
    
    private Connection conn = null;
    private PreparedStatement getUserInfoStmt = null;
    private PreparedStatement insertUserStmt = null;
    
    public User getUserInfo (String user_email) throws SQLException {
    	PreparedStatement stmt = getGetUserInfoStmt();
    	stmt.setString(1, user_email);
    	ResultSet rs = stmt.executeQuery();
    	if (rs.next()) {
    		String user_name = rs.getString(2);
    		String user_password = rs.getString(3);
    		String user_phone = rs.getString(4);
    		String user_address = rs.getString(5);
    		return new User(user_email, user_name, user_password, user_phone, user_address);
    	}
    	else return null;
    }
    
    public int insertUser(User user) throws SQLException {
    	PreparedStatement stmt = getInsertUserStmt();
    	stmt.setString(1, user.getUser_email());
    	stmt.setString(2, user.getUser_name());
    	stmt.setString(3, user.getUser_password());
    	stmt.setString(4, user.getUser_phone());
    	stmt.setString(5, user.getUser_address());
    	int rs = stmt.executeUpdate();
        return rs;
    }
    
    public void clearConnection() throws SQLException {
    	if (getUserInfoStmt != null || !getUserInfoStmt.isClosed()) {
    		getUserInfoStmt.close();
    		getUserInfoStmt = null;
    	}
    	
    	if (insertUserStmt != null || !insertUserStmt.isClosed()) {
    		insertUserStmt.close();
    		insertUserStmt = null;
    	}
    	
    	if (conn != null || !conn.isClosed()) {
    		conn.close();
    		conn = null;
    	}
    }
    
    private PreparedStatement getGetUserInfoStmt() throws SQLException {
    	if (conn == null || conn.isClosed()) {
    		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	}
    
	    if (getUserInfoStmt == null || getUserInfoStmt.isClosed()) {
	    	getUserInfoStmt = conn.prepareStatement(GET_USER_INFO);
	    }
	    return getUserInfoStmt;
    }
    
    private PreparedStatement getInsertUserStmt() throws SQLException {
    	if (conn == null || conn.isClosed()) {
    		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	}
    
	    if (insertUserStmt == null || insertUserStmt.isClosed()) {
	    	insertUserStmt = conn.prepareStatement(INSERT_USER);
	    }
	    return insertUserStmt;
    }
    
}
