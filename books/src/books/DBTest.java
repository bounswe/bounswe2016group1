package books;

import static org.junit.Assert.*;

import org.junit.Test;

public class DBTest {

	@Test
	public void testCreateDatabase() {
		
		int success=DB.createDatabase();
		//assertEquals(success,1);
		
	}

	@Test
	public void testConnect() {
		assertNotNull(DB.connect());
	}

	@Test
	public void testAddTable() {
		//Assuming that the table does not exist.
		assertNotNull(DB.addTable("test"));
	
	}

	@Test
	public void testDeleteAll() {
	
		int contains=1;
		DB.add("testuser","test","test","test","test","test");
		DB.deleteAll("testuser");
		if(DB.printAll("testuser").contains("test")){contains=0;}
		assertEquals(contains,1);
	
	}

	@Test
	public void testPrintAll() {
		int contains=0;
		DB.add("testuser","test","test","test","test","test");
		if(DB.printAll("testuser").contains("test")){ contains=1; }
		assertEquals(contains,1);
		
	}

	@Test
	public void testAdd() {
		int contains=0;
		DB.add("testuser","newbook","test","test","test","test");
		if(DB.printAll("testuser").contains("newbook")){contains=1;}
		assertEquals(contains,1);
		
	
	}

}
