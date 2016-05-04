<%@ page import="cities.Cities" %>

<html>
<head><title>Hello World</title></head>
<body>

<table border="1">
  <%
	out.print(cities.Cities.getTableRows());
	%>
</table>
</body>
</html>