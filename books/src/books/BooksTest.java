package books;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.Map;

public class BooksTest {

	@Test
	public void testGetData() {
		String authorName="TolkIen";
		String tolkienBooks=Books.getData(authorName,"","","");
		assert(tolkienBooks.contains("Lord Of The Rings"));
		
		
		
		
	}

	@Test
	public void testSaveData() {
	}

}
