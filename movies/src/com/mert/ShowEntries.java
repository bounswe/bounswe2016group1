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
 * Queries and displays entries stored in local DB
 * Only the ID is stored and is shown.
 *  @author Mert
 */
@WebServlet("/ShowEntries")
public class ShowEntries extends HttpServlet {
	static final String USER = "root";
	static final String PW = "1234";
	// static final String DB_URL =
	// "jdbc:mysql://ec2-54-191-203-200.us-west-2.compute.amazonaws.com:3306/devs";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";
	Connection conn = null;
	Statement stmt = null;
	String sql = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowEntries() {
		super();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
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
			out.println("<table border=\"1\">");
			
			String sql = "SELECT `entries`.`genreID` FROM `sakila`.`entries`";

			stmt.executeQuery(sql);
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
			stmt.close();
			conn.close();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
