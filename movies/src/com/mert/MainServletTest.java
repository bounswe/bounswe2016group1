package com.mert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

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
 * @author Mert
 * Landing page of this web app
 */

public class MainServletTest  {

	@Test 
	/**
	* Test database connector
	*/
	public void connectToDBTest(){
		assertNotNull(MainServlet.connectToDB);
		MainServlet.DB_URL="non_existing_sql_db_url_for_test";
		assertNull(MainServlet.connectToDB();
	}
}