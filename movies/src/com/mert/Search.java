package com.mert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
 * Creates an arraylist of arraylists that hold Strings( i.e 2D String holder
 * data structures) a movie subject from db is split into its words and are
 * inserted into the arraylist.
 * 
 * @author Mert
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	Connection conn = null;
	Statement stmt = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Search() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		MainServlet.connectToDB();
		conn = MainServlet.conn;
		stmt = MainServlet.stmt;
		ArrayList<ArrayList<String>> db = new ArrayList<ArrayList<String>>();
		ArrayList<String> keywords;
		String searchTerm = request.getParameter("pid");
		String sql = "SELECT `movies`.`genreID` ,  `movies`.`genre`  FROM `mert`.`movies`";
		try {
			stmt.executeQuery(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				keywords = new ArrayList<String>(Arrays.asList(rs.getString("genre").split(" ")));
				keywords.add(0, rs.getString("genreID"));
				db.add(keywords);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> foundAt = searchArrayList(db, searchTerm);
		if (foundAt == null) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Movies</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("couldnt find any subjects related to searched term");
			out.println("</body>");
			out.println("</html>");
			return;
		}  
			

		TreeMap<String, Integer> sorted_map;
		sorted_map = sortResults(foundAt);
		displayResults(sorted_map, out);
		MainServlet.closeDBConnections();

	}

	public TreeMap<String, Integer> sortResults(ArrayList<String> foundAt) {
		MainServlet.connectToDB();
		conn = MainServlet.conn;
		stmt = MainServlet.stmt;

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Comp genreComp = new Comp(map);
		while (!foundAt.isEmpty()) {
			String sql = "SELECT `movies`.`count`   FROM `mert`.`movies` WHERE `movies`.`genreID`=\"" + foundAt.get(0)+ "\"";
			try {
				stmt.executeQuery(sql);
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					map.put(foundAt.get(0), rs.getInt("count"));
				}
				foundAt.remove(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(genreComp);
		sortedMap.putAll(map);
		
		MainServlet.closeDBConnections();
		return sortedMap;
	}

	/**
	 * The function then compares each word for a match with the searched term.
	 * If a match is found its main subject ID is saved to results. It is case
	 * sensitive and can not handle small typos.
	 * 
	 * @param db
	 *            Named as it holds the info from the database to be searched.
	 * @param searchTerm
	 *            Entered
	 * @return list of IDs of subjects
	 */
	public ArrayList<String> searchArrayList(ArrayList<ArrayList<String>> db, String searchTerm) {
		String ID = null;
		ArrayList<String> results = new ArrayList<String>();
		while (!db.isEmpty()) {
			if (!db.get(0).isEmpty()) {
				ID = db.get(0).get(0);
				db.get(0).remove(0);
			}
			while (!db.get(0).isEmpty()) {
				if (searchTerm.equals(db.get(0).get(0))) {
					results.add(ID);
				}
				db.get(0).remove(0);
			}
			db.remove(0);
		}

		return results;

	}

	/**
	 * Receives results of search from the searchArrayList function as an
	 * ArrayList. Queries wikidata.org for movies with the related Main Subjects
	 * that were found in search. Lastly prints results of wikidata query to
	 * html.
	 * 
	 * @param foundAt
	 *            List of search results
	 * @param out
	 *            PrintWriter object used to print to html.
	 */
	public void displayResults(TreeMap<String, Integer> foundAt, PrintWriter out) {
		

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
		ArrayList<String> aList = new ArrayList<>(foundAt.keySet()); 
		while (!aList.isEmpty()) {
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
					+ "  				?awardWork wdt:P921 ?genreID . filter (?genreID=wd:" +  aList.get(0) + ")"
					+ "	SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\". }			" + "	 }";
			Query query = QueryFactory.create(queryString);
			QueryExecution qe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
			org.apache.jena.query.ResultSet results = qe.execSelect();
			aList.remove(0);
			/*
			 * Take all the query results and print them to html.
			 */
			while (results.hasNext()) {
				QuerySolution querySolution = results.next();
				out.println("<tr>");
				out.print("<td>" + UpdateDB.parseGenre(querySolution.get("awardWorkLabel").toString()) + "</td>");
				out.print("<td>" + UpdateDB.parseGenre(querySolution.get("genreIDLabel").toString()) + "</td>");
				out.println("</tr>");
			}
			
		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");

	}
	/**
	 * 
	 * @author Mert
	 *
	 */
	class Comp implements Comparator<String> {
		Map<String, Integer> base;

		public Comp(Map<String, Integer> base) {
			this.base = base;
		}

		public int compare(String a, String b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
