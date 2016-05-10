package gamesquery;

import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;




public class VideoGame {
	
	
	
	public VideoGame(String genre, String publisher) throws FileNotFoundException {
		
	org.apache.log4j.BasicConfigurator.configure();


		//query for genre and publisher of video game
	    String sparqlQuery = "" +
				"PREFIX wikibase: <http://wikiba.se/ontology#> \n" +
				"PREFIX wd: <http://www.wikidata.org/entity/> \n" +
				"PREFIX wdt: <http://www.wikidata.org/prop/direct/> \n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
				"SELECT ?h ?video_game ?k ?genre ?m ?publisher\n" +
				"WHERE { \n" +
				"?h wdt:P31 wd:Q7889. \n" +
				"?h wdt:P136 ?k. \n" +
				"?h wdt:P123 ?m. \n" +
				"OPTIONAL { \n" +
				"?h rdfs:label ?video_game . \n" +
				"} \n" +
				"OPTIONAL { \n" +
				"?k rdfs:label ?genre . \n" +
				"} \n" +	
				"OPTIONAL { \n" +
				"?m rdfs:label ?publisher . \n" +
				"} \n" +
				"FILTER ((regex(?genre, \"" + genre + "\")) && lang(?genre) = \"en\" && lang(?video_game) = \"en\"). \n"  +
				"FILTER ((regex(?publisher, \"" + publisher + "\")) && lang(?publisher) = \"en\"). \n" +
				"}LIMIT 20";
	
	    //process query
	    
		Query query = QueryFactory.create(sparqlQuery);		
		QueryExecution qExe = QueryExecutionFactory.sparqlService( "https://query.wikidata.org/sparql", query );
		ResultSet rs = qExe.execSelect();
		List<String> l=rs.getResultVars();
        //ResultSetFormatter.out(System.out, rs, query) ;
		
		//create a new jsp file named select_table.jsp and write to it
		File file = new File("C:/Users/Casper/workspace/Drugs/WebContent/select_table.jsp");
		PrintWriter pw=new PrintWriter(file);
		pw.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%>");
		pw.println("<%@page import=\"org.apache.jasper.servlet.JspServlet\" %>");
		pw.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		pw.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"></head><body>");
		pw.println("<h2>RESULT</h2>");
		
		pw.println("<form name=\"form1\" action=\"save_database.jsp\" method=\"post\">");
		pw.println("<table border=\"2\">");
		pw.println("<tr>");
		for(int i=1;i<l.size();i+=2)
		 pw.println("<th>"+l.get(i)+"</th>");
		pw.println("</tr>");
		pw.println("<tbody>");
		while(rs.hasNext())
		{
		 QuerySolution qs=rs.nextSolution();
		 pw.println("<tr>");
		 
		 String val=qs.get(l.get(1).toString()).toString();
		 val=val.substring(0,val.length()-3);
		 System.out.println(val+"\n");
		 String vall=val;
		 String valll="";

		 for(int i=3;i<l.size();i+=2)
		 {
		 valll=qs.get(l.get(i).toString()).toString();
		 valll=valll.substring(0,valll.length()-3);
		 
		 //store rows of table as a string
		 vall=vall+"*"+valll;
		 }			 
	
		 pw.println("<td><input type=\"checkbox\" name=\"row\" value="+"\""+vall+"\"/>"+val+"</td>");
		 
		 for(int i=3;i<l.size();i+=2)
		 {
		 val=qs.get(l.get(i).toString()).toString();
		 val=val.substring(0,val.length()-3);
		 pw.println("<td>"+val+"</td>");
		 System.out.println(val+"\n");
		 }
		 pw.println("</tr>");
		}
		pw.println("</tbody></table>");
		pw.println("<input type=\"submit\" value=\"submit\"/>");
		pw.println("</form>");
		pw.println("<form name=\"myFormm\" action=\"drugs_sparql.jsp\" method=\"post\">");
		pw.println("<input type=\"submit\" value=\"Go Back To Main Page\"/>");
		pw.println("</form>");
		pw.print("</body></html>");
		pw.close();
	
	}
	

public static void main(String[] args) throws FileNotFoundException {
	org.apache.log4j.BasicConfigurator.configure();

	}

}
