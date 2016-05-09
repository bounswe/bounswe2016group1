package books;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;



public class BooksTest {

	@Test
	//Test the getData method by an example of Tolkien.
	public void testGetData() {
		//Test a case where authorName is entered.
		String authorName="TolkIen";
		String tolkienBooks=Books.getData(authorName,"","","");
		assert(tolkienBooks.contains("Lord Of The Rings"));
		
		
	}
	@Test
	//Test the filtering method of books with different kind of filtering options.
	public void fiter() {
		//Test a case where authorName is entered.
		ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
		Map<String, String> columns=new HashMap<String,String>();
		columns.put("book", "The Stranger");
		columns.put("genre", "Unknwn");
		columns.put("author", "Albert Camus");
		columns.put("date", "Unknown");
		columns.put("series", "Unknown");
		list.add(columns);
		
		Map<String, String> columns2=new HashMap<String,String>();
		columns2.put("book", "America");
		columns2.put("genre", "Unknwn");
		columns2.put("author", "Albert Camus");
		columns2.put("date", "Unknown");
		columns2.put("series", "Unknown");
		list.add(columns2);
		
		list=Books.filter("genre","",list);
		assertEquals(list.size(),list.size());
		assertEquals(list.get(0).get("book"),columns.get("book"));
		assertEquals(list.get(0).get("genre"),columns.get("genre"));
		assertEquals(list.get(0).get("series"),columns.get("series"));
		assertEquals(list.get(0).get("series"),columns.get("date"));
		
		
		
		assertEquals(list.get(1).get("book"),columns2.get("book"));
		assertEquals(list.get(1).get("genre"),columns2.get("genre"));
		assertEquals(list.get(1).get("series"),columns2.get("series"));
		assertEquals(list.get(1).get("series"),columns2.get("date"));


		list=Books.filter("book", "The Stranger", list);
		assertEquals(list.size(),1);
		assertEquals(list.get(0).get("book"),"The Stranger");
		assertEquals(list.get(0).get("author"),"Albert Camus");

		
		list=Books.filter("book", "Strange", list);
		
		assertEquals(list.size(),1);
		assertEquals(list.get(0).get("book"),"The Stranger");
		
		
		
		
	}


}
