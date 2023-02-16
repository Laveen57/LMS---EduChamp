package edu.learn.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.learn.dao.UserDao;
import edu.learn.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("dzName");
		String username = request.getParameter("dzUserName");
		String email = request.getParameter("dzEmail");
		String domain = request.getParameter("Domain");
		String password = request.getParameter("dzPass");
		
		User user = new User(name,username,email,password,domain);
		
		UserDao userDAO = new UserDao();
		String result=userDAO.authenticateUser(user);
		
		if(result.equals("success")) {
			response.sendRedirect("Learner-index.jsp");
			
		}else {
			request.setAttribute("error","Invalid Username or Password");
			RequestDispatcher rd=request.getRequestDispatcher("/login.jsp");            
			rd.include(request, response);
		}
		
		
	}

}
