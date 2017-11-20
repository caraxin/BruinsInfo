package com.bruinsinfo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bruinsinfo.dao.UserDAO;
import com.bruinsinfo.model.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
        System.out.println("doGet Login ======");
        String userName = request.getParameter("userName");

        try {
            UserDAO dao = new UserDAO();
            User user = dao.getUserInfo(userName);
            response.getWriter().write(new Gson().toJson(user));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println("doPost Login ======");

        String user_email = request.getParameter("user_email");
        String user_password = request.getParameter("user_pass");
        System.out.println(user_email + " " + user_password);
        UserDAO userDAO = new UserDAO();
        
		try {
			User user = userDAO.getUserInfo(user_email);
			if (user == null) {
	            out.println("0");
	            System.out.println("0");
	        } else if (user.getUser_password().equals(user_password)){
	            out.println("1");
	            System.out.println("1");
	        } else {
	        	out.println("2");
	        	System.out.println("2");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.flush();
        out.close();
	}

}
