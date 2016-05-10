package getter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.CountryList;
import model.CountryModel;

public class CountryGetter {
	
	static ArrayList<CountryModel> countries = new ArrayList<CountryModel>();
	static HashMap<String, CountryModel> countryMap = new HashMap<String, CountryModel>();
	
	public static ArrayList<CountryModel> show() throws IOException, ParserConfigurationException, SAXException{
		getAll();
		return getOthers();
	}

	public static void getAll() throws IOException, ParserConfigurationException, SAXException{
		String query = "SELECT ?country ?capital ?continent WHERE {"+
				"?c wdt:P31 wd:Q6256 . ?c wdt:P36 ?city . ?c wdt:P30 ?ct ."+
				"OPTIONAL { ?c rdfs:label ?country filter (lang(?country) = \"en\") . }"+
				"OPTIONAL { ?city rdfs:label ?capital filter (lang(?capital) = \"en\"). }"+
				"OPTIONAL { ?ct rdfs:label ?continent filter (lang(?continent) = \"en\"). }}";
		String urlName = "https://query.wikidata.org/sparql?query=" + URLEncoder.encode(query, "utf-8");
		URL url = new URL(urlName);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputLine;
		String results="";
		while ((inputLine = in.readLine()) != null){
			results+=inputLine+"\n";
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		ByteArrayInputStream stream = new ByteArrayInputStream(results.getBytes());
		Document doc = builder.parse(stream);
		NodeList nodeList = doc.getElementsByTagName("result");
		for(int i=0; i<nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			Element element = (Element) node;
			NodeList fields = element.getElementsByTagName("literal");
			CountryModel country = new CountryModel(
					fields.item(0).getTextContent(),
					fields.item(1).getTextContent(),
					fields.item(2).getTextContent());
			countries.add(country);
			countryMap.put(country.getName(), country);
		}
		in.close();
		
	}
	
	public static ArrayList<CountryModel> getByName(String[] nameList){
		ArrayList<CountryModel> list = new ArrayList<CountryModel>();
		for(int i=0; i<nameList.length; i++){
			CountryModel temp = countryMap.get(nameList[i]);
			list.add(temp);
			for(int j=0; j<countries.size(); j++){
				if(temp.getName().equals(countries.get(j).getName())){
					countries.remove(j);
					j--;
				}
			}
		}
		return list;
	}
	
	public static ArrayList<CountryModel> getOthers(){
		return countries;
	}
}
