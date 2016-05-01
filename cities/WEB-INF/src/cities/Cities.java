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
	public static ArrayList<HashMap<String, String>> getString() {
		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
		String str = "SELECT ?city ?cityLabel ?countryLabel ?population " + "WHERE " + "{ " + "?city wdt:P31 wd:Q515. "
				+ "?city wdt:P17 ?country. " + "?city wdt:P1082 ?population. " + "SERVICE wikibase:label { "
				+ "bd:serviceParam wikibase:language 'en' . " + "} " + "} " + "LIMIT 10 ";
		try {
			String urlString = "https://query.wikidata.org/sparql?query=" + URLEncoder.encode(str, "utf-8");
			URL url = new URL(urlString);

			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String xmlFile = "";

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


	public static String getTableRows() {
		String ret = "";
		ArrayList<HashMap<String, String>> rows = getString();

		HashMap<Integer, String> tableColumn = new HashMap<Integer, String>();
		ret += "<tr>\n";
		ret += "<th>Select</th>\n";

		Set<String> names = rows.get(0).keySet();
		int cnt = 0;
		for (String name : names) {
			tableColumn.put(cnt++, name);
			ret += "<th>" + name + "</th>\n";

		}
		ret += "</tr>\n";

		for (int i = 0; i < rows.size(); i++) {
			ret += "<tr>\n";
			ret += "<td><input type='checkbox' value='" + i + "'></td>\n";
			for (int j = 0; j < tableColumn.size(); j++)
				ret += "<td>" + rows.get(i).get(tableColumn.get(j)) + "</td>\n";
			ret += "</tr>\n";
		}
		return ret;
	}

	public static void main(String[] args) {

	}
}