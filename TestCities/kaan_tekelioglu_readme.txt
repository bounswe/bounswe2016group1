Deployment:
1) Compile CitySearch.java with $CATALINA_HOME/lib/servlet-api.jar in $CLASSPATH.
2) Copy CitySearch.class to $CATALINA_HOME/webapps/ROOT/WEB-INF/classes
3) Add the following to web.xml in folder $CATALINA_HOME/webapps/ROOT/WEB-INF/:
	<servlet>
		<servlet-name>CitySearch</servlet-name>
		<servlet-class>CitySearch</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>CitySearch</servlet-name>
		<url-pattern>/CitySearch</url-pattern>
	</servlet-mapping>
4) Restart tomcat and connect to http://localhost:8080/CitySearch

Queries:
I have used the wikidata dataset in this assignment. I have chosen cities as my subject, and their countries and populations as attributes. With each http request, I send a query to wikidata with the parameters of the request. Here is an example request, looking for cities with "ist" in its name with population greater than 300,000 , sorted in ascending order by the country name:

SELECT ?city ?cityLabel ?country ?countryLabel ?population WHERE { 
	?city wdt:P31 wd:Q515. 
	?city wdt:P17 ?country. 
	?city wdt:P1082 ?population. 
    FILTER regex(?cityLabel, "ist", "i").
 	FILTER(?population>300000).
	SERVICE wikibase:label { 
		bd:serviceParam wikibase:language 'en' .
      	?city rdfs:label ?cityLabel .
        ?country rdfs:label ?countryLabel .
	} 
} 
ORDER BY ASC(?countryLabel)
LIMIT 10 

The following data is fetched from the wikidata server:
city	cityLabel	country	countryLabel	population
http://www.wikidata.org/entity/Q79990	Christchurch	http://www.wikidata.org/entity/Q664	New Zealand	362000
http://www.wikidata.org/entity/Q406	Istanbul	http://www.wikidata.org/entity/Q43	Turkey	14657434

