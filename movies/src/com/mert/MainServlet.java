package com.mert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
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
			sql = "CREATE TABLE `movies` (  `movie_id` int(10) unsigned NOT NULL AUTO_INCREMENT,  `title` varchar(255) NOT NULL, `score` varchar(50), `publicationDate` int(10),  `duration` int(10),  `instanceOf`   varchar(255),  `director`  varchar(50),  `castMember` varchar(50),  `producer` varchar(50),  `productionCompany` varchar(50),  `genre` varchar(50),  `mainSubject` varchar(50),  `awardReceived` varchar(50),  PRIMARY KEY (`movie_id`) ) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;";
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
	public void updateMoviesTable() {
		String queryString = "";
		try {
			Query query = QueryFactory.create(queryString);

			// Execute the query and obtain results
			QueryExecution qe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
			org.apache.jena.query.ResultSet results = qe.execSelect();

			while (results.hasNext()) {
				Map<String, String> columns = new HashMap<String, String>();
				QuerySolution querySolution = results.next();

				String booklabel = querySolution.get("bookLabel").toString();

				System.out.println(querySolution);

			}

		} catch (Exception e) {

		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		updateMoviesTable();

	}

}