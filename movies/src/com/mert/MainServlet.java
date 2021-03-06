package com.mert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mert Landing page of this web app
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	static final String USER = "root";
	static final String PW = "";
	// static final String DB_URL =
	public static String DB_URL = "jdbc:mysql://localhost:3306/mert";
	 //public static String DB_URL = "jdbc:mysql://localhost:3306/mert";
	static Connection conn = null;
	static Statement stmt = null;
	static String sql = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Handles all of the sql connections, it is static and is used by other
	 * classes too
	 * 
	 * @return true if a connection already existed or just established
	 * @return false if connection could not be established
	 */
	public static boolean connectToDB() {
		if (conn != null)
			try {
				if (conn.isValid(0))
					return true;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		try {

			// STEP 2: Register JDBC driver
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PW);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			return false;
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Closes open local DB connections gracefully.
	 * 
	 */
	public static void closeDBConnections() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prints out all the html contents on main page such as buttons and search
	 *
	 */
	public void drawPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ClassNotFoundException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Movies</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("Movies that have won Best Picture in Oscars");

		out.println("<form action=\"UpdateDB\">");
		out.println("<input type=\"submit\" name=\"but1\" method=\"POST\" id=\"f1\" value=\"Update local DB\">");
		out.println("</form>");
		out.println("<form action=\"ShowMovies\">");
		out.println(
				"<input type=\"submit\" name=\"but2\" method=\"POST\" id=\"f2\" value=\"Show Movies that won oscar on saved topics\">");
		out.println("</form>");
		out.println("<form action=\"ShowEntries\">");
		out.println("<input type=\"submit\" name=\"but3\" method=\"POST\" id=\"f3\"value=\"Show stored entries\">");
		out.println("</form>");
		out.println("<form method=\"post\" name=\"frm\" action=\"Search\">");
		out.println("<table border=\"0\" width=\"430\">");
		out.println("<tr><td colspan=2 style=\"font-size:12pt;\" align=\"right\">");
		out.println("<tr><td ><b>Search Movie Subject</b></td>");
		out.println("<td>: <input  type=\"text\" name=\"pid\" id=\"pid\">");
		out.println("</td></tr>        ");
		out.println("<tr><td  align=\"right\">");
		out.println("<input  type=\"submit\" name=\"submit\" value=\"Search\"></td></tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("<table border=\"1\">");
		out.println("<form action=\"SaveEntries\" method=\"POST\" id=\"my_form\">");
		out.println("<input type=\"submit\" name=\"but4\" value=\"Store checked entries\">");

		try {
			connectToDB();
			sql = "SELECT `movies`.`genre`, `movies`.`genreID`, `movies`.`count` FROM `mert`.`movies` ORDER BY `count` DESC;";
			ResultSet rs = stmt.executeQuery(sql);
			out.println("<tr>");
			out.println("<td>store?</td>");
			out.println("<td>subjectID</td>");
			out.println("<td>Main Subject</td>");
			out.println("<td>count</td>");
			out.println("</tr>");
			while (rs.next()) {
				out.println("<tr>");
				String genre = rs.getString("genre");
				int count = rs.getInt("count");
				String ID = rs.getString("genreID");
				out.print(
						"<td>" + "<input type=\"checkbox\" name=\"boxes\" value=\"" + ID + "\" form=\"my_form\"></td>");
				out.print("<td>" + ID + "</td>");
				out.print("<td>" + genre + "</td>");
				out.print("<td>" + count + "</td>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		out.close();
		closeDBConnections();

	}

	/**
	 * This function acts as main and is executed when page is requested.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			drawPage(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}