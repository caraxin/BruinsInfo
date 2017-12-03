package com.bruinsinfo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bruinsinfo.model.Landmark;
import com.bruinsinfo.model.User;

public class LandmarkDAO {
	private String USERNAME = "root";
	private String PASSWORD = "bruininfo";
	
	private static final String URL ="jdbc:mysql://localhost:3306/BruinInfo";
    private static final String MYSQL_DRIVER ="com.mysql.jdbc.Driver";
    private static final String GET_NEAREST_LANDMARK = "SELECT *, " +
    		"ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - latitude * PI() / 180) / 2), 2)" +
    		" + COS(? * PI() / 180) * COS(latitude * PI() / 180) * POW(SIN((? * PI() / 180 - longitude * PI() / 180) / 2), 2))) * 1000) AS distance " +
    		"FROM GeoInfo ORDER BY distance asc LIMIT 1";
    
    public static void main(String args[]) throws SQLException {
    	LandmarkDAO dao = new LandmarkDAO();
    	//dao.updateUserInfo("caraxin", "0129", "123@123.com");
    	Landmark l = dao.getNearestLandmark(34.0686201, -118.4428575);
    	System.out.println(l.getName() + " " + l.getLatitude() + " " + l.getLongitude() + " " + l.getDistance());
    }
    
    static {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found.");
        }
    }
    
    private Connection conn = null;
    private PreparedStatement getNearestLandmarkStmt = null;
    
    public Landmark getNearestLandmark(double latitude, double longitude) throws SQLException {
    	PreparedStatement stmt = getGetNearestLandmarkStmt();
    	stmt.setDouble(1, latitude);
    	stmt.setDouble(2, latitude);
    	stmt.setDouble(3, longitude);
    	ResultSet rs = stmt.executeQuery();
    	if (rs.next()) {
    		String landmark_name = rs.getString(1);
    		double landmark_latitude = rs.getDouble(2);
    		double landmark_longitude = rs.getDouble(3);
    		String landmark_url = rs.getString(4);
    		double distance = rs.getDouble(5);
    		clearConnection();
    		return new Landmark(landmark_name, landmark_latitude, landmark_longitude, landmark_url, distance);
    	}
    	else return null;
    }
    
    public void clearConnection() throws SQLException {
    	if (getNearestLandmarkStmt != null && !getNearestLandmarkStmt.isClosed()) {
    		getNearestLandmarkStmt.close();
    		getNearestLandmarkStmt = null;
    	}
    	
    	if (conn != null && !conn.isClosed()) {
    		conn.close();
    		conn = null;
    	}
    }
    
    private PreparedStatement getGetNearestLandmarkStmt() throws SQLException {
    	if (conn == null || conn.isClosed()) {
    		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    	}
    
	    if (getNearestLandmarkStmt == null || getNearestLandmarkStmt.isClosed()) {
	    	getNearestLandmarkStmt = conn.prepareStatement(GET_NEAREST_LANDMARK);
	    }
	    return getNearestLandmarkStmt;
    }
}
