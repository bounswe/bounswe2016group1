package com.mert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
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
 * Receives main subjects IDS from local DB and searches wikidata for movies that won oscar with that subject. 
 * Prints resulting movies and their subjects as a table
 *  @author Mert
 */
@WebServlet("/ShowMovies")
public class ShowMovies extends HttpServlet {

	Connection conn = null;
	Statement stmt = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowMovies() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MainServlet.connectToDB();
		conn=MainServlet.conn;
		stmt=MainServlet.stmt;
		ArrayList<String> genresToList = new ArrayList<String>();
		String sql = "SELECT `entries`.`genreID` FROM `sakila`.`entries`";
		
		try {
			stmt.executeQuery(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				genresToList.add(rs.getString("genreID"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		out.println("<table border=\"1\">");
		out.println("<tr>");
		out.print("<td>Movie Title</td>");
		out.print("<td>Main Subject</td>");
		out.println("</tr>");
		while (!genresToList.isEmpty()) {
			String queryString = "PREFIX bd: <http://www.bigdata.com/rdf#> "
					+ "PREFIX wikibase: <http://wikiba.se/ontology#> "
					+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
					+ "PREFIX wd: <http://www.wikidata.org/entity/> " + "PREFIX p: <http://www.wikidata.org/prop/>"
					+ "PREFIX ps: <http://www.wikidata.org/prop/statement/>"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
					+ "SELECT DISTINCT ?awardWorkLabel  ?genreIDLabel  " + "WHERE  {"
					+ "				?award wdt:P31 wd:Q19020 . 				"
					+ "				?awardWork wdt:P31 wd:Q11424 ." + "  				?awardWork p:P166 ?awardStat ."
					+ "  				?awardStat ps:P166 ?award ."
					+ "  				?awardWork wdt:P921 ?genreID . filter (?genreID=wd:" + genresToList.get(0) + ")"
					+ "	SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\". }			" + "	 }";
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
			org.apache.jena.query.ResultSet results = qe.execSelect();

			while (results.hasNext()) {
				QuerySolution querySolution = results.next();
				out.println("<tr>");
				out.print("<td>" + UpdateDB.parseGenre(querySolution.get("awardWorkLabel").toString()) + "</td>");
				out.print("<td>" + UpdateDB.parseGenre(querySolution.get("genreIDLabel").toString()) + "</td>");
				out.println("</tr>");
			}
			genresToList.remove(0);
		}

		out.println("</table>");
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
