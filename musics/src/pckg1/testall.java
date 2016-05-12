package pckg1;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class testall {

	@Test
	public void connecttest() throws ClassNotFoundException, SQLException {
		MainServlet.conn = null;
		functions.connect();
		assertNotNull(MainServlet.conn);

	}
	
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

}
