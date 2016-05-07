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

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSetFormatter;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
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
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Create movies in local db assuming it does not exist
	 */
	public void createMoviesTable() {
		try {
			// STEP 2: Register JDBC driver
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PW);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "CREATE TABLE `movies` (  " + "`movie_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  "
					+ "`title` varchar(255) NOT NULL, " + "`publicationDate` int(10),  " + "`duration` int(10),  "
					+ "`director`  varchar(50),  " + "`genre` varchar(50),  "
					+ "`mainSubject` varchar(50), PRIMARY KEY (`movie_id`) ) "
					+ "ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;";
			stmt.executeUpdate(sql);

			// STEP 6: Clean-up environment

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

	/*
	 * Updates local movies table with the new information on wikidata.org
	 */
	public void insertIntoTable(String title, String publicationDate, String duration, String director, String genre,
			String mainSubject) {
		try {
			// STEP 2: Register JDBC driver
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PW);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO `sakila`.`movies`" + "`title`," + "`publicationDate`," + "`duration`,"
					+ "`director`," + "`genre`," + "`mainSubject`)" + "VALUES ('" + title + "',  '" + publicationDate
					+ "', '" + duration + "', '" + director + "', '" + genre + "', '" + mainSubject + "');";

			stmt.executeUpdate(sql);

			// STEP 6: Clean-up environment

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

	public void connectToDB() {
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
	 * creates experimental movies table in local db
	 */
	public void createSampleTable() {

		try {
			sql = "INSERT INTO `sakila`.`movies`(" + "`title`," + "`publicationDate`," + "`duration`,"
					+ "`director`," + "`genre`," + "`mainSubject`)" + "VALUES ('The Shawshank Redemption', '" + "9.2"
					+ "', '" + "1994" + "', '" + "100" + "', '" + "alice" + "', '" + "asd" + "', '" + "prison" + "');";

			stmt.executeUpdate(sql);

			sql = "INSERT INTO `sakila`.`movies`(" + "`title`," + "`publicationDate`," + "`duration`,"
					+ "`director`," + "`genre`," + "`mainSubject`)" + "VALUES ('" + "The Godfather" + "', '" + "9.2"
					+ "', '" + "1972" + "', '" + "180" + "', '" + "bob" + "', '" + "asdasd" + "', '" + "mafia" + "');";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO `sakila`.`movies`(" + "`title`," + "`publicationDate`," + "`duration`,"
					+ "`director`," + "`genre`," + "`mainSubject`)" + "VALUES ('" + "The Dark Knight" + "', '" + "8.9"
					+ "', '" + "2008" + "', '" + "110" + "', '" + "chris" + "', '" + "asdasd" + "', '" + "batman"
					+ "');";
			stmt.executeUpdate(sql);
			// STEP 6: Clean-up environment

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Prints out movies table from the local db
	 */
	public void displayTable(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ClassNotFoundException {
		try {
			// STEP 2: Register JDBC driver
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PW);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Movies</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<table border=\"1\">");
			try {

				sql = "SELECT `movies`.`movie_id`," + "    `movies`.`title`," 
						+ "    `movies`.`publicationDate`," + "    `movies`.`duration`," + "    `movies`.`director`,"
						+ "    `movies`.`genre`," + "    `movies`.`mainSubject`" + "FROM `sakila`.`movies`;";
				ResultSet rs = stmt.executeQuery(sql);
				out.println("<tr>");
				out.println("<td>movie_id</td>");
				out.println("<td>title</td>");
				out.println("<td>publicationDate</td>");
				out.println("<td>duration</td>");
				out.println("<td>director</td>");
				out.println("<td>genre</td>");
				out.println("<td>mainSubject</td>");
				out.println("</tr>");
				while (rs.next()) {
					out.println("<tr>");
					int movieId = rs.getInt("movie_id");
					String title = rs.getString("title");
					int publicationDate = rs.getInt("publicationDate");
					int duration = rs.getInt("duration");
					String director = rs.getString("director");
					String genre = rs.getString("genre");
					String mainSubject = rs.getString("mainSubject");
					out.print("<td>" + movieId + "</td>");
					out.print("<td>" + title + "</td>");
					out.print("<td>" + publicationDate + "</td>");
					out.print("<td>" + duration + "</td>");
					out.print("<td>" + director + "</td>");
					out.print("<td>" + genre + "</td>");
					out.print("<td>" + mainSubject + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("</body>");
				out.println("</html>");
			} catch (SQLException e) {
				out.println("An error occured while retrieving " + "all employees: " + e.toString());
			} finally {
				try {
					if (stmt != null) {
						stmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException ex) {
				}
			}
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
			out.close();
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

	public void drawPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ClassNotFoundException {
		try {
			// STEP 2: Register JDBC driver
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PW);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Movies</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form action=\"MainServlet\">");
			out.println("<input type=\"button\" name=\"but1\" value=\"Movies that won Oscar\">");
			out.println("</form>");

			out.println("<form action=\"CastMembers\">");
			out.println("<input type=\"button\" name=\"but2\" value=\"Cast members played in selected Movies\">");
			out.println("</form>");

			out.println("<form action=\"ShowEntries\">");
			out.println("<input type=\"button\" name=\"but3\" value=\"Show stored entries\">");
			out.println("</form>");

			out.println("<form action=\"SaveEntries\" method=\"POST\" id=\"my_form\">");
			out.println("<table border=\"1\">");
			try {

				sql = "SELECT `movies`.`movie_id`," + "    `movies`.`title`," + "    `movies`.`publicationDate`,"
						+ "    `movies`.`duration`," + "    `movies`.`director`," + "    `movies`.`genre`,"
						+ "    `movies`.`mainSubject`" + "FROM `sakila`.`movies`;";
				ResultSet rs = stmt.executeQuery(sql);
				out.println("<tr>");
				out.println("<td>store?</td>");
				out.println("<td>movie_id</td>");
				out.println("<td>title</td>");
				out.println("<td>publicationDate</td>");
				out.println("<td>duration</td>");
				out.println("<td>director</td>");
				out.println("<td>genre</td>");
				out.println("<td>mainSubject</td>");
				out.println("</tr>");
				int rowIndex = 0;
				while (rs.next()) {
					out.println("<tr>");
					int movieId = rs.getInt("movie_id");
					String title = rs.getString("title");
					int publicationDate = rs.getInt("publicationDate");
					int duration = rs.getInt("duration");
					String director = rs.getString("director");
					String genre = rs.getString("genre");
					String mainSubject = rs.getString("mainSubject");
					rowIndex++;
					out.print("<td>" + "<input type=\"checkbox\" name=\"" + Integer.toString(rowIndex)
							+ "\" form=\"my_form\"></td>");
					out.print("<td><input type=\"text\" name=\"id" + Integer.toString(rowIndex)
							+ "\" readonly=\"yes\"  value=\"" + movieId + "\" </td>");

					out.print("<td>" + title + "</td>");
					out.print("<td>" + publicationDate + "</td>");
					out.print("<td>" + duration + "</td>");
					out.print("<td>" + director + "</td>");
					out.print("<td>" + genre + "</td>");
					out.print("<td>" + mainSubject + "</td>");
					out.println("</tr>");
				}

				out.println("</table>");
				out.println("<input type=\"submit\" name=\"but4\" value=\"Store checked entries\">");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
			} catch (SQLException e) {
				out.println("An error occured while retrieving " + e.toString());
			} finally {
				try {
					if (stmt != null) {
						stmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException ex) {
				}
			}
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
			out.close();
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

	/*
	 * Queries the wikidata.org to get up-to-date information and stores it in
	 * local db
	 */
	public void updateMoviesTable() {
		String queryString = "PREFIX bd: <http://www.bigdata.com/rdf#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX wd: <http://www.wikidata.org/entity/> " + "PREFIX wikibase: <http://wikiba.se/ontology#>"
				+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
				+ "SELECT (SAMPLE(?movie) as ?movies) (SAMPLE(?title) AS ?titles) "
				+ "(SAMPLE(?genre) AS ?genres) (SAMPLE(year(?publicationDate)) as ?year) (SAMPLE(?duration) AS ?durations)     "
				+ "(SAMPLE(?mainSubject) AS ?mainSubjects) (SAMPLE(?directorName) AS ?directorNames)  "
				+ "(SAMPLE(?productionCompany) AS ?productionCompanies) " + "WHERE { 	"
				+ "	?movie wdt:P31 wd:Q11424 .     "
				+ "	?movie wdt:P1476 ?title filter (lang(?title) = \"en\") .           "
				+ "	?movie wdt:P2047 ?duration .         " + "	?movie wdt:P162 ?producer .         "
				+ "	?movie wdt:P272 ?prodComp .         " + "	?movie wdt:P136 ?genreID .         "
				+ "	?movie wdt:P921 ?mainSubjectID .     "
				+ "	?movie wdt:P577 ?publicationDate .	FILTER (year(?publicationDate) < year(now()))     "
				+ "	?movie wdt:P57 ?director .     " + "	SERVICE wikibase:label " + "			{       "
				+ "			bd:serviceParam wikibase:language \"en\" .         "
				+ "			?director rdfs:label ?directorName .         "
				+ "			?genreID rdfs:label ?genre .         "
				+ "			?prodComp rdfs:label ?productionCompany .         "
				+ "			?mainSubjectID rdfs:label ?mainSubject .     " + "			} " + "	} "
				+ "GROUP BY ?title ORDER BY DESC(?year) LIMIT 50	";
		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
		org.apache.jena.query.ResultSet results = qe.execSelect();
		ResultSetFormatter.out(System.out, results, query);

		while (results.hasNext()) {
			QuerySolution querySolution = results.next();

			String title = querySolution.get("title").toString();
			String duration = querySolution.get("duration").toString();

			System.out.println(title);
			System.out.println(duration);

		}

	}

	/**
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