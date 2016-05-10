package com.mert.test;

import static org.junit.Assert.*;

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
import org.junit.Test;

import com.mert.UpdateDB;

/**
 * Tests for UpdateDB class
 */

public class UpdateDBTest {

	@Test
	public void parseCountTest() {

		assertEquals(UpdateDB.parseCount("123^456"), UpdateDB.parseCount("123"));
		assertEquals(UpdateDB.parseCount("67^stringfortest^test"), 67);
	}

	@Test
	public void parseGenreTest() {

		assertEquals(UpdateDB.parseGenre("123@456"), UpdateDB.parseGenre("123"));
		assertEquals(UpdateDB.parseGenre("stringfortest^test@asd"), "stringfortest^test");

	}

	@Test
	public void parseLinkTest(String s) {
		assertEquals((UpdateDB.parseLink("asd/ada/test/test2")), "test2");

	}

}
