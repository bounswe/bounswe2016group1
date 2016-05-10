package com.mert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Receives checked IDs by POST method and inserts them into local mysql DB
 * ignores duplicate entries and prints results to html.
  * @author Mert
 */
@WebServlet("/SaveEntries")
public class SaveEntries extends HttpServlet {
	Connection conn = null;
	Statement stmt = null;
	String sql = null;
	private static final long serialVersionUID = 1L;
	public static final int MYSQL_DUPLICATE_PK = 1062;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveEntries() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MainServlet.connectToDB();
		conn = MainServlet.conn;
		stmt = MainServlet.stmt;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Movies</title>");
		out.println("</head>");
		out.println("<body>");

		try {

			String[] IDs = request.getParameterValues("boxes");
			if (IDs != null) {
				for (int i = 0; i < IDs.length; i++) {
					try {
						sql = "INSERT INTO `mert`.`entries` (`genreID`) VALUES(\"" + IDs[i] + "\")";
						stmt.executeUpdate(sql);
					} catch (SQLException e) {
						if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
							out.println(IDs[i] + " already exists, continuing");
							continue;
						}
					}
				}
			} else {
				out.println("No checkbox selected");
				out.println("</body>");
				out.println("</html>");
			}

			MainServlet.connectToDB();

			String sql = "SELECT `entries`.`genreID` FROM `mert`.`entries`";

			out.println("<table border=\"1\">");
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				out.println("<tr>");
				String genreID = rs.getString("genreID");
				out.print("<td>" + genreID + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {

		}finally {
			MainServlet.closeDBConnections();
			}
	}

}
