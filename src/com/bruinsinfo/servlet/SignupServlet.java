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

import com.bruinsinfo.dao.UserDAO;
import com.bruinsinfo.model.User;

/**
 * Servlet implementation class SigninServlet
 */
@WebServlet("/SigninServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
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
        System.out.println("doPost Signup ======");
        System.out.println(request.getParameter("param"));
        
		try {
			String req = request.getParameter("param");
	        JSONObject jsonObject = new JSONObject(req);
	        
			String user_name = jsonObject.getString("user_name");
	        String user_pass = jsonObject.getString("user_pass");
	        String user_email = jsonObject.getString("user_email");
	        String user_phone = jsonObject.getString("user_phone");
	        String user_address = jsonObject.getString("user_address");
	        System.out.println(user_name + " " + user_email);
	        
	        User user = new User(user_email, user_name, user_pass, user_phone, user_address);
	        UserDAO userDAO = new UserDAO();
			int changed = userDAO.insertUser(user);
			out.println(changed);
			System.out.println(changed);
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(0);
		out.flush();
        out.close();
	}

}
