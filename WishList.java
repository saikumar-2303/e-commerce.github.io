package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/wishlist")
public class WishList extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		int id=Integer.parseInt(req.getParameter("id"));
		Connection con=null;
		PreparedStatement ps =null;
		
		String url = "jdbc:mysql://localhost:3306/storemanager";
		String user="root";
		String password="root";
		PrintWriter out=resp.getWriter();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,user,password);
			ps=con.prepareStatement("SELECT * FROM products WHERE id=?");
			
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				ps=con.prepareStatement("INSERT INTO wishlist (id,name,model,cost,ram,rom,color,remaining) VALUES(?,?,?,?,?,?,?,?)");
				ps.setInt(1, rs.getInt(1));
				ps.setString(2, rs.getString(2));
				ps.setString(3, rs.getString(3));
				ps.setInt(4, rs.getInt(4));
				ps.setInt(5, rs.getInt(5));
				ps.setInt(6, rs.getInt(6));
				ps.setString(7, rs.getString(7));
				ps.setInt(8, rs.getInt(8));
				ps.executeUpdate();
				RequestDispatcher res=req.getRequestDispatcher("displayAll");
				res.forward(req, resp);
			}
			
			
		} catch(SQLIntegrityConstraintViolationException e)
		{
			RequestDispatcher rs=req.getRequestDispatcher("displayAll");
			rs.forward(req, resp);
		}
		catch (ClassNotFoundException | SQLException e) {
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

