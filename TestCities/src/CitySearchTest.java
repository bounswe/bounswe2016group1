import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
/**
 * Unit tests for CitySearch.
 * @author KaanBulut
 *
 */
public class CitySearchTest {

	/** Tests for a query for the city Istanbul.
	 */
	@Test
	public void runTestQuery()
	{
		CitySearch searcher = new CitySearch();
		String query = "SELECT ?city ?cityLabel WHERE {  ?city wdt:P31 wd:Q515. FILTER regex(?cityLabel, \"ist\", \"i\")."
				+ "SERVICE wikibase:label { bd:serviceParam wikibase:language 'en'. ?city rdfs:label ?cityLabel.}}";
		ArrayList<HashMap<String, String>> results = searcher.runQuery(query);
		assert(results.size()==1);
		HashMap<String, String> onlyResult = results.get(0);
		assert(onlyResult.get("cityLabel").equalsIgnoreCase("Istanbul"));
		assert(onlyResult.get("city").equalsIgnoreCase("https://www.wikidata.org/wiki/Q406"));
	}
	
	/** Tests for an empty query.
	 */
	@Test
	public void runEmptyQuery()
	{
		CitySearch searcher = new CitySearch();
		String query = "SELECT ?city ?cityLabel WHERE {  ?city wdt:P31 wd:Q515. FILTER regex(?cityLabel, \"Mordor\", \"i\")."
				+ "SERVICE wikibase:label { bd:serviceParam wikibase:language 'en'. ?city rdfs:label ?cityLabel.}}";
		ArrayList<HashMap<String, String>> results = searcher.runQuery(query);
		assert(results.size()==0);
	}
}
