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
	
	static int listlength = 20;
	static String outputtable = "";
	

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
		
		pw.println("<form method=\"post\" action =\"MainServlet\"  >");
		
		pw.println("<b>Search Singles</b>");
		
		pw.println("<input  type=\"text\" name=\"keyword\"   >");
		pw.println("<input  type=\"submit\" value=\"Search\">");
		
		pw.println("</form>");
	
		pw.println("</body>");
		pw.println("</html>");
		
		
	}

	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter pw = response.getWriter();
		
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Singles</title>");
		pw.println("</head>");
		pw.println("<body>");
		
		pw.println("<form method=\"post\" action =\"MainServlet\" id= \"form1\" >");
		
		pw.println("<b>Search Singles</b>");
				
		pw.println("<input  type=\"text\" name=\"keyword\" form = \"form1\"  >");
		pw.println("<input  type=\"submit\" value=\"Search\">");
		
		pw.println("</form>");
				
		// ----------------------------------------------------
		
		try {
			functions.connect();
			functions.resetmark();
		} catch (ClassNotFoundException | SQLException e) {	e.printStackTrace();}
		
		// ----------------------------------------------------
		
		String keys = request.getParameter("keyword");
		
		if(!keys.equals("")){
			int first = 0;
			int last = 0;
			int length = keys.length();
			
			for(int i = 0; i < length ; i++){
				if( keys.charAt(i) == ' '){
					last = i;
					String key1 = keys.substring(first, last);
					
					if(!key1.equals("")){
						try {
							functions.marker(key1);
						} catch (SQLException e) {e.printStackTrace();}
					}
					first = last+1;
				}
			}
			String key1 = keys.substring(first);
			
			if(!key1.equals("")){
				try {
					functions.marker(key1);
				} catch (SQLException e) {e.printStackTrace();}
			}
			
			// ----------------------------------------------------
			
			// DISPLAY
			try {
				listlength = 20;
				outputtable = functions.display(listlength);
				pw.println(outputtable);
			} catch (SQLException e) {e.printStackTrace();}

			// ----------------------------------------------------
		
		}
		
		
		pw.println("</body>");
		pw.println("</html>");
		
		
	}

}
