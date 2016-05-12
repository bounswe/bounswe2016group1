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
 * All project goes on around this class.
 * 
 * @author sahin_batmaz
 *
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final String user = "root";
	static final String pass = "";
	static final String url = "jdbc:mysql://localhost:3306/test";

	static Connection conn = null;
	static String query = null;
	static ResultSet result = null;
	static PreparedStatement statement = null;
	
	static int listlength = 0;
	static String outputtable = "";
	static String currentuser = "";
	static String currentkeyword = "";
	

    /**
     * @see HttpServlet#HttpServlet()
     * It is default for dynamic web project.
     */
    public MainServlet() {
        super();
    }

    // -----------------------------------------------------------
    // -----------------------------------------------------------
     
    
	/**
	 * doGet method handles the login page.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		currentuser = "";
		currentkeyword = "";
		outputtable = "";
		
		response.setContentType("text/html");
		
		PrintWriter pw = response.getWriter();
		
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Best-selling Digital Singles</title>");
		pw.println("</head>");
		pw.println("<body>");
		
		pw.println("<form method=\"post\" action =\"MainServlet\"  >");
		
		pw.println("<b>Enter a user name</b>");
		
		pw.println("<input  type=\"text\" name=\"username\"   >");
		pw.println("<input  type=\"submit\" value=\"Login\">");
		
		pw.println("</form>");
	
		pw.println("</body>");
		pw.println("</html>");
		
		
	}

	
	
	
	
	/**
	 * Main part of the project is here. Whenever a post action is required, program comes to this method.
	 * Post actions are after login page, after search button, after show saved entries, after save selected entries.
	 * And there is one get action for returning login page.
	 * This method handles creating and running html code of the functions that are listed above.
	 * And so this method is the main user interface of my project.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter pw = response.getWriter();
		
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>Singles</title>");
		
		pw.println("<style>");
		pw.println("table, td {");
		pw.println("border: 1px solid black;");
		pw.println("border-collapse: collapse;");
		pw.println("}");
		pw.println("</style>");
		
		
		pw.println("</head>");
		pw.println("<body>");
		
		// ----------------------------------------------------
		pw.println("<form method=\"get\" action =\"MainServlet\" >");
		
		pw.println("<input  type=\"submit\" value=\"Go back to login\">");
		
		pw.println("</form>");
		// ----------------------------------------------------
		
		pw.println("<form method=\"post\" action =\"MainServlet\" >");
		
		pw.println("<input  type=\"submit\" name=\"savedresults\" value=\"Show all saved results.\">");
		
		pw.println("</form>");
		// ----------------------------------------------------
		
		pw.println("<form method=\"post\" action =\"MainServlet\" id= \"form1\" >");
		
		pw.println("<b>Search Best-selling Digital Singles</b>");
				
		pw.println("<input  type=\"text\" name=\"keyword\" value=\"You can enter singer name, single name, year\" style=\"width:600px\" form = \"form1\"  >");
		pw.println("<input  type=\"submit\" value=\"Search\">");
		
		pw.println("</form>");
				
		// ----------------------------------------------------
		
		try {
			functions.connect();
			functions.resetmark();
		} catch (ClassNotFoundException | SQLException e) {	e.printStackTrace();}
		
		// ----------------------------------------------------
		// post after username
		String username = request.getParameter("username");
		
		if(username != null){
			try {
				currentuser = username;
				pw.println(functions.insertuser(username));
			} catch (SQLException e) {	e.printStackTrace();}
		}

		// ----------------------------------------------------
		// post after savedresults
		String savedresults = request.getParameter("savedresults");

		if(savedresults != null){
			try {
				outputtable = "";
				outputtable += "<table>";
				outputtable += "<tr>";
				
				outputtable += "<td width=\"33%\"> Singer name </td>";
				outputtable += "<td width=\"33%\"> Single name </td>";
				outputtable += "<td width=\"34%\"> Year </td>";

				outputtable += "</tr>";
				
				query = "SELECT * FROM db WHERE id IN (SELECT id FROM saved WHERE name='"+currentuser+"');";
				functions.runnerQuery();
				
				while(result.next()){
					outputtable += "<tr>";
					
					outputtable += "<td> " + result.getString("f1") + " </td>";
					outputtable += "<td> " + result.getString("f2") + " </td>";
					outputtable += "<td> " + result.getString("f3") + " </td>";

					outputtable += "</tr>";
				}
				outputtable += "</table>";

			} catch (SQLException e) {	e.printStackTrace();}
		}


		
		// ----------------------------------------------------
		// post after search
		String keys = request.getParameter("keyword");
		
		if(keys != null){
			if(!keys.equals("")){
				currentkeyword = keys;
				
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
				
				// PREPARE DISPLAY CODE
				try {
					listlength = 20;
					outputtable = functions.display(listlength);
				} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
		// ----------------------------------------------------
		// post after checked boxes
		
		for(int i = 0 ; i < listlength ; i++){
			String id = request.getParameter("box"+(i+1));
			if(id != null){
				try {
					functions.insertrecordforsaving(currentuser, id, currentkeyword);
				} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
		
		// ----------------------------------------------------
		
		pw.println(outputtable);
		
		// ----------------------------------------------------
		
		pw.println("</body>");
		pw.println("</html>");
		
		
	}

}
