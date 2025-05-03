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

@WebServlet("/displayAll")
public class DisplayAll extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		Connection con=null;
		PreparedStatement ps =null;
		
		String url = "jdbc:mysql://localhost:3306/storemanager";
		String user="root";
		String password="root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,user,password);
			ps=con.prepareStatement("SELECT * FROM products");
			
			
			ResultSet rs=ps.executeQuery();
			PrintWriter out= resp.getWriter();
			out.println("<html>");
			out.println("<title>Apple</title>");
			
			out.println("<body>");
			
			out.println("<div id='division'>");
			while(rs.next())
			{
				
				out.println("<h5 id='heading' class='inputs'>");
				
				out.println("Name: "+rs.getString(2));
				out.println("Model: "+rs.getString(3));
				out.println("ram : "+rs.getInt(5));
				out.println("rom: "+rs.getInt(6));
				out.println("color: "+rs.getString(7));
				out.println("Cost: "+rs.getInt(4));
				out.println("</h5>");
				if(rs.getInt(8)<=3) {
					out.println("<span id='span'>only " +rs.getInt(8)+" left</span>");
				}
				
				out.println("<form action='buy' method='post'>");
				out.println("<input type='hidden' name='id' value='" + rs.getInt(1) + "'>");
				out.println("<input type='hidden' name='remaining' value='" + rs.getInt(8) + "'>"); 
				out.println("<button type='submit' class='buttons'>Buy</button>");
				out.println("</form>");
				out.println("<form action='wishlist?id="+rs.getInt(1)+"' method='post'><button type='submit' class='buttons'>Whislist</button></form>");
				out.println("<form action='cart?id="+rs.getInt(1)+"' method='post'><button type='submit' class='buttons'>More</button></form>");
			}
			out.println("<form action='wldisplay?id="+rs.getInt(1)+"' method='post'><button type='submit' class='buttons'>ViewWhislist</button></form>");

			out.println("</div>");
			
			out.println("</body>");
			out.println("</html>");
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(con!=null)
				{
					con.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
