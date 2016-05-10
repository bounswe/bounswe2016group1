package com.mert.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.mert.MainServlet;

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

		assertNotNull(MainServlet.connectToDB());
		MainServlet.DB_URL="non_existing_sql_db_url_for_test";
		assertNull(MainServlet.connectToDB());
	}
}