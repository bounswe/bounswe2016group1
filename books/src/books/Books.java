package books;

import java.sql.DriverManager;
import java.util.*;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


/**
 * This class is used to fetch books from online wikidata database and to select the ones that satisfies some conditions.
 * Conditions are simply satisfying a given author name, book name and genre.
 * It is also used to save the selected data to a database.
 * 
 * @author Guneykan Ozgul 2012400090.
 *
 */
public class Books {
	  		
	    /**
	     * Table Rows.
	     */
		static ArrayList<Map<String,String>> rows;
        
		/**
		 * Fetches the data from the online database and filter.
		 * @param _author    Author Name of the book.
		 * @param _genre     Genre of the book.
		 * @param _book      Name of the book.
		 * @param _series    Series of the book.
		 * @return           Data in html format.
		 */
		public static String getData(String _author,String _genre,String _book,String _series){
			
			//Convert the author name into suitable format.
		
			String [] authorstring=_author.split(" ");
			_author="";
			for(int i=0;i<authorstring.length;i++){
				String word=authorstring[i];
				String initial=word.charAt(0)+"";
				if(i!=authorstring.length-1)
					_author+=initial.toUpperCase(Locale.ENGLISH)+word.substring(1,word.length()).toLowerCase(Locale.ENGLISH)+" ";
				else
					_author+=initial.toUpperCase(Locale.ENGLISH)+word.substring(1,word.length()).toLowerCase(Locale.ENGLISH);

			}
			System.out.println(_author);
			//Sparql query to fetch books that have given author from wikidata. 
			String queryString="PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
	                "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
	                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
	                "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
	                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
					"SELECT ?book ?bookLabel ?authorLabel ?genre_label ?series_label ?publicationDate\n"+
					"WHERE\n"+
					"{\n"+
						"?author ?label \""+_author+"\"@en .\n"+
						"?book wdt:P31 wd:Q571 .\n"+
						"?book wdt:P50 ?author .\n"+
						"OPTIONAL {\n"+
							"?book wdt:P136 ?genre .\n"+
							"?genre rdfs:label ?genre_label filter (lang(?genre_label) = \"en\").\n"+
						"}\n"+
						"OPTIONAL {\n"+
							"?book wdt:P179 ?series .\n"+
							"?series rdfs:label ?series_label filter (lang(?series_label) = \"en\").\n"+
						"}\n"+
						"OPTIONAL {\n"+
						"	?book wdt:P577 ?publicationDate .\n"+
						"}\n"+
						"SERVICE wikibase:label {\n"+
						"	bd:serviceParam wikibase:language \"en\" .\n"+
						"}\n"+
					"}\n";
			
			
			        //Create Jena Query.
					Query query= QueryFactory.create(queryString);
	
		        	// Execute the query and obtain results
		        	QueryExecution qe =  QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
		        	
		        	
		        	//Result Set.
		        	ResultSet results = qe.execSelect();
		        	
		        	//Initialize table rows.
		        	rows=new ArrayList<Map<String,String>>();
		        	
		        	
		        	
		        	//Fetch the table columns from result set and build table rows.
		        	while(results.hasNext()){
		        		Map<String,String> columns=new HashMap<String,String>();
	        			QuerySolution querySolution=results.next();
	        			String genrelabel="Unknown";
	        			String booklabel="Unknown";
	        			String authorlabel="Unknown";
	        			String publicdate="Unknown";
	        			String series="Unknown";
	        			try{
	        				genrelabel=querySolution.get("genre_label").toString();
	        				genrelabel=genrelabel.substring(0, genrelabel.indexOf("@"));
	        			}	
	        			catch(Exception e){}	
		        		
	        			try{
	        			     booklabel=querySolution.get("bookLabel").toString();
		        			 booklabel=booklabel.substring(0, booklabel.indexOf("@"));
	        			}
	        			catch(Exception e){}	

	        			try{
		        			authorlabel=querySolution.get("authorLabel").toString();
		        			authorlabel=authorlabel.substring(0, authorlabel.indexOf("@"));
	        			}	 
	        			catch(Exception e){}	
	 
	        			try{
		        			publicdate=querySolution.get("publicationDate").toString();
		        			publicdate=publicdate.substring(0, publicdate.indexOf("T"));
	        			}	        			
	        			catch(Exception e){}	

	        			try{
		        			series=querySolution.get("series_label").toString();
		        			series=series.substring(0, series.indexOf("@"));
	        			}
	        			catch(Exception e){}	

		        			columns.put("book", booklabel);
	        				columns.put("genre", genrelabel);
		        			columns.put("author", authorlabel);
		        			columns.put("date", publicdate);
		        			columns.put("series", series);
		        			rows.add(columns);
		        			System.out.println(querySolution);	        			
		        	}
		        	
		        	
		        	//Filter the table according to book name.
		        	rows=filter("book",_book,rows);  
		        	
		        	//Filter the table according to genre.
		        	rows=filter("genre",_genre,rows);

		        	
		        	//Filter the table according to series.
		        	rows=filter("series",_series,rows);
	
		        	//Create html table from the data.
		        	String result="";
		        	result+="<table>";
		        	String blank="<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"; 
		        	result+="<tr><td>"+"Select\t"+"</td>"+blank+"<td>"+"Book-Name"+blank+"</td><td>"+
		        	"Author"+blank+"</td><td>"+"Genre"+blank+"</td><td>"+"Publication Date"+blank+"</td><td>"+"Series"+
		        			blank+"</td></tr>";
		        	for(int i=0;i<rows.size();i++){
		        		Map<String,String> column=rows.get(i);
		        		String book_name=column.get("book");
		        		String genre=column.get("genre");
		        		String author=column.get("author");
		        		String date=column.get("date");
		        		String series=column.get("series");
                        result+="<tr><td>"+"<input type=\"checkbox\" name=\"id\" value=\""+i+"\">"+ "</tr></td>";
		        		result+=blank+"<td>"+book_name+"</td>"+blank+"<td>"+
		    		        	author+"</td>"+blank+"<td>"+genre+"</td>"+blank+"<td>"+date+"</td>"+
		        				blank+"<td>"+series+"</td>";
		
   
		        	}
		        	
		        	
		        	
		        	result+="</table>";
		        	return result;
			
		        
	
		        	
		} 
		
		/**
		 * Selects the data that have given column value in given column name.
		 * @param c_name   Column Name
		 * @param c_value   Column Value
		 * @param list   Data to be filtered.
		 * @return       Filtered data.
		 */
		private static ArrayList<Map<String,String>> filter(String c_name,String c_value,ArrayList<Map<String,String>> list){
			
			//If the user does not specify do not filter.
			if(c_value.equals("")){
				return list;
				
			}else{
				//If the user specifies query term, filter it.
				ArrayList<Map<String,String>> rlist=new ArrayList<Map<String,String>>();
				for(int i=0;i<list.size();i++){
					Map<String,String> temp=list.get(i);
					try{
						if(temp.get(c_name).toLowerCase(Locale.ENGLISH).contains(c_value.toLowerCase(Locale.ENGLISH))){
							rlist.add(temp);
						}
						
					}catch(Exception e){
						System.out.println("No such field");
						
					}
					
					
				}
					
					return rlist;
			
				
			}
			
			
	}
		
		public static void saveData(String select[]){
		
			for(int i=0;i<rows.size();i++){
				for(int j=0;j<select.length;j++){
					if(Integer.parseInt(select[j])==i){
						DB.add(rows.get(i).get("book"), rows.get(i).get("author"), rows.get(i).get("date"), rows.get(i).get("series"));
						//Write to database.
						break;
					}	
				
				}
			}
			
			
		}
		

}
