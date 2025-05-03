package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/login")
public class Login extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		String url = "jdbc:mysql://localhost:3306/storemanager";
		String user="root";
		String pass="root";
		
		Connection con = null;
		PreparedStatement ps = null;
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pass);
			ps=con.prepareStatement("SELECT * FROM customer WHERE email=?");
			ps.setString(1, email);
			ResultSet res = ps.executeQuery();
			if(res.next())
			{
				if(res.getString(4).equals(password))
				{
					HttpSession ses = req.getSession();
					ses.setAttribute("name", res.getString(2));
					ses.setAttribute("id", res.getInt(1));
					RequestDispatcher rd=req.getRequestDispatcher("displayAll");
					rd.forward(req, resp);
				}
				else {
					RequestDispatcher rs = req.getRequestDispatcher("Login.html");
					out.println("<h5>InvalidPassword</h5>");
					rs.include(req, resp);
				}
			}
			else {
				RequestDispatcher rs = req.getRequestDispatcher("Login.html");
				out.println("<h5>InvaliUserName</h5>");
				rs.include(req, resp);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			try {
				if(con!=null)
				con.close();
				if(ps!=null)
					ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.println("<html>");
		out.println("<body>");
	}
}
