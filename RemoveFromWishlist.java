package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/oneRemove")
public class RemoveFromWishlist extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		int id = Integer.parseInt(req.getParameter("id"));
		
		String url = "jdbc:mysql://localhost:3306/storemanager";
		String user="root";
		String password="root";
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,user,password);
			ps=con.prepareStatement("DELETE FROM wishlist WHERE id=?");
			ps.setInt(1, id);
			
			int res = ps.executeUpdate();
			PrintWriter out =resp.getWriter();
			
			out.println("<html>");
			out.println("<title>Apple</title>");
			
			out.println("<body>");
			out.println("<form action='wldisplay' method='post'>");
			out.println("<div id='division'>");
			if(res==1)
			{
//				out.println("<h1 id='heading' class='inputs'>successful</h1>");
//				out.println("<button type='submit' class='inputs input'>Go Back</button>");
				RequestDispatcher rs= req.getRequestDispatcher("wldisplay");
				rs.forward(req, resp);
			}
			else {
				RequestDispatcher rsd = req.getRequestDispatcher("oneRemove");
				out.println("<h5 id='heading' class='inputs'>NoData</h5>");
				rsd.include(req, resp);
			}
			out.println("</div>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
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
	}
}
