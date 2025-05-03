package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/buy")
public class Buy extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
        int left = Integer.parseInt(req.getParameter("remaining"));

		Connection con = null;
		PreparedStatement ps = null;
		PrintWriter out = resp.getWriter();
    

    try {
       		String url = "jdbc:mysql://localhost:3306/storemanager";
			String user = "root";
			String password = "root";

			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);

			ps = con.prepareStatement("UPDATE products SET remaining = ? WHERE id = ?");
			int remaining = left - 1;
			
			ps.setInt(1, remaining);
			ps.setInt(2, id);
		
			int res = ps.executeUpdate();

			out.println("<html><head><title>Apple</title></head><body><div id='division'>");
			if (res == 1) {
				out.println("<h5 id='heading' class='inputs'>Order Placed</h5>");
			} else {
				out.println("<h5 id='heading' class='inputs'>Error Placing Order</h5>");
			}
			out.println("</div></body></html>");
		

    }catch (ClassNotFoundException | SQLException e) 
	{
        e.printStackTrace();
    }
	finally 
	{
        try 
		{
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) 
		{
            e.printStackTrace();
        }
    }
	}
}
