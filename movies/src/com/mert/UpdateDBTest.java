package com.mert;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
import org.apache.jena.query.ResultSetFormatter;

/**
* Tests for UpdateDB class
*/
@Test 
public class UpdateDBTest  {

	public void parseCountTest(){

		assertEquals(parseCount("123^456"),parseCount("123");
		assertEquals(parseCount("67^stringfortest^test"),67);
	}

		public void parseGenreTest(){

		assertEquals(parseGenre("123@456"),parseGenre("123");
		assertEquals(parseGenre("stringfortest^test@asd"),"stringfortest^test");


	}
		public String parseLinkTest(String s) {
		assertEquals(parseLink("asd/ada/test/test2","test2");

		}

}
