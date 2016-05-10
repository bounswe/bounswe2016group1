package com.mert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

/**
 * Queries the wikidata.org to get up-to-date information and stores it in local DB
 * @author Mert
 */
@WebServlet("/UpdateDB")
public class UpdateDB extends HttpServlet {
	
	Connection conn = null;
	Statement stmt = null;
	String sql = null;

	/**
	 * Parses the retrieved "count" string and turns it into the integer before character "^"
	 * @param s String to be parsed
	 * @return count as integer
	 */
	public static int parseCount(String s) {
		int iend = s.indexOf("^");
		String subString = null;
		if (iend != -1)
			subString = s.substring(0, iend);
		return Integer.parseInt(subString);

	}
	/**
	 * Parses the retrieved "genre" string and turns it into the integer before character "@"
	 * @param s String to be parsed
	 * @return genre as string
	 */
	public static String parseGenre(String s) {
		int iend = s.indexOf("@");
		String subString = null;
		if (iend != -1)
			subString = s.substring(0, iend);
		return subString;

	}

	/**
	 * Parses the retrieved "link" string and takes the last ID part
	 * @param s String to be parsed
	 * @return ID as string i.e Q342
	 */
	public static String parseLink(String s) {
		String[] parts = s.split("/");
		String lastWord = parts[parts.length - 1];
		return lastWord;

	}
	public UpdateDB() {

	}

	/**
	 * Deletes all entries from the local DB
	 */
	public void truncateDB() {
		try {
			MainServlet.connectToDB();
			conn = MainServlet.conn;
			stmt = MainServlet.stmt;
			sql = "TRUNCATE `mert`.`movies`;";

			stmt.executeUpdate(sql);

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		ArrayList<Genre> genreList = new ArrayList<Genre>();
		String queryString = "PREFIX bd: <http://www.bigdata.com/rdf#> "
				+ "PREFIX wikibase: <http://wikiba.se/ontology#> "
				+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
				+ "PREFIX wd: <http://www.wikidata.org/entity/> " + "PREFIX p: <http://www.wikidata.org/prop/> "
				+ "PREFIX ps: <http://www.wikidata.org/prop/statement/> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "SELECT (sample(?labelX) as ?genre) (count(distinct ?movie) as ?count) ?genreID " + "WHERE " + "{"
				+ "  ?award wdt:P31 wd:Q19020 . " + "  ?movie wdt:P31 wd:Q11424 . " + "  ?movie p:P166 ?awardStat . "
				+ "  ?awardStat ps:P166 ?award . " + "  ?movie wdt:P921 ?genreID . " + "	SERVICE wikibase:label { "
				+ "		bd:serviceParam wikibase:language \"en\" ." + "        ?genreID rdfs:label ?labelX" + "	}"
				+ "} GROUP BY ?genreID";
		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
		org.apache.jena.query.ResultSet results = qe.execSelect();

		while (results.hasNext()) {
			QuerySolution querySolution = results.next();

			Genre g = new Genre(parseGenre(querySolution.get("genre").toString()),
					parseCount(querySolution.get("count").toString()),parseLink(querySolution.get("genreID").toString()));
			genreList.add(g);
		}
		truncateDB(); //flush db first
		try {
			
			MainServlet.connectToDB();
			conn = MainServlet.conn;
			stmt = MainServlet.stmt;
			/**
			 * Insert into movies into the local fresh db
			 */
			while (!genreList.isEmpty()) {
				sql = "INSERT INTO `mert`.`movies` (`genre`,`count`,`genreID`) "
						+ "VALUES(\"" + genreList.get(0).genre + "\","+ genreList.get(0).count +",\"" + genreList.get(0).genreID + "\");";
				genreList.remove(0);
				stmt.executeUpdate(sql);
			}
		} catch (Exception e) {	
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Movies</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("Update failed");
			out.println("</body>");
			out.println("</html>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
			e.printStackTrace();
		}
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Movies</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Update successful");
		out.println("</body>");
		out.println("</html>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
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
