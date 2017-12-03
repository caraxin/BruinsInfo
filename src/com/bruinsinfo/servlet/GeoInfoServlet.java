package com.bruinsinfo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.bruinsinfo.dao.LandmarkDAO;
import com.bruinsinfo.dao.UserDAO;
import com.bruinsinfo.model.Landmark;
import com.bruinsinfo.model.User;
import com.bruinsinfo.util.UserCount;
import com.google.gson.Gson;

/**
 * Servlet implementation class GeoInfoServlet
 */
@WebServlet("/GeoInfoServlet")
public class GeoInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeoInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        System.out.println("doPost GeoInfo ======");
        System.out.println(request.getParameter("param"));
        
		try {
			String req = request.getParameter("param");
	        JSONObject jsonObject = new JSONObject(req);
	        
			double latitude = jsonObject.getDouble("latitude");
	        double longitude = jsonObject.getDouble("longitude");
	        String email = jsonObject.getString("email");
	        System.out.println(latitude + " " + longitude + " " + email);
	        
	        LandmarkDAO landmarkDAO = new LandmarkDAO();
			Landmark landmark = landmarkDAO.getNearestLandmark(latitude, longitude);
			// update landmark_user_count
			UserCount.addUser(landmark.getName(), email);
			
			//grab data object returned by getUserCount
			
			HashMap<Integer, HashSet<String>> user_list = new HashMap<>();
			
			user_list = UserCount.getUserCount(landmark.getName());
			List<Integer> landmk_user_count = new ArrayList<Integer>(user_list.keySet());
			/*int landmark_user_count = UserCount.getUserCount(landmark.getName());*/
			
			int landmark_user_count = landmk_user_count.get(0);
			
			List<String> landmk_user_emails = new ArrayList<String>(user_list.get(landmark_user_count));
			
			landmark.setUserCount(landmark_user_count);
						
			Gson gson = new Gson();
			String ret = gson.toJson(landmark);
			System.out.println(ret);
			out.println(ret);
			
			//also print e-mail list
			
			String emailsret = gson.toJson(landmk_user_emails);
			System.out.println(emailsret);
			out.println(emailsret);
			
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
        out.close();
	}

}
