package books;

import java.util.*;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class Books {
		static ArrayList<Map<String,String>> rows;

		public static String getData(){
			
			
			String queryString="PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
	                "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
	                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
	                "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
	                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#/>\n"+
					"SELECT ?book ?bookLabel ?authorLabel ?genre_label ?series_label ?publicationDate\n"+
					"WHERE\n"+
					"{\n"+
						"?author ?label \"George Orwell\"@en .\n"+
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
		        	Query query = QueryFactory.create(queryString);

		        	// Execute the query and obtain results
		        	QueryExecution qe =  QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
		        	ResultSet results = qe.execSelect();
		        	rows=new ArrayList<Map<String,String>>();
		        	
		        	while(results.hasNext()){
		        		Map<String,String> columns=new HashMap<String,String>();
	        			QuerySolution querySolution=results.next();
		        		try{
		        			String booklabel=querySolution.get("bookLabel").toString();
		        			booklabel=booklabel.substring(0, booklabel.lastIndexOf("@"));
		        		//	String genrelabel=querySolution.get("genre_label").toString();
		        		//	genrelabel=genrelabel.substring(0, genrelabel.lastIndexOf("@"));

		        			String authorlabel=querySolution.get("authorLabel").toString();
		        			authorlabel=authorlabel.substring(0, authorlabel.lastIndexOf("@"));

		        			String publicdate=querySolution.get("publicationDate").toString();
		        			publicdate=publicdate.substring(0, publicdate.lastIndexOf("^^"));

		        			columns.put("book", booklabel);
		        			//columns.put("genre", genrelabel);
		        			columns.put("author", authorlabel);
		        			columns.put("date", publicdate);
		        			rows.add(columns);
		        			
		        		}catch(Exception e){
		        			
		        		}
	        			System.out.println(querySolution);

		        		
		        	}
		        	
		        	
		        	
		        	String result="";
		        	result+="<table>";
		        	result+="<tr><td>"+"Select"+"</td><td>"+"Book-Name"+"</td><td>"+
		        	"Author"+"</td><td>"+"Genre"+"</td><td>"+"Publication Date"+"</td></tr>";
		        	for(int i=0;i<rows.size();i++){
		        		Map<String,String> column=rows.get(i);
		        		String book_name=column.get("book");
		        		String genre=column.get("genre");
		        		String author=column.get("author");
		        		String date=column.get("date");
                        result+="<tr><td>"+"<input type=\"checkbox\" name=\"id\" value=\""+i+"\">"+ "</tr></td>";
		        		result+="<td>"+book_name+"</td><td>"+
		    		        	author+"</td><td>"+genre+"</td><td>"+date+"</td>";
		
   
		        	}
		        	
		        	
		        	
		        	result+="</table>";
		        	
		        	
		        	
		        	
		        	
		        	return result;
			
		        
		        	
		        	
		} 
		
		public static void writeData(String select[]){
		
			for(int i=0;i<rows.size();i++){
				for(int j=0;j<select.length;j++){
					if(Integer.parseInt(select[j])==i){
						System.out.println(rows.get(i).get("book"));
						//Write to database.
						break;
					}	
				
				}
			}
			
			
		}
		

}
