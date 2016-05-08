import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.nio.charset.Charset;
import java.sql.*;

public class CitySearch extends HttpServlet {

	public String[] categories = { "cityLabel", "countryLabel", "population" };
	public String[] links = { "city", "country", "" };
	public static final int MENU_PAGES = 9;
	public static final int ENTRIES_PER_PAGE = 10;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sys";
	static final String TABLE_NAME = "cities";

	
	static final String USER = "root";
	static final String PASS = "1234";

	Connection conn;

	private ArrayList<HashMap<String, String>> runQuery(String query) {
		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
		String xmlFile = "";
		try {
			String urlString = "https://query.wikidata.org/sparql?query=" + URLEncoder.encode(query, "ISO-8859-1");
			URL url = new URL(urlString);

			InputStreamReader is = new InputStreamReader(url.openStream(), "ISO-8859-1");
			BufferedReader in = new BufferedReader(is);

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				xmlFile += inputLine + "\n";
			in.close();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			ByteArrayInputStream stream = new ByteArrayInputStream(xmlFile.getBytes("ISO-8859-1"));

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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rows;
	}

	private String getQueryBody(HttpServletRequest request) {
		String queryBody = "";
		queryBody += "?city wdt:P31 wd:Q515.";
		queryBody += "?city wdt:P17 ?country.";
		queryBody += "?city wdt:P1082 ?population.";
		if (request.getParameter("city") != null && !request.getParameter("city").equals(""))
			queryBody += "FILTER regex(?cityLabel, \"" + request.getParameter("city") + "\", \"i\").";
		if (request.getParameter("country") != null && !request.getParameter("country").equals(""))
			queryBody += "FILTER regex(?countryLabel, \"" + request.getParameter("country") + "\", \"i\").";
		if (request.getParameter("populationLess") != null && !request.getParameter("populationLess").equals(""))
			queryBody += "FILTER(?population>" + request.getParameter("populationLess") + ").";
		if (request.getParameter("populationGreat") != null && !request.getParameter("populationGreat").equals(""))
			queryBody += "FILTER(?population<" + request.getParameter("populationGreat") + ").";
		queryBody += "SERVICE wikibase:label {";
		queryBody += "bd:serviceParam wikibase:language 'en'.";
		queryBody += "?city rdfs:label ?cityLabel .";
		queryBody += "?country rdfs:label ?countryLabel .}";
		return queryBody;
	}

	private ArrayList<HashMap<String, String>> runDataQuery(HttpServletRequest request) {
		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();

		String query = "SELECT ?city ?cityLabel ?country ?countryLabel ?population WHERE { " + getQueryBody(request)
				+ "} ";
		if (request.getParameter("order") != null && request.getParameter("order").equals("ascending")) {
			if (request.getParameter("sort") != null && request.getParameter("sort") != "")
				query += "ORDER BY ASC(?" + request.getParameter("sort") + ") ";
		} else if (request.getParameter("order") != null && request.getParameter("order").equals("descending")) {
			if (request.getParameter("sort") != null && request.getParameter("sort") != "")
				query += "ORDER BY DESC(?" + request.getParameter("sort") + ") ";
		}
		query += "LIMIT " + ENTRIES_PER_PAGE + " ";
		try {
			int pageNumber = Integer.parseInt(request.getParameter("page"));
			query += "OFFSET " + (pageNumber - 1) * ENTRIES_PER_PAGE + " ";
		} catch (Exception e) {
		}

		return runQuery(query);
	}

	public String getOptions(HttpServletRequest request) {
		String optionCode = "";
		optionCode += "<select name=\"sort\">";
		String sortValue = request.getParameter("sort") == null ? "" : request.getParameter("sort");
		for (String name : categories)
			if (sortValue.equals(name))
				optionCode += "<option value=\"" + name + "\" selected=\"selected\">" + name + "</option>";
			else
				optionCode += "<option value=\"" + name + "\">" + name + "</option>";
		optionCode += "</select>";
		return optionCode;
	}

	public String getTable(HttpServletRequest request) {
		String ret = "";
		ArrayList<HashMap<String, String>> rows = runDataQuery(request);

		ret += "<table border=\"1\">";
		ret += "<form action=\"CitySearch\" method=\"POST\" target=\"_blank\">";
		ret += "<tr>\n";
		ret += "<th>Select</th>\n";

		int cnt = 0;
		for (String name : categories) {
			ret += "<th>" + name + "</th>\n";

		}
		ret += "</tr>\n";

		for (int i = 0; i < rows.size(); i++) {
			ret += "<tr>\n";
			ret += "<td><input type='checkbox' name='" + i + "' value='" + rows.get(i).get(links[0]) + "'></td>\n";
			for (int j = 0; j < categories.length; j++)
				if (links[j].equals(""))
					ret += "<td>" + rows.get(i).get(categories[j]) + "</td>\n";
				else
					ret += "<td><a href=\"" + rows.get(i).get(links[j]) + "\">" + rows.get(i).get(categories[j])
							+ "</a></td>\n";
			ret += "</tr>\n";
		}
		ret += "<tr><td><input type=\"submit\" value=\"Save\" /></td></tr>";
		ret += "</form>";
		ret += "</table>";
		return ret;
	}

	public String getPageMenu(HttpServletRequest request) {
		int pageNumber = 1;

		try {
			pageNumber = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
		}

		String query = "SELECT (count(?city) as ?count) WHERE {" + getQueryBody(request) + "}";

		ArrayList<HashMap<String, String>> rows = runQuery(query);
		int maxCount = (int) Math.ceil(1.0 * Integer.parseInt(rows.get(0).get("count").trim()) / ENTRIES_PER_PAGE);
		String menuCode = "<table> <tr>\n";

		String url = request.getRequestURI() + "?";
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			String param = e.nextElement();
			if (!param.equals("page"))
				url += param + "=" + request.getParameter(param) + "&";
		}
		url += "page=";

		if (pageNumber > MENU_PAGES / 2 + 1) {
			menuCode += "<td><a href=\"" + url + "1\">1</a></td>\n";
			menuCode += "<td> ... </td>\n";
		}

		for (int i = Math.max(1, pageNumber - MENU_PAGES / 2); i < pageNumber; i++)
			menuCode += "<td><a href=\"" + url + i + "\">" + i + "</a></td>\n";

		menuCode += "<td><b> " + pageNumber + " </b></td>";

		for (int i = pageNumber + 1; i <= Math.min(maxCount, pageNumber + MENU_PAGES / 2); i++)
			menuCode += "<td><a href=\"" + url + i + "\">" + i + "</a></td>\n";

		if (maxCount - pageNumber > MENU_PAGES / 2) {
			menuCode += "<td> ... </td>\n";
			menuCode += "<td><a href=\"" + url + maxCount + "\">" + maxCount + "</a></td>\n";
		}

		menuCode += "</tr> </table>";
		return menuCode;
	}

	public String generateHTML(HttpServletRequest request) {
		String html = "";
		html += "<html>";
		html += "<head><title>Search Cities</title></head>";
		html += "<body>";
		html += "<form action=\"CitySearch\" method=\"GET\">";
		String cityValue = request.getParameter("city") == null ? "" : request.getParameter("city");
		html += "City: <input type=\"text\" name=\"city\" value =\"" + cityValue + "\">";
		html += "<br />";
		String countryValue = request.getParameter("country") == null ? "" : request.getParameter("country");
		html += "Country: <input type=\"text\" name=\"country\" value =\"" + countryValue + "\">";
		html += "<br />";
		String populationLessValue = request.getParameter("populationLess") == null ? ""
				: request.getParameter("populationLess");
		html += "<input type=\"text\" name=\"populationLess\" value =\"" + populationLessValue + "\">";
		html += "&le; Population &le;";
		String populationGreatValue = request.getParameter("populationGreat") == null ? ""
				: request.getParameter("populationGreat");
		html += "<input type=\"text\" name=\"populationGreat\" value =\"" + populationGreatValue + "\">";
		html += "<br />";
		html += "Sort By: " + getOptions(request);
		html += "<select name=\"order\">";
		String orderValue = request.getParameter("order") == null ? "" : request.getParameter("order");
		String selectString = orderValue.equals("ascending") ? "selected=\"selected\"" : "";
		html += "<option value=\"ascending\"" + selectString + ">Ascending</option>";
		selectString = orderValue.equals("descending") ? "selected=\"selected\"" : "";
		html += "<option value=\"descending\"" + selectString + ">Descending</option>";
		html += "</select>";
		html += "<br />";
		html += "<input type=\"submit\" value=\"Filter\" />";
		html += "</form>";
		html += getTable(request);
		html += getPageMenu(request);

		return html;
	}

	public void init() throws ServletException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}catch(Exception e){System.out.println("Failed DB connection" + e.getMessage());}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println(generateHTML(request));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		Enumeration paramNames = request.getParameterNames();

		out.println("<html><body>");
		out.println("<b>URLs saved</b>");
		out.println("<table border=1>");
		out.println("<tr><th>URL</th></tr>");

		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			out.println("<tr><td>" + paramValue + "</td></tr>");
			try{
				Statement stmt = conn.createStatement();
				String sql = "INSERT INTO "+TABLE_NAME+" VALUES (\""+paramValue+"\");";
				stmt.executeUpdate(sql);
				stmt.close();
			}catch(Exception e){System.out.println("Failed Query" + e.getMessage());}
		}
		out.println("</table></body></html>");
	}

	public void destroy() {
		try{
			conn.close();
		}catch(Exception e){System.out.println("Failed Destroy");}
	}
}