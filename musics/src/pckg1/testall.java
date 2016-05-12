package pckg1;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class testall {

	/**
	 * This test sets conn to null as a first task.
	 * Then it runs connect method to setup the connection with database.
	 * It checks whether conn is null or not.
	 * Morover, the test checks that can we run a query after this connection or not by checking the result of the query.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void connecttest() throws ClassNotFoundException, SQLException {
		MainServlet.conn = null;
		functions.connect();
		assertNotNull(MainServlet.conn);

		MainServlet.query = "select * from db";
		functions.runnerQuery();
		assertNotNull(MainServlet.result);
	}
	
	/**
	 * Note that there is a record with id field '53' and f1 field 'Imagine Dragons'
	 * For this test, I set a query which search for a record 
	 * and run the runnerQuery method for the execution of the query.
	 * To check this method, I control the id and f1 fields of the resulting record.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void runnerquerytest() throws SQLException, ClassNotFoundException{
		functions.connect();
		MainServlet.query = "select * from db where id ='53'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("id"),"53");
			assertEquals(MainServlet.result.getString("f1"),"Imagine Dragons");
		}
	}
	
	/**
	 * Note that there is a record with id field '53' and f1 field 'Imagine Dragons' and
	 * this record's f1 field is also unique in addition to id. (This is known for this test.)
	 * For this test, I set a query for updating the id of this record to 'deneme'
	 * and run the runnerUpdate method for the execution of the query.
	 * Then I searched for the record whose f1 field is 'Imagine Dragons',
	 * so that I could control this resulting record's id field is 'deneme' or not.
	 * After that I set another query that sets the records id back to '53' and 
	 * run the runnerUpdate method. Then I searched for the record whose f1 field
	 * is 'Imagine Dragons' again and checked that whether id field '53' or not.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void runnerupdatetest() throws ClassNotFoundException, SQLException{
		functions.connect();
		MainServlet.query = "update db set id='deneme' where f1='Imagine Dragons'";
		functions.runnerUpdate();
		MainServlet.query = "select * from db where f1 ='Imagine Dragons'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("id"),"deneme");
		}
		MainServlet.query = "update db set id='53' where f1='Imagine Dragons'";
		functions.runnerUpdate();
		MainServlet.query = "select * from db where f1 ='Imagine Dragons'";
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("id"),"53");
		}
			
		
	}
	
	/**
	 * Note that there is a record with id field '53' and f1 field 'Imagine Dragons' and
	 * this record's f1 field is also unique in addition to id. (This is known for this test.)
	 * Note that there is a record with id field '109' and f1 feild 'Greeeen' and
	 * this record's f1 field is also unique in additon to id. (This is known for this test.)
	 * For this test, I set matchno field of the record whose f1 field is 'Imagine Dragons' to '37'.
	 * Then I searched for this record and checked whether matchno field is '37' or not.
	 * After that, I set matchno field of the record whose f1 field is 'Greeeen' to '46'.
	 * Then I searched for this record and checked whether matchno field is '46' or not.
	 * Finally I run the resetmark method.
	 * Then I searched for the records whose f1 fields are 'Imagine Dragons' and 'Greeeen'. 
	 * And I checked one by one that whether these records matchno fields are 0 or not. 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void resetmarkertest() throws ClassNotFoundException, SQLException{
		functions.connect();
		
		MainServlet.query = "update db set matchno='37' where f1='Imagine Dragons'";
		functions.runnerUpdate();
		MainServlet.query = "select * from db where f1 ='Imagine Dragons'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"37");
		}
		
		MainServlet.query = "update db set matchno='46' where f1='Greeeen'";
		functions.runnerUpdate();
		MainServlet.query = "select * from db where f1 ='Greeeen'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"46");
		}
		
		functions.resetmark();
		
		MainServlet.query = "select * from db where f1 ='Imagine Dragons'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"0");
		}
		
		MainServlet.query = "select * from db where f1 ='Greeeen'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"0");
		}
		
	}

	/**
	 * Note that there is a record with id field '53' and f1 field 'Imagine Dragons' and
	 * this record's f1 field is also unique in addition to id. (This is known for this test.)
	 * For this test, I run resetmark method before I start.
	 * I searched for the record whose f1 field is 'Imagine Dragons' and checked whether its matchno field is 0 or not.
	 * Then I run marker method with 'Dragons' keyword. (Note that 'Dragons' keyword is only contained by one record in the database. )	 
	 * I sorted the table according to matchno field in decreasing order.
	 * I checked that the first record of the resulting records has matchno field with value '1' or not.
	 * Then I checked that the second record of the resulting records has matchno field with value '0' or not.
	 * Since there is only one record containing 'Dragons' keyword in it, there must be a record with matchno field with value '1'
	 * and the next highest matchno value must be '0' in order to prove that.
	 * Moreover, since we know the id of the record must be '53', I searched for this record and
	 * checked whether matchno value is '1' or not.
	 * Then as a postcondition I run resetmark again.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void markertest() throws ClassNotFoundException, SQLException{
		functions.connect();

		functions.resetmark();
		
		MainServlet.query = "select * from db where f1='Imagine Dragons'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"0");
		}
		
		functions.marker("Dragons");
		
		MainServlet.query = "select * from db order by matchno desc";
		functions.runnerQuery();
		
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"1");
		}
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"0");
		}
		
		MainServlet.query = "select * from db where id='53'";
		functions.runnerQuery();
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("matchno"),"1");
		}
		
		functions.resetmark();
		
	}

	/**
	 * This is a test for simple select query which is done by 'select' method in functions class.
	 * This query only has 'select arg1 from arg2' in it.
	 * Note that it is guaranteed that the first 20 records of db table has id values from 1 to 20 in order.
	 * I run the select method for taking every record of db table.
	 * For the first 20 records, I checked that their id fields are as expected or not.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void selectwithoutwheretest() throws ClassNotFoundException, SQLException{
		functions.connect();
		
		functions.select("*", "db");
		
		for(int i = 1 ; i <= 20 ; i++){
			MainServlet.result.next();
			assertEquals(MainServlet.result.getString("id"),""+i);
		}
	}
	
	/**
	 * Note that there is a record with id field '53' and f1 field 'Imagine Dragons' and
	 * this record's f1 field is also unique in addition to id. (This is known for this test.)
	 * This is also a test for simple select query which is done by 'select' method in functions class.
	 * This query has 'select arg1 from arg2 where arg3' sequence in it.
	 * As arguments I give '*' , 'db' and 'f1=Imagine Dragons'. 
	 * So if the select function works fine, the result should contain only one record in it and
	 * this record must have id value '53'.
	 * I checked whether id values match with each other or not.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void selectwithwheretest() throws ClassNotFoundException, SQLException{
		functions.connect();
		
		functions.select("*", "db", "f1='Imagine Dragons'");
		
		if(MainServlet.result.next()){
			assertEquals(MainServlet.result.getString("id"),""+53);
		}
	}
}
