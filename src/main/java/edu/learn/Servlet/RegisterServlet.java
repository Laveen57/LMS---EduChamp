package edu.learn.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.learn.dao.UserDao;
import edu.learn.model.User;
import edu.learn.util.DBUtil;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher dispatcher = null;
		String name = request.getParameter("dzName");
		String username = request.getParameter("dzUserName");
		String email = request.getParameter("dzEmail");
		String domain = request.getParameter("Domain");
		String password = request.getParameter("dzPass");
		User user = new User(name, username, email, password, domain);
		user.setName(name);
		user.setUsername(username);
		user.setEmail(email);
		user.setDomain(domain);
		user.setPassword(password);
		con = DBUtil.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO LMS (NAME, USERNAME, EMAIL, PASSWORD, DOMAIN) VALUES (?,?,?,?,?)");
			
			pst.setString(1,user.getName());
			pst.setString(2,user.getUsername());
			pst.setString(3,user.getEmail());
			pst.setString(4,user.getPassword());
			pst.setString(5,user.getDomain());
			int i = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("login.jsp");
			if(i > 0) {
				request.setAttribute("status", "success");
//				request.getRequestDispatcher("login.jsp").include (request, response);
	        }
			else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		
	}

}
