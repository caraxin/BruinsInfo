package com.bruinsinfo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
	        System.out.println(latitude + " " + longitude);
	        
	        LandmarkDAO landmarkDAO = new LandmarkDAO();
			Landmark landmark = landmarkDAO.getNearestLandmark(latitude, longitude);
			Gson gson = new Gson();
			String ret = gson.toJson(landmark);
			System.out.println(ret);
			out.println(ret);
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
        out.close();
	}

}
