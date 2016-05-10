Name: Güneykan Özgül
ID:2012400090



                         			CmpE 352 Project ReadMe



Project Description: 
	
	This project aims to create a very simple and primitive book catalog based on the data stored in WikiData. User simply logins to the system with an arbitrary user-name and then in the next page user can search the books  written by a given author satisfying some other query terms such as genre, series and book name. Since all the books consists of a very large data, giving the author name of the book is a required condition to be able to shorten the search time. After user finds the books, s/he can save the selected data in a database and can see the previous saved data by logging to the system with the previous selected user name.


Technical Details:

The project is developed and tested in Eclipse Mars in local computer and test server. To fetch the data, Jena library and following sparql query is used.

			   PREFIX bd: <http://www.bigdata.com/rdf
	                PREFIX wikibase: <http://wikiba.se/ontology#>
	                PREFIX wdt: <http://www.wikidata.org/prop/direct/>
	                PREFIX wd: <http://www.wikidata.org/entity/>
	                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
					SELECT ?book ?bookLabel ?authorLabel ?genre_label ?series_label ?publicationDate
					WHERE{
						?author ?label "_author@en.”
						?book wdt:P31 wd:Q571 .
						?book wdt:P50 ?author .
						OPTIONAL {
							?book wdt:P136 ?genre .
							?genre rdfs:label ?genre_label filter (lang(?genre_label) = "en").
						}
						OPTIONAL {
							?book wdt:P179 ?series.
							?series rdfs:label ?series_label filter (lang(?series_label) = "en").
						}
						OPTIONAL {
							?book wdt:P577 ?publicationDate .\n
						}
						SERVICE wikibase:label
							bd:serviceParam wikibase:language "en" .
						}
					}


where _author is the author name.



Then filterin is done based on give ngenre,series and book name in the Book.class.



Example Data: When user inputs 'Albert Camus' as author and  'novel' as genre the following data is obtained.




Select 

Book-Name 

Author 

Genre 

Publication Date 

Series 



The Stranger

Albert Camus

philosophical novel

1942-01-01

Absurd cycle



The Plague

Albert Camus

novel

1947-06-01

Unknown



The First Man

Albert Camus

autobiographical novel

1994-01-01

Unknown



The Fall

Albert Camus

philosophical novel

1956-01-01

Unknown



A Happy Death

Albert Camus

novel

1971-04-15

Unknown



Or,

When user inputs 'Tolkien' as author and  'fantasy'  and 'Lord Of The Rings' as book name the following data is obtained.


Select 

Book-Name 

Author 

Genre 

Publication Date 

Series 



The Lord of the Rings

J. R. R. Tolkien

fantasy novel

1954-01-01

Unknown




 
