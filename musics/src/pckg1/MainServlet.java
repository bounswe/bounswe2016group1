package pckg1;

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

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final String user = "root";
	static final String pass = "12345678";
	static final String url = "jdbc:mysql://localhost:3306/test";

	static Connection conn = null;
	static String query = null;
	static ResultSet result = null;
	static PreparedStatement statement = null;
	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
    }

    
    
    
    
    
    // -----------------------------------------------------------
    // -----------------------------------------------------------
    // -----------------------------------------------------------
     
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter pw = response.getWriter();
		
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Singles</title>");
		pw.println("</head>");
		pw.println("<body>");
		
		pw.println("<form method=\"post\" action =\"MainServlet\" >");
		
		pw.println("<b>Search Singles</b>");
		
		pw.println("<input  type=\"text\" name=\"keyword\" id=\"keyword\">");
		pw.println("<input  type=\"submit\" value=\"Search\">");
		
		pw.println("</form>");
	
		pw.println("</body>");
		pw.println("</html>");
		
	
			try {
				if( functions.connect()){
					functions.select("*", "table1");
					while(result.next()){
						pw.println(result.getString(1)+" "+result.getString(2));
					}
					//functions.insertrecord(tablename, field1, field2);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}

	// -----------------------------------------------------------
    // -----------------------------------------------------------
    // -----------------------------------------------------------
   
	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
