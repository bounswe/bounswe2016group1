<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
	<table style="width:100%">
	  <tr>
	  	<td></td>
	  	<td>Name</td>
	  	<td>Capital</td>
	  	<td>Continent</td>
	  </tr>
		<form action="/countries/main" method="post" commandName="countryName">
			<c:forEach var="country" items="${countryList}" varStatus="status">
			  <tr>
			  	<td>
			  		<input name="countries" type="checkbox" value="${country.name}">
			  	</td>
			    <td>${country.name}</td>
			    <td>${country.capital}</td>		
			    <td>${country.continent}</td>
			  </tr>
			</c:forEach>
			<input role="button" type="submit" value="Add to DB" />
		</form>
	</table>
</body>
</html>
