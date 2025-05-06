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
@WebServlet("/signup")
public class SingUp extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String name= req.getParameter("name");
		long num=Long.parseLong(req.getParameter("number"));
		
		String url = "jdbc:mysql://sql8.freesqldatabase.com:3306/sql8777200";
		String user="root";
		String pass="root";
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pass);
			
			
			
			ps=con.prepareStatement("INSERT INTO customer (name,email,password,number) VALUES(?,?,?,?)");
			
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setLong(4, num);
			
			int res = ps.executeUpdate();
			if(res==1)
			{
				RequestDispatcher r = req.getRequestDispatcher("Login.html");
				r.forward(req, resp);
			}
			else {
				
			}
		} catch (ClassNotFoundException | SQLException e) 
		{	// TODO Auto-generated catch block
			e.printStackTrace();
		}finally 
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
	}
}
