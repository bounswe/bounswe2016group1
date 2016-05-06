<%@ page import="cities.Cities" %>

<html>
<head><title>Hello World</title></head>
<body>

<%
	out.print(request.getParameter("country"));
%>

<form action="test.jsp" method="GET">
	City: <input type="text" name="country">
	<br />
	Country: <input type="text" name="country">
	<br />
	<input type="text" name="populationLess" /> &le; Population &le; <input type="text" name="populationGreat" />
	<br />
	Sort By: <% out.print(cities.Cities.getOptions()); %>
	<select name="order">
  		<option value="ascending">Ascending</option>
  		<option value="descending">Descending</option>
	</select>
	<input type="text" style="border: 0" type="hidden" name="page">
	<br />
	<input type="submit" value="Filter" />
</form>

<table border="1">
  <%
	out.print(cities.Cities.getTableRows());
	%>
</table>
<%	out.print(cities.Cities.getPageMenu(request.getParameter("page"))); %>

</body>
</html>