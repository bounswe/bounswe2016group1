package cities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.algebra.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jdk.internal.org.xml.sax.InputSource;
import sun.net.www.URLConnection;

public class Cities {
	
	public static String[] categories = {"cityLabel", "countryLabel", "population"};
	public static String[] links = {"city", "country", ""};
	public static final int MENU_PAGES = 9;
	public static final int ENTRIES_PER_PAGE = 10;
	
	
	private static ArrayList<HashMap<String, String>> runQuery(String query){
		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
		String xmlFile = "";
		try {
			String urlString = "https://query.wikidata.org/sparql?query=" + URLEncoder.encode(query, "utf-8");
			URL url = new URL(urlString);

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				xmlFile += inputLine + "\n";
			in.close();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			ByteArrayInputStream stream = new ByteArrayInputStream(xmlFile.getBytes());
			Document doc = builder.parse(stream);

			NodeList nList = doc.getElementsByTagName("result");
			for (int i = 0; i < nList.getLength(); i++) {
				rows.add(new HashMap<String, String>());
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				NodeList childNodes = eElement.getElementsByTagName("binding");
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node child = childNodes.item(j);
					String label = child.getAttributes().item(0).getTextContent();
					String value = child.getTextContent();
					rows.get(i).put(label, value);
				}
			}
		} catch (Exception e) {}
		return rows;
	}
	
	public static ArrayList<HashMap<String, String>> getString() {
		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
		String query = "SELECT ?city ?cityLabel ?country ?countryLabel ?population " + "WHERE " + "{ " + "?city wdt:P31 wd:Q515. "
				+ "?city wdt:P17 ?country. " + "?city wdt:P1082 ?population. " + "SERVICE wikibase:label { "
				+ "bd:serviceParam wikibase:language 'en' . " + "} " + "} " + "LIMIT 10 ";
		return runQuery(query);
	}

	public static String getOptions(){
		String optionCode = "";
		optionCode += "<select name=\"sort\">";
		for(String name : categories) 
			optionCode+="<option value=\""+name+"\">"+name+"</option>";
		optionCode += "</select>";
		return optionCode;
	}

	public static String getTableRows() {
		String ret = "";
		ArrayList<HashMap<String, String>> rows = getString();

		ret += "<tr>\n";
		ret += "<th>Select</th>\n";

		int cnt = 0;
		for (String name : categories) {
			ret += "<th>" + name + "</th>\n";

		}
		ret += "</tr>\n";

		for (int i = 0; i < rows.size(); i++) {
			ret += "<tr>\n";
			ret += "<td><input type='checkbox' value='" + i + "'></td>\n";
			for (int j = 0; j < categories.length; j++)
				if(links[j].equals(""))
					ret += "<td>" + rows.get(i).get(categories[j]) + "</td>\n";
				else
					ret += "<td><a href=\""+rows.get(i).get(links[j])+"\">" + rows.get(i).get(categories[j]) + "</a></td>\n";			
			ret += "</tr>\n";
		}
		return ret;
	}
	
	
	public static String getPageMenu(String page){
		int pageNumber = 1;
		
		try{
			pageNumber = Integer.parseInt(page);
		}catch(Exception e){}
		
		String query = "SELECT (count(?city) as ?count) WHERE { "
				+ "?city wdt:P31 wd:Q515. "
				+ "SERVICE wikibase:label { bd:serviceParam wikibase:language 'en' .} } ";
		ArrayList<HashMap<String, String>> rows = runQuery(query);
		int maxCount = (int)Math.ceil(1.0*Integer.parseInt(rows.get(0).get("count").trim())/ENTRIES_PER_PAGE);
		String menuCode = "<table> <tr>\n";
		
		if(pageNumber > MENU_PAGES/2+1){
			menuCode += "<td> 1 </td>\n";
			menuCode += "<td> ... </td>\n";
		}
		
		for(int i = Math.max(1, pageNumber - MENU_PAGES/2); i < pageNumber; i++)
			menuCode += "<td> " + i + " </td>\n";
		
		menuCode += "<td><b> "+ pageNumber +" </b></td>";
		
		for(int i = pageNumber + 1; i <= Math.min(maxCount, pageNumber + MENU_PAGES/2); i++)
			menuCode += "<td> " + i + " </td>\n";
		
		if(maxCount - pageNumber > MENU_PAGES/2){
			menuCode += "<td> ... </td>\n";
			menuCode += "<td>" + maxCount + "</td>\n";
		}

		menuCode += "</tr> </table>";
		return menuCode;
	}

	public static void main(String[] args) {
	}
}